package com.zy.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zy.service.SyncHttp;

import edu.njupt.zhb.main.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsDetailActivity extends Activity{
	
	String newsID = "";
	String userID = "";
	
	private ArrayList<HashMap<String, Object>> mNewsData; // 存储新闻信息的数据集合
	private ArrayList<HashMap<String, Object>> commentData; // 存储新闻信息的数据集合
	
    private TextView newsTitle;
    private TextView newsBody;
    private Button commentDetail;
    private LinearLayout mNewsReplyEditLayout;	//新闻回复的布局
    private LinearLayout mNewsReplyImgLayout;	//新闻图片回复的布局
    private EditText mNewsReplyEditText;		//新闻回复的文本框
    private boolean keyboardShow;				//键盘是否显示
    private Button ReplyBtn;
    
    String titleTest = "";
    
    int commentNumber = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetails_layout);
		
		newsTitle = (TextView)findViewById(R.id.newsTitle);
		newsBody = (TextView)findViewById(R.id.newsBody);
		commentDetail = (Button)findViewById(R.id.newsdetail_titlebar_comments);
		ReplyBtn = (Button)findViewById(R.id.news_reply_post);
		newsBody.setMovementMethod(ScrollingMovementMethod.getInstance());
		ImageButton newsReplyImgBtn = (ImageButton) findViewById(R.id.news_reply_img_btn);
		mNewsReplyEditLayout = (LinearLayout) findViewById(R.id.news_reply_edit_layout);
		mNewsReplyImgLayout = (LinearLayout) findViewById(R.id.news_reply_img_layout);
		mNewsReplyEditText = (EditText) findViewById(R.id.news_reply_edittext);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		newsID = bundle.getString("newsID");
		userID = bundle.getString("userID");
		
		mNewsData = new ArrayList<HashMap<String, Object>>();
		commentData = new ArrayList<HashMap<String, Object>>();
		getDetailNewsAsyncTack upImageAsync = new getDetailNewsAsyncTack();
		upImageAsync.execute(newsID,mNewsData);
		
		commentDetail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(commentNumber>0){
					Intent intent = new Intent(NewsDetailActivity.this,
							CommentActivity.class);
					intent.putExtra("commentData", commentData);
					startActivity(intent);
				}
			}
		});
		
		newsReplyImgBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				InputMethodManager m = (InputMethodManager) mNewsReplyEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				mNewsReplyEditLayout.setVisibility(View.VISIBLE);
				mNewsReplyImgLayout.setVisibility(View.GONE);
				mNewsReplyEditText.requestFocus();
				//显示输入法
				m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
				keyboardShow = true;
			}
		});
		
		ReplyBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				InputMethodManager m = (InputMethodManager) mNewsReplyEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				String str = mNewsReplyEditText.getText().toString();
				if(str.equals("")){
					Toast.makeText(NewsDetailActivity.this, "不能为空",
							Toast.LENGTH_SHORT).show();
				}
				else {
					/*
					 * getDetailNewsAsyncTack upImageAsync = new getDetailNewsAsyncTack();
		upImageAsync.execute(newsID,mNewsData);
					 * */
					addCommentAsyncTack aCommentAsync = new addCommentAsyncTack();
					aCommentAsync.execute(str);
					mNewsReplyEditLayout.setVisibility(View.GONE);
					mNewsReplyImgLayout.setVisibility(View.VISIBLE);
				}
				/*InputMethodManager m = (InputMethodManager) mNewsReplyEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				mNewsReplyEditLayout.setVisibility(View.VISIBLE);
				mNewsReplyImgLayout.setVisibility(View.GONE);
				mNewsReplyEditText.requestFocus();
				//显示输入法
				m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
				keyboardShow = true;*/
				
			}
		});
	}
	
	/**
	 * 获取指定类型的新闻列表
	 * 
	 * @param cid
	 * @return
	 */
	private int getNewsByID(int newsID, List<HashMap<String, Object>> newsList) {
		
		// 本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		// wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/GetNewsByNewsID";
		String params = "newsID=" + newsID;
		SyncHttp syncHttp = new SyncHttp();

		try {
			// 通过Http协议发送Get请求，返回字符串
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			int retCode = jsonObject.getInt("ret");
			if (retCode == 0) {
				JSONObject dataObj = jsonObject.getJSONObject("data");
				// 获取返回数目
				int totalNum = dataObj.getInt("totalnum");
				if (totalNum > 0) {
					// 获取返回新闻集合
					JSONArray newslistArray = dataObj.getJSONArray("newsList");
					// 将用JSON格式解析的数据添加到数据集合当中
					for (int i = 0; i < newslistArray.length(); i++) {
						JSONObject newsObject = (JSONObject) newslistArray
								.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("title",newsObject.getString("ntitle"));
						titleTest = newsObject.getString("ntitle");
						hashMap.put("body",newsObject.getString("body"));
						hashMap.put("createTime",newsObject.getString("ncreateTime"));
						newsList.add(hashMap);
					}
					return 0;
				} else {
					// 第一次加载新闻列表
					return 1;
				}
			} else {
				return 3; // 加载新闻失败
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3; // 加载新闻失败
		}
	}
	
	private class getDetailNewsAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			getNewsByID(Integer.valueOf(newsID), mNewsData);
			return 1;
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			String s1 = (String) mNewsData.get(0).get("title");
			String s2 = (String) mNewsData.get(0).get("body");
			newsTitle.setText(s1);
			newsBody.setText(s2 + "\n" + "\n"+ "\n"+ "\n");
			getCommentAsyncTack getComment = new getCommentAsyncTack();
			getComment.execute(newsID,commentData);
		}
	}
	
