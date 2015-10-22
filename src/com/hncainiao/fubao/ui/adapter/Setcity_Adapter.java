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
import com.hncainiao.fubao.model.Kind;

public class Setcity_Adapter extends BaseAdapter {
	
	Context context;
    List<Kind>group;	
    public  int tmep=-1;
    
	public Setcity_Adapter(Context context, List<Kind> groups) {
		super();
		this.context = context;
		if(groups!=null){
			group=groups;
		} 
		else {
			group=new ArrayList<Kind>();
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return group.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return group.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public static class ViewHolder{
		public TextView top;
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
		holder.top.setText(group.get(arg0).getName());
		
		if (tmep==arg0) {
			//arg1.setBackgroundResource(R.drawable.my_tab_background2);
			holder.top.setBackgroundResource(R.drawable.my_tab_background2);
		}
		else {
			//arg1.setBackgroundResource(R.drawable.my_tab_background);
			holder.top.setBackgroundResource(R.drawable.my_tab_background);
		}
		return arg1;
	}
	
	
	/**
	 * 设置选中
	 */
	public void setSelet(int postion)
	{
		tmep=postion;
	}
	
	

}
