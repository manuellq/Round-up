package com.mlcorrea.roundup.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.error.ErrorMessageFactory

/**
 * Created by manuel on 17/08/19
 */
abstract class BaseActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null

    /*----------------PUBLIC METHOD--------*/

    fun setToolbar(homeAsUpEnabled: Boolean, displayTitle: Boolean = true) {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
            supportActionBar!!.setDisplayShowTitleEnabled(displayTitle)
        }
    }

    fun addFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment)
            .commit()
    }

    /**
     * Display a snack bar
     *
     * @param message [String]
     */
    fun showSnackBar(message: String?) {
        if (message?.isEmpty() == true) {
            return
        }
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            Snackbar.make(view, message!!, Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * get the error message
     *
     * @param exception [Exception]
     * @return [String]
     */
    fun getUserMessageError(exception: Exception?): String {
        return ErrorMessageFactory.createFromException(this, exception)
    }
}