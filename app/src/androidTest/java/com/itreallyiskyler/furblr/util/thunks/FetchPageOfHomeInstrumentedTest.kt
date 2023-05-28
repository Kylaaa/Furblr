package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.testhelpers.enum.EnumMapper
import com.itreallyiskyler.testhelpers.managers.MockNetworkingManager
import com.itreallyiskyler.testhelpers.managers.SingletonInitializer
import com.itreallyiskyler.testhelpers.mocks.MockThumbnail
import com.itreallyiskyler.testhelpers.persistence.DBTestClass
import com.itreallyiskyler.testhelpers.persistence.DBTestUtils
import kotlin.system.measureTimeMillis
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchPageOfHomeInstrumentedTest : DBTestClass() {

    @Before
    fun initManagers() {
        val db = getDB()
        DBTestUtils.populateDB(db)

        val homeIds = db.homePageDao().getHomePagePostIdsByPage()
        val storedPosts = db.viewsDao().getExistingViewsWithIds(homeIds)
        val submissions = storedPosts.map { it->
            MockThumbnail(
                postId = it.id,
                creatorName = it.profileId,
                imageSrc = it.submissionImgUrl ?: "",
                imageHeight = it.submissionImgSizeHeight.toFloat(),
                imageWidth = it.submissionImgSizeWidth.toFloat(),
                title = it.title,
                ageRating = EnumMapper.getByValue<AgeRating>(it.rating.lowercase())
            )
        }.toList()
        val mappedSubmissions = storedPosts.associate { it ->
            Pair(it.id.toLong(), PagePostDetails(
                title = it.title,
                contentUrl = it.contentUrl,
                artist = it.profileId,
                category = EnumMapper.getByValue<PostCategory>(it.category),
                comments = listOf(),
                uploadDate = it.date,
                thumbnailUrl = it.submissionImgUrl,
                gender = EnumMapper.getByValue<PostGender>(it.gender),
                description = it.description,
                favoriteKey = it.favKey,
                hasFavorited = it.hasFavorited,
                kind = EnumMapper.getByValue<PostKind>(it.kind),
                rating = EnumMapper.getByValue<AgeRating>(it.rating.lowercase()),
                size = Pair(it.submissionImgSizeWidth, it.submissionImgSizeHeight),
                tags = listOf(),
                theme = EnumMapper.getByValue<PostTheme>(it.theme),
                totalFavorites = it.favoriteCount,
                totalViews = it.viewCount,
            ))
        }

        val networkManager = object : MockNetworkingManager() {
            override fun requestSubmissions(
                scrollDirection: SubmissionScrollDirection,
                pageSize: Int,
                offsetId: Long?
            ): IRequestAction {
                return object : IRequestAction {
                    override fun fetchContent(): Promise {
                        return Promise.resolve(PageSubmissions(
                            submissions = submissions
                        ))
                    }
                }
            }

            override fun requestView(postId: Long): IRequestAction {
                return object : IRequestAction {
                    override fun fetchContent(): Promise {
                        val storedView = mappedSubmissions[postId]!!
                        return Promise.resolve(storedView)
                    }
                }
            }
        }
        SingletonInitializer.init(networkingManager = networkManager)
    }

    @Test
    fun localFetchResolvesQuickly() {
        // TODO - IMPROVE THE PERFORMANCE OF THIS TEST AS IT FAILS WHEN A DEBUGGER IS ATTACHED.
        val timeMS = measureTimeMillis {
            FetchPageOfHome(
                forceRefresh = false
            )
        }
        val thresholdMS = 100
        Assert.assertTrue("Did not finish in alotted $thresholdMS ms. Task took $timeMS", timeMS <= thresholdMS)
    }

}