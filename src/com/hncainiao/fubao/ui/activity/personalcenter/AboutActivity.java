package com.hncainiao.fubao.ui.activity.personalcenter;

import android.os.Bundle;
import android.webkit.WebView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class AboutActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		setTitle("关于我们");
		WebView webView =(WebView)findViewById(R.id.web_about);
		//webView.loadUrl("file:///android_asset/about.html");
		webView.loadUrl("http://gw.zgcainiao.com/about us.html");
	}
}
