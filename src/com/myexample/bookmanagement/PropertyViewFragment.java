package com.myexample.bookmanagement;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PropertyViewFragment extends Fragment {
	private Button setAccountButton;
	private static final String TAG = "LifeCycleProperty";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG,"onCreate");
		View view = inflater.inflate(R.layout.property_view, container, false);
		super.onCreateView(inflater, container, savedInstanceState);
		setAccountButton = (Button)view.findViewById(R.id.setAccountButton);
	    SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
	    String state = prefs.getString("state", "");
	    if(state.equals("first")){
	    	Log.d("state",""+state);
	    	SharedPreferences.Editor editor = prefs.edit();
	    	editor.putString("state", "active");
	    	editor.apply();
	    	setAccountButtonTapped();
	    }
		return view;
	}
        	
	@Override
	public void onStart()
	{
		Log.d(TAG,"onStart");
		super.onStart();
		SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
	    String state = prefs.getString("state", "");
	    Log.d("state",""+state);
		setAccountButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setAccountButtonTapped();
			}
		}
		);	
     }
	
	private void setAccountButtonTapped()
	{
		//アカウント設定ボタンが押された時の動作
		Intent intent = new Intent(getActivity(), AccountViewActivity.class);
		int requestCode = MainActivity.REQUEST_CODE_SAVE_ACCOUNT;
		startActivityForResult(intent,requestCode);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		//編集して保存する場合。
		if(requestCode == MainActivity.REQUEST_CODE_SAVE_ACCOUNT)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				super.onActivityResult(requestCode, resultCode, intent);
				String saveMailAddress = intent.getStringExtra("mailAddress");
				String savePassword = intent.getStringExtra("password");
				Log.d("saveAccount",""+saveMailAddress);
				Log.d("saveAccount",""+savePassword);
			}
		}
	}
}
