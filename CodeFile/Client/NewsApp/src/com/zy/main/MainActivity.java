package com.zy.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yanzi.ui.HorizontalListView;
import org.yanzi.ui.HorizontalListViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zy.service.SyncHttp;

import edu.njupt.zhb.main.R;

public class MainActivity extends Activity {

	private ImageView view;
	
	HorizontalListView hListView;
	HorizontalListViewAdapter hListViewAdapter; //新闻类别水平布
	private int mCid; // 新闻编号

	private SimpleAdapter mNewslistAdapter; // 为新闻内容提供需要显示的列表
	private ArrayList<HashMap<String, Object>> mNewsData; // 存储新闻信息的数据集合
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static final String SAVE_PATH_IN_SDCARD = "/bi.data/"; // 图片及其他数据保存文件夹
	public static final String IMAGE_CAPTURE_NAME = "cameraTmp.png"; // 照片名称
	public final static int REQUEST_CODE_TAKE_PICTURE = 12;// 设置拍照操作的标志
	private ArrayList<HashMap<String, Object>> mCategoriesData; // 存储新闻信息的数据集合
	
	private ListView mNewslist; // 新闻列表
	private LayoutInflater mInflater; // 用来动态载入没有loadmore_layout界面
	
	private Button mLoadmoreButton; // 加载更多按钮
	
	private String categories[] = {"要闻", "国内", "国际", "社会", "军事", "航空", "政务", "媒体", "探索", "公益"};//保存新闻类别的全局字符串
	private int count = 0;
	
	private LoadNewsAsyncTack mLoadNewsAsyncTack; // 声明LoadNewsAsyncTack引用
	
	private Button mTitleBarRefresh; // 标题栏的刷新按钮
	private ProgressBar mTitleBarProgress; // 进度条
	private TextView emotionText;
	private String emotion = emotion = "anger:0.0" +"  contempt:0.0" + "\n"+"disgust:0.0" + "  fear:0.0" + "\n"+"happiness:0.0" +"\n" +"neutral:0.0" + "\n"+"sadness:0.0" + "  surprise:0.0";
    private int emotionValue = 1;
    String userID = "";
    String newsID = "";
    
    
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mCategoriesData = new ArrayList<HashMap<String, Object>>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		userID = bundle.getString("userID");
		showTip("userID:"+userID);
		
		view = (ImageView)findViewById(R.id.imageViewBtn);
		emotionText = (TextView)findViewById(R.id.emotionText);
		emotionText.setText(emotion);
		// 初始化新闻分类的编号
		mCid = 1;
		
		mInflater = getLayoutInflater();
		
		// 通过id来获取按钮的引用
		mTitleBarRefresh = (Button) findViewById(R.id.titlebar_refresh);
		mTitleBarProgress = (ProgressBar) findViewById(R.id.titlebar_progress);
		
		mTitleBarRefresh.setOnClickListener(loadmoreListener);
		
/*		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	    }*/

