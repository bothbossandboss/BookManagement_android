package com.myexample.bookmanagement;

import java.util.ArrayList;
import java.util.HashMap;

public class GotDataOfBooks {
	public String status;
	public String error;
	public ArrayOfBooks data;	
	public GotDataOfBooks(String status, String error, ArrayOfBooks data)
	{
		this.status = status;
		this.error = error;
		this.data = data;
	}
	
	public String getStatus()
	{
		System.out.println("Got:OK");
		return status;
	}
}
