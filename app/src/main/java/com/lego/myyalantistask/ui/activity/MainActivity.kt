package com.lego.myyalantistask.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.lego.myyalantistask.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), ActivityView {

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(container.id, fragment, fragment.tag)
                .commit()
    }
}
