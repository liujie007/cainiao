package com.hncainiao.fubao.ui.fragment;

import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.healthshop.GoodsCarActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.MainMyPay;
import com.hncainiao.fubao.ui.activity.personalcenter.MyFollowActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.MyPatientLogo;
import com.hncainiao.fubao.ui.activity.personalcenter.PersonMyBank;
import com.hncainiao.fubao.ui.activity.personalcenter.PersonSettingActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PersonalMsgActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PhyOrderActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PhyTalActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.RegisOrderActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.UsuallyPatient;
import com.hncainiao.fubao.ui.activity.personalcenter.UsuallyPhyPerson;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.FileService;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
 
/**
 * @author liujie
 * @version 2015年4月15日 下午1:34:28
 * 
 *          个人中心
 */
public class PersonalCenterFragment extends Fragment {

	private Context mContext;

	private View view;

	/**
	 * | 设置
	 */
	private Button btnSet;

	/**
	 * | 个人信息
	 */
	private RelativeLayout rlPerMsg;

	/**
	 * | 我的关注
	 */
	private LinearLayout llMyFollow;
	/**
	 * | 我的支付
	 */
	
	private LinearLayout ll_person_mypay;

	/**
	 * | 挂号预约
	 */
	private RelativeLayout regisOrder;
	/**
	 * | 体检预约
	 */
	private RelativeLayout phyOrder;
	/**
	 * | 商场订单
	 */
	private RelativeLayout storeOrder;
	/**
	 * | 我的病历
	 */
	private RelativeLayout medRecord;
	/**
	 * | 体检报告
	 */
	private RelativeLayout phyReport;
	/**
	 * | 我的银行卡
	 */
	private RelativeLayout bankCard;
	/**
	 * | 常用就诊人
	 */
	private RelativeLayout commPatient;
	/**
	 * | 常用体检人
	 * 
	 */
	private RelativeLayout usuallyphyperson;
	
	/**
	 * | 购物车
	 * 
	 */
	private RelativeLayout reordercar;
	
	
	private TextView tvName,tvAddress;
	
	private ImageView imHead;

	Broadcast broadcast;
	private void setupView(View view) {
		((TextView) view.findViewById(R.id.title_txt)).setText("个人中心");
		((LinearLayout) view.findViewById(R.id.title_btn_left))
				.setVisibility(View.INVISIBLE);
		btnSet = (Button) view.findViewById(R.id.title_right_setting);
		btnSet.setVisibility(View.VISIBLE);
		rlPerMsg = (RelativeLayout) view.findViewById(R.id.rl_personal_msg);
		regisOrder = (RelativeLayout) view.findViewById(R.id.rl_person_regis_order);
		llMyFollow = (LinearLayout) view.findViewById(R.id.ll_person_myfollow);
		phyOrder = (RelativeLayout) view.findViewById(R.id.rl_person_phy_order);
		storeOrder = (RelativeLayout) view
				.findViewById(R.id.rl_person_store_order);
		medRecord = (RelativeLayout) view
				.findViewById(R.id.rl_person_medical_record);
		phyReport = (RelativeLayout) view
				.findViewById(R.id.rl_person_phy_record);
		bankCard = (RelativeLayout) view.findViewById(R.id.rl_person_bank_card);
		commPatient = (RelativeLayout) view
				.findViewById(R.id.rl_person_common_patient);
		usuallyphyperson=(RelativeLayout) view.
				findViewById(R.id.usually_phyperson);
		reordercar=(RelativeLayout) view.
				findViewById(R.id.rl_person_store_car);
		ll_person_mypay=(LinearLayout) view.findViewById(R.id.ll_person_mypay);	
		tvName=(TextView) view.findViewById(R.id.tv_person_name);
		tvAddress=(TextView) view.findViewById(R.id.tv_person_address);
		imHead=(ImageView) view.findViewById(R.id.iv_person_head);
	}

