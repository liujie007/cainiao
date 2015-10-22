package com.hncainiao.fubao.ui.activity.healthshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorDetailActivity;
import com.hncainiao.fubao.ui.activity.healthshop.bean.EVlauactionBean;
import com.hncainiao.fubao.ui.activity.healthshop.bean.GoodsCarBean;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.views.ListViewForScrollView;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.jmheart.base.BaseApplication;
import com.jmheart.net.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-13上午10:43:15
 */
public class GoodsInfoActivity extends BaseActivity {

	ListViewForScrollView listEValuation;
	List<EVlauactionBean> listDate=new ArrayList<EVlauactionBean>();
	Button btnPay;
	String product_id;
	ImageView imProdutimg;
	TextView tvTitle,tvMtitle,tvPrice,tvsuprive,tvspec,tvcontent,tvOutnum,tvAddnum,tvStock;
	TextView tvPJ;
	EditText edGoodnum;
	LinearLayout lin_load;
	List<GoodsCarBean> mlist=new ArrayList<GoodsCarBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_info);
		lin_load=(LinearLayout)findViewById(R.id.lin_load);

		product_id=getIntent().getStringExtra("product_id");
		showLog("product_id=="+product_id);
		setTitle("商品详情");
		inintView();
		getProvdutInfo();
		lenster();
	
	}
	
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		btnPay=getView(R.id.btn_pay);
		listEValuation=getView(R.id.list_evaluation);
		tvTitle=getView(R.id.tv_tile);
		tvMtitle=getView(R.id.tv_mtile);
		tvPrice=getView(R.id.tv_price);
		tvsuprive=getView(R.id.tv_supprice);
		tvsuprive.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tvspec=getView(R.id.tv_spec);
		tvcontent=getView(R.id.tv_content);
		edGoodnum=getView(R.id.ed_goods_num);
		imProdutimg=getView(R.id.im_produtimg);
		tvOutnum=getView(R.id.tv_outnum);
		tvOutnum.setOnClickListener(this);
		tvAddnum=getView(R.id.tv_addnum);
		tvAddnum.setOnClickListener(this);
		tvPJ=getView(R.id.tv_pj);
		tvStock=getView(R.id.tv_stock);
	}
	/**
	 * 事件监听
	 */
	int Goodnum=1;
	private void lenster()
	{
		btnPay.setOnClickListener(this);
		getView(R.id.btn_add_shopcar).setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_pay:
			//订单支付
			if (IsLogin.isLogin()) {
					
				Intent iuIntent=new Intent(GoodsInfoActivity.this,PayOrderActivity.class);
				mlist.get(0).setCount(edGoodnum.getText().toString());
				if (Goodnum>0&&Goodnum<=Integer.parseInt(tvStock.getText().toString().substring(5))) {
					
					iuIntent.putExtra("order_goods", (Serializable)mlist);
					
					startActivity(iuIntent);
				}
				else {
					showToast("商品数量不为零且不超过库存量！");
				}
			}
			else {
					ToastManager.getInstance(this).showToast("您尚未登陆，请先登陆");
					Intent intent = new Intent(GoodsInfoActivity.this,LoginActivity.class);
					intent.putExtra("mlogin", "self");
					startActivity(intent);
					
				
			}
			break;
		case R.id.btn_add_shopcar:
			if (IsLogin.isLogin()) {
				
				mlist.get(0).setCount(edGoodnum.getText().toString());
				if (Goodnum>0&&Goodnum<=Integer.parseInt(tvStock.getText().toString().substring(5))) {
					addShopCar();
				}
				else {
					showToast("商品数量不为零且不超过库存量！");
				}
				
			}
			else {
				ToastManager.getInstance(this).showToast("您尚未登陆，请先登陆");
				Intent intent = new Intent(GoodsInfoActivity.this,LoginActivity.class);
				intent.putExtra("mlogin", "self");
				startActivity(intent);
			}
			
			break;
		case R.id.tv_outnum:
			Goodnum=Integer.parseInt(""+edGoodnum.getText().toString()) ;
			if (Goodnum!=0) {
				Goodnum--;
			}
			
			edGoodnum.setText(Goodnum+"");
			break;
		case R.id.tv_addnum:
			Goodnum=Integer.parseInt(""+edGoodnum.getText().toString()) ;
			Goodnum++;
			edGoodnum.setText(Goodnum+"");
			break;
		default:
			break;
		}
	}
	
	/**
	 * 加入购物车
	 */
	private void addShopCar()
	{
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			ShopApi.addCart(product_id,edGoodnum.getText().toString(), addCarthandler);
		}
		else {
			showToastNotNet();
		}
	}
	AsyncHttpResponseHandler addCarthandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			String msg;
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//成功
					msg="加入购物车成功！\n";
					//
				}
				else {
					msg="此商品已存在购物车！\n";
				
				}
				final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(GoodsInfoActivity.this);
				dialogBuilder.withTitle("提示")   
				//.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage(msg)                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor(getResources().getColor(R.color.blue))     
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Newspager)                                         //def Effectstype.Slidetop
                .withButton1Text("进入购物车")                                      //def gone
                .withButton2Text("返回")                 
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	dialogBuilder.dismiss();
                    	startActivity(new Intent(GoodsInfoActivity.this,GoodsCarActivity.class));
                        //Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	dialogBuilder.dismiss();
                        //Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			showToast("请求失败");
			Dissloading();
		};
	};
	/**
	 * 得到商品详情
	 */
	private void getProvdutInfo()
	{
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			ShopApi.getProductInfo(product_id, ProductInfohandler);
		}
		else {
			showToastNotNet();
		}
	}
	AsyncHttpResponseHandler ProductInfohandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					lin_load.setVisibility(View.GONE);
					mlist.clear();
					JSONObject jsinfoObject=jsonObject.getJSONObject("product");
				
					GoodsCarBean goodsCarBean=new GoodsCarBean(jsinfoObject.getString("id"),jsinfoObject.getString("id"),"1",
							jsinfoObject.getString("name"),jsinfoObject.getString("remark"),jsinfoObject.getString("price"),
							jsinfoObject.getString("img"));
					mlist.add(goodsCarBean);
					tvTitle.setText(""+jsinfoObject.getString("name"));
					tvMtitle.setText(""+jsinfoObject.getString("remark"));
					tvPrice.setText(""+jsinfoObject.getString("price")+"￥");
					tvsuprive.setText(""+jsinfoObject.getString("suggested_price")+"￥");
					tvspec.setText("商品规格："+jsinfoObject.getString("spec"));
					tvcontent.setText(""+jsinfoObject.getString("content"));
					tvStock.setText("库存量为："+jsinfoObject.getString("stock"));
					BaseApplication.imageLoader.displayImage(FuBaoApplication.appHOST+jsinfoObject.getString("img"), imProdutimg);
					try {
						listDate=JsonUtil.parserJsonToList(EVlauactionBean.class, jsinfoObject.getJSONArray("product_comment"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tvPJ.setText("用户评价("+listDate.size()+")");
					listEValuation.setAdapter(new CommonAdapter<EVlauactionBean>(getApplicationContext(),listDate,R.layout.evlauation_itme) {
						@Override
						public void convert(ViewHolder helper, EVlauactionBean item,int pst) {
							// TODO Auto-generated method stub
							helper.setText(R.id.tv_content, item.getContent());
						}

						
					});
				}
				else
				{
					showLog("获取失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToast("请求失败");
		};
	};
}
