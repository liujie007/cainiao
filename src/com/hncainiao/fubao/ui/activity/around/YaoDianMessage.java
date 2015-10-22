package com.hncainiao.fubao.ui.activity.around;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ZBapi;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.jmheart.net.NetworkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class YaoDianMessage extends BaseActivity{
	/**
	 * 药店详情
	 * */
	Context mContext;
	TextView other;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yaodianmessage);
		Initview();
		Addlisten();
		getnet();
		
	}

	private void getnet()
	{
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			ZBapi.getNearbyInfo(getIntent().getStringExtra("id"), handler);
		}
		else {
			showToastNotNet();
		}
	}
	AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
			
					JSONObject jsonObject=new JSONObject(content);
					if (jsonObject.getInt("err")==0) {
						//{"ret":{"id":"8","name":"湖南省肿瘤医院","type":"三甲医院","img":"554c85a12c750.jpg",
						//"remark":"湖南省肿瘤医院是所集医疗、科研、防治为一体的三级甲等肿瘤医院。医院位于长沙市湘江西岸岳麓山西北咸嘉湖畔。始建于1972年，医院占地155亩，建筑面...",
						//"createtime":"1430283273","status":"1","region_id":"197","address":"湖南省长沙市桐梓坡路283号","lat":"28.2235710000","lng":"112.9446060000","telphone":"0731-89762518",
						//"his":null,"his_hospital_id":null,"company_type":"2"},"err":0}
						BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+jsonObject.getJSONObject("ret").getString("img"), (ImageView)getView(R.id.im_logo_2));
						BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+jsonObject.getJSONObject("ret").getString("img"), (ImageView)getView(R.id.im_logo_1));
						((TextView)getView(R.id.tv_name)).setText(""+jsonObject.getJSONObject("ret").getString("name"));
						((TextView)getView(R.id.tv_address)).setText(""+jsonObject.getJSONObject("ret").getString("address"));
						((TextView)getView(R.id.tv_phone)).setText(""+jsonObject.getJSONObject("ret").getString("telphone"));
						((TextView)getView(R.id.tv_info)).setText(""+jsonObject.getJSONObject("ret").getString("remark"));
							
					}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToastNetTime();
		};
	};

	private void Initview() {
		// TODO Auto-generated method stub
		mContext=this;
		other=(TextView) findViewById(R.id.jituanjianjie);
		TextView titlte=(TextView) findViewById(R.id.title_txt);
		titlte.setText("药店详情");
		((LinearLayout)getView(R.id.lin_call_phone)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				callPhone(((TextView)getView(R.id.tv_phone)).getText().toString());
			}
		});
	}

	private void Addlisten() {
		other.setOnClickListener(new l());
		
	}
	class l implements View.OnClickListener{

		Intent intent=null;
		@Override
		public void onClick(View arg0) {
			if(arg0.getId()==R.id.jituanjianjie){
				intent=new Intent(mContext,OtherShopActivity.class);
				intent.putExtra("name", ((TextView)getView(R.id.tv_name)).getText().toString());
				startActivity(intent);
			}
			
		}
		
	};
}
