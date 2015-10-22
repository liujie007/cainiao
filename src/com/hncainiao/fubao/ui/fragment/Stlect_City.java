package com.hncainiao.fubao.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Kind;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.Juti_Zm_Adapter;
import com.hncainiao.fubao.ui.adapter.Setcity_Adapter;
import com.hncainiao.fubao.ui.sqlite.Create_sqlite_datas;
public class Stlect_City extends BaseActivity {
	Context mContext;
	LinearLayout city,Jilu,ju_tiZm;
	SQLiteDatabase db;
	String current_city;
	GridView Jilu_grid,Zm_grid,Show_ZM_grid;
	private String[] ZM = { "A", "B", "C", "D", "E", "F", "G", "H",
 			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
 			"V", "W", "X", "Y", "Z" };
	    private  List<Kind>groups;
	    private  List<String>data,jiludatas; 
	    TextView First_ZM,tvCity;
       int tag;//点击的标志位 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_city);
		mContext=this;
		initView();
		getData_ZM();
	    IsHaveJilu();//
	}
	private void IsHaveJilu() {
		
		  jiludatas=new ArrayList<String>();
		  Cursor re = db.rawQuery("select count(*) as total from Jilu where _id>?", new String[]{"0"});
		  re.moveToFirst();
		  int nums=re.getInt(re.getColumnIndex("total"));
		if(nums>0){
			Jilu.setVisibility(ViewGroup.VISIBLE);
			Jilu_grid.setVisibility(ViewGroup.VISIBLE);
			Cursor res = db.rawQuery("select * from Jilu where _id>? order by onclick desc limit ?", new String[]{"0","6"});
			while(res.moveToNext()){
				String city=res.getString(res.getColumnIndex("citynm"));
				jiludatas.add(city);
    		} 
			Juti_Zm_Adapter adapter=new Juti_Zm_Adapter(mContext, jiludatas);
			Jilu_grid.setAdapter(adapter);
			Jilu_grid.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if(!jiludatas.get(arg2).equals
							(SharedPreferencesConfig.getStringConfig(mContext, "city"))){
						SharedPreferencesConfig.saveStringConfig(mContext, "off_name", "");
						SharedPreferencesConfig.saveStringConfig(mContext, "hospital_name", "");
					}
				 SharedPreferencesConfig.saveStringConfig(mContext,"address", jiludatas.get(arg2));
				SharedPreferencesConfig.saveStringConfig(mContext,"city", jiludatas.get(arg2));
				
				

				db.execSQL("update Jilu set onclick=onclick+1 where citynm='"+jiludatas.get(arg2)+"'");
				    finish();
				}
				
			});
			if(nums==0){
				Jilu.setVisibility(ViewGroup.GONE);
				Jilu_grid.setVisibility(ViewGroup.GONE);
			}
		}
	}
	Setcity_Adapter adapter;
	private void getData_ZM() {
		groups=new ArrayList<Kind>();
		for(int i=0;i<ZM.length;i++){
			groups.add(new Kind(i+"", ZM[i]));	
	}
		 adapter=new Setcity_Adapter(mContext, groups);
		Zm_grid.setAdapter(adapter);
		 getJUti_zmCity();
	  	adapter.setSelet(0);
	  	adapter.notifyDataSetChanged();
		Zm_grid.setOnItemClickListener(new click());
	}
	private void initView() {
		setTitle("选择城市");
		
		RelativeLayout	selectCity = (RelativeLayout) findViewById(R.id.rl_select_city);
		selectCity.setVisibility(View.VISIBLE);
		selectCity.setEnabled(false);
		// 城市名
		Create_sqlite_datas sqlite_datas = new Create_sqlite_datas(mContext);
		db = sqlite_datas.getWritableDatabase();
		tvCity = (TextView) findViewById(R.id.tv_city);
		current_city=SharedPreferencesConfig.getStringConfig(mContext, "city");
		First_ZM=(TextView) findViewById(R.id.First_ZM);
		
		city=(LinearLayout) findViewById(R.id.city);
		Jilu=(LinearLayout) findViewById(R.id.Jilu);
		ju_tiZm=(LinearLayout)findViewById(R.id.ju_tiZm);
		
		Zm_grid=(GridView) findViewById(R.id.Zm_grid);
		Show_ZM_grid=(GridView) findViewById(R.id.Show_ZM_grid);
		Jilu_grid=(GridView) findViewById(R.id.Jilu_grid);

		city.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				location();
				
			}
		});
	
		
	}

	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		current_city=arg0.getCity();
		SharedPreferencesConfig.saveStringConfig(mContext, "city",current_city);
   	    tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city")); 
   	   SharedPreferencesConfig.saveStringConfig(mContext,"address", current_city);
		
   	    finish();
		super.onReceiveLocation(arg0);
	}
	
	@Override
	protected void onResume() {
		 String str = null ;
		SharedPreferencesConfig.getStringConfig(mContext, "city");
         if(SharedPreferencesConfig.getStringConfig(mContext, "city").length()>4){
        	 str= SharedPreferencesConfig.getStringConfig(mContext, "city").substring(0,4)+"...";
        	 tvCity.setText(str);
         }else{
        	 tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city")); 
         }
		

		super.onResume();
	}
	
