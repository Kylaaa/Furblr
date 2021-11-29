package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistPageJournalDetails(dbImpl: AppDatabase,
                              journalId : Long,
                              pageJournalDetails: PageJournalDetails) {
    // Add the journal to the Journals table
    val journal = Journal(
        journalId,
        pageJournalDetails.Artist,
        pageJournalDetails.Title,
        pageJournalDetails.Contents,
        pageJournalDetails.UploadDate
    )
    dbImpl.journalsDao().insertOrUpdateJournal(journal)

    // add each comment to the Comments table
    pageJournalDetails.Comments.forEach { comment: IPostComment ->
        run {
            val commentEntity = Comment(
                comment.Id,
                journalId,
                CommentLocationId.Journal.id,
                comment.UploaderName,
                comment.Content,
                comment.Date
            )
            dbImpl.commentsDao().insertOrUpdateComment(commentEntity)
        }
    }

    // Add the post to the feed
    val feedId = FeedId(ContentFeedId.Home.id, PostKind.Text.id, journalId, pageJournalDetails.UploadDate)
    dbImpl.contentFeedDao().insertOrUpdate(feedId)
}