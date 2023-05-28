package com.itreallyiskyler.testhelpers.persistence

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.furblr.util.DateFormatter

object DBTestUtils {
    private var nextViewId : Long = 0;
    fun getViewIdRange() : LongRange { return (1 .. nextViewId) }

    fun createView(
        username : String = "testUserA",
        submissionDate : String = DateFormatter.createDate(2000, 1, 1, 1, 1),
        title: String = "testTitle",
        rating : AgeRating = AgeRating.General,
    ) : View {
        nextViewId++

        return View(
            profileId = username,

            id = nextViewId,
            title = title,
            description = "testDescription",
            contentUrl = "https://d.test.com/img/${nextViewId}",

            // Submission Image Data
            submissionImgUrl = "https://test.com/view/${nextViewId}",
            submissionImgSizeWidth = 400,
            submissionImgSizeHeight = 400,

            // Counts
            viewCount = 0,
            commentCount = 0,
            favoriteCount = 0,

            // Favoriting
            favKey = "123456789",
            hasFavorited = false,

            // Post Metadata
            date = submissionDate,
            rating = rating.name,
            kind = PostKind.Image.id,
            category = PostCategory.All.id,
            theme = PostTheme.All.id,
            gender = PostGender.Any.id
        )
    }

    fun createUser(username: String = "testUserA") : User {
        return User(
            username = username,
            nickname = "",
            avatarId = 1,
            description = "",
            dateJoined = DateFormatter.createDate(2000, 1, 1, 1, 1))
    }

    fun createTags(view : View, tags : List<String>) : List<Tag> {
        return tags.map {
            Tag(_parentPost = view.id, _contents = it)
        }
    }

    fun createFeed(views : List<View>, feedId: ContentFeedId) : List<FeedId> {
        return views.map { FeedId.fromPost(feedId, it) }
    }

