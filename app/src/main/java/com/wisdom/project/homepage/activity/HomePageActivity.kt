package com.wisdom.project.homepage.activity

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.homepage.fragment.HomepageFragment
import com.wisdom.project.homepage.fragment.MineFragment
import com.wisdom.project.homepage.fragment.NinePointNineFragment
import com.wisdom.project.homepage.fragment.TypeClassFragment
import kotlinx.android.synthetic.main.activity_home_page.*

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.activity.HomePageActivity
 * @class describe：首页
 * @author HanXueFeng
 * @time 2018/12/24 11:37
 * @change
 */
class HomePageActivity : BaseActivity() {
    val TAG = HomePageActivity::class.java.simpleName
    //Tab 文字
    private val TAB_TITLES =
        intArrayOf(R.string.tab_homepage, R.string.tab_nine_point_nine, R.string.tab_type, R.string.tab_my)
    //Tab 图片
    private val TAB_IMGS = intArrayOf(
        R.drawable.ic_tab_homepage_selector,
        R.drawable.ic_tab_nine_point_nine_selector,
        R.drawable.ic_tab_type_selector,
        R.drawable.ic_tab_mine_selector
    )
    //Fragment 数组
    var TAB_FRAGMENTS: Array<Fragment>? = null
    //Tab 数目
    val COUNT = TAB_TITLES.size

    override fun setlayoutIds() {
        setContentView(R.layout.activity_home_page)
    }

    override fun initViews() {
        setTabs(tab_home, layoutInflater, TAB_TITLES, TAB_IMGS)
        TAB_FRAGMENTS= arrayOf(HomepageFragment(),NinePointNineFragment(),TypeClassFragment(),MineFragment())
        vp_home.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_home))
        vp_home.setAdapter(MyViewPagerAdapter(supportFragmentManager))
        tab_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //                if (tab.getPosition() == 1) {
                //                    if (!U.isLogin()) {
                //                        //ToastUtil.showToast("请先登录！");
                //                       // startActivityForResult(new Intent(HomePageActivity.this, LoginActivity.class), 1);
                //                    } else {
                //                        mViewPager.setCurrentItem(tab.getPosition());
                //                    }
                //                } else {
                vp_home.setCurrentItem(tab.position, false)
                // }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    /**
     * @description: 设置添加Tab
     */
    private fun setTabs(tabLayout: TabLayout, inflater: LayoutInflater, tabTitlees: IntArray, tabImgs: IntArray) {
        for (i in tabImgs.indices) {
            val tab = tabLayout.newTab()
            var view: View? = null
            view = inflater.inflate(R.layout.tab_item, null)
            tab.customView = view
            val tvTitle = view!!.findViewById<View>(R.id.tab_tv) as TextView
            tvTitle.setText(tabTitlees[i])
            val counts = view.findViewById<TextView>(R.id.tab_tv_logo)
            val imgTab = view.findViewById<View>(R.id.tab_img) as ImageView
            imgTab.setImageResource(tabImgs[i])
            tabLayout.addTab(tab)
        }
    }

    /**
     * @description: ViewPager 适配器
     */
    private inner class MyViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return TAB_FRAGMENTS!![position]
        }

        override fun getCount(): Int {
            return COUNT
        }
    }
}