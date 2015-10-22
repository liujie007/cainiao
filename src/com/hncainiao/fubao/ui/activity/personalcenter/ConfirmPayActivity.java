package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ConfiremPayAdapt;

public class ConfirmPayActivity extends BaseActivity {
	
	
	Context mContext;
	List<Map<String, Object>>mList;
	ListView listView;
	ConfiremPayAdapt adapt;
	Button btn_next;
	RelativeLayout select_bank,select_hospital;
	private ImageView ivBank, ivHospital;
	
	/**
	 * type=1选择银行卡支付
	 * type=2 选择到院支付
	 * */
	int type=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmpay);
		Initview();
		getData();
	}
	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<2;i++){
			map=new HashMap<String, Object>();
			map.put("name", "小刘");
			map.put("data", "2015-19-23");
			map.put("department", "眼科");
			map.put("idcard", "430524199209195996");
			map.put("hospiatl", "湘雅附一");
			map.put("zhenchaxiang", "诊查费>");
			map.put("jiti", "眼底检查");
			mList.add(map);
			
		}
		adapt=new ConfiremPayAdapt(mContext);
		adapt.setList(mList);
		listView.setAdapter(adapt);
		
		
		
	}
	private void Initview() {
		mContext=this;
		setTitle("确认支付");
		listView=(ListView) findViewById(R.id.list);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
		btn_next=(Button) findViewById(R.id.btn_confirm_order);
		btn_next.setOnClickListener(new l());
		ivBank = (ImageView) findViewById(R.id.iv_select_bank);
		ivHospital = (ImageView) findViewById(R.id.iv_select_hospital);
		select_hospital=(RelativeLayout) findViewById(R.id.rl_select_hospital);
        select_bank=(RelativeLayout) findViewById(R.id.rl_select_bank);
        select_hospital.setOnClickListener(new l());
        select_bank.setOnClickListener(new l());
	}
	private  class l implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=null;
			switch (arg0.getId()) {
			case R.id.btn_confirm_order:
				intent=new Intent(mContext,SuccessPayActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.rl_select_hospital:
				    type=2;
					ivHospital.setImageResource(R.drawable.img_select);
					ivBank.setImageResource(R.drawable.img_unselect);
					select_hospital.setBackgroundResource(R.drawable.corners_selected_bg);
					select_bank.setBackgroundResource(R.drawable.corners_unselected_bg);
				break;
			case R.id.rl_select_bank:
				type=1;
				ivBank.setImageResource(R.drawable.img_select);
				ivHospital.setImageResource(R.drawable.img_unselect);
				select_bank.setBackgroundResource(R.drawable.corners_selected_bg);
				select_hospital.setBackgroundResource(R.drawable.corners_unselected_bg);
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	

}
