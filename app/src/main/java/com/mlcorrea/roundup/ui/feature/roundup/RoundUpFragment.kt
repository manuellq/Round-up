package com.mlcorrea.roundup.ui.feature.roundup


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.ui.base.BaseFragmentList
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import com.mlcorrea.roundup.ui.feature.GoalStep
import com.mlcorrea.roundup.ui.feature.MainActivity
import com.mlcorrea.roundup.ui.feature.MainActivityVM
import com.mlcorrea.roundup.ui.feature.roundup.adapter.AccountDiffCallBack
import com.mlcorrea.roundup.ui.feature.roundup.adapter.AccountsViewRender
import com.mlcorrea.roundup.ui.renders.RendererRecyclerViewSortedAdapter
import com.mlcorrea.roundup.ui.renders.baserenders.EmptyListViewRender
import com.mlcorrea.roundup.ui.renders.baserenders.LoadMoreViewRender
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import org.koin.androidx.scope.currentScope
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [RoundUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RoundUpFragment : BaseFragmentList(R.layout.fragment_round_up) {

    private val viewModel: RoundUpFragmentVM by currentScope.inject()
    private var viewModelParent: MainActivityVM? = null


    override val getViewModel: BaseSimpleListViewModel
        get() = viewModel

    override fun onCreateInitViewModel(savedInstanceState: Bundle?) {
        viewModel.setViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModelParent = (it as MainActivity).getViewModelParent()
            it.title = getString(R.string.text_currency_accounts)
        }
    }

    override fun initViews() {
        //Set recycler view
        renderRecyclerView =
            RendererRecyclerViewSortedAdapter(
                emptyViewRender = EmptyListViewRender(),
                loadMoreViewRender = LoadMoreViewRender(object :
                    LoadMoreViewRender.LoadMoreViewListener {
                    override fun onRetryClick() {
                        viewModel.retry()
                    }
                })
            )
        val sortedListComparatorWrapper: SortedListComparatorWrapper<ViewModelData>
        sortedListComparatorWrapper = AccountDiffCallBack(renderRecyclerView)
        renderRecyclerView.setComparatorWrapper(sortedListComparatorWrapper)
        renderRecyclerView.registerRenderer(
            AccountsViewRender { _, account ->
                Timber.d("onclick account")
                onClickAccount(account)
            }
        )
        context?.let { context ->
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val horizontalDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

            uiRecyclerView.addItemDecoration(horizontalDecoration)
            uiRecyclerView.layoutManager = linearLayoutManager
        }

        uiRecyclerView.adapter = renderRecyclerView
        uiSwipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun onClickAccount(account: Accounts) {
        viewModelParent?.moveStep(GoalStep.StepTransactions(account))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RoundUpFragment.
         */
        @JvmStatic
        fun newInstance() = RoundUpFragment()
    }
}
