package com.hncainiao.fubao.ui.activity.permedicine;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class JiYinTestActivity extends BaseActivity {
	/**
	 * 基因检测
	 * */
	
	ImageView imageView;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiyintest);
		mContext=this;
		
		TextView title=(TextView) findViewById(R.id.title_txt);
		title.setText("基因检测");
		imageView=(ImageView) findViewById(R.id.img);
		addListen();
	}
	private void addListen() {
		imageView.setOnTouchListener(new OnTouchListener(){
			WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
 
              
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				double width = wm.getDefaultDisplay().getWidth();
				double height = wm.getDefaultDisplay().getHeight();
				System.out.println("屏幕宽度"+width);
				System.out.println("屏幕高度"+height);
				System.out.println("x左边"+arg1.getX()+"y左边"+arg1.getY());
				System.out.println(Math.round((arg1.getX()*10/width*1000)/1000));
				System.out.println(Math.round((arg1.getY()*10/width*1000)/1000));
				if(Math.round((arg1.getX()*10/width*1000)/1000)>1.9&&Math.round((arg1.getX()*10/width*1000)/1000)<3.5
						&&Math.round((arg1.getY()*10/width*1000)/1000)>3.8&&Math.round((arg1.getY()*10/width*1000)/1000)<5.2){
					showToast("健康风险");
					
					
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=1&&Math.round((arg1.getX()*10/width*1000)/1000)<=2
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=7&&Math.round((arg1.getY()*10/width*1000)/1000)<8.5){
					showToast("遗传疾病");
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=1.9&&Math.round((arg1.getX()*10/width*1000)/1000)<=3
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=9&&Math.round((arg1.getY()*10/width*1000)/1000)<=11){
					showToast("药物反应");
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=4&&Math.round((arg1.getX()*10/width*1000)/1000)<=6
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=12&&Math.round((arg1.getY()*10/width*1000)/1000)<14){
					showToast("饮食建议");
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=8&&Math.round((arg1.getX()*10/width*1000)/1000)<=9
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=10&&Math.round((arg1.getY()*10/width*1000)/1000)<=11){
					showToast("健康指导");
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=8&&Math.round((arg1.getX()*10/width*1000)/1000)<=9
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=7&&Math.round((arg1.getY()*10/width*1000)/1000)<=9){
					showToast("天赋特征");
				}
				if(Math.round((arg1.getX()*10/width*1000)/1000)>=7&&Math.round((arg1.getX()*10/width*1000)/1000)<=9
						&&Math.round((arg1.getY()*10/width*1000)/1000)>=4&&Math.round((arg1.getY()*10/width*1000)/1000)<=5){
					showToast("组源分析");
				}
				return false;
			}
			
		});
	}

}
