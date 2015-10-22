package com.hncainiao.fubao.ui.activity.doctorConsultation;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class Freeconsultation extends BaseActivity {
	
	  Context mContext;
	  LinearLayout setoff;
	  EditText age,editview;
	  String genger;
	  ImageView pic1,pic2,pic3,pic4,pic5;
	  
	  
	 
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.freezixun_layout);
		Initview();
		
	}






	private void Initview() {
		mContext=this;
		setoff=(LinearLayout) findViewById(R.id.ll_off);
		age=(EditText) findViewById(R.id.age);
		editview=(EditText) findViewById(R.id.editview);
		pic1=(ImageView) findViewById(R.id.pic1);
		pic2=(ImageView) findViewById(R.id.pic2);
		pic3=(ImageView) findViewById(R.id.pic3);
		pic4=(ImageView) findViewById(R.id.pic4);
		pic5=(ImageView) findViewById(R.id.pic5);

	}
	

}
