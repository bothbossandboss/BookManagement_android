package com.myexample.bookmanagement;

//List�ɕ\������v�f���܂Ƃ߂��N���X
public class ListViewItem
{
	private int resourceID;
	private String bookName;
	private String price;
	private String date;
	//�摜�͂Ƃ肠���������Ă����B
	/*
	 *�R���X�g���N�^ 
	 */
	public ListViewItem(int constructorID, String constructorBookName, String constructorPrice, String constructorDate)
	{
		this.resourceID = constructorID;
		this.bookName = constructorBookName;
		this.price = constructorPrice;
		this.date = constructorDate;
	}
	/*
	 *Getter 
	 */
	public int getResourceID()
	{
		return resourceID;
	}
	public String getBookName()
	{
		return bookName;
	}
	public String getPrice()
	{
		return price;
	}
	public String getDate()
	{
		return date;
	}
}

