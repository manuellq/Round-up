package com.mlcorrea.roundup.domain.iteractor.goals

import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.UseCase
import com.mlcorrea.roundup.domain.model.input.SavingGoalBody
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import io.reactivex.Observable

/**
 * Created by manuel on 18/08/19
 */
class AddSavingGoal constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val platformRepository: PlatformRepository
) :
    UseCase<String, AddSavingGoal.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Observable<String> {
        return platformRepository.addSavingGoal(params.accountUid, params.body)
    }

    data class Params(
        val accountUid: String, val body: SavingGoalBody
    )
}