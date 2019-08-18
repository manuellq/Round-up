package com.mlcorrea.roundup.ui.feature.transaction


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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.ResponseRx
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.framework.extension.observe
import com.mlcorrea.roundup.framework.extension.parseToDateSting
import com.mlcorrea.roundup.ui.base.BaseFragmentList
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import com.mlcorrea.roundup.ui.feature.GoalStep
import com.mlcorrea.roundup.ui.feature.MainActivity
import com.mlcorrea.roundup.ui.feature.MainActivityVM
import com.mlcorrea.roundup.ui.feature.transaction.adapter.TransactionsDiffCallBack
import com.mlcorrea.roundup.ui.feature.transaction.adapter.TransactionsViewRender
import com.mlcorrea.roundup.ui.renders.RendererRecyclerViewSortedAdapter
import com.mlcorrea.roundup.ui.renders.baserenders.EmptyListViewRender
import com.mlcorrea.roundup.ui.renders.baserenders.LoadMoreViewRender
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.scope.currentScope
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 * Use the [TransactionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TransactionsFragment : BaseFragmentList(R.layout.fragment_transactions), DatePickerDialog.OnDateSetListener {


    @BindView(R.id.ui_date_btn)
    lateinit var uiBtnDate: TextView
    @BindView(R.id.ui_date_from)
    lateinit var uiDateFrom: TextView
    @BindView(R.id.ui_date_to)
    lateinit var uiDateTo: TextView
    @BindView(R.id.ui_accept_btn)
    lateinit var uiAcceptBtn: FloatingActionButton
    @BindView(R.id.ui_loading)
    lateinit var uiProgressBar: LinearLayout


    private val viewModel by currentScope.inject<TransactionsFragmentVM>()
    private var viewModelParent: MainActivityVM? = null

    override val getViewModel: BaseSimpleListViewModel
        get() = viewModel

    override fun onCreateInitViewModel(savedInstanceState: Bundle?) {
        viewModel.apply {
            observe(rangeDateFeed, ::handleRangeDate)
            observe(roundUpFeedItems, ::handleRoundUp)
        }
        var account: Accounts? = null
        arguments?.let {
            account = it.getSerializable(ARG_ACCOUNT_ID) as? Accounts
        }

        viewModel.setViewModel(account)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModelParent = (it as MainActivity).getViewModelParent()
            it.title = getString(R.string.text_transactions)
        }
    }

    @SuppressLint("CheckResult")
    override fun initViews() {
        RxView.clicks(uiBtnDate)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                openDateDialog()
            }
        RxView.clicks(uiAcceptBtn)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.getRoundUp()
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
        sortedListComparatorWrapper = TransactionsDiffCallBack(renderRecyclerView)
        renderRecyclerView.setComparatorWrapper(sortedListComparatorWrapper)
        renderRecyclerView.registerRenderer(
            TransactionsViewRender { _, feedItem ->
                Timber.d("onclick feedItem")
                onClickFeedItem(feedItem)
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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        Timber.d("Start date: $year-$monthOfYear-$dayOfMonth")
        viewModel.setStartDate(year, monthOfYear, dayOfMonth)
    }


    private fun onClickFeedItem(feedItem: FeedItem) {
        //To implement
    }

    private fun handleRoundUp(responseRx: ResponseRx<BigDecimal>?) {
        when (responseRx) {
            is ResponseRx.Error -> {
                progressBarStatus(false)
                showSnackBar(responseRx.exception.message)
            }
            is ResponseRx.Loading -> progressBarStatus(true)
            is ResponseRx.Success ->
                viewModelParent?.moveStep(GoalStep.StepGoals(responseRx.data ?: BigDecimal(0)))

        }
    }

    private fun progressBarStatus(visible: Boolean) {
        uiProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun handleRangeDate(rangeDate: RangeDate?) {
        rangeDate?.let {
            uiDateFrom.text = rangeDate.startDate.parseToDateSting()
            uiDateTo.text = rangeDate.endDate.parseToDateSting()
        }
    }

    private fun openDateDialog() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR), // Initial year selection
            now.get(Calendar.MONTH), // Initial month selection
            now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        )
        dpd.show(fragmentManager, DATE_PICKER)
    }


    companion object {
        private const val ARG_ACCOUNT_ID = "ARG_ACCOUNT_ID"
        private const val DATE_PICKER = "DATE_PICKER"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TransactionsFragment.
         */
        @JvmStatic
        fun newInstance(accounts: Accounts) =
            TransactionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ACCOUNT_ID, accounts)
                }
            }
    }
}
