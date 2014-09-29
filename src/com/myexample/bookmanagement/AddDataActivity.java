package com.myexample.bookmanagement;
/*
 * �V�K���Гo�^��ʂ�activity
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

public class AddDataActivity extends DetailViewActivity{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Activity�N�����ɂ����ɃL�[�{�[�h�������オ��Ȃ��悤�ɂ���B
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.detail_view);
		Intent intent = getIntent();
		resourceID = intent.getIntExtra("resourceID", 0);
		editBookName = (EditText)findViewById(R.id.editBookName);
		editPrice = (EditText)findViewById(R.id.editPrice);
		editDate = (EditText)findViewById(R.id.editDate);
		controlKeyboard(editBookName);
		controlKeyboard(editPrice);
		controlDatePicker(editDate);
	}
}