    fun populateDB(db : AppDatabase) {
        // create some users
        val users = listOf(
            createUser("Argus"),
            createUser("Bethany"),
            createUser("Charles"),
            createUser("Delila"),
            createUser("Ed"),
        )
        users.forEach { db.usersDao().insertOrUpdateUsers(it) }

        // create some posts
        val views = listOf(
            // Argus' content
            createView(users[0].username, DateFormatter.createDate(2000, 1, 2, 1, 1), "Check out my dog"),
            createView(users[0].username, DateFormatter.createDate(2000, 2, 3, 1, 1), "My dog is real cool"),
            createView(users[0].username, DateFormatter.createDate(2000, 3, 4, 1, 1), "My dog can do no wrong"),
            createView(users[0].username, DateFormatter.createDate(2000, 4, 5, 1, 1), "I love my dog"),
            createView(users[0].username, DateFormatter.createDate(2000, 5, 6, 1, 1), "Check out my dog's people-sona"),
            createView(users[0].username, DateFormatter.createDate(2000, 6, 7, 1, 1), "My dog had a puppy",),
            createView(users[0].username, DateFormatter.createDate(2000, 7, 8, 1, 1), "Check out how big my new puppy is"),
            createView(users[0].username, DateFormatter.createDate(2000, 8, 9, 1, 1), "Check out how big my new puppy is"),
            createView(users[0].username, DateFormatter.createDate(2000, 9, 10, 1, 1), "Check out how big my new puppy is"),
            createView(users[0].username, DateFormatter.createDate(2000, 10, 11, 1, 1), "Check out how big my new puppy is"),
            createView(users[0].username, DateFormatter.createDate(2000, 11, 12, 1, 1), "Check out how big my new puppy is"),

            // Bethany's content
            createView(users[1].username, DateFormatter.createDate(2000, 1, 11, 1, 1), "Commissions"),
            createView(users[1].username, DateFormatter.createDate(2000, 2, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 3, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 4, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 5, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 6, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 7, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 8, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 9, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 10, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 11, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2000, 12, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 1, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 2, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 3, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 4, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 5, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 6, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 7, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 8, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 9, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 10, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 11, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2001, 12, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 1, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 2, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 3, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 4, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 5, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 6, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 7, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 8, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 9, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 10, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 11, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2002, 12, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 1, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 2, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 3, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 4, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 5, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 6, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 7, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 8, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 9, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 10, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 11, 11, 1, 1), "Commission Reminder"),
            createView(users[1].username, DateFormatter.createDate(2003, 12, 11, 1, 1), "Commission Reminder"),

            // Charles' content
            createView(users[2].username, DateFormatter.createDate(2002, 1, 1, 1, 1), "Here's my OC, Bongo", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2002, 6, 1, 1, 1), "Here's my OC, Tango", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2003, 2, 1, 1, 1), "Here's my OC, Congo", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2004, 8, 1, 1, 1), "Here's my OC, Mango", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2005, 6, 1, 1, 1), "Here's my OC, Zingo", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2006, 1, 1, 1, 1), "Here's my OC, Kingo", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2006, 3, 1, 1, 1), "Here's my OC, Bingo", AgeRating.Mature),
            createView(users[2].username, DateFormatter.createDate(2006, 7, 1, 1, 1), "Here's my OC, Dingo", AgeRating.Mature),

            // Delila's content
            createView(users[3].username, DateFormatter.createDate(2000, 2, 1, 1, 1), "Butt Stuff", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 3, 5, 1, 1), "Stutt Buff", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 4, 9, 1, 1), "Bongo Bum", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 7, 19, 1, 1), "Booty Brigade", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 9, 22, 1, 1), "Butt Pirate", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 11, 25, 1, 1), "X Marks the Butt", AgeRating.Adult),
            createView(users[3].username, DateFormatter.createDate(2000, 12, 31, 1, 1), "New Year New Butt", AgeRating.Adult),

            // Ed's content
            createView(users[4].username, DateFormatter.createDate(2000, 5, 5, 1, 1), "Fursuit FYI"),
            createView(users[4].username, DateFormatter.createDate(2001, 2, 2, 1, 1), "Crafting Cues"),
            createView(users[4].username, DateFormatter.createDate(2001, 9, 9, 1, 1), "Glowing Gloves"),
        )
        views.forEach { db.viewsDao().insertOrUpdate(it) }

        // create some tags for those views
        val tags = listOf(
            // Argus' post tags
            createTags(views[0], listOf("dog", "german_shepard")),
            createTags(views[1], listOf("dog", "german_shepard", "radical", "skateboard")),
            createTags(views[2], listOf("dog", "german_shepard", "homicide", "police_chase")),
            createTags(views[3], listOf("dog", "german_shepard", "sleepy", "doggo")),
            createTags(views[4], listOf("dog", "german_shepard", "gijinka")),
            createTags(views[5], listOf("dog", "german_shepard", "puppy")),
            createTags(views[6], listOf("dog", "german_shepard", "puppers")),
            createTags(views[7], listOf("dog", "german_shepard", "puppers")),
            createTags(views[8], listOf("dog", "german_shepard", "puppers")),
            createTags(views[9], listOf("dog", "german_shepard", "puppers")),
            createTags(views[10], listOf("dog", "german_shepard", "puppers")),

            // Bethany's post tags
            createTags(views[11], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[12], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[13], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[14], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[15], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[16], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[17], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[18], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[19], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[20], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[21], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[22], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[23], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[24], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[25], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[26], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[27], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[29], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[30], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[31], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[32], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[33], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[34], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[35], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[36], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[37], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[38], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[39], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[40], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[41], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[42], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[43], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[44], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[45], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[46], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[47], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[48], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[49], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[50], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[51], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[52], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[53], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[54], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[55], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[56], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[57], listOf("commission", "my_artwork", "buy_my_stuff")),
            createTags(views[58], listOf("commission", "my_artwork", "buy_my_stuff")),

            // Charles' content
            createTags(views[59], listOf("my_oc", "bongo")),
            createTags(views[60], listOf("my_oc", "tango")),
            createTags(views[61], listOf("my_oc", "congo")),
            createTags(views[62], listOf("my_oc", "mango")),
            createTags(views[63], listOf("my_oc", "zingo")),
            createTags(views[64], listOf("my_oc", "kingo")),
            createTags(views[65], listOf("my_oc", "bingo")),
            createTags(views[66], listOf("my_oc", "dingo")),

            // Delila's content
            createTags(views[67], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[68], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[69], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[70], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[71], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[72], listOf("butt", "butts", "bum", "booty", "pirate")),
            createTags(views[73], listOf("butt", "butts", "bum", "booty", "pirate")),

            // Ed's content
            createTags(views[74], listOf("fursuit", "diy", "crafting")),
            createTags(views[75], listOf("fursuit", "diy", "crafting")),
            createTags(views[76], listOf("fursuit", "diy", "crafting")),

        )
        tags.forEach { tagList->
            tagList.forEach {
                db.tagsDao().insertOrUpdateTag(it)
            }
        }

        // assemble the content feed
        val homeContentFeed = createFeed(views, ContentFeedId.Home)
        homeContentFeed.forEach {
            db.contentFeedDao().insertOrUpdate(it)
        }

        // add comments to the posts

    }
}