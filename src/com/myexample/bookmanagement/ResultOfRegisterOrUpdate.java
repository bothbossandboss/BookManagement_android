package com.myexample.bookmanagement;
/*
 * ���АV�K�o�^�E�X�V�̎��̃f�[�^�x�[�X����̃��X�|���X���i�[���邽�߂̃N���X
 */
import java.util.HashMap;

public class ResultOfRegisterOrUpdate {
	private String status;
	private String error;
	private HashMap<String, String> data;
	
	public ResultOfRegisterOrUpdate(String status, String error, HashMap<String, String> data)
	{
		this.status = status;
		this.error = error;
		this.data = data;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public String getError()
	{
		return error;
	}
	
	public String getBookId()
	{
		String bookId = data.get("book_id");
		return bookId;
	}
}
