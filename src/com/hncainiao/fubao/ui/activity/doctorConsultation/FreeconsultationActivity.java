package com.hncainiao.fubao.ui.activity.doctorConsultation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.views.SelectPicPopupWindow;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.SystemMethod;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FreeconsultationActivity extends BaseActivity {
	
	/**
	 * 免费咨询
	 * */
	
	  Context mContext;
	  LinearLayout setoff;
	  EditText age,editview;
	  String genger;
	  RadioGroup group;
	  RadioButton radioButton;
	  ImageView pic1,pic2,pic3,pic4,pic5;
	  Button btn_order;
	  
	  private String imageName = "";
	  private Bitmap bitm;
	  int i;
	  List<ImageView>imageViews=new ArrayList<ImageView>();//上传图片
	  List<Bitmap>bitmaps=new ArrayList<Bitmap>();//需要上传的图片集合
	  private File sdcardTmpFile;
	  /**
		 * 修改头像
		 */
		private SelectPicPopupWindow selectPicPopupWindow;//弹出拍照，或者相册选择的下面选择
		/**
		 * 弹出PopupWindow后 背景变暗
		 */
		private RelativeLayout mCanversLayout;
	  /***
	   * 性别选择
	   * 1=男
	   * 0=女
	   * */
	  String sex="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.freezixun_layout);
		Initview();
		Addlisten();
		
	}






	private void Addlisten() {
		setoff.setOnClickListener(new l());
		pic1.setOnClickListener(new l());
		pic2.setOnClickListener(new l());
		pic3.setOnClickListener(new l());
		pic4.setOnClickListener(new l());
		pic5.setOnClickListener(new l());
		imageViews.add(pic1);
		imageViews.add(pic2);
		imageViews.add(pic3);
		imageViews.add(pic4);
		imageViews.add(pic5);
		btn_order.setOnClickListener(new l());
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				radioButton=(RadioButton)group.findViewById(arg1);
				if(radioButton.getText().toString().equals("男")){
					sex=1+"";
				}
				if(radioButton.getText().toString().equals("女")){
					sex=0+"";
				}
			}
			
		});
		
		
	}
	private void PopulWindown(){
		selectPicPopupWindow = new SelectPicPopupWindow(mContext,
				itemsOnClick);
		// 显示修改头像的对话框
		selectPicPopupWindow.showAtLocation(FreeconsultationActivity.this
				.findViewById(R.id.layoutfreezixun),
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
	}
	
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
	private void Initview() {
		mContext=this;
		
		imageName = Constant.ROOT_PATH + "/"
				+ String.valueOf(System.currentTimeMillis() + ".jpg");
		
		sdcardTmpFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/", "zixun_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpg");
		
		
		setoff=(LinearLayout) findViewById(R.id.ll_off);
		mCanversLayout = (RelativeLayout) findViewById(R.id.rl_canvers);
		age=(EditText) findViewById(R.id.age);
		editview=(EditText) findViewById(R.id.editview);
		btn_order=(Button) findViewById(R.id.btn_submit);
		group=(RadioGroup) findViewById(R.id.group);
		radioButton=(RadioButton) group.findViewById(group.getCheckedRadioButtonId());
		pic1=(ImageView) findViewById(R.id.pic1);
		pic2=(ImageView) findViewById(R.id.pic2);
		pic3=(ImageView) findViewById(R.id.pic3);
		pic4=(ImageView) findViewById(R.id.pic4);
		pic5=(ImageView) findViewById(R.id.pic5);

	}
	
	
	class l implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.ll_off:
				showToast("选择科室");
				break;
			case R.id.pic1:
				i=0;
				PopulWindown();
				break;
			case R.id.pic2:
				i=1;
				PopulWindown();
				break;
			case R.id.pic3:
				i=2;
				PopulWindown();
				break;
			case R.id.pic4:
				i=3;
				PopulWindown();
				break;
			case R.id.pic5:
				i=4;
				PopulWindown();
				break;
			case R.id.btn_submit://提交上传图片
				showToast("提交");
				
				if(bitmaps!=null){
					for(int i=0;i<bitmaps.size();i++){
						File file = new File(sdcardTmpFile.getAbsolutePath());
						upImgeDate(file,bitmaps.get(i));	
						System.out.println("上传弟"+i+"张图片");
					}
					
				}
				break;
			default:
				break;
			}

		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case RESULT_CANCELED:
			break;
		case Constant.PHOTO_REQUEST_GALLERY: // 从相册中选择
			Uri uri = null;
			if (data == null) {
				showToast("取消上传");
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
				showToast("取消上传图片");
			}
			break;
		case Constant.PHOTO_REQUEST_CUT:
			if (data == null) {
				showToast("取消上传图片");
				return;
			} else {
				if(i<4){
					imageViews.get(i+1).setVisibility(ViewGroup.VISIBLE);
				}
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
				
				//File file = new File(sdcardTmpFile.getAbsolutePath());
				
				bitmaps.add(bitmap);
				imageViews.get(i).setImageBitmap(bitmap);	
				//上传头像
				//upImgeDate(file,bitmap);

			}
		} else {
			showToast("获取裁剪照片错误");
		}
	}
	
	
	
	/**
	 * 上传图片
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
					//Showloading();
		
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					//Dissloading();
					if (!CheckJson(responseBody).equals("")) {
					
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								showToast("上传图片成功");
								if (bitm!=null) {
									//updateAvatar(bitm);
							//imageViews.get(i).setImageBitmap(bitm);	
								
								}
							}
							else {
								showToast("上传失败！");
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
					//Dissloading();
					showToastNetTime();
				}
			});
		}
		else {
			//Dissloading();
			showToastNotNet();
		}
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


}
