package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.AddAllPayAdapter;
import com.hncainiao.fubao.ui.adapter.AddAllPayAdapter.ViewHolder;

public class AddAllPayActivity extends BaseActivity {
	/**
	 * 合并付款
	 * */
	
	Context mContext;
	ListView listView;
	List<Map<String, Object>>mList;
	AddAllPayAdapter adapter;
	LinearLayout cannel,pay;
	
	/**
	 * 选中的数量
	 * */
	int choosenum;
	
	/**
	 * 选中项的Id集合
	 * */
	List<String>lis=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addallpay);
		Initview();
		getData();
	}

	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<5;i++){
			map=new HashMap<String, Object>();
			map.put("name", "张艺馨");
			map.put("data", "03-17");
			map.put("connect", "验光检查");
			map.put("money", "30元");
			map.put("hospiatl", "长沙市第一医院");
			map.put("id", i);
			mList.add(map);
		}
		adapter=new AddAllPayAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
	
	}

	private void Initview() {
		mContext=this;
		((TextView) findViewById(R.id.hebingfukuan_hui))
		.setVisibility(View.VISIBLE);
		setTitle("诊中支付");
		listView=(ListView) findViewById(R.id.list);
		cannel=(LinearLayout) findViewById(R.id.cannel);
		pay=(LinearLayout) findViewById(R.id.pay);
		cannel.setOnClickListener(new l());
		pay.setOnClickListener(new l());
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
	             ViewHolder holder = (ViewHolder) arg1.getTag();
		                // 改变CheckBox的状态
		                holder.box.toggle();
		                // 将CheckBox的选中状况记录下来
		                AddAllPayAdapter.getIsSelected().put(arg2, holder.box.isChecked()); 
		                // 调整选定条目
		                if (holder.box.isChecked() == true) {
		                    choosenum++;
		                    lis.add(mList.get(arg2).get("id")+"");  
		                } else {
		                	choosenum--;
                          lis.remove(mList.get(arg2).get("id")+"");
		                }
		                // 用TextView显示
		                showToast(lis.toString());
		            }
		});
		
		
	}
	
	class l implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=null;
			
			switch (arg0.getId()) {
			
			case R.id.cannel:
				intent =new Intent(mContext,MyPayActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.pay:
				intent=new Intent(mContext,ConfirmPayActivity.class);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				startActivity(intent);
				break;

			default:
				break;
			}
			
		}
		
	}


}
