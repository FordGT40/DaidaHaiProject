package com.wisdom.project.homepage.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wisdom.project.R
import com.wisdom.project.login.activity.RegisterActivity
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：首页
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class HomepageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_homepage, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv.setOnClickListener { activity!!.startActivity(Intent(activity, RegisterActivity::class.java)) }
    }


}