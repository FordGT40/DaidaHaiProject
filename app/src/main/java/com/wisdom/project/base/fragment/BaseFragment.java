package com.wisdom.project.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wisdom.project.R;

/**
 * @authorzhanglichao
 * @date2018/3/29 11:25
 * @package_name com.wisdom.nhoa.base.fragment
 */

public abstract  class BaseFragment extends Fragment {

    protected Context context;
    protected TextView title;
    protected LinearLayout backIv;
    protected TextView right;
    protected ImageView rightIv;
    protected View rootView;
    protected Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(getLayoutId(),container,false);
        unbinder= ButterKnife.bind(this,rootView);
        context = getActivity();
        initHeadView();
        this.initView();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return rootView;
    }
    public void initHeadView() {
        title = (TextView) rootView.findViewById(R.id.comm_head_title);
        backIv = (LinearLayout) rootView.findViewById(R.id.head_back_iv);
        right = (TextView) rootView.findViewById(R.id.head_right);
        rightIv = (ImageView) rootView.findViewById(R.id.head_right_iv);
    }

    public void setTitle(int resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRight(int resId) {
        if (right != null) {
            right.setText(resId);
            right.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRightIcon(int resId) {
        if (null != rightIv) {
            rightIv.setImageResource(resId);
            rightIv.setVisibility(View.VISIBLE);
        }
    }

    public void showRight() {
        if (null != right)
            right.setVisibility(View.VISIBLE);
    }

    public void hideRight() {
        if (null != right)
            right.setVisibility(View.GONE);
    }

    public void hideBackIv() {
        if (null != backIv)
            backIv.setVisibility(View.GONE);
    }

    public abstract int getLayoutId();
    public abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
