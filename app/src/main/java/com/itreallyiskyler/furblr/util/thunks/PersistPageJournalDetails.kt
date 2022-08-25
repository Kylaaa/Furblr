package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistPageJournalDetails(journalId : Long, pageJournalDetails: PageJournalDetails) {
    val dbImpl = SingletonManager.get().DBManager.getDB()

    // Add the journal to the Journals table
    val journal = Journal(
        journalId,
        pageJournalDetails.artist,
        pageJournalDetails.title,
        pageJournalDetails.contents,
        pageJournalDetails.uploadDate
    )
    dbImpl.journalsDao().insertOrUpdateJournal(journal)

    // add each comment to the Comments table
    pageJournalDetails.comments.forEach { comment: IPostComment ->
        run {
            val commentEntity = Comment(
                comment.id,
                journalId,
                CommentLocationId.Journal.id,
                comment.uploaderName,
                comment.content,
                comment.date
            )
            dbImpl.commentsDao().insertOrUpdateComment(commentEntity)
        }
    }

    // Add the post to the feed
    val feedId = FeedId(ContentFeedId.Home.id, PostKind.Journal.id, journalId, pageJournalDetails.uploadDate)
    dbImpl.contentFeedDao().insertOrUpdate(feedId)
}