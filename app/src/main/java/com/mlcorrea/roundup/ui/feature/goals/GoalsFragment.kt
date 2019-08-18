package com.mlcorrea.roundup.ui.feature.goals


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.ResponseRx
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.framework.extension.observe
import com.mlcorrea.roundup.ui.base.BaseFragmentList
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import com.mlcorrea.roundup.ui.feature.GoalStep
import com.mlcorrea.roundup.ui.feature.MainActivity
import com.mlcorrea.roundup.ui.feature.MainActivityVM
import com.mlcorrea.roundup.ui.feature.goals.adpter.SavingsDiffCallBack
import com.mlcorrea.roundup.ui.feature.goals.adpter.SavingsViewRender
import com.mlcorrea.roundup.ui.renders.RendererRecyclerViewSortedAdapter
import com.mlcorrea.roundup.ui.renders.baserenders.EmptyListViewRender
import com.mlcorrea.roundup.ui.renders.baserenders.LoadMoreViewRender
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.scope.currentScope
import timber.log.Timber
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [GoalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GoalsFragment : BaseFragmentList(R.layout.fragment_goals) {

    @BindView(R.id.ui_add_goal_btn)
    lateinit var uiAddGoalBtn: FloatingActionButton
    @BindView(R.id.ui_loading)
    lateinit var uiProgressBar: LinearLayout
    @BindView(R.id.ui_title)
    lateinit var uiTitle: TextView


    private val viewModel by currentScope.inject<GoalsFragmentVM>()
    private var viewModelParent: MainActivityVM? = null

    override val getViewModel: BaseSimpleListViewModel
        get() = viewModel

    override fun onCreateInitViewModel(savedInstanceState: Bundle?) {
        viewModel.apply {
            observe(requestProgress, ::handleResponse)
            observe(titleData, ::handleTitle)
        }
        var totalSave: BigDecimal? = null
        var account: Accounts? = null
        arguments?.let {
            totalSave = it.getSerializable(ARG_TOTAL_GOAL) as? BigDecimal
            account = it.getSerializable(ARG_ACCOUNT_UID) as? Accounts
        }
        viewModel.setViewModel(totalSave, account)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModelParent = (it as MainActivity).getViewModelParent()
            it.title = getString(R.string.text_savings_goal)
        }
    }

    @SuppressLint("CheckResult")
    override fun initViews() {
        RxView.clicks(uiAddGoalBtn)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                openAddDialog()
            }
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
        sortedListComparatorWrapper = SavingsDiffCallBack(renderRecyclerView)
        renderRecyclerView.setComparatorWrapper(sortedListComparatorWrapper)
        renderRecyclerView.registerRenderer(
            SavingsViewRender { _, savings ->
                Timber.d("onclick savings")
                onClickSavings(savings)
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

    private fun handleResponse(responseRx: ResponseRx<Boolean>?) {
        when (responseRx) {
            is ResponseRx.Success -> {
                progressBarStatus(false)
                if (responseRx.data == true) {
                    viewModelParent?.moveStep(GoalStep.StepAccount(true))
                }
            }
            is ResponseRx.Error -> progressBarStatus(false)
            is ResponseRx.Loading -> progressBarStatus(true)
        }
    }

    private fun handleTitle(titleSavingsGoal: TitleSavingsGoal?) {
        titleSavingsGoal?.let {
            uiTitle.text = getString(R.string.text_title_send_savings_goal, it.amount, it.currency)
        }
    }

    private fun openAddDialog() {
        context?.let {
            MaterialDialog(it).show {
                input(hint = getString(R.string.text_name), allowEmpty = false) { _, text ->
                    viewModel.createSavingGoal(text.toString())
                }
                positiveButton(R.string.text_ok)
                negativeButton(R.string.text_cancle)
                title(R.string.text_name_title_dialog)
            }
        }
    }

    private fun onClickSavings(savings: SavingsGoal) {
        viewModel.addMoneySavingsGoal(savings)
    }

    private fun progressBarStatus(visible: Boolean) {
        uiProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }


    companion object {
        private const val ARG_TOTAL_GOAL = "ARG_TOTAL_GOAL"
        private const val ARG_ACCOUNT_UID = "ARG_ACCOUNT_UID"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment GoalsFragment.
         */
        @JvmStatic
        fun newInstance(total: BigDecimal, account: Accounts) =
            GoalsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TOTAL_GOAL, total)
                    putSerializable(ARG_ACCOUNT_UID, account)
                }
            }
    }
}
