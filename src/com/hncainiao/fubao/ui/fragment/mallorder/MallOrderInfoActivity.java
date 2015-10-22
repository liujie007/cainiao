package com.hncainiao.fubao.ui.fragment.mallorder;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.fragment.MallOrderActivity;
import com.hncainiao.fubao.ui.fragment.mallorder.bean.KdBean;
import com.hncainiao.fubao.ui.fragment.mallorder.bean.OrderInfoBean;
import com.hncainiao.fubao.ui.fragment.mallorder.bean.TabAllBean;
import com.hncainiao.fubao.ui.views.ListViewForScrollView;
import com.jmheart.base.BaseApplication;
import com.jmheart.net.JsonUtil;
import com.jmheart.net.NetworkUtil;
import com.jmheart.tools.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-16上午8:53:10
 *  订单详情
 */
public class MallOrderInfoActivity extends BaseActivity {

	ListViewForScrollView listview;
	ListViewForScrollView listviewkd;
	List<OrderInfoBean> listdate;
	List<KdBean> kdlistdate;
	TabAllBean tabAllBean;
	TextView tvState;
	TextView tvDanhao;
	Button btnState,btnTuikang;
	CommonAdapter< OrderInfoBean> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall_order_info);
		setTitle("订单详情");
		inintDate();
		inintView();
		getKd();
	}
	private void inintView(){
		tvDanhao=getView(R.id.tv_danhao);
		((TextView) getView(R.id.tv_name)).setText("姓名："+tabAllBean.getConsignee());
		
		((TextView) getView(R.id.tv_phone)).setText("电话："+tabAllBean.getConsignee_phone());
		
		((TextView) getView(R.id.tv_address)).setText("地址："+tabAllBean.getConsignee_address());
		
		
		((TextView) getView(R.id.tv_order_num)).setText("订单："+tabAllBean.getSn());
		
		((TextView) getView(R.id.tv_time)).setText("时间："+StringUtil.getStrTime(tabAllBean.getPay_time()));
		
		((TextView) getView(R.id.tv_goods_num)).setText("共"+listdate.size()+"件商品,商品金额:");
		
		((TextView) getView(R.id.tv_goods_price)).setText("￥"+tabAllBean.getTotal_price());
		
		((TextView) getView(R.id.tv_all_price)).setText("￥"+tabAllBean.getTotal_fee());
		
		((TextView) getView(R.id.tv_kd_price)).setText("￥"+tabAllBean.getShipping_fee());
		tvState=getView(R.id.tv_state);
		btnState=getView(R.id.btn_state);btnState.setOnClickListener(this);
		btnTuikang=getView(R.id.btn_tuikang);btnTuikang.setOnClickListener(this);
		switch (Integer.parseInt(tabAllBean.getStatus())) {
		case 0:
			
			break;
		case 1:
			//待付款
			tvState.setText("待付款");
			btnState.setText("付款");
			btnState.setVisibility(View.VISIBLE);
			btnTuikang.setVisibility(View.GONE);
			break;
		case 2:
			//待发货
			btnTuikang.setVisibility(View.VISIBLE);
			tvState.setText("等待商家发货");
			btnState.setVisibility(View.INVISIBLE);
			break;
		case 3:
			//确认收货
			btnTuikang.setVisibility(View.VISIBLE);
			tvState.setText("已发货待收货");
			btnState.setText("确认收货");
			btnState.setVisibility(View.VISIBLE);
			break;
		case 4: 
			//评价
			btnTuikang.setVisibility(View.VISIBLE);
			tvState.setText("已收货待评价");
			btnState.setText("评价");
			btnState.setVisibility(View.INVISIBLE);
			
			break;
		case 5:
			//已完成
			tvState.setText("已完成");
			btnState.setVisibility(View.INVISIBLE); 
			btnTuikang.setVisibility(View.GONE);
			break;
		case 6:
			btnTuikang.setVisibility(View.GONE);
			tvState.setText("退款等待审核...");
			btnState.setVisibility(View.INVISIBLE);
			break;
		case 7:
			//已完成
			tvState.setText("退款完成");
			btnState.setVisibility(View.INVISIBLE); 
			btnTuikang.setVisibility(View.GONE);
			break;

		default:
			break;
		}
		
		listview=(ListViewForScrollView)findViewById(R.id.list_goods_order_host);
		listviewkd=(ListViewForScrollView)findViewById(R.id.list_kd);
		listviewkd.setEmptyView(((TextView)findViewById(R.id.tv_list_null)));
		listview.setAdapter(adapter=new CommonAdapter<OrderInfoBean>(getApplicationContext(),listdate,R.layout.order_info_itme) {

			@Override
			public void convert(ViewHolder helper, final OrderInfoBean item,final int pos) {
				// TODO Auto-generated method stub
				
				if (tabAllBean.getStatus().equals("4")) {
					helper.getView(R.id.btn_pj).setVisibility(View.VISIBLE);
					 if (item.getOrder_detail_comment_status().equals("1")) {
						 ((Button)helper.getView(R.id.btn_pj)).setText("已评价");
						 ((Button)helper.getView(R.id.btn_pj)).setEnabled(false);
					}
				}
				else {
					helper.getView(R.id.btn_pj).setVisibility(View.GONE);
					
				}
				helper.getView(R.id.btn_pj).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(MallOrderInfoActivity.this);
						
						View view=LayoutInflater.from(MallOrderInfoActivity.this).inflate(R.layout.mall_pj_itme, null);
						final EditText edPjContent=(EditText)view.findViewById(R.id.ed_content);
						
						 ((Button)view.findViewById(R.id.btn_post)).setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								
								if (NetworkUtil.isOnline(MallOrderInfoActivity.this)) {
									if (!edPjContent.getText().toString().equals("")) {
										dialogBuilder.dismiss();
										Showloading();
										item.setOrder_detail_comment_status("1");
										ShopApi.OrderPj(listdate.get(pos).getOrder_detail_id(),tabAllBean.getId(),edPjContent.getText().toString(),pjhander);
								
									}
									else {
										showToast("请输入评价的内容");
									}
								}
								else
								{
									showToastNotNet();
								}
							}
						});
						 ((Button)view.findViewById(R.id.btn_cacel)).setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									dialogBuilder.dismiss();
								}
							});
						dialogBuilder.withTitle("评价") ;
						dialogBuilder.withEffect(Effectstype.Fliph);
						dialogBuilder.setContentView(view);
						dialogBuilder.show();
					}
				});
				
				helper.setText(R.id.tv_mtile, item.getProduct_name());
				helper.setText(R.id.tv_tile, item.getRemark());
				helper.setText(R.id.tv_price, item.getPrice()+"元");
				helper.setText(R.id.tv_heath_shop_start_money, "数量："+item.getCount());
				BaseApplication.imageLoader.displayImage(item.getImg(), (ImageView)helper.getView(R.id.im_heath_shop_img));
				
			}

			
		});
	}
	private void inintDate()
	{
		tabAllBean=(TabAllBean)getIntent().getSerializableExtra("listdate");
        showLog(tabAllBean.toString());
		try {
			listdate = JsonUtil.parserJsonToList(OrderInfoBean.class, tabAllBean.getOrder_detail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 switch (arg0.getId()) {
		case R.id.btn_state:
			switch (Integer.parseInt(tabAllBean.getStatus())) {
			case 1:
				//付款
				if (NetworkUtil.isOnline(this)) {
					Showloading();
					ShopApi.pay(tabAllBean.getSn(),payhander);
				}
				
				break;
			case 2:
							
				break;
				
			case 3:
				//确认收货
				if (NetworkUtil.isOnline(MallOrderInfoActivity.this)) {
            		Showloading();
					ShopApi.SureOrder(tabAllBean.getSn()+"",suerhander);
				}
            	else {
					showToastNotNet();
				}
				break;
			case 4:
					
				break;
			case 5:
				
				break;
			case 6:
				
				break;

			default:
				break;
			}
		
			break;
		case R.id.btn_tuikang:
			//申请退款
			final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(MallOrderInfoActivity.this);
			
			View view=LayoutInflater.from(MallOrderInfoActivity.this).inflate(R.layout.mall_pj_itme, null);
			final EditText edPjContent=(EditText)view.findViewById(R.id.ed_content);
			edPjContent.setHint("请填写退款说明");
			 ((Button)view.findViewById(R.id.btn_post)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					if (NetworkUtil.isOnline(MallOrderInfoActivity.this)) {
						if (!edPjContent.getText().toString().equals("")) {
							dialogBuilder.dismiss();
							Showloading();
							ShopApi.TuiOrder(""+edPjContent.getText().toString(),tabAllBean.getSn()+"",tuihander);
						}
						else {
							showToast("请填写退款说明");
						}
			 
						}
		           	else {
						showToastNotNet();
					}
				}
			});
			 ((Button)view.findViewById(R.id.btn_cacel)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialogBuilder.dismiss();
					}
				});
			dialogBuilder.withTitle("退款单") ;
			dialogBuilder.withEffect(Effectstype.Fliph);
			dialogBuilder.setContentView(view);
			dialogBuilder.show();
			
			
			break;
		default:
			break;
		}
	}
	/**
	 * 申请退款
	 */
	AsyncHttpResponseHandler tuihander=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
				Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				showToast(jsonObject.getString("msg"));
				if (jsonObject.getInt("err")==0) {
					//退款成功
				/*	Intent intent=new Intent(MallOrderInfoActivity.this,MallOrderActivity.class);
					intent.putExtra("state", "退款成功");
					startActivity(intent);*/
				}
				if (jsonObject.getInt("err")==0) {
					//退款等待审核
					Intent intent=new Intent(MallOrderInfoActivity.this,MallOrderActivity.class);
					intent.putExtra("state", "退款等待审核");
					startActivity(intent);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToast("请求错误");
		};
	};
	AsyncHttpResponseHandler suerhander=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//确认收货成功
					tabAllBean.setStatus("4");
					tvState.setText("已收货待评价");
					btnState.setText("评价");
					btnState.setVisibility(View.INVISIBLE);
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
		};
	};
	AsyncHttpResponseHandler pjhander=new AsyncHttpResponseHandler()

	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//评价成功
					adapter.notifyDataSetChanged();
					if (jsonObject.getInt("order_comment_status")==1) {
						tvState.setText("已完成");
					}
					//
					//btnState.setVisibility(View.INVISIBLE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
		};
	};
	AsyncHttpResponseHandler payhander=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				//{"ret":{"order_id":"79","sn":"20150928134937244435","tn":null},"err":0}
				if (jsonObject.getInt("err")==0) {
					
					UPPayAssistEx.startPayByJAR(MallOrderInfoActivity.this, PayActivity.class, null, null,
							jsonObject.getJSONObject("ret").getString("tn").trim(), "00");
					
		
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		public void onFailure(Throwable error) {
			Dissloading();
		};
	};
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
            startActivity(new Intent(MallOrderInfoActivity.this,MallOrderActivity.class));
            //跳转
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
	/**
	 * 快递
	 */
	private  void getKd()
	{
		
		if (NetworkUtil.isOnline(this)) {
			
			   Showloading();
			   AsyncHttpClient handler =new AsyncHttpClient();
			   handler.addHeader("apikey", "9f5c91bd067f93e32eec0ec18cd6d6fe");
				RequestParams params = new RequestParams();
				params.put("expresscode", tabAllBean.getShipping_alias());
				params.put("billno", tabAllBean.getShipping_sn());
				//params.put("apikey", "5b873de5dbba01e5538680b09495f0e1");
				showLog(params.toString());
				tvDanhao.setText("运单号："+tabAllBean.getShipping_sn());
				handler.get("http://apis.baidu.com/ppsuda/waybillnoquery/waybillnotrace", params, kdhead);
			 
		}
		else {
			showToastNotNet();
		}
		
	}
	AsyncHttpResponseHandler kdhead=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			showLog(content);
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
					if (jsonObject.getInt("result")==1) {
						try {
							kdlistdate=JsonUtil.parserJsonToList(KdBean.class, jsonObject.getJSONArray("data").getJSONObject(0).getJSONArray("wayBills"));
						
							listviewkd.setAdapter(new CommonAdapter<KdBean>(MallOrderInfoActivity.this,kdlistdate,R.layout.kd_mall_itme) {
								@Override
								public void convert(ViewHolder helper, KdBean item,int pst) {
									// TODO Auto-generated method stub
									if (pst==0) {
										((TextView)helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.red));
										((TextView)helper.getView(R.id.tv_time)).setTextColor(getResources().getColor(R.color.red));	
									}
									helper.setText(R.id.tv_name, item.getProcessInfo());
									helper.setText(R.id.tv_time, item.getTime());
								}
						
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				  
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
		};
	};
}
