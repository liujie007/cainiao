package com.hncainiao.fubao.ui.activity.personalcenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.fragment.Stlect_City;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.SelectPicPopupWindow;
import com.hncainiao.fubao.utils.FileService;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.SystemMethod;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2015年4月15日 下午6:21:39
 * 
 *          个人信息
 */
/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-27下午3:08:24
 */
public class PersonalMsgActivity extends BaseActivity {

	private static final String TAG = "PersonalMsgActivity";

	private Context mContext;

	/**
	 * 修改头像
	 */
	private SelectPicPopupWindow selectPicPopupWindow;//弹出拍照，或者相册选择的下面选择

	private RelativeLayout rlChangeHead;

	private ImageView ivHead;
	
	private Button btnOutLogin;
	private Bitmap bitm;
	public static final int phone=101;
	public static final int pwd=201;
	public static final int nick=301;
	public static final int address=234;

	Dialog dialog;
	/**
	 * 弹出PopupWindow后 背景变暗
	 */
	private RelativeLayout mCanversLayout;

	private File sdcardTmpFile;

	private String imageName = "";

	/**
	 * 初始化图片保存的路径和名字
	 */
	private void initImgPath() {
		sdcardTmpFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/", "tmp_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpg");
		imageName = Constant.ROOT_PATH + "/"
				+ String.valueOf(System.currentTimeMillis() + ".jpg");
	}

