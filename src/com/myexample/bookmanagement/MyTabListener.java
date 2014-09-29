package com.myexample.bookmanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

public class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private Activity mActivity;
    private String mTag;
    private Class<T> mClass;

    //�R���X�g���N�^
    public MyTabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
        mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
        Log.d("Listener",""+mTag);
    }

    //�^�u���I�����ꂽ�Ƃ�
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            android.app.FragmentManager fm = mActivity.getFragmentManager();
            fm.beginTransaction().add(R.id.container, mFragment, mTag).commit();
        } else {
            if (mFragment.isDetached()) {
                android.app.FragmentManager fm = mActivity.getFragmentManager();
                fm.beginTransaction().attach(mFragment).commit();
            }

        }
    }

    //�^�u�̑I�����������ꂽ�Ƃ�
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            android.app.FragmentManager fm = mActivity.getFragmentManager();
            fm.beginTransaction().detach(mFragment).commit();
        }
    }

    //�I�����ꂽ�^�u���I�����ꂽ�Ƃ�
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}