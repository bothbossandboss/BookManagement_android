package com.myexample.bookmanagement;
/*
 * ���Џڍ׉�ʂɂ����鏑�Ђ̉摜���Ǘ�����N���X
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageController{
	private Uri imageUri;
	
	public ImageController(Uri uri)
	{
		imageUri = uri;
	}
	
	// �擾����URI��p���ĉ摜��ǂݍ���
	public Bitmap loadImage(int viewWidth, int viewHeight, Activity ac){
		// URI����摜��ǂݍ���Bitmap���쐬
		Bitmap originalBitmap = null;
		try {
			//originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
			InputStream in = ac.getContentResolver().openInputStream(imageUri);
			originalBitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// MediaStore�����]�����擾
		final int orientation;
		Cursor cursor = MediaStore.Images.Media.query(ac.getContentResolver(), imageUri, new String[] {
			MediaStore.Images.ImageColumns.ORIENTATION
		});
		if (cursor != null) {
			cursor.moveToFirst();
			orientation = cursor.getInt(0);
		} else {
			orientation = 0;
		}
		
		int originalWidth = originalBitmap.getWidth();
		int originalHeight = originalBitmap.getHeight();
		
		// �k���������v�Z
		final float scale;
		if (orientation == 90 || orientation == 270) {
			// �c�����̉摜�͔����̃T�C�Y�ɕύX
			scale = Math.min(((float)viewWidth / originalHeight)/2, ((float)viewHeight / originalWidth)/2);
		} else {
			// �������̉摜
			scale = Math.min((float)viewWidth / originalWidth, (float)viewHeight / originalHeight);
		}
		
		// �ϊ��s��̍쐬
		final Matrix matrix = new Matrix();
		if (orientation != 0) {
			//�摜����]������
			matrix.postRotate(orientation);
		}
		if (scale < 1.0f) {
			// Bitmap���g��k������
			matrix.postScale(scale, scale);
		}
		
		// �s��ɂ���ĕϊ����ꂽBitmap��Ԃ�
		return Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, matrix,
				true);
	}
}	
	