	private void setupView() {
		setTitle("个人信息");
		btnOutLogin=(Button)findViewById(R.id.btn_out);
		ivHead = (ImageView) findViewById(R.id.iv_person_msg_head);
		mCanversLayout = (RelativeLayout) findViewById(R.id.rl_canvers);
		rlChangeHead = (RelativeLayout) findViewById(R.id.rl_change_head);
		
		((RelativeLayout)findViewById(R.id.re_modify_address)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.re_modify_nick)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.re_modify_phone)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.re_modify_pwd)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.re_modify_sex)).setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	  switch (arg0.getId()) {
	case R.id.re_modify_address:
		// startActivityForResult(new Intent(PersonalMsgActivity.this,ModifyAddressActivity.class),address);
		Intent intent=new Intent(this,Stlect_City.class);
		startActivityForResult(intent,234);
		break;
	case R.id.re_modify_nick:
		startActivityForResult(new Intent(PersonalMsgActivity.this,ModifyNickActivity.class),nick);
			
		break;
	case R.id.re_modify_phone:
		startActivityForResult(new Intent(PersonalMsgActivity.this,ModifyPhoneActivity.class),phone);
			
		break;
	case R.id.re_modify_pwd:
		startActivityForResult(new Intent(PersonalMsgActivity.this,ModifyPwdActivity.class),pwd);
			
		break;
	case R.id.re_modify_sex:
		 //自定义对话框
		choseSexDialog();
		break;

	case R.id.tv_wem:
		modifySex("女");
		dialog.dismiss();
		break;
	case R.id.tv_mem:
		modifySex("男");
		dialog.dismiss();
		break;
	default:
		break;
	}
		
	}

	/**
	 * 修改地址
	 */
	private void modifyAddress(final String address)
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("address", address);
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
		
			httClient.post(Constant.url_modify_address,params, new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								showToast("修改地址成功！");
								SharedPreferencesConfig.saveStringConfig(PersonalMsgActivity.this, "address",address );
								((TextView)findViewById(R.id.tv_person_msg_address)).setText(address);
							}
							else {
								showToast("修改地址失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						showToast("修改参数失败！");
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToastNetTime();
				}
			});
		}
		else {
			showToastNotNet();
		}
	}
	
	/**
	 * 修改性别
	 */
	private void modifySex(final String sexsting)
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			if (sexsting.equals("男")) {
				params.put("gender", 1);
			}
			else
			{
				params.put("gender", 0);
			}
		
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
		
			httClient.post(Constant.url_modify_sex,params, new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								showToast("修改性别成功！");
								SharedPreferencesConfig.saveStringConfig(PersonalMsgActivity.this, "sex",sexsting );
								((TextView)findViewById(R.id.tv_person_msg_sex)).setText(sexsting);
							}
							else {
								showToast("修改性别失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						showToast("修改参数失败！");
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToastNetTime();
				}
			});
		}
		else {
			showToastNotNet();
		}
	}
	/**
	 * 性别选择框
	 */
	private void choseSexDialog()
	{
		View view =LayoutInflater.from(this).inflate(R.layout.chose_sex_diaglog, null);
		TextView tvMem=(TextView)view.findViewById(R.id.tv_mem);
		TextView tvWem=(TextView)view.findViewById(R.id.tv_wem);
		tvMem.setOnClickListener(this);
		tvWem.setOnClickListener(this);
		 dialog = new Dialog(this);
		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 dialog.setContentView(view);
		 dialog.show();
		
	}
	/**
	 * 添加监听事件
	 */
	private void addListener() {
		rlChangeHead.setOnClickListener(l);
		//注销登陆
		btnOutLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Constant.isLocation=false;
				new FileService().delePhoto("imghead");
				SharedPreferencesConfig.saveStringConfig(mContext, "member_id", "");
				SharedPreferencesConfig.clear(PersonalMsgActivity.this);
				Intent intent=new Intent(mContext,LoginActivity.class);
				startActivity(intent);
			    finish();
			    FuBaoApplication.getInstance().aHashMaps.get(0).get("MainActivity").finish();
				
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_message);
		mContext = this;
		setupView();
		addListener();
		initImgPath();
		loadDate();
	}
	
	
	
	/**
	 * 加载数据
	 */
	private  void loadDate()
	{
		String jsonString;
		try {
			jsonString = new FileService().read("member");
			if (!jsonString.equals("")) {
			 try {
				JSONObject jsonObject =new JSONObject(jsonString);
				if (!SharedPreferencesConfig.getStringConfig(mContext, "nick").equals("")) {
					((TextView)findViewById(R.id.tv_person_msg_nick)).setText(SharedPreferencesConfig.getStringConfig(mContext, "nick")+"");
						
				}
				else {
					((TextView)findViewById(R.id.tv_person_msg_nick)).setText(jsonObject.getString("nickname")+"");
					
				}
				
				if (!SharedPreferencesConfig.getStringConfig(mContext, "sex").equals("")) {
					((TextView)findViewById(R.id.tv_person_msg_sex)).setText(SharedPreferencesConfig.getStringConfig(mContext, "sex"));
				}
				else {
					if (jsonObject.getString("gender").equals("0")) {
						((TextView)findViewById(R.id.tv_person_msg_sex)).setText("男");
					}
					else {
						((TextView)findViewById(R.id.tv_person_msg_sex)).setText("女");
					}
				}
				
				//地址
				if (!SharedPreferencesConfig.getStringConfig(mContext, "address").equals("")) {
					((TextView)findViewById(R.id.tv_person_msg_address)).setText(SharedPreferencesConfig.getStringConfig(mContext, "address")+"");
					
				}
				else {
					((TextView)findViewById(R.id.tv_person_msg_address)).setText(jsonObject.getString("address")+"");
						
				}
				//手机号码
				if (!SharedPreferencesConfig.getStringConfig(mContext, "phone").equals("")) {
					((TextView)findViewById(R.id.tv_person_msg_phone)).setText(SharedPreferencesConfig.getStringConfig(mContext, "phone")+"");
		            		
				}
				else {
					((TextView)findViewById(R.id.tv_person_msg_phone)).setText(jsonObject.getString("phone")+"");
		            			
				}
				//从本地读取头像
			     Bitmap bitmap =new FileService().readPhoto("imghead");
                 if (bitmap!=null) {
					ivHead.setImageBitmap(bitmap);
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
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showLog("--zhuddd");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 showLog("--zhu");
		if (!SharedPreferencesConfig.getStringConfig(mContext, "address").equals("")) {
			((TextView)findViewById(R.id.tv_person_msg_address)).setText(SharedPreferencesConfig.getStringConfig(mContext, "address")+"");
		}
	}
	


	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_change_head: // 头像
				selectPicPopupWindow = new SelectPicPopupWindow(mContext,
						itemsOnClick);
				// 显示修改头像的对话框
				selectPicPopupWindow.showAtLocation(PersonalMsgActivity.this
						.findViewById(R.id.layout_personal_message),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				mCanversLayout.setVisibility(View.VISIBLE);
				
				//弹出框关闭
				selectPicPopupWindow.setOnDismissListener(new OnDismissListener() {

							@Override
							public void onDismiss() {
								// TODO Auto-generated method stub
								mCanversLayout.setVisibility(View.GONE);
							}
						});
				break;
			
			}
		}
	};
	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selectPicPopupWindow.dismiss();
			switch (v.getId()) {
			case R.id.camera: // 拍照
				try {
					// 执行拍照前 应先判断SD卡是否存在
					if (hasSdcard()) {
						camera();
					} else {
						showToast("内存卡不存在!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.album: // 从相册中选择
				gallery();
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case RESULT_CANCELED:
			break;
		
		case phone:
			//手机
			if (data!=null) {
			
				((TextView)findViewById(R.id.tv_person_msg_phone)).setText(	data.getStringExtra("phone"));
			}
			break;
		case pwd:
			//密码
			break;
		case nick:
			//手机
			if (data!=null) {
			
				((TextView)findViewById(R.id.tv_person_msg_nick)).setText(	data.getStringExtra("nick"));
			}
			//昵称
			break;
		case address:
			//手机
			modifyAddress(SharedPreferencesConfig.getStringConfig(mContext, "address"));
			//地址
			break;
		case Constant.PHOTO_REQUEST_GALLERY: // 从相册中选择
			Uri uri = null;
			if (data == null) {
				showToast("取消修改头像");
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					showToast("SD卡不可用");
					return;
				}
				uri = data.getData();
				startPhotoZoom(uri);
			} else {
				showToast("获取照片失败");
			}
			break;
		case Constant.PHOTO_REQUEST_CAMERA: // 拍照
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					showToast("SD卡不可用");
					return;
				}
				File file = new File(imageName);
				startPhotoZoom(Uri.fromFile(file));
			} else {
				showToast("取消修改头像");
			}
			break;
		case Constant.PHOTO_REQUEST_CUT:
			if (data == null) {
				showToast("取消修改头像");
				return;
			} else {
				saveCropPhoto(data);
			}
			break;
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 保存裁剪的照片 并上传
	 * 
	 * @param data
	 */
	private void saveCropPhoto(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			bitmap = SystemMethod.toRoundCorner(bitmap, 15);
			if (bitmap != null) {
				
				File file = new File(sdcardTmpFile.getAbsolutePath());
				Log.i(TAG, "file:" + file);
				//上传头像
				upImgeDate(file,bitmap);

			}
		} else {
			showToast("获取裁剪照片错误");
		}
	}

	/**
	 * 上传头像
	 */
	
	private void upImgeDate(File file,Bitmap bitmap)
	{
		 bitm=bitmap;
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams requestParams =new RequestParams();
			try {
				requestParams.put("avatar", file);
				requestParams.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 showLog(requestParams.toString());
			httpClient.post(Constant.upimage,requestParams, new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
		
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
					
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								showToast("修改头像成功！");
								if (bitm!=null) {
									updateAvatar(bitm);
								}
							}
							else {
								showToast("修改头像失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToastNetTime();
				}
			});
		}
		else {
			showToastNotNet();
		}
	}
	/**
	 * 更新头像
	 */
	private void updateAvatar(Bitmap bitmap) {
		ivHead.setImageBitmap(bitmap);
		new FileService().savePhoto(bitmap, "imghead");
		Intent intent =new Intent();
		intent.setAction(Constant.action_image);
		sendBroadcast(intent);
	}

	/**
	 * 从相册获取
	 */
	public void gallery() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, Constant.PHOTO_REQUEST_GALLERY);
	}

	/**
	 * 从相机获取
	 */
	public void camera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(imageName);
		file.getParentFile().mkdirs();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, Constant.PHOTO_REQUEST_CAMERA);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param uri
	 */
	private void startPhotoZoom(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("output", Uri.fromFile(sdcardTmpFile));
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		// 图片格式
		intent.putExtra("scale", true);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, Constant.PHOTO_REQUEST_CUT);
	}

	/**
	 * 判断SD卡是否存在
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
