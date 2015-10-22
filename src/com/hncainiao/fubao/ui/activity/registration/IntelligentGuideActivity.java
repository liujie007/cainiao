package com.hncainiao.fubao.ui.activity.registration;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.registration.bean.MainInteliBean;
import com.hncainiao.fubao.ui.activity.registration.bean.SeInteliBean;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;

/**
 * 	智能导诊
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-10-13下午2:31:33
 */
public class IntelligentGuideActivity extends BaseActivity {

	ListView  listMain,listse;
	CommonAdapter<MainInteliBean> mainAdapter;
	CommonAdapter<SeInteliBean> seAdapter;
	List<MainInteliBean> mainlistdate=new ArrayList<MainInteliBean>();
	List<SeInteliBean> celistdate=new ArrayList<SeInteliBean>();
	Context mContext;
	int mpostion=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intelligentguide_activity);
		mContext=this;
		loaddate();
		inintView();
	}
	
	
	private void inintView()
	{
		setTitle("智能导诊");
		listMain=(ListView)findViewById(R.id.list_mian);
		listse=(ListView)findViewById(R.id.list_se);
		listMain.setAdapter(mainAdapter=new CommonAdapter<MainInteliBean>(mContext, mainlistdate, R.layout.intenligenguide_item) {

			@Override
			public void convert(ViewHolder helper, MainInteliBean item,
					int position) {
				// TODO Auto-generated method stub
				  if (mpostion==position) {
					  helper.getView(R.id.tv_name)
					   .setBackgroundColor(getResources().getColor(R.color.white));
					 ((TextView) helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.blue));
				  }
				  else
				  {
						 ((TextView) helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.black));
							
					  helper.getView(R.id.tv_name).setBackgroundColor(0);
				  }
				helper.setText(R.id.tv_name, item.getName());
				
			}
		});
		listMain.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				   mpostion=arg2;
				// arg1.setBackgroundColor(getResources().getColor(R.color.white));
				 mainAdapter.notifyDataSetChanged();
			}
		});
		listse.setAdapter(new CommonAdapter<SeInteliBean>(mContext, celistdate, R.layout.intenligenguide_ce_item) {

			@Override
			public void convert(ViewHolder helper, SeInteliBean item,
					int position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_name, item.getName());
			}
		});
	}
	private void loaddate()
	{
		for (int i = 0; i < 30; i++) {
			mainlistdate.add(new MainInteliBean("脑壳"));
			celistdate.add(new SeInteliBean("脑壳坏掉"));
		}
		
	}
}
