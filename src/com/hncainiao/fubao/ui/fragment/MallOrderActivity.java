package com.hncainiao.fubao.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.ui.adapter.base.TabPagerAdapter;
import com.hncainiao.fubao.ui.fragment.mallorder.TabAllFragment;
import com.hncainiao.fubao.ui.fragment.mallorder.TabFinshFragment;
import com.hncainiao.fubao.ui.fragment.mallorder.TabOutpayFragment;
import com.hncainiao.fubao.ui.fragment.mallorder.TabPayFragment;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-15上午9:03:32
 *  商城订单
 */
public class MallOrderActivity extends FragmentActivity implements OnClickListener{

	    ViewPager viewPager;
	    TabPagerAdapter tabPagerAdapter;
	    TextView tvAllbg,tvPaybg,tvFinshbg,tvOutpaybg;
	    TextView tvAll,tvPay,tvFinsh,tvOutpay;
	    List<Fragment> listFragments=new ArrayList<Fragment>();
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.actvity_mall_order);
	        FuBaoApplication.getInstance().activities.add(this);
	        
	        
	        inintView();
	        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),listFragments);
	        viewPager.setAdapter(tabPagerAdapter);
	        inintTab();
	        if (getIntent().getStringExtra("state")!=null) {
				if (getIntent().getStringExtra("state").equals("退款成功")) {
					inintTab();
					tvFinshbg.setVisibility(View.VISIBLE);
					tvFinsh.setTextColor(getResources().getColor(R.color.blue));
					viewPager.setCurrentItem(2);
				}
				else {
					inintTab();
					tvOutpaybg.setVisibility(View.VISIBLE);
					tvOutpay.setTextColor(getResources().getColor(R.color.blue));
					viewPager.setCurrentItem(3);
				}
			}
	        else
	        {
		        viewPager.setCurrentItem(0);
		        tvAllbg.setVisibility(View.VISIBLE);
		    	tvAll.setTextColor(getResources().getColor(R.color.blue));
	        }
	       
	        
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            /**
	             * on swipe select the respective tab
	             * */
	            @Override
	            public void onPageSelected(int position) {
	               switch (position) {
				case 0:
					inintTab();
					tvAllbg.setVisibility(View.VISIBLE);
					tvAll.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 1:
					inintTab();
					tvPaybg.setVisibility(View.VISIBLE);
					tvPay.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 2:
					inintTab();
					tvFinshbg.setVisibility(View.VISIBLE);
					tvFinsh.setTextColor(getResources().getColor(R.color.blue));
					
					break;
				case 3:
					inintTab();
					tvOutpaybg.setVisibility(View.VISIBLE);
					tvOutpay.setTextColor(getResources().getColor(R.color.blue));
					
					break;

				default:
					break;
				}
	            }

	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) { }

	            @Override
	            public void onPageScrollStateChanged(int arg0) { }
	        });
	    }
	 
	    /**
	     * 控件初始化
	     */
	    private void inintView()
	    {
	    	tvAllbg=(TextView)findViewById(R.id.tv_all_bg);
	    	tvPaybg=(TextView)findViewById(R.id.tv_pay_bg);
	    	tvFinshbg=(TextView)findViewById(R.id.tv_finsh_bg);
	    	tvOutpaybg=(TextView)findViewById(R.id.tv_outpay_bg);
	    	(tvAll=(TextView)findViewById(R.id.tv_all)).setOnClickListener(this);
	    	(tvPay=(TextView)findViewById(R.id.tv_pay)).setOnClickListener(this);
	    	(tvFinsh=(TextView)findViewById(R.id.tv_finsh)).setOnClickListener(this);
	    	(tvOutpay=(TextView)findViewById(R.id.tv_outpay)).setOnClickListener(this);
	    	viewPager = (ViewPager) findViewById(R.id.pager);
	    	listFragments.add(new TabAllFragment());
	    	listFragments.add(new TabPayFragment());
	    	listFragments.add(new TabFinshFragment());
	    	listFragments.add(new TabOutpayFragment());
	    }
	    /**
	     * 初始化tab条
	     */
	    private void inintTab()
	    {
	    	tvAllbg.setVisibility(View.INVISIBLE);
	    	tvPaybg.setVisibility(View.INVISIBLE);
	    	tvFinshbg.setVisibility(View.INVISIBLE);
	    	tvOutpaybg.setVisibility(View.INVISIBLE);
	    	((TextView)findViewById(R.id.title_txt)).setText("商城订单");
	    	tvAll.setTextColor(getResources().getColor(R.color.black));
	    	tvPay.setTextColor(getResources().getColor(R.color.black));
	    	tvFinsh.setTextColor(getResources().getColor(R.color.black));
	    	tvOutpay.setTextColor(getResources().getColor(R.color.black));
	    }

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.tv_all:
				inintTab();
				tvAllbg.setVisibility(View.VISIBLE);
				tvAll.setTextColor(getResources().getColor(R.color.blue));
				viewPager.setCurrentItem(0);
				break;
			case R.id.tv_pay:
				inintTab();
				tvPaybg.setVisibility(View.VISIBLE);
				tvPay.setTextColor(getResources().getColor(R.color.blue));
				viewPager.setCurrentItem(1);		
				break;
			case R.id.tv_finsh:
				inintTab();
				tvFinshbg.setVisibility(View.VISIBLE);
				tvFinsh.setTextColor(getResources().getColor(R.color.blue));
				
				viewPager.setCurrentItem(2);
				break;
			case R.id.tv_outpay:
				inintTab();
				tvOutpaybg.setVisibility(View.VISIBLE);
				tvOutpay.setTextColor(getResources().getColor(R.color.blue));
				viewPager.setCurrentItem(3);
				break;

			default:
				break;
			}
		}
		public void leftbuttonclick(View view)
		{
			onBackPressed();
		}
	
}

