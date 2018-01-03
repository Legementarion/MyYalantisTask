package com.lego.myyalantistask.ui.activity

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpView

interface ActivityView : MvpView {

    fun showFragment(fragment: Fragment)

}