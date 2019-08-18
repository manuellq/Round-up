package com.mlcorrea.roundup.domain.iteractor.transactionfeed

import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.UseCase
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
class TransactionRangeDate constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val platformRepository: PlatformRepository
) :
    UseCase<List<FeedItem>, TransactionRangeDate.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Observable<List<FeedItem>> {
        return platformRepository.getFeedItemsRangeDate(
            params.accountUid,
            params.categoryUid,
            params.minDate,
            params.maxDate
        )
    }

    data class Params(
        val accountUid: String,
        val categoryUid: String,
        val minDate: String,
        val maxDate: String
    )
}