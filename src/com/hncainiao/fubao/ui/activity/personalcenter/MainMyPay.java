package com.hncainiao.fubao.ui.activity.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class MainMyPay extends BaseActivity {
	 
	Context mContext;
	LinearLayout pay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmy_pay);
		InitView();
		Initlisten();
	}

	private void Initlisten() {
		pay.setOnClickListener(new l());
		
	}

	private void InitView() {
		mContext=this;
		setTitle("我的支付");
		pay=(LinearLayout) findViewById(R.id.zhenzhongzhifu);
		
	}
	class l implements OnClickListener{
		Intent intent=null;
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.zhenzhongzhifu:
				intent=new Intent(mContext,MyPayActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;

			default:
				break;
			}
			
		}
		
	}
	

}
