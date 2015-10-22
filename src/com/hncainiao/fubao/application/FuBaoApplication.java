package com.hncainiao.fubao.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;

import com.baidu.mapapi.SDKInitializer;
import com.jmheart.base.BaseApplication;
import com.jmheart.net.ApiHttpClient;
import com.loopj.android.http.AsyncHttpClient;

/**
 * @author liujie
 * @version 2015年04月01日 上午09:19:22
 * 
 *          全局存储域
 */
public class FuBaoApplication extends BaseApplication {

	public static final String TAG = "FuBaoApplication";

	private HashMap<String, Object> map = new HashMap<String, Object>();
	/**
	 * 调试模式
	 */
	public static boolean appislog=true;
	/**
	 * 主机域名
	 */
	public static String appHOST="http://wx.zgcainiao.com";
	/**
	 * 接口地址
	 */
	public static String appAPI_URL="http://wx.zgcainiao.com/index.php/shop/";

	/**
	 * FuBaoApplication实例 单例模式
	 */
	private static FuBaoApplication INSTANCE;

	public static void setInstance(FuBaoApplication i) {
		INSTANCE = i;
	}

	public static FuBaoApplication getInstance() {
		return INSTANCE;
	}

	public ArrayList<Activity> activities = new ArrayList<Activity>();

	public List<HashMap<String, Activity>> aHashMaps =new ArrayList<HashMap<String,Activity>>();

	/**
	 * 退出所有的Activity
	 */
	public void quitAllActivity() {
		for (Activity activity : activities) {
			activity.finish();
		}
		// 把应用全清了，在程序列表启动程序会执行applicatin.onCreate()
		System.exit(0);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setInstance(this);
		config();
		SDKInitializer.initialize(getApplicationContext());
}

	private void config()
	{
		HOST=appHOST;
		API_URL=appAPI_URL;
		islog=appislog;
	}
	/**
	 * @param key
	 * @param object
	 * 数据存
	 */
	public void set(String key, Object object) {
		map .put(key, object);
	}

	/**
	 * @param key
	 * @return
	 * 数据取
	 */

	public Object get(String key) {
		return map.get(key);
	}
	

	/**
	 * @param i
	 */
	int intdate=100;
	/**
	 * @param i
	 * 保存整數
	 */
	public void  setInt(int i)
	{
		this.intdate=i;
	}
	/**
	 * @return
	 * 得到整數
	 */
	public int getInt()
	{
		return intdate;
		
	}
}
