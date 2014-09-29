package com.myexample.bookmanagement;

public class Account {
	private int user_id;
	private String mail_address;
	private String password;
	
	public Account(int user_id, String mail_address, String password)
	{
		this.user_id = user_id;
		this.mail_address = mail_address;
		this.password = password;
	}
	
	public int getUserId()
	{
		return user_id;
	}
	
	public String getMailAddress()
	{
		return mail_address;
	}
}
