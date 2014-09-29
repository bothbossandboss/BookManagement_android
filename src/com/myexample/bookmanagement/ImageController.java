package com.myexample.bookmanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
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
	// æ“¾‚µ‚½URI‚ğ—p‚¢‚Ä‰æ‘œ‚ğ“Ç‚İ‚Ş
	public Bitmap loadImage(int viewWidth, int viewHeight, Activity ac){
		// URI‚©‚ç‰æ‘œ‚ğ“Ç‚İ‚İBitmap‚ğì¬
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
		
		// MediaStore‚©‚ç‰ñ“]î•ñ‚ğæ“¾
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
		
		// k¬Š„‡‚ğŒvZ
		final float scale;
		if (orientation == 90 || orientation == 270) {
			// cŒü‚«‚Ì‰æ‘œ‚Í”¼•ª‚ÌƒTƒCƒY‚É•ÏX
			scale = Math.min(((float)viewWidth / originalHeight)/2, ((float)viewHeight / originalWidth)/2);
		} else {
			// ‰¡Œü‚«‚Ì‰æ‘œ
			scale = Math.min((float)viewWidth / originalWidth, (float)viewHeight / originalHeight);
		}
		
		// •ÏŠ·s—ñ‚Ìì¬
		final Matrix matrix = new Matrix();
		if (orientation != 0) {
			//‰æ‘œ‚ğ‰ñ“]‚³‚¹‚é
			matrix.postRotate(orientation);
		}
		if (scale < 1.0f) {
			// Bitmap‚ğŠg‘åk¬‚·‚é
			matrix.postScale(scale, scale);
		}
		
		// s—ñ‚É‚æ‚Á‚Ä•ÏŠ·‚³‚ê‚½Bitmap‚ğ•Ô‚·
		return Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, matrix,
				true);
	}
}	
	