private int getCommentByNewsID(int newsID, List<HashMap<String, Object>> commentList) {
		
		// 本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		// wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/GetCommentServlet";
		String params = "newsID=" + newsID;
		SyncHttp syncHttp = new SyncHttp();

		try {
			// 通过Http协议发送Get请求，返回字符串
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			int retCode = jsonObject.getInt("ret");
			if (retCode == 0) {
				JSONObject dataObj = jsonObject.getJSONObject("data");
				// 获取返回数目
				int totalNum = dataObj.getInt("totalnum");
				commentNumber = totalNum;
				if (totalNum > 0) {
					// 获取返回新闻集合
					JSONArray commentlistArray = dataObj.getJSONArray("commentList");
					// 将用JSON格式解析的数据添加到数据集合当中
					for (int i = 0; i < commentlistArray.length(); i++) {
						JSONObject commentObject = (JSONObject) commentlistArray.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("commentID",commentObject.getString("commentID"));
						hashMap.put("content",commentObject.getString("content"));
						hashMap.put("createTime",commentObject.getString("createTime"));
						hashMap.put("userID",commentObject.getString("userID"));
						hashMap.put("newsID",commentObject.getString("newsID"));
						
						commentList.add(hashMap);
					}
					return 0;
				} else {
					// 第一次加载新闻列表
					return 1;
				}
			} else {
				return 3; // 加载新闻失败
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3; // 加载新闻失败
		}
	}
	
	private class getCommentAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			return getCommentByNewsID(Integer.valueOf(newsID), commentData);
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			if(result == 0){
				commentDetail.setText(String.valueOf(commentNumber)+"跟帖");
			}
		}
	}
	
	private int addComment(int newsID, int userID, String content) {
		
		// 本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		// wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/AddCommentServlet";
		String params = "newsID=" + newsID +"&userID="+userID + "&content="+content;
		Log.i("response", params);
		SyncHttp syncHttp = new SyncHttp();

		try {
			// 通过Http协议发送Get请求，返回字符串
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			int retCode = jsonObject.getInt("ret");
			if (retCode == 0) {
				return 0;
			} else {
				return 1; // 上载评论失败
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1; // 上载评论失败
		}
	}
	
	private class addCommentAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			return addComment(Integer.valueOf(newsID),Integer.valueOf(userID), (String)params[0]);
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			if(result == 0){
				showTip("上传评论成功！！！");
				getCommentAsyncTack getComment = new getCommentAsyncTack();
				getComment.execute(newsID,commentData);
			}else{
				showTip("上传评论失败！！！");
			}
		}
	}

	private void showTip(String msg)
	{
		Toast.makeText(NewsDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
	
}
