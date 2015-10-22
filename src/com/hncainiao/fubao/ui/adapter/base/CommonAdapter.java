package com.hncainiao.fubao.ui.adapter.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.hncainiao.fubao.R;
import com.jmheart.base.BaseActivity;

public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	public  List<T> mDatas;
	protected final int mItemLayoutId;
	public List<Boolean> chList=new ArrayList<Boolean>();
	boolean ischek=false;
	public static boolean isclear=false;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}
	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId,boolean mischek)
	{
		this.ischek=mischek;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
		for (int i = 0; i < mDatas.size(); i++) {
			chList.add(false);
		}
	}
	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder viewHolder = getViewHolder(position, convertView,parent);
	
		if (ischek) {
			viewHolder.box =(CheckBox)viewHolder.getView(R.id.ch_shop);
			viewHolder.box.setChecked(chList.get(position));
			BaseActivity.showLog(""+chList.toString());
			BaseActivity.showLog("position->"+position);
			viewHolder.getConvertView().setTag(viewHolder);
		}
		
		convert(viewHolder, getItem(position),position);
		
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder helper, T item,int position);

	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent)
	{
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}
	

}
