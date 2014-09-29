package com.myexample.bookmanagement;
/*
 * 1�Ԗڂ�tab�ł��鏑�Јꗗ��ʂ�fragment 
 */
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListViewFragment extends Fragment {
	public static final  int FIRST_BEGIN_PAGE = 28;
	public static final  int NUM_OF_PAGE = 10;
	public static final String IP_ADDRESS = "10.0.1.44";
	private static final String TAG = "LifeCycleList";
	private List<ListViewItem> list;
	private ListView listView ;
	private RequestQueue mQueue;
	private CustomListItemAdapter adapter;
	private int tmpID;
	private String tmpTitle;
	private String tmpPrice;
	private String tmpDate;
	private int tmpIndex;
	private String isThisFirstGet;

	/*
	 * method of fragment's life cycle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ListView�ɕ\��������v�f���쐬����B
		list = new ArrayList<ListViewItem>();
		//�����ŏ���������ƁA�������f�[�^���\�����ꂸ�ŏ�����DB�̃f�[�^���\�������悤�ɂȂ�B
		int i;
		for(i=0;i<10;i++)
		{
			String firstBookName = String.format("book"+ i);
			String firstPrice = String.format(""+( i * 1000));
			String firstDate = String.format("2014/10/"+( i + 1));
			int firstResourceId = i;
			list.add( new ListViewItem(firstResourceId, firstBookName, firstPrice, firstDate) );
		}	
		mQueue = Volley.newRequestQueue(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.list_view, container, false);
		setHasOptionsMenu(true);
		//ListView�̎擾
		listView = (ListView) view.findViewById(R.id.ListView);
		//ListView�͈ꗗ�̒��g���Ǘ��ł��Ȃ��̂ŁAAdapter���o�C���h�����Ǘ�����B
		adapter = new CustomListItemAdapter(getActivity(), 0, list);
		listView.setAdapter(adapter);
		//�����l�͑������邯�ǁAlist���e��DB�������Ă���̂�onCreate���ł��Ȃ���list���e���X�V����܂����ĕςɂȂ�B
		try {
			getBooksVolley("latest", FIRST_BEGIN_PAGE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("list",""+list.size());
		//ListView�̗v�f(cell)���^�b�v���ꂽ���̏����B
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{	      
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView listView = (ListView)parent;
				ListViewItem item = (ListViewItem)listView.getItemAtPosition(position);
				int ID = item.getResourceID();
				String title = item.getBookName();
				String price = item.getPrice();
				String date = item.getDate();
				Log.d("tag","ID:"+ID);				
				Log.d("name:%s", title);
				Log.d("price:%s", price);
				Log.d("date:%s", date);
				//DetailView�ɑJ�ڂ��邽�߂̃C���e���g���쐬����B
				Intent intent = new Intent(getActivity(), DetailViewActivity.class);
				intent.putExtra("resourceID",ID);
				intent.putExtra("bookName",title);
				intent.putExtra("price",price);
				intent.putExtra("date",date);
				intent.putExtra("position",position);
				Log.d("intent","intent�쐬���� ");
				//�J�ڐ悩��ԋp����鎯�ʃR�[�h���w�肷�邱�Ƃŕԋp�l�𔽉f�ł���B
				int requestCode = MainActivity.REQUEST_CODE_SAVE;
				startActivityForResult(intent,requestCode);
			}
		});
	}

	private void showAlert(String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(msg).setPositiveButton("OK", null);
		builder.show();
	}

	/*
	 * method to control action bar
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
	{
		inflater.inflate(R.menu.list_action_bar_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	//�ǉ��{�^���������ꂽ���̃��\�b�h
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addButton:
			Log.d("addButton","tapped");
			Intent intent = new Intent(getActivity(), AddDataActivity.class);
			intent.putExtra("resourceID",list.size() );
			int requestCode = MainActivity.REQUEST_CODE_ADD;
			startActivityForResult(intent,requestCode);
			return true;
		case R.id.reloadButton:
			Log.d("reloadButton","tapped");
			reloadMoreData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void reloadMoreData()
	{
		SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
		int numOfBooks = prefs.getInt("numOfBooks", 0);
		int beginPage = numOfBooks - list.size();
		try {
			getBooksVolley("old", beginPage);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		//�ҏW���ĕۑ�����ꍇ�B
		if(requestCode == MainActivity.REQUEST_CODE_SAVE)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				super.onActivityResult(requestCode, resultCode, intent);
				int resourceID = intent.getIntExtra("ID", 0);
				int index = intent.getIntExtra("index", 0);
				String saveTitle = intent.getStringExtra("bookName");
				String savePrice = intent.getStringExtra("price");
				String saveDate = intent.getStringExtra("date");
				String saveUriString = intent.getStringExtra("imageURI");
				//Uri imageUri = Uri.parse(saveUriString);
				Log.d("saveIntent",""+saveTitle);
				Log.d("saveIntent",""+savePrice);
				Log.d("saveIntent",""+saveDate);
				try {
					updateEditedDataOfBook(resourceID, saveTitle, savePrice, saveDate, index);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}else if(requestCode == MainActivity.REQUEST_CODE_ADD)
		{
			//�ǉ�
			if(resultCode == Activity.RESULT_OK)
			{
				super.onActivityResult(requestCode, resultCode, intent);
				String addTitle = intent.getStringExtra("bookName");
				String addPrice = intent.getStringExtra("price");
				String addDate = intent.getStringExtra("date");	
				String addUriString = intent.getStringExtra("imageURI");
				//Uri imageUri = Uri.parse(addUriString);
				try {
					registerEditedDataOfBook(addTitle, addPrice, addDate);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * method to access database
	 */
	//throws JSONException�͗�O����
	private void getBooksVolley(String requestPagePosition, int beginPage) throws JSONException {
		isThisFirstGet = requestPagePosition;
		final Gson gson = new Gson();
		SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
		JSONObject requestToken = new JSONObject();
		requestToken.put("user_id", prefs.getString("user_id", ""));
		requestToken.put("mail_address", prefs.getString("mail_address", ""));
		requestToken.put("password", prefs.getString("password",""));
		JSONObject requestPage = new JSONObject();
		requestPage.put("begin", beginPage);
		requestPage.put("numOfPage", NUM_OF_PAGE);
		requestPage.put("position", requestPagePosition);
		JSONObject params = new JSONObject();
		params.put("request_token",requestToken);
		params.put("page",requestPage);
		JSONObject param = new JSONObject();
		param.put("method", "book/get");
		param.put("params", params);
		// Volley �Ń��N�G�X�g
		//mamp��genymotion�̘A���́Alocalhost��genymotion�̂���[����IP�A�h���X�ɕύX������K�v����B
		String url = "http://"+IP_ADDRESS+":8888/cakephp/book/get";
		//JsonObjectRequest�́A(POST/GET, url, request, response, error)�̊����B
		JsonObjectRequest  request = new JsonObjectRequest(Method.POST, url, param,
				new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				GotDataOfBooks gotDataOfBook = gson.fromJson(response.toString(),GotDataOfBooks.class);
				if(gotDataOfBook.getStatus().equals("ok")){
					int i;
					int numOfData = gotDataOfBook.getData().getNumOfData();
					if(numOfData == 0)
					{
						//�f�[�^�x�[�X�Ƀf�[�^�������Ƃ�
						Log.d("getData", "end data");
						String msg = "����ȏ�̓o�^���Ђ͂���܂���";
						showAlert(msg);
					}
					int nowListSize = list.size();
					for(i=0;i<numOfData;i++)
					{
						DataOfBook tmp = gotDataOfBook.getData().getDataWithLabel(i);
						String bookName = tmp.getTitle();
						String price = tmp.getPrice();
						String date = tmp.getDate();
						int resourceID = tmp.getBookId();
						System.out.println(resourceID+":"+"name:"+bookName+"price:"+price+"date:"+date);
						if(isThisFirstGet.equals("latest")){
							list.set(i, new ListViewItem(resourceID, bookName, price, date) );
						}else{
							list.add(i+nowListSize, new ListViewItem(resourceID, bookName, price, date) );
							Log.d("get", ""+ list.size());
						}
					}
					SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("numOfBooks", gotDataOfBook.getData().getNumOfBooks());
					editor.apply();
					adapter.notifyDataSetChanged();
				}else{
					System.out.println("error:"+gotDataOfBook.getError());
				}
			}
		}, new Response.ErrorListener() {
			@Override public void onErrorResponse(VolleyError error) {
				Log.d(TAG,"error"+error);
			}}
				);	
		int socketTimeout = 300;//0.3 seconds 
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(policy);
		mQueue.add(request);
		mQueue.start();
		Log.d("net","done");
	}

	private void registerEditedDataOfBook(String bookName, String price, String purchaseDate) throws JSONException
	{
		final Gson gson = new Gson();
		tmpTitle = bookName;
		tmpPrice = price;
		tmpDate = purchaseDate;
		//image��userID�̓e�X�g�p
		JSONObject request_token = new JSONObject();
		request_token.put("user_id", "3");
		request_token.put("mail_address", "01234@567.com");
		request_token.put("password", "01234567");
		JSONObject params = new JSONObject();
		params.put("request_token",request_token);
		params.put("book_name",bookName);
		params.put("price",price);
		params.put("purchase_date",purchaseDate);
		params.put("image","no image");
		JSONObject param = new JSONObject();
		param.put("method", "book/register");
		param.put("params", params);
		String url = "http://"+IP_ADDRESS+":8888/cakephp/book/regist";
		JsonObjectRequest  request = new JsonObjectRequest(Method.POST, url, param,
				new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				ResultOfRegisterOrUpdate resultData = gson.fromJson(response.toString(),ResultOfRegisterOrUpdate.class);
				String tmp = resultData.getBookId();
				tmpID = Integer.parseInt(tmp);
				list.add(0,new ListViewItem(tmpID,tmpTitle,tmpPrice,tmpDate));
				Log.d("register",""+tmpID);
			}}, new Response.ErrorListener() {
				@Override public void onErrorResponse(VolleyError error) {
					Log.d(TAG,"error"+error);
				}}
				);
		int socketTimeout = 300;//0.3 seconds 
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(policy);
		mQueue.add(request);
		mQueue.start();
	}

	private void updateEditedDataOfBook(int ID,String bookName,
			String price, String purchaseDate, int index) throws JSONException
	{
		final Gson gson = new Gson();
		tmpIndex = index;
		tmpID = ID;
		tmpTitle = bookName;
		tmpPrice = price;
		tmpDate = purchaseDate;
		//image��userID�̓e�X�g�p�B
		JSONObject request_token = new JSONObject();
		request_token.put("user_id", "3");
		request_token.put("mail_address", "01234@567.com");
		request_token.put("password", "01234567");
		JSONObject params = new JSONObject();
		params.put("request_token",request_token);
		params.put("book_id", ID);
		params.put("book_name",bookName);
		params.put("price",price);
		params.put("purchase_date",purchaseDate);
		params.put("image","no image");
		JSONObject param = new JSONObject();
		param.put("method", "book/update");
		param.put("params", params);
		String url = "http://"+IP_ADDRESS+":8888/cakephp/book/update";
		JsonObjectRequest  request = new JsonObjectRequest(Method.POST, url, param,
				new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				ResultOfRegisterOrUpdate resultData = gson.fromJson(response.toString(),ResultOfRegisterOrUpdate.class);
				String tmp = resultData.getBookId();
				tmpID = Integer.parseInt(tmp);
				list.set(tmpIndex,new ListViewItem(tmpID,tmpTitle,tmpPrice,tmpDate));
				Log.d("update",""+tmpID);
			}}, new Response.ErrorListener() {
				@Override public void onErrorResponse(VolleyError error) {
					Log.d(TAG,"error"+error);
				}}
				);
		int socketTimeout = 300;//0.3 seconds 
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(policy);
		mQueue.add(request);
		mQueue.start();
	}	
}
