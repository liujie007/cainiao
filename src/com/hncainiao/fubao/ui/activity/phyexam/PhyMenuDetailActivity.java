package com.hncainiao.fubao.ui.activity.phyexam;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.SuspendScrollView;
import com.hncainiao.fubao.ui.views.SuspendScrollView.OnScrollListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2015年4月13日 下午5:44:09
 * 
 */
public class PhyMenuDetailActivity extends BaseActivity implements
		OnScrollListener {

	// private static final String TAG = "PhyMenuDetailActivity";

	private Context mContext;

	/**
	 * 自定义的具有悬浮效果的ScrollView
	 */
	private SuspendScrollView mScrollView;

	/**
	 * 在SuspendScrollView里面的购买布局
	 */
	private RelativeLayout mBuyLayout;
	/**
	 * 位于顶部的购买布局
	 */
	private RelativeLayout mTopBuyLayout;

	/**
	 * 现价 原价
	 */
	private TextView tvNowPrice,tvNowPrice1, tvOriPrice,tvOriPrice1;

	/**
	 * 在SuspendScrollView里面立即购买
	 */
	private Button btnBuy;
	/**
	 * 在顶部布局中的立即购买
	 */
	private Button btnTopBuy;

	/**
	 * 查看更多详情
	 */
	private TextView tvMoreDetail;

	private String taoCan,tvPrice,id;
	private RatingBar ratingBar;
	private void setupView() {
		setTitle("体检套餐详情");
		ratingBar=(RatingBar)findViewById(R.id.room_ratingbar);
		mScrollView = (SuspendScrollView) findViewById(R.id.suspend_scrollview);
		
		mBuyLayout = (RelativeLayout) findViewById(R.id.layout_buy_phymenu);
		mTopBuyLayout = (RelativeLayout) findViewById(R.id.top_buy_layout);
		
		tvMoreDetail = (TextView) findViewById(R.id.tv_see_more_detail);
		tvNowPrice = (TextView) findViewById(R.id.tv_phymenu_now_price);
		tvOriPrice = (TextView) findViewById(R.id.tv_phymenu_original_price);
		tvNowPrice1 = (TextView) findViewById(R.id.tv_phymenu_now_price1);
		tvOriPrice1 = (TextView) findViewById(R.id.tv_phymenu_original_price1);
		btnBuy = (Button) ((RelativeLayout) findViewById(R.id.layout_buy_phymenu))
				.findViewById(R.id.btn_phymenu_buy);
		btnTopBuy = (Button) ((RelativeLayout) findViewById(R.id.top_buy_layout)).findViewById(R.id.btn_phymenu_buy);
	}

	private void addListener() {
		mScrollView.setOnScrollListener(this);
		tvMoreDetail.setOnClickListener(l);
		btnBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (IsLogin.isLogin()) {
					Intent intent = new Intent(mContext,PhyExamMessageActivity.class);
					intent.putExtra("taoCan", taoCan);
					intent.putExtra("tvPrice", tvPrice);
					intent.putExtra("id", id);
					startActivity(intent);
				}
				else {
					ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆!");
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
				}
				
			}
		});
		btnTopBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,PhyExamMessageActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phyexam_menu_detail);
		mContext = PhyMenuDetailActivity.this;
		Intent iuIntent =getIntent();
		setupView();
		addListener();
		tvOriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

		if (iuIntent!=null) {
			try {
				/*
				 * {"id":"3","hospital_id":"6","cid":"3","name":"十一女性体检",
				 * "remark":"十一女性体检","info":"十一女性体检十一女性体检十一女性体检
				 * 十一女性体检十一女性体检十一女性体检十一女性体检","current_price":"700.00","market_price":
				 * "2800.00","star":"5","createtime":"1430733128","status":"1","sort":"255"}
				 */
				if (iuIntent.getIntExtra("flag", 0)==2) {
					
				JSONObject jsonObject =new JSONObject(iuIntent.getStringExtra("intendate"));
				//填充信息
				taoCan=jsonObject.getString("name");
				tvPrice=jsonObject.getString("current_price");
				id=jsonObject.getString("id");
				((TextView)findViewById(R.id.tv_name)).setText(""+taoCan);
				((TextView)findViewById(R.id.tv_rkname)).setText(""+jsonObject.getString("remark"));
				((TextView)findViewById(R.id.tv_zdname)).setText(""+jsonObject.getString("name"));
				((TextView)findViewById(R.id.tv_zdrkname)).setText(""+jsonObject.getString("info"));
				((TextView)findViewById(R.id.tv_yyinfo)).setText(""+jsonObject.getString("msg"));
				ratingBar.setRating(Integer.parseInt(""+jsonObject.getString("star")));
				tvNowPrice1.setText(""+jsonObject.getString("current_price"));
				tvOriPrice1.setText("￥"+jsonObject.getString("market_price"));
				
				tvNowPrice.setText(""+jsonObject.getString("current_price"));
				tvOriPrice.setText("￥"+jsonObject.getString("market_price"));
				}
				else {
					//
					getNetDate(iuIntent.getStringExtra("package_id"));
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		// 当布局的状态或者控件的可见性发生改变回调的接口
		findViewById(R.id.phyexam_menu_detail).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						onScroll(mScrollView.getScrollY());
					}
				});

	}

	/**
	 * 得到网络数据
	 */
	private void getNetDate(String package_id)
	{
		if (NetworkUtil.isOnline(this)) {
			
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params=new RequestParams();
			params.put("package_id", package_id);
			httpClient.post(Constant.url_taocan, params,new AsyncHttpResponseHandler()
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
						//开始解析数据
						 try {
							JSONObject jsonO =new JSONObject(new String(responseBody));
							if (jsonO.getInt("err")==0) {
								
								JSONArray jArray =jsonO.getJSONArray("package");
								JSONObject jsonObject =jArray.getJSONObject(0);
								taoCan=jsonObject.getString("name");
								id=jsonObject.getString("id");
								tvPrice=jsonObject.getString("current_price");
								((TextView)findViewById(R.id.tv_name)).setText(""+taoCan);
								((TextView)findViewById(R.id.tv_rkname)).setText(""+jsonObject.getString("remark"));
								((TextView)findViewById(R.id.tv_zdname)).setText(""+jsonObject.getString("name"));
								((TextView)findViewById(R.id.tv_zdrkname)).setText(""+jsonObject.getString("info"));
								((TextView)findViewById(R.id.tv_yyinfo)).setText(""+jsonObject.getString("msg"));
								
								tvNowPrice1.setText(""+jsonObject.getString("current_price"));
								tvOriPrice1.setText(""+jsonObject.getString("market_price"));
								
								tvNowPrice.setText(""+jsonObject.getString("current_price"));
								tvOriPrice.setText(""+jsonObject.getString("market_price"));
							}
							else {
								showToast("没有数据！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					else {
						showToast("没有数据！");
					}
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
				}
			});
		}
		else {
			showToastNotNet();
		}
	}
	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.tv_see_more_detail: // 查看更多图文详情
				intent = new Intent(mContext, PhyMenuMoreDetailActivity.class);
				startActivity(intent);
				break;
			// case R.id.btn_phymenu_buy: // 立即购买
			// showToast("立即购买");
			// break;
			}
		}
	};

	// 监听滚动Y值变化，通过addView和removeView来实现悬停效果
	@Override
	public void onScroll(int scrollY) {
		// TODO Auto-generated method stub
		int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
		mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(),
				mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
	}
}
