package com.mlcorrea.roundup.domain.iteractor.goals

import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.UseCase
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import io.reactivex.Observable

/**
 * Created by manuel on 18/08/19
 */
class GetSavingsGoal constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val platformRepository: PlatformRepository
) :
    UseCase<List<SavingsGoal>, GetSavingsGoal.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Observable<List<SavingsGoal>> {
        return platformRepository.getSavingsGoals(params.accountUid)
    }

    data class Params(
        val accountUid: String
    )
}