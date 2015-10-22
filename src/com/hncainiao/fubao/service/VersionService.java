package com.hncainiao.fubao.service;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.UpDate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/** 
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-8下午5:49:49
 *  版本更新
 */
public class VersionService extends Service {

	File file;
	boolean openflag=true;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (openflag) {
		//	getNewVersion();
			openflag=false;
			Log.i("fubao", "版本更新关闭了");
		}
		Log.i("fubao", "服务开启了");
		return super.onStartCommand(intent, flags, startId);
	}
//	// 从服务器获取最新版本
//		public void getNewVersion() {
//			Log.i("fubao", "服务getNewVersion");
//			if (NetworkUtil.isOnline(getApplicationContext())) {
//				AsyncHttpClient versionHttp = new AsyncHttpClient();
//				versionHttp.setTimeout(5000);
//				versionHttp.post(Constant.upversion, new AsyncHttpResponseHandler() {
//					@Override
//					public void onStart() {
//						// TODO Auto-generated method stub
//						super.onStart();
//					}
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						  if (!new String(responseBody).equals("")) {
//							try {
//								//{"data":{"id":"1","version":"1.0","file":"/Uploads/App/FuBaoHealth_1.0.apk","msg":"初始化版本一","createtime":"1430791018"},"err":0}
//								JSONObject jsonObject =new JSONObject(new String(responseBody));
//								 	 if (jsonObject.getInt("err")==0) {
//											//解析到数据
//								 	  	Log.i("fubao", "---返回数据-"+new String(responseBody));
//										 JSONObject jObject =jsonObject.getJSONObject("data");
//								 		if (jObject.getString("version")!=null&&!jObject.getString("version").equals("")) {
//								 			if (!jObject.getString("version").equals(FuBaoApplication.getInstance().getVersionCode(getApplicationContext()))) {
//												//更新版本
//									  			makeAlert(getApplicationContext(), jObject.getString("msg"), Constant.host+jObject.getString("file"));
//											}
//										}
//								 		 
//									}
//								 	 
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//				}
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers,
//						byte[] responseBody, Throwable error) {
//				     Log.i("fubao", "网络超时");
//				}
//			});
//			}
//			
//		}
		/**
		 * 打开软件安装包
		 */
		Handler hd =new Handler()
		{
			public void handleMessage(android.os.Message msg) {
				if (file!=null) {
					new UpDate().openFile(file, getApplicationContext());
					stopSelf();
				}
			
			};
		};
		/**
		 * @param context
		 * @param str
		 * @param url
		 * 弹出更新对话框
		 */
		String urlString;
		public void makeAlert(Context context,String str,final String url)
		{
			    urlString=url;
			    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);  
			    builder.setTitle("提示");  
			    builder.setMessage(str+"");  
			    builder.setNegativeButton("取消", new OnClickListener() {  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  
			  
			        }  
			    });  
			    builder.setPositiveButton("确定", new OnClickListener() {  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  
			        	 Toast.makeText(getApplicationContext(), "新版本正在下载请稍候！", 1).show();
			             new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
						    	  file= new UpDate().downLoadFile(urlString, getApplicationContext());
						    	  Message mgMessage =new Message();
							      hd.sendMessage(mgMessage);
							}
						}).start();
			        }  
			    });  
			    final AlertDialog dialog = builder.create();  
			    dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));  
			    dialog.show();  
		}
		

}
