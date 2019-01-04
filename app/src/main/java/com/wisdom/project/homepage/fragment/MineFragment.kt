package com.wisdom.project.homepage.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wisdom.project.R
import com.wisdom.project.R.id.rl_personal_info_alter_active_code
import com.wisdom.project.homepage.activity.AlterActiveCodeActivity
import com.wisdom.project.homepage.activity.PersonalInfoActivity
import com.wisdom.project.login.activity.FindPswActivity
import com.wisdom.project.login.activity.LoginActivity
import com.wisdom.project.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：我的
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class MineFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_mine_personal_info.setOnClickListener(this@MineFragment)
        rl_mine_alter_psw.setOnClickListener(this@MineFragment)
        rl_mine_alter_active_code.setOnClickListener(this@MineFragment)
        rl_mine_check_update.setOnClickListener(this@MineFragment)
        iv_login.setOnClickListener(this@MineFragment)
    }


    /**
     *  @describe 页面内的点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  15:55
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_mine_personal_info -> {
                //个人信息
                context?.startActivity(Intent(activity, PersonalInfoActivity::class.java))
            }
            R.id.rl_mine_alter_psw -> {
                //修改密码
                context?.startActivity(Intent(activity, FindPswActivity::class.java))
            }
            R.id.rl_mine_alter_active_code -> {
                //修改激活码
                startActivity(
                    Intent(activity, AlterActiveCodeActivity::class.java)
                )
            }
            R.id.rl_mine_check_update -> {
                //检查版本更新
            }
            R.id.iv_login -> {
                if (SharedPreferenceUtil.getUserInfo(activity) == null) {
                    //未登录状态
                    context?.startActivity(Intent(activity, LoginActivity::class.java))
                } else {
                    //登录状态
                    context?.startActivity(Intent(activity, PersonalInfoActivity::class.java))
                }
            }
        }
    }

}