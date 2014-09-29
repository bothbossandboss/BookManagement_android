package com.myexample.bookmanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	//#define���̑���Ɉȉ��̂悤�ɒ�`����Bclass�O����Q�Ƃ���ꍇ��class��.~�ŎQ�ƁB
	public static final  int REQUEST_CODE_SAVE = 10001;
	public static final  int REQUEST_CODE_ADD = 10002;
	public static final  int REQUEST_CODE_SELECT_IMAGE = 10003;
	public static final  int REQUEST_CODE_SAVE_ACCOUNT = 20001;

	private static final String TAG = "LifeCycleMain";
	
	//onCreate�̓A�N�e�B�r�e�B���J�n����鎞�ɌĂ΂�郁�\�b�h�B
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyTabListener<ListViewFragment> listListener = new MyTabListener<ListViewFragment>
																							(this, "tab1",ListViewFragment.class); 
		MyTabListener<PropertyViewFragment> propertyListener = new MyTabListener<PropertyViewFragment>
																							(this, "tag2",PropertyViewFragment.class);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// �^�u���쐬���Ēǉ��B
		//�^�u�̑I���E�����E�đI�����n���h�����O����R�[���o�b�N�� TabListener ���Z�b�g���Ȃ��Ǝ��s����O�ŃN���b�V������
       actionBar.addTab(actionBar.newTab().setText("���Јꗗ").setTabListener(listListener));
       actionBar.addTab(actionBar.newTab().setText("�ݒ�").setTabListener(propertyListener));
       //�A�J�E���g�ݒ�̃^�u�֐؂�ւ��B
       actionBar.getTabAt(1).select();
       SharedPreferences prefs = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = prefs.edit();
       editor.putString("state","first");
       editor.apply();
	}
	@Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        super.onDestroy();
    }
}