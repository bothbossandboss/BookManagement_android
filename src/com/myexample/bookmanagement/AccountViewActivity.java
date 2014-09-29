package com.myexample.bookmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class AccountViewActivity extends Activity {
	private EditText mailAddressEditText;
	private EditText passwordEditText;
	private EditText confirmEditText;
	
	public void controlKeyboard(final EditText ed)
	{
		ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        @Override
	        public void onFocusChange(View v, boolean hasFocus) {
	            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            Log.d("keyboard", "hasFocus:"+hasFocus);
	            // フォーカスを受け取ったとき
	            if(hasFocus){
	                // ソフトキーボードを表示する
	                inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
	            }
	            // フォーカスが外れたとき
	            else{
	                // ソフトキーボードを閉じる
	                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
	            }
	        }
	    });
	}
	 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.account_view);
		mailAddressEditText = (EditText)findViewById(R.id.mailAddressEditText);
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
		confirmEditText = (EditText)findViewById(R.id.confirmEditText);
		controlKeyboard(mailAddressEditText);
		controlKeyboard(passwordEditText);
		controlKeyboard(confirmEditText);
		Intent intent = getIntent();
	}
	
	public void onStart()
	{
		super.onStart();
		Button saveAccountButton = (Button)findViewById(R.id.saveAccountButton);
		saveAccountButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SpannableStringBuilder sb = (SpannableStringBuilder)mailAddressEditText.getText();
				String saveMailAddress = sb.toString();
				sb = (SpannableStringBuilder)passwordEditText.getText();
				String savePassword = sb.toString();
				sb = (SpannableStringBuilder)confirmEditText.getText();
				String saveConfirm = sb.toString();
				if(savePassword.equals(saveConfirm)){
					Log.d("password","OK");
					Intent intent = new Intent();
					intent.putExtra("mailAddress",saveMailAddress);
					intent.putExtra("password",savePassword);
					setResult(Activity.RESULT_OK, intent);
					finish();
				}else{
					showAlert();
				}
			}
		});	
	}
	
	private void showAlert()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("パスワードが違います").setPositiveButton("OK", null);
		builder.show();
	}
}
