package com.mlcorrea.roundup.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mlcorrea.roundup.R

/**
 * Created by manuel on 17/08/19
 */
abstract class BaseFragment : Fragment() {


    /*------ abstract methods ------*/

    protected abstract val fragmentLayout: Int

    /*------ end abstract methods ------*/

    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(fragmentLayout, container, false)
        injectViews(view)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unbinder?.unbind()
    }

    /*-------------PRIVATE METHOD----------*/

    private fun injectViews(view: View) {
        this.unbinder = ButterKnife.bind(this, view)
    }

    /**
     * Display a snack bar
     *
     * @param message [String]
     */
    fun showSnackBar(message: String?) {
        activity?.let {
            (it as BaseActivity).showSnackBar(message)
        }
    }

    /**
     * Get error message from the exception
     *
     * @param exception [Exception]
     * @return [String]
     */
    fun getErrorMessage(exception: Exception?): String {
        return if (activity == null) {
            getString(R.string.exception_generic_message)
        } else (activity as BaseActivity).getUserMessageError(exception)
    }
}
