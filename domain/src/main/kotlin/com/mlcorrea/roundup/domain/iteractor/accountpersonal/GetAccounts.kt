package com.mlcorrea.roundup.domain.iteractor.accountpersonal

import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.UseCase
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
class GetAccounts constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val platformRepository: PlatformRepository
) :
    UseCase<List<Accounts>, Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit): Observable<List<Accounts>> {
        return platformRepository.getAccounts()
    }


}