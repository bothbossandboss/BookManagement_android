package com.myexample.bookmanagement;

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