	private void addListener() {
		btnSet.setOnClickListener(l);
		rlPerMsg.setOnClickListener(l);
		llMyFollow.setOnClickListener(l);
		regisOrder.setOnClickListener(l);
		phyOrder.setOnClickListener(l);
		storeOrder.setOnClickListener(l);
		medRecord.setOnClickListener(l);
		phyReport.setOnClickListener(l);
		bankCard.setOnClickListener(l);
		commPatient.setOnClickListener(l);
		usuallyphyperson.setOnClickListener(l);
		ll_person_mypay.setOnClickListener(l);
		reordercar.setOnClickListener(l);
	}
   
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 if (!IsLogin.isLogin()) {
			 view = inflater.inflate(R.layout.bank, null);
			 startActivity(new Intent(getActivity(),LoginActivity.class));
			 getActivity().finish();
				//System.exit(0);
		
		 }else {
			 view = inflater.inflate(R.layout.fragment_personal_center, null);
				broadcast=new Broadcast();
				IntentFilter intentFilter =new IntentFilter();
				intentFilter.addAction(Constant.action_image);
				getActivity().registerReceiver(broadcast, intentFilter);
				mContext = getActivity();
				setupView(view);
				addListener();
				loadDate();
		}
			    
		
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (IsLogin.isLogin()) {
			getActivity().unregisterReceiver(broadcast);
		}
		
	}
	/**
	 * 	项目：FuBaoHealth
	 * 		@author liujie
	 *	日期：2015-5-21下午7:30:22
	 *  广播接受者更新头像
	 */
	class Broadcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bitmap bmp=	new FileService().readPhoto("imghead");
			  if (bmp!=null) {
				  imHead.setImageBitmap(bmp);
			}
			  if (!SharedPreferencesConfig.getStringConfig(mContext, "address").equals("")) {
				  tvAddress.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "address"));
					
			}
			  if (!SharedPreferencesConfig.getStringConfig(mContext, "nick").equals("")) {
				  tvName.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "nick"));
					
				}
		}
		
	}
	/**
	 * 得到头像
	 */
	private void getimage(String url)
	{
		if (NetworkUtil.isOnline(getActivity())) {
			
		
		AsyncHttpClient client = new AsyncHttpClient();  
		// 指定文件类型  
		String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };  
		// 获取二进制数据如图片和其他文件  
		client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) { 
			
			@Override
			public void onSuccess(byte[] binaryData) {
				// TODO Auto-generated method stub
			
			        // TODO Auto-generated method stub  
				  Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0,  
			                binaryData.length);
				  new FileService().savePhoto(bmp, "imghead");
				  if (bmp!=null) {
					  imHead.setImageBitmap(bmp);
				}
				 
			        
			  
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseData, Throwable error) {
				// TODO Auto-generated method stub
				//失败
			}
		});
	  }
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	  Log.i("fubaojiaok", "gengxing");
	  if (IsLogin.isLogin()) {
		  loadDate();
	  }
	  
	}
	/**
	 * 加载数据
	 */
	private void loadDate()
	{
		FileService fileService=new FileService();
		String jsonString;
		try {
			jsonString = fileService.read("member");
			if (!jsonString.equals("")) {
			 try {
				JSONObject jsonObject =new JSONObject(jsonString);
				if (!SharedPreferencesConfig.getStringConfig(mContext, "nick").equals("")) {
					 tvName.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "nick"));
				}
				else {
					 tvName.setText(""+jsonObject.getString("nickname"));
				}
				if (!SharedPreferencesConfig.getStringConfig(mContext, "address").equals("")) {
					 tvAddress.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "address"));
				}
				else {
					 tvAddress.setText(""+jsonObject.getString("address"));
				}
				
				
				 Bitmap btim=fileService.readPhoto("imghead");
				 if (!jsonObject.getString("avatar").equals("")||btim!=null) {
					
					 if (btim==null) {
						 getimage(Constant.URL_IMAGE_HOST_STRING+jsonObject.getString("avatar"));
							
					}
					 else {
						  imHead.setImageBitmap(btim);
					}
				}
			
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.title_right_setting: // 设置
				startActivity(new Intent(mContext,PersonSettingActivity.class));
				break;
			case R.id.rl_personal_msg: // 个人信息
			
				intent = new Intent(mContext, PersonalMsgActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_person_myfollow: // 我的关注
				intent = new Intent(mContext, MyFollowActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_person_mypay://我的支付
				 ToastManager.getInstance(getActivity()).showToast("功能正在开发，敬请期待！");
				/*intent=new Intent(mContext,MainMyPay.class);
				startActivity(intent);*/
				break;
				
			case R.id.rl_person_regis_order: // 挂号预约
				intent = new Intent(mContext, RegisOrderActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_person_phy_order: // 体检预约
				intent = new Intent(mContext, PhyOrderActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_person_store_order: // 商场订单
				startActivity(new Intent(mContext,MallOrderActivity.class));
				break;
				//购物车
			case R.id.rl_person_store_car:
				startActivity(new Intent(mContext,GoodsCarActivity.class));
				break;
			case R.id.rl_person_medical_record: // 我的病历
				intent=new Intent(mContext,MyPatientLogo.class);
				startActivity(intent);
				break;
			case R.id.rl_person_phy_record: // 体检报告
				intent = new Intent(mContext, PhyTalActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_person_bank_card: // 我的银行卡
				intent = new Intent(mContext, PersonMyBank.class);
				startActivity(intent);
				break;
			case R.id.rl_person_common_patient: // 常用就诊人
				intent=new Intent(mContext,UsuallyPatient.class);
				startActivity(intent);
				break;
			case R.id.usually_phyperson: // 常用体检人
				intent=new Intent(mContext,UsuallyPhyPerson.class);
				startActivity(intent);
			}
		}
	};
	
}
