package com.myexample.bookmanagement;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/*
 *ListView�ɃZ�b�g����A�_�v�^�̃N���X 
 *<>�̒���List�̒��g�̌^�i���Ǝv����j�B
 */
public class CustomListItemAdapter  extends ArrayAdapter<ListViewItem>{
	//���s���ɕʂ�xml�t�@�C����view��ǉ�����ۂɗp����B
	private LayoutInflater layoutInflater;
	//�R���X�g���N�^
	public CustomListItemAdapter(Context context, int textViewResourceID, List<ListViewItem> objects)
	{
		super(context, textViewResourceID,objects);
		layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	//ListView�̊e�s���\������v�f��Ԃ��B
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//convertView��null�̏ꍇ�̂݁Axml�t�@�C���w���view��ǂݍ��ށB
		if(convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.custom_list_item, parent, false);
		}
		//position�s�ڂ̃f�[�^���擾����B
		ListViewItem item = (ListViewItem)getItem(position);
		//TextView�ɕ�������Z�b�g����B
		TextView bookNameText = (TextView)convertView.findViewById(R.id.title);
		bookNameText.setText( item.getBookName() );
		TextView priceText = (TextView)convertView.findViewById(R.id.price);
		priceText.setText( item.getPrice() );
		TextView dateText = (TextView)convertView.findViewById(R.id.date);
		dateText.setText( item.getDate() );
		return convertView;
	}
}	