class click implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			First_ZM.setText(ZM[arg2]);
			tag= Integer.parseInt(groups.get(arg2).getId()) ;
			
		  	getJUti_zmCity();
		  	 //---
		  	adapter.setSelet(arg2);
		  	adapter.notifyDataSetChanged();
		  	
			System.out.println(tag);
	
	}
	
	
}
	private void getJUti_zmCity() {
	data=new ArrayList<String>();
	for(int i=0;i<Constant.ary[tag].length;i++){
		String city=Constant.ary[tag][i];
		data.add(city);
	}
	ju_tiZm.setVisibility(ViewGroup.VISIBLE);
	Juti_Zm_Adapter adapter=new  Juti_Zm_Adapter(mContext, data);
	Show_ZM_grid.setVisibility(ViewGroup.VISIBLE);
	Show_ZM_grid.setAdapter(adapter);
	Show_ZM_grid.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Cursor re = db.rawQuery("select count(*) as total from Jilu where _id>0", new String[]{});
			   re.moveToFirst();
			   int nums=re.getInt(re.getColumnIndex("total"));
			   //showToast(nums+"");
			   if(nums<6){
				   String city = data.get(arg2);
					 Cursor res = db.rawQuery("select count(*) as total from Jilu where citynm=?", new String[]{city} );
					  res.moveToFirst();
					   int num=res.getInt(re.getColumnIndex("total"));
					   if(num>0){
						   db.execSQL("update Jilu set onclick=onclick+1 where citynm='"+city+"'");
						   
					   }else{
						   db.execSQL("insert into Jilu(citynm,onclick) values('"+city+"','1') ");
					   }  
				   
			   }else if(nums>=6){
				   			db.execSQL("delete from Jilu where citynm=?",new String[]{jiludatas.get(0)});
				   //db.execSQL("delete from Jilu where _id>?",new String[]{"0"});
				  // showToast("刪除了"+jiludatas.get(0));
				   String city = data.get(arg2);
					 Cursor res = db.rawQuery("select count(*) as total from Jilu where citynm=?", new String[]{city} );
					  res.moveToFirst();
					   int num=res.getInt(re.getColumnIndex("total"));
					   if(num>0){
						   db.execSQL("update Jilu set onclick=onclick+1 where citynm='"+city+"'");
						   
					   }else{
						   db.execSQL("insert into Jilu(citynm,onclick) values('"+city+"','1') ");
					   }  
				  
			   }
			   if(!data.get(arg2).equals
						 (SharedPreferencesConfig.getStringConfig(mContext, "city"))){
					SharedPreferencesConfig.saveStringConfig(mContext, "off_name", "");
					SharedPreferencesConfig.saveStringConfig(mContext, "hospital_name", "");
				 }
				 SharedPreferencesConfig.saveStringConfig(mContext,"city", data.get(arg2));
				 SharedPreferencesConfig.saveStringConfig(mContext,"address", data.get(arg2));
				
				 
				 finish();
	}
		
	});
	
	
}

}
