package com.hncainiao.fubao.ui.activity.around;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.DoctorPagerAdapter;
import com.hncainiao.fubao.ui.views.NoScrollViewPager;

public class ZhenSuoMessageActivity extends FragmentActivity {
	/**
	 * 诊所详情
	 * */
	Context mContext;
	NoScrollViewPager viewPager;
	JutiIntrofuceFragment introfuceFragment;//具体介绍
	ZhenSuoMapFragment  mapFragment;//地图位置
	ZhenSuoPingjiaFragment pingjiaFragment;//平价
	TextView yaodian,zhensu,yiyaosongsi;
	View yd_view,zs_view,gongsi_view;
	private ArrayList<Fragment> fragments;
	private int currentPageIndex = 0;
	ImageView share;
	
	LinearLayout title_btn_left ;//返回
	TextView title;//标题
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhensuomessage);

		mContext=this;
		Initview();
		addListen();
	}
	private void addListen() {
		   yaodian.setOnClickListener(new l());
		   zhensu.setOnClickListener(new l());
		   yiyaosongsi.setOnClickListener(new l());
		   title_btn_left.setOnClickListener(new l());
		   share.setOnClickListener(new l());
		   viewPager.setOnPageChangeListener(new OnPageChangeListener(){

				@Override
				public void onPageScrollStateChanged(int arg0) {
					
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					currentPageIndex=arg0;
					setLine();
				}
				
			});
		}
	
	
	 
	   class l implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.yaodian:
				currentPageIndex=0;
				viewPager.setCurrentItem(currentPageIndex);
				setLine();
				
				break;
			case R.id.zhensuo:
			currentPageIndex=1;
			viewPager.setCurrentItem(currentPageIndex);
			setLine();
			break;
			case R.id.yo:
				currentPageIndex=2;
				viewPager.setCurrentItem(currentPageIndex);
				setLine();
				break;
			case R.id.title_btn_left:
				finish();
				break;
			case R.id.share:
				//showShare();
				break;

			default:
				break;
			}
			
			
			
		}
		   
	   };

	   private void setLine(){
		   if(currentPageIndex==0){
			   yd_view.setVisibility(ViewGroup.VISIBLE); 
			   yaodian.setTextColor(Color.parseColor("#4b83e7"));
			   yiyaosongsi.setTextColor(Color.BLACK);
			   zhensu.setTextColor(Color.BLACK);
			   
			   zs_view.setVisibility(ViewGroup.INVISIBLE);
			   gongsi_view.setVisibility(ViewGroup.INVISIBLE);
		   }else 
			 if(currentPageIndex==1){
				 yd_view.setVisibility(ViewGroup.INVISIBLE);  
				   zs_view.setVisibility(ViewGroup.VISIBLE);
				   gongsi_view.setVisibility(ViewGroup.INVISIBLE); 
				   yaodian.setTextColor(Color.BLACK);
				   yiyaosongsi.setTextColor(Color.BLACK);
				   zhensu.setTextColor(Color.parseColor("#4b83e7"));
			 }
		   if(currentPageIndex==2){
			   yd_view.setVisibility(ViewGroup.INVISIBLE);  
			   zs_view.setVisibility(ViewGroup.INVISIBLE);
			   gongsi_view.setVisibility(ViewGroup.VISIBLE); 
			   yaodian.setTextColor(Color.BLACK);
			   yiyaosongsi.setTextColor(Color.parseColor("#4b83e7"));
			   zhensu.setTextColor(Color.BLACK);
		   }
		   
		   
	   }
	private void Initview() {
		share=(ImageView) findViewById(R.id.share);
		title_btn_left=(LinearLayout) findViewById(R.id.title_btn_left);
		title=(TextView) findViewById(R.id.title_txt);
		title.setText("诊所详情");
		viewPager=(NoScrollViewPager) findViewById(R.id.viewpager_);
		introfuceFragment=new JutiIntrofuceFragment();
		mapFragment=new ZhenSuoMapFragment();
		pingjiaFragment=new ZhenSuoPingjiaFragment();
		yaodian=(TextView) findViewById(R.id.yaodian);
		zhensu=(TextView) findViewById(R.id.zhensuo);
		yiyaosongsi=(TextView) findViewById(R.id.yo);
		yd_view=findViewById(R.id.yaodian_view);
		zs_view=findViewById(R.id.zhensuo_view);
		gongsi_view=findViewById(R.id.yiyaogongsi_view);
		fragments = new ArrayList<Fragment>();
		fragments.add(introfuceFragment);
		fragments.add(mapFragment);
		fragments.add(pingjiaFragment);
		DoctorPagerAdapter adapter = new DoctorPagerAdapter(this.getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPageIndex);
		setLine();
		
	}
	/*private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://www.zgcainiao.com/");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 
		 
		 oks.setUrl("http://www.zgcainiao.com/");
		 oks.setTitle("福報健康");
		 oks.setTitleUrl("http://www.zgcainiao.com/");
		 oks.setText("福報健康，健康千萬家");
		 oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//分享圖片
		 
		 
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://www.zgcainiao.com/");
		 
		// 启动分享GUI
		 oks.show(this);
		 }*/

}
