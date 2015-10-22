package com.hncainiao.fubao.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Kind;



public class tjinfenlei_Adapt extends BaseAdapter {
	
	Context context;
	List<Kind>fenleiList;
	

	public tjinfenlei_Adapt(Context context, List<Kind> fenleiList) {
		this.context = context;
		this.fenleiList = fenleiList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fenleiList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return fenleiList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	static class ViewHolder{
		TextView fenlei;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=LayoutInflater.from(context).inflate(R.layout.item_tijianfenlei, null);
			holder.fenlei=(TextView) arg1.findViewById(R.id.fenlei_info);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		holder.fenlei.setText(fenleiList.get(arg0).getName());
		return arg1;
	}

}
