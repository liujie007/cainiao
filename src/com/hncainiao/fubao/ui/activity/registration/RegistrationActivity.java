package com.hncainiao.fubao.ui.activity.registration;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorListActivity;
import com.hncainiao.fubao.ui.activity.doctor.History_Doctor;
import com.hncainiao.fubao.ui.activity.hospital.SelectHospitalActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.MyFollowActivity;
import com.hncainiao.fubao.ui.fragment.Stlect_City;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;

/**
 * @author liujie
 * @version 2010年8月7日 上午9:21:00
 *          申请挂号
 */
public class RegistrationActivity extends BaseActivity {

	private static final String TAG = "RegistrationActivity";
	
	private Context mContext;
	/**
	 * 选择城市
	 */
	private RelativeLayout selectCity;
	private TextView tvCity;
	private String locationCity = ""; // 定位到的城市
            String  changeCity="";
	private RelativeLayout selectHospitai; // 选择医院
	private RelativeLayout selectOffices; // 选择科室
	
	private ImageButton linDaozhen;
	LinearLayout search;//搜索
	//百度地图定位
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	
	ImageButton Intelligent_guide,history_doctor,my_Concern;
	Button start;
	Intent intent = null;

	private void setupView() {
		setTitle("申请挂号");
		linDaozhen=(ImageButton)findViewById(R.id.Intelligent_guide);
		selectCity = (RelativeLayout) findViewById(R.id.rl_select_city);
		selectCity.setVisibility(View.VISIBLE);
		// 城市名
		tvCity = (TextView) findViewById(R.id.tv_city);
		//默认城市为长沙
		tvCity.setText("长沙市");
	//	tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		selectHospitai = (RelativeLayout) findViewById(R.id.rl_select_hospital);
		// 选择科室
		search=(LinearLayout) findViewById(R.id.ll_serach);
		selectOffices = (RelativeLayout) findViewById(R.id.rl_select_offices);
		TextView front_hospital=(TextView) findViewById(R.id.front_hospital);
		if(SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").equals("")){
			front_hospital.setText("请选择医院");
		}else{
			front_hospital.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "hospital_name"));
		}
		TextView front_kesi=(TextView) findViewById(R.id.front_keshi);
		if(SharedPreferencesConfig.getStringConfig(mContext, "hospital_id").equals("")){
			front_kesi.setText("请选择科室");
		}else if(!SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").equals("")&&
				SharedPreferencesConfig.getStringConfig(mContext, "hospital_id").equals("")	){
			front_kesi.setText("请选择科室");
		}else if(SharedPreferencesConfig.getStringConfig(mContext, "off_name").equals("")){
			front_kesi.setText("请选择科室");
		}
		
		else{
			front_kesi.setText(SharedPreferencesConfig.getStringConfig(mContext, "off_name")+"");

		}
	    //关注
		my_Concern=(ImageButton) findViewById(R.id.my_Concern);
		my_Concern.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (IsLogin.isLogin()) {
					intent=new Intent(mContext,MyFollowActivity.class);
					startActivity(intent);	
				}
				else {
					intent=new Intent(mContext,LoginActivity.class);
					startActivity(intent);	
				}
				 
			}
		});	
		//开始挂号
		start=(Button) findViewById(R.id.start_quee);
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String hospital_name=SharedPreferencesConfig.getStringConfig(mContext, "hospital_name");
				String hospital_id=SharedPreferencesConfig.getStringConfig(mContext, "hospital_id");
				if(hospital_name.equals("")&&SharedPreferencesConfig.getStringConfig(mContext, "off_id").equals("")){
					intent = new Intent(mContext, SelectHospitalActivity.class);
					if(intent!=null){
						intent.putExtra("flag", "registration");
						startActivity(intent);
					}
					

				}else if(!hospital_name.equals("")&&SharedPreferencesConfig.getStringConfig(mContext, "off_id").equals("")){
					intent = new Intent(mContext, SelectOfficesActivity.class);
					if(intent!=null){
						intent.putExtra("hospital_name",hospital_name);
						intent.putExtra("hospital_id",hospital_id);	
						startActivity(intent);
					}
					
				}else if(!hospital_name.equals("")&&!SharedPreferencesConfig.getStringConfig(mContext, "off_id").equals("")){
					intent=new Intent(mContext,DoctorListActivity.class);
					if(intent!=null){
						intent.putExtra("Offices_id", SharedPreferencesConfig.getStringConfig(mContext, "off_id")+"");
						intent.putExtra("offices",  SharedPreferencesConfig.getStringConfig(mContext, "off_name")+"");
						startActivity(intent);
					}
					
					
				}
			}
		});
		
		//搜索
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 intent=new Intent(mContext,Registration_search.class);
				 startActivity(intent);
			}
		});
		//历史医生
		history_doctor=(ImageButton) findViewById(R.id.history_doctor);
		history_doctor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 intent=new Intent(mContext,History_Doctor.class);
				 startActivity(intent);
				
			}
		});		
	}

	private void addListener() {
		selectCity.setOnClickListener(listener);
		selectHospitai.setOnClickListener(listener);
		selectOffices.setOnClickListener(listener);
		linDaozhen.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_apply_registration);
		mContext = this;
		setupView();
		addListener();
		if(!Constant.isLocation||tvCity.getText().toString().equals("")){
			GetCity();
		}
	}
	
	@Override
	public void leftbuttonclick(View view) {
		// TODO Auto-generated method stub
		 Intent intent=new Intent (mContext,MainActivity.class);
		 FuBaoApplication.getInstance().setInt(0);
		 startActivity(intent);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		String str=null;
		//tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		 if(SharedPreferencesConfig.getStringConfig(mContext, "city").length()>4){
        	 str= SharedPreferencesConfig.getStringConfig(mContext, "city").substring(0,4)+"...";
        	 tvCity.setText(str);
         }else{
        	 tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city")); 
         }
	
		TextView front_hospital=(TextView) findViewById(R.id.front_hospital);
		if(SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").equals("")){
			front_hospital.setText("请选择医院");
		}else{
			front_hospital.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "hospital_name"));
		}
		
		TextView front_kesi=(TextView) findViewById(R.id.front_keshi);
		if(SharedPreferencesConfig.getStringConfig(mContext, "hospital_id").equals("")){
			front_kesi.setText("请选择科室");
		}else if(!SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").equals("")&&
				SharedPreferencesConfig.getStringConfig(mContext, "hospital_id").equals("")	){
			front_kesi.setText("请选择科室");
		}else if(SharedPreferencesConfig.getStringConfig(mContext, "off_name").equals("")){
			front_kesi.setText("请选择科室");
		}
		
		else{
			front_kesi.setText(SharedPreferencesConfig.getStringConfig(mContext, "off_name")+"");

		}
		super.onResume();
	}
	private void GetCity() {
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {
		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_select_city: // 选择城市
				intent=new Intent(mContext,Stlect_City.class);
				startActivity(intent);

				break;
			case R.id.rl_select_hospital: // 选择医院
				intent = new Intent(mContext, SelectHospitalActivity.class);
				if(intent!=null){
					intent.putExtra("flag", "registration");
					startActivity(intent);
				}
				
				break;
			case R.id.rl_select_offices: // 选择科室
				String hospital_name=SharedPreferencesConfig.getStringConfig(mContext, "hospital_name");
				String hospital_id=SharedPreferencesConfig.getStringConfig(mContext, "hospital_id");
				if(hospital_name.equals("")){
					intent = new Intent(mContext, SelectHospitalActivity.class);
					if(intent!=null){
						intent.putExtra("flag", "registration");
						startActivity(intent);
					}
					

				}else{
					intent = new Intent(mContext, SelectOfficesActivity.class);
					if(intent!=null){
						intent.putExtra("hospital_name",hospital_name);
						intent.putExtra("hospital_id",hospital_id);
						startActivity(intent);
					}
					
				}
				break;
			case R.id.Intelligent_guide:
				intent=new Intent(mContext,IntelligentGuideActivity.class);
				startActivity(intent);
				//showToast("我们的程序员正在加班加点开发，敬请期待");
				break;
			}
		}
	};

	/**
	 * 获取当前的城市
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			locationCity =location.getCity();
			tvCity.setText(locationCity);
			SharedPreferencesConfig.saveStringConfig(mContext, "city",locationCity);
			if(!SharedPreferencesConfig.getStringConfig(mContext, "city").equals("")){
				Constant.isLocation = true;
			}else{
				Constant.isLocation = false;

			}
			
			HashMap<String, Object> has = new HashMap<String, Object>();
			has.put("lng", location.getLongitude());
			has.put("lat", location.getLatitude());
			has.put("city", location.getCity());
			setMap(has, "location");
			mLocClient.stop();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mLocClient!=null){
			mLocClient.stop();
		}
		
	}
	


	
	
}
