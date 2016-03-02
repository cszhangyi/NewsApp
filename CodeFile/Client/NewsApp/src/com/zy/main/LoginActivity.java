package com.zy.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zy.service.SyncHttp;

import edu.njupt.zhb.main.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final String MSG_LOGIN_SUCCESS = "登陆成功";
	private static final String MSG_LOGIN_ERROR = "登陆错误";
	public static final String MSG_LOGIN_FAIL = "登陆名或密码错误";
	public static final String MSG__LOGIN_RESPONSE_ERROR = "登陆响应错误";
	private static final String MSG_REQUEST_ERROR = "服务器请求超�?";
	private static final String MSG_RESPONSE_ERROR = "服务器响应超�?";
	private EditText loginNameEditText;
	private EditText loginPasswordEditText;
	private Button loginButton;
	private Button resetButton;
	private static ProgressDialog dialog;
	
	
	//private String userID = "";

	/**
	 * 初始化控�?
	 */
	private void init()
	{
		this.loginNameEditText = (EditText) findViewById(
				R.id.edittext_login_name);
		this.loginPasswordEditText = (EditText) findViewById(
				R.id.edittext_login_password);
		this.loginButton = (Button) findViewById(R.id.button_login);
		this.resetButton = (Button) findViewById(R.id.button_reset);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		// 初始
		this.init();
		// 点击登陆
		this.loginButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ValidateAsyncTack validateAsyncTack = new ValidateAsyncTack();
				validateAsyncTack.execute();
			}
		});
		// 重置
		this.resetButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				loginNameEditText.setText("");
				loginPasswordEditText.setText("");
			}
		});
	}

	private class ValidateAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			//validate();
			return validate();
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			if(result == 0){
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				intent.putExtra("userID", loginNameEditText.getText().toString());
				/*intent.putExtra("categoryTitle", mCategoryTitle);
				intent.putExtra("newsData", mNewsData);
				intent.putExtra("position", position);*/
				startActivity(intent);
			}
			else{
				showTip("用户名或密码错误!");
			}
			
			/*
			 * Intent intent = new Intent(MainActivity.this,
						NewsDetailActivity.class);
				intent.putExtra("categoryTitle", mCategoryTitle);
				intent.putExtra("newsData", mNewsData);
				intent.putExtra("position", position);
				startActivity(intent);
			 */
		}
	}
	
	public int validate() {
		String url = "http://192.168.1.107:8080/newsWeb/getUserInfo";  
		String params = "userID=" + loginNameEditText.getText() + "&userPassword=" + loginPasswordEditText.getText();
		SyncHttp syncHttp = new SyncHttp();

		try {
			// 通过Http协议发�?�Get请求，返回字符串
			String retStr = syncHttp.httpGet(url, params);
			//LoginResult = retStr;
			JSONObject jsonObject = new JSONObject(retStr);
			int retCode = jsonObject.getInt("ret");
			/*
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
						hashMap.put("nid", newsObject.getInt("nid"));
						hashMap.put("newslist_item_title",
								newsObject.getString("ntitle"));
						hashMap.put("newslist_item_digest",
								newsObject.getString("nkeywords"));
						hashMap.put("newslist_item_source",
								newsObject.getString("nurl"));
						hashMap.put("newslist_item_ptime",
								newsObject.getString("ncreateTime"));
						newsList.add(hashMap);
					}
					return 0;
				} else {
					// 第一次加载新闻列�?
					if (firstTime) {
						return 1; // 没有新闻
					} else {
						return 2; // 没有更多新闻
					}
				}
			} else {
				return 3; // 加载新闻失败
			}*/
			return retCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1; // 加载新闻失败
		}
    }
	
	private void showTip(String msg)
	{
		Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
}
