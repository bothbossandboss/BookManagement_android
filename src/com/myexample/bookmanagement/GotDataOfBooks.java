package com.myexample.bookmanagement;
/*
 * ���Јꗗ�f�[�^�擾���̃��X�|���X���i�[����N���X
 */
public class GotDataOfBooks {
	private String status;
	private String error;
	private ArrayOfBooks data;	
	
	public GotDataOfBooks(String status, String error, ArrayOfBooks data)
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
	
	public ArrayOfBooks getData()
	{
		return data;
	}
}
