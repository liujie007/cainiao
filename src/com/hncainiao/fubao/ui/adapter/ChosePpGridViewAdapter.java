package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.healthshop.bean.TypeBean;

public class ChosePpGridViewAdapter extends BaseAdapter {

	List<TypeBean> list;
	Context mContext;
	public static List<Boolean> lIntegers=new ArrayList<Boolean>();
	public ChosePpGridViewAdapter(List<TypeBean> mlist,Context context)
	{
		mContext=context;
		if (mlist!=null) {
			list=mlist;
		}
		else
		{
			list=new ArrayList<TypeBean>();
		}
		for (int i = 0; i < list.size(); i++) {
			lIntegers.add(false);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TypeBean typeBean=list.get(arg0);
		ViewCeChe viewCeChe;
		if (arg1==null) {
			arg1=LinearLayout.inflate(mContext, R.layout.heath_grid_item, null);
			viewCeChe=new ViewCeChe();
			viewCeChe.tvname=(TextView)arg1.findViewById(R.id.tv_grid);
			arg1.setTag(viewCeChe);
		}else {
			viewCeChe=(ViewCeChe) arg1.getTag();
		}
	
		if (lIntegers.get(arg0)) {
			viewCeChe.tvname.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
			
		}
		else {
			viewCeChe.tvname.setBackgroundResource(R.drawable.heath_sai_box);
			
		}
		viewCeChe.tvname.setText(typeBean.getName());
		return arg1;
	}
public  class ViewCeChe
  {
	  TextView tvname;
  }

}