        hListView = (HorizontalListView)findViewById(R.id.horizon_listview);
        initUI();
	}
	
		
	public void initUI(){
		//加载新闻类别
		
		//hListView = (HorizontalListView)findViewById(R.id.horizon_listview);
		/*if(categories.length <= 0){
			String titles[] = null;
			titles = {"要闻", "国内", "国际", "社会", "军事", "航空", "政务", "媒体", "探索", "公益"};
			categories
			  
		}*/
		//mLoadNewsAsyncTack = new LoadNewsAsyncTack();
		//mLoadNewsAsyncTack.execute(0, true);
		
		//categories = {"要闻", "国内", "国际", "社会", "军事", "航空", "政务", "媒体", "探索", "公益"};
		//if(null!=categories){
			hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),categories,categories.length);
			//Log.i("category", "newcategory");
		/*}else{
			hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),categories1,categories1.length);
		}*/
		
		hListView.setAdapter(hListViewAdapter);
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override 
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				hListViewAdapter.setSelectIndex(position);
				hListViewAdapter.notifyDataSetChanged();
				Log.i("message", String.valueOf(position));
				//获取新闻分类编号
				mCid = position+1;
				mLoadNewsAsyncTack = new LoadNewsAsyncTack();
				mLoadNewsAsyncTack.execute(0, true);
			}
		});
		
		// 第一次获取新闻列表
		//getSpecCatNews(mCid, mNewsData, 0, true);
		
		// 存储新闻信息的数据集合
		mNewsData = new ArrayList<HashMap<String, Object>>();
		
		//加载特定类别新闻
		mNewslistAdapter = new SimpleAdapter(this, mNewsData,
				R.layout.newslist_item_layout, new String[] {
						"newslist_item_title", "newslist_item_digest",
						"newslist_item_source", "newslist_item_ptime" },
				new int[] { R.id.newslist_item_title,
						R.id.newslist_item_digest, R.id.newslist_item_source,
						R.id.newslist_item_ptime });
		mNewslist = (ListView) findViewById(R.id.news_list);

		View footerView = mInflater.inflate(R.layout.loadmore_layout, null);
		// 在LiseView下面添加“加载更多”
		mNewslist.addFooterView(footerView);
		// 显示列表
		mNewslist.setAdapter(mNewslistAdapter);

		mNewslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				int result = (Integer) mNewsData.get(position).get("nid");
				Log.i("response", String.valueOf(result));
				
				Intent intent = new Intent(MainActivity.this,
						NewsDetailActivity.class);
				intent.putExtra("newsID", String.valueOf(result));
				intent.putExtra("userID", String.valueOf(userID));
				startActivity(intent);
				
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(MainActivity.this,
						NewsDetailActivity.class);
				intent.putExtra("newsID", newsID);
				startActivity(intent);*/
			}

		});

		mLoadmoreButton = (Button) findViewById(R.id.loadmore_btn);
		mLoadmoreButton.setOnClickListener(loadmoreListener);

	}
	
	/**
	 * 为“加载更多”按钮定义匿名内部类
	 */
	private OnClickListener loadmoreListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mLoadNewsAsyncTack = new LoadNewsAsyncTack();
			switch (v.getId()) {
			// 点击加载更多
			case R.id.loadmore_btn:
				mLoadNewsAsyncTack.execute(mNewsData.size(), false); // 不是第一次加载新闻里列表
				break;
			// 点击刷新按钮
			case R.id.titlebar_refresh:
				mLoadNewsAsyncTack.execute(0, true);
				break;
			}
		}
	};
	
	/**
	 * 异步更新UI
	 * 
	 * @author wwj
	 * 
	 */
	private class LoadNewsAsyncTack extends AsyncTask<Object, Integer, Integer> {

		// 准备运行
		@Override
		protected void onPreExecute() {
			mTitleBarRefresh.setVisibility(View.GONE);
			mTitleBarProgress.setVisibility(View.VISIBLE);
			mLoadmoreButton.setText(R.string.loadmore_text);
		}

		// 在后台运行
		@Override
		protected Integer doInBackground(Object... params) {
			//getCategory(0, mCategoriesData, (Integer) params[0],(Boolean) params[1]);
			return getSpecCatNews(mCid, mNewsData,(Integer) params[0],(Boolean) params[1]);
		}

		// 完成后台任务
		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			// 该栏目没有新闻
			case 1:
				Toast.makeText(MainActivity.this, "nonews",
						Toast.LENGTH_SHORT).show();
				break;
			// 该栏目没有更多新闻
			case 2:
				Toast.makeText(MainActivity.this, "nomorenews",
						Toast.LENGTH_SHORT).show();
				break;
			// 加载失败
			case 3:
				Toast.makeText(MainActivity.this, "loadnewserror",
						Toast.LENGTH_SHORT).show();
				break;
			}
			mTitleBarRefresh.setVisibility(View.VISIBLE); // 刷新按钮设置为可见
			mTitleBarProgress.setVisibility(View.GONE); // 进度条设置为不可见
			mLoadmoreButton.setText(R.string.loadmore_btn); // 按钮信息替换为“加载更多”
			hListViewAdapter.notifyDataSetChanged(); // 通知ListView更新数据
			mNewslistAdapter.notifyDataSetChanged(); // 通知ListView更新数据
		}
	}
	
	/**
	 * 获取新闻类别名
	 * 
	 * @param cid
	 * @return
	 * @method e.g: getCategory(0, mCategoriesData, 0, true);
	 */
	private int getCategory(int cid, List<HashMap<String, Object>> categoryList,
			int startnid, boolean firstTime) {

		// 如果是第一次加载的话
		if (firstTime) {
			categoryList.clear();
		}
		//本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		//wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/GetCategoriesServlet";
		String params = "startnid=" + startnid + "&count=" + 0
				+ "&cid=" + 0;
		Log.i("params", params);
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
					JSONArray newslistArray = dataObj.getJSONArray("categoryList");
					// 将用JSON格式解析的数据添加到数据集合当中
					String categoryString[] = new String[newslistArray.length()];
					for (int i = 0; i < newslistArray.length(); i++) {
						JSONObject newsObject = (JSONObject) newslistArray
								.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("cid", newsObject.getInt("cid"));
						hashMap.put("ctitle", newsObject.getString("ctitle"));
						categoryString[i] = newsObject.getString("ctitle");
						Log.i("title", newsObject.getString("ctitle"));
						categoryList.add(hashMap);
					}
					categories = categoryString;
					for(String i : categories){
						Log.i("categories", i);						
					}
					return 0;
				} else {
					//第一次加载新闻列表
					if (firstTime) {
						return 1;			//没有新闻
					} else {
						return 2;		//没有更多新闻
					}
				}
			} else {
				return 3;			//加载新闻失败
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;			//加载新闻失败
		}
	}
	
	/**
	 * 获取指定类型的新闻列表
	 * 
	 * @param cid
	 * @return
	 */
	private int getSpecCatNews(int categoryID, List<HashMap<String, Object>> newsList,int startnid,boolean firstTime) {
		
		// 如果是第一次加载的话
		if(firstTime) {
			newsList.clear();
		}
		// 本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		// wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/getSpecifyCategoryNews";
		String params = "categoryID=" + categoryID + "&startnid=" + startnid + "&count=" + 5 + "&emotionValue=" + emotionValue;
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
					// 第一次加载新闻列表
					if (firstTime) {
						return 1; // 没有新闻
					} else {
						return 2; // 没有更多新闻
					}
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
	
	

	public void imageOnClick(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		/*
		 * String out_file_path = "/sdcard/Image/"; File dir = new
		 * File(out_file_path); if(!dir.exists()){ dir.mkdirs(); } String
		 * capturePath = out_file_path + "UserImage.jpg";
		 * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
		 * File(capturePath))); intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,
		 * 1);
		 */
		startActivityForResult(intent, 1);
	}

	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
				Log.i("TestFile",
						"SD card is not available/writeable right now.");
				return;
			}
			new DateFormat();
			// String name = DateFormat.format("yyyyMMdd_hhmmss",
			// Calendar.getInstance(Locale.CHINA)) + ".jpg";
			String name = "UserImage" + ".jpg";
			//Toast.makeText(this, name, Toast.LENGTH_LONG).show();
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");

			FileOutputStream b = null;
			File file = new File("/sdcard/Image/");
			file.mkdirs();
			String fileName = "/sdcard/Image/" + name;
			try {
				b = new FileOutputStream(fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
			} catch (FileNotFoundException e) {
				Log.e("error", "FileNotFoundException_error");
				e.printStackTrace();
			} finally {
				try {
					b.flush();
					b.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				view.setImageBitmap(bitmap);
				UpLoadImageAsyncTack upImageAsync = new UpLoadImageAsyncTack();
				upImageAsync.execute(fileName);
			} catch (Exception e) {
				Log.e("error", e.getMessage());
			}

		}
	}
	
	/**
	 * 从MS获取用户情感信息
	 * @author Y.Zhang
	 *
	 */
	private class UpLoadImageAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			uploadFile((String) params[0]);
			return 1;
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			emotionText.setText(emotion);
			mLoadNewsAsyncTack = new LoadNewsAsyncTack();
			mLoadNewsAsyncTack.execute(0, true);
			
			UpLoadUserEmotionAsyncTack upLoadUserEmotionAsyncTack = new UpLoadUserEmotionAsyncTack();
			upLoadUserEmotionAsyncTack.execute();
		}
	}
	
	/**
	 * 上传用户情感信息
	 * @author Y.Zhang
	 *
	 */
	private class UpLoadUserEmotionAsyncTack extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			int result = 1;
			result = uploadEmotion();
			return result;
		}
	
		@Override
		protected void onPostExecute(Integer result) {
			if(result == 0){
				showTip("上传用户情感信息成功！");
			}else{
				showTip("上传用户情感信息失败！");
			}
		}
	}
	
	public String uploadFile(String file) {
		String RequestURL = "https://api.projectoxford.ai/emotion/v1.0/recognize";  
        int TIME_OUT = 10*10000000; //超时时间 
        try {  
            URL url = new URL(RequestURL);   
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); conn.setReadTimeout(TIME_OUT); conn.setConnectTimeout(TIME_OUT);  
            conn.setDoInput(true); //允许输入流  
            conn.setDoOutput(true); //允许输出流  
            conn.setUseCaches(false); //不允许使用缓存   
            conn.setRequestMethod("POST"); //请求方式  
            //conn.setRequestProperty("Charset", "utf-8");   
            //设置编码   
            conn.setRequestProperty("Content-Type", "application/octet-stream");   
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", "44ad5ece8b004260b076ef962a3d96bb");  
            if(file!=null) {   
                /** * 当文件不为空，把文件包装并且上传 */  
                OutputStream outputSteam=conn.getOutputStream();   
                DataOutputStream dos = new DataOutputStream(outputSteam);   
                StringBuffer sb = new StringBuffer();   
                /*sb.append(PREFIX);   
                sb.append(BOUNDARY); sb.append(LINE_END);   
                *//**  
                * 这里重点注意：  
                * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件  
                * filename是文件的名字，包含后缀名的 比如:abc.png  
                */   
                /*sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);  
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);   */
                /*sb.append(LINE_END);  */ 
                //dos.write(sb.toString().getBytes());   
                InputStream is = new FileInputStream(file);  
                byte[] bytes = new byte[1024];   
                int len = 0;   
                while((len=is.read(bytes))!=-1)   
                {   
                   dos.write(bytes, 0, len);   
                }   
                is.close();    
                dos.flush();  
                /**  
                * 获取响应码 200=成功  
                * 当响应成功，获取响应的流  
                */   
                int res = conn.getResponseCode();   
                String message = conn.getResponseMessage();
                conn.getInputStream();
                String line = null;
                StringBuffer sBuffer = new StringBuffer();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(  
                        conn.getInputStream()));  
                while ((line = bReader.readLine()) != null) {  
                    sBuffer.append(line);  
                } 
                
                Log.e("response", "response-code:" +res+ " messsage:" + message + " sBuffer:" + sBuffer.toString());
				try {
					//emotionText.setText(sBuffer.toString());
					JSONObject jsonObject;
					jsonObject = new JSONObject(sBuffer.toString().substring(1, sBuffer.toString().length()-1));
					JSONObject dataObj = jsonObject.getJSONObject("scores");
					DecimalFormat df=new DecimalFormat("0.##");
					double anger = dataObj.getDouble("anger");
					Log.e("response", "anger:" + df.format(anger));
					
					double contempt = dataObj.getDouble("contempt");
					Log.e("response", "contempt:" + df.format(contempt));
					
					double disgust = dataObj.getDouble("disgust");
					Log.e("response", "disgust:" + df.format(disgust));
					
					double fear = dataObj.getDouble("fear");
					Log.e("response", "fear:" + df.format(fear));
					
					double happiness = 0;
					happiness = dataObj.getDouble("happiness");
					Log.e("response", "happiness:" + df.format(happiness));
					
					double neutral = 0;
					neutral = dataObj.getDouble("neutral");
					Log.e("response", "neutral:" + df.format(neutral));
					
					double sadness = 0;
					sadness = dataObj.getDouble("sadness");
					Log.e("response", "sadness:" + df.format(sadness));
					
					double surprise = dataObj.getDouble("surprise");
					Log.e("response", "surprise:" + df.format(surprise));
					
					emotion = "anger:" + df.format(anger) +"  contempt:" + df.format(contempt)+ "\n"+"disgust:" 
					+ df.format(disgust)+"  fear:" + df.format(fear)+ "\n"+"happiness:" + df.format(happiness)+ "\n"
					+"neutral:" + df.format(neutral)+ "\n"+"sadness:" + df.format(sadness)+"  surprise:" + df.format(surprise);
					if(happiness >= (neutral+sadness)){
						emotionValue = 1;
					}else{
						emotionValue = 2;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
                if(res==200)   
                {  
                return "1";   
                }  
            }   
        } catch (MalformedURLException e)   
        { e.printStackTrace(); }   
        catch (IOException e)   
        { e.printStackTrace(); }   
        return "0";   
        
    }
	
	/**
	 * 上传用户心情记录
	 * @return
	 */
	private int uploadEmotion() {

		// 本机:http://10.0.2.2:8080/web/getSpecifyCategoryNews
		// wifi局域网：192.168.220.1
		String url = "http://192.168.1.107:8080/newsWeb/UpdateEmotionHistory";
		String params = "userID=" + userID + "&emotionValue=" + emotionValue;
		SyncHttp syncHttp = new SyncHttp();

		try {
			// 通过Http协议发送Get请求，返回字符串
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			int retCode = jsonObject.getInt("ret");
			if (retCode == 0) {
				//showTip("上载用户情感信息成功！");
				return 0;
			} else {
				//showTip("上载用户情感信息失败！");
				return 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1; // 加载新闻失败
		}
	}
	
	private void showTip(String msg)
	{
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
}
