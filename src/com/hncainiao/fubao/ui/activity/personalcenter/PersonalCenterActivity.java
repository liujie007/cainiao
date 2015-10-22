package com.hncainiao.fubao.ui.activity.personalcenter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.FileService;

/**
 * @author zhaojing
 * @version 2015年4月15日 上午10:35:13
 * 
 *          个人中心
 */
public class PersonalCenterActivity extends BaseActivity {

	private Context mContext;
	private TextView tvName,tvAddress;
	
	/**
	 * 个人信息
	 */
	private RelativeLayout rlPerMsg;
	private void setupView() {
		setTitle("个人中心");
		rlPerMsg = (RelativeLayout) findViewById(R.id.rl_personal_msg);
		tvName=(TextView)findViewById(R.id.tv_person_name);
		tvAddress=(TextView)findViewById(R.id.tv_person_address);
	}

	private void addListener() {
		rlPerMsg.setOnClickListener(l);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		mContext = this;
		setupView();
		addListener();
		loadDate();
	}
	/**
	 * 加载数据
	 */
	private void loadDate()
	{
	 try {
		String jsonString=new FileService().read("member");
		if (!jsonString.equals("")) {
			//
			try {
				JSONObject jsonObject =new JSONObject(jsonString);
				 tvName.setText(""+jsonObject.getString("name"));
				 tvAddress.setText(""+jsonObject.getString("address"));
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
			case R.id.rl_personal_msg: // 个人信息
				intent = new Intent(mContext, PersonalMsgActivity.class);
				startActivity(intent);
				break;
			
				
			default:
				break;
			}
		}
	};
}
