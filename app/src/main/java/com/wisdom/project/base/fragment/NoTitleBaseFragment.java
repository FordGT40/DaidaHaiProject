package com.wisdom.project.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @authorzhanglichao
 * @date2018/3/29 11:25
 * @package_name com.wisdom.nhoa.base.fragment
 */

public abstract  class NoTitleBaseFragment extends Fragment {

    protected Context context;
    protected View rootView;
    protected Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(getLayoutId(),container,false);
        unbinder= ButterKnife.bind(this,rootView);
        context = getActivity();
        this.initView();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return rootView;
    }

    public abstract int getLayoutId();
    public abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
