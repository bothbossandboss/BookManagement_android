package com.myexample.bookmanagement;
/*
 * �f�[�^�x�[�X����擾�����A���Џڍ׏����i�[���邽�߂̃N���X
 * �ϐ�����php���Ɋ��S�Ɉ�v�����邱�ƁI
 */
public class DataOfBook {
	private String image_url;
	private String title;
	private String price;
	private String purchase_date;
	private int book_id;
	
	public DataOfBook(String image_url, String title, String price, String purchase_date, int book_id)
	{
		this.image_url = image_url;
		this.title = title;
		this.price =price;
		this.purchase_date = purchase_date;
	}
	
	public String getImageUrl()
	{
		return image_url;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getPrice()
	{
		return price;
	}
	
	public String getDate()
	{
		return purchase_date;
	}
	
	public int getBookId()
	{
		return book_id;
	}
}
