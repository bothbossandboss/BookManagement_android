package com.myexample.bookmanagement;

import java.sql.Date;
import java.util.HashMap;

//•Ï”–¼‚Íphp‘¤‚ÉŠ®‘S‚Éˆê’v‚³‚¹‚é‚±‚ÆI
public class DataOfBook {
	public String image_url;
	public String title;
	public String price;
	public String purchase_date;
	public int book_id;
	
	public DataOfBook(String image_url, String title, String price, String purchase_date, int book_id)
	{
		this.image_url = image_url;
		this.title = title;
		this.price =price;
		this.purchase_date = purchase_date;
	}
	
	public String getDate()
	{
		return purchase_date;
	}
}
