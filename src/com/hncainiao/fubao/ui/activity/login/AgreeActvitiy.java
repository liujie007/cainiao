package com.hncainiao.fubao.ui.activity.login;


import android.os.Bundle;
import android.webkit.WebView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.jmheart.net.LoadWebUrl;

public class AgreeActvitiy  extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agree_activity);
		setTitle("注册协议");
		LoadWebUrl.showHtml("www/fubao_register.html", ((WebView)findViewById(R.id.web_argee)));
	}
}
