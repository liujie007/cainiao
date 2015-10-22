package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class Juti_Zm_Adapter extends BaseAdapter {
	
	Context context;
	List<String>mlist;
	

	public Juti_Zm_Adapter(Context context, List<String> list) {
		super();
		this.context = context;
		if(list!=null){
			mlist=list;
		}else{
			mlist=new ArrayList<String>();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	class ViewHolder{
		TextView top;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder=null;
		if(arg1==null){
			arg1=LayoutInflater.from(context).inflate(R.layout.item_gridview_othercity, null);
			holder=new ViewHolder();
			holder.top=(TextView) arg1.findViewById(R.id.cityname);
             arg1.setTag(holder);			
		}else{ 
			holder = (ViewHolder) arg1.getTag();

		}
		holder.top.setText(mlist.get(arg0));
		return arg1;
	}
	
	
	

}
