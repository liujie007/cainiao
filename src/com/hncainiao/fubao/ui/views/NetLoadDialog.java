package com.hncainiao.fubao.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;



public class NetLoadDialog extends Dialog {
	Dialog loadingDialog;	// 定义dialog
	Context context;
    Window window = null;  
	AnimationDrawable mAnimation;
	public NetLoadDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public void SetMessage(String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.net_dialog_loaing, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.layout);// 加载布局
		TextView tipTextView = (TextView) v.findViewById(R.id.text);// 提示文字
		// 设置加载信息
		tipTextView.setText(msg);
		ImageView mImageView= (ImageView) v.findViewById(R.id.image);// 提示文字
		Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.diaog);  
		LinearInterpolator lin = new LinearInterpolator();  
		operatingAnim.setInterpolator(lin); 
		mImageView.startAnimation(operatingAnim);
		//mImageView.setBackgroundResource(R.anim.diaog);  
	       /* // 通过ImageView对象拿到背景显示的AnimationDrawable  
		      mAnimation = (AnimationDrawable) mImageView.getBackground();  
	        // 为了防止在onCreate方法中只显示第一帧的解决方案之一  
	        mImageView.post(new Runnable() {  
	            @Override  
	            public void run() {  
	                mAnimation.start();  
	  
	            }  
	        }); */ 
	    window = getWindow(); //得到对话框  
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画  
      	loadingDialog = new Dialog(context, R.style.Translucent_NoTitle);
	//	loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.FILL_PARENT,
		LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
	}
	/**
	 * 打开dialog
	 */
	public void showDialog() {
		
			loadingDialog.show();
		
		
	}

	/**
	 * 关闭dialog
	 */
	public void dismissDialog() {
		
			loadingDialog.dismiss();
		
		
	}
}
