package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Group;
import com.hncainiao.fubao.model.Kind;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.doctor.DoctorListActivity;
import com.hncainiao.fubao.ui.views.MyGridView;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午2:38:55
 * 
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private ArrayList<Group> groupList;
	private ArrayList<List<Kind>> childList;
	private LayoutInflater inflater;
	private int temp=-1;
	
	public ExpandableListAdapter(Context context, ArrayList<Group> groupList,
		ArrayList<List<Kind>> childList) {
		this.context = context;
		this.groupList = groupList;
		this.childList = childList;
		this.inflater = LayoutInflater.from(context);
		
	}

	// 返回父列表个数
	@Override
	public int getGroupCount() {
		return groupList.size();
	}
	// 返回子列表个数
	@Override
	public int getChildrenCount(int groupPosition) {
		//return childList.get(groupPosition).size();
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
		

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public boolean hasStableIds() {

		return true;
	}

	@SuppressLint("ResourceAsColor") @Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			groupHolder = new GroupHolder();
			convertView = inflater.inflate(R.layout.group, null);
			groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
			groupHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		

		groupHolder.textView.setText(((Group) getGroup(groupPosition)).getName());
		
		if(isExpanded){
			groupHolder.textView.setTextColor(Color.RED);
		}else{
			groupHolder.textView.setTextColor(Color.BLACK);
		}
		
		
		
		
		
		if (isExpanded)// ture is Expanded or false is not isExpanded
			groupHolder.imageView.setImageResource(R.drawable.expanded);
		else
			groupHolder.imageView.setImageResource(R.drawable.collapse);
		if (temp==groupPosition) {
			
			groupHolder.textView.setTextColor(context.getResources().getColor(R.color.red));
		}
		else {
			groupHolder.textView.setTextColor(context.getResources().getColor(R.color.black));
		}
		return convertView;
	}
	public void setSelet(int postion)
	{
		temp=postion;
	}
	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
				childHolder = new ChildHolder();
				convertView = inflater.inflate(R.layout.child, null);
				childHolder.toolbarGrid = (MyGridView) convertView.findViewById(R.id.Grideview);
				childHolder.toolbarGrid.setNumColumns(2);// 设置每行列数
				childHolder.toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
				childHolder.toolbarGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 设置菜单Adapter
			//toolbarGrid.setOnItemClickListener(this);

			//childHolder.textName = (TextView) convertView
			//		.findViewById(R.id.name);
			//childHolder.textAge = (TextView) convertView.findViewById(R.id.age);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
	 	//	childHolder.textName.setText(((Kind) getChild(groupPosition,childPosition)).getName());
        //childHolder.textAge.setText(((Kind) getChild(groupPosition,childPosition)).getId());
		 ChildrenGridviewAdapter adapter=new ChildrenGridviewAdapter(childList.get(groupPosition), context);
		 childHolder.toolbarGrid.setAdapter(adapter);
		 childHolder.toolbarGrid.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				SharedPreferencesConfig.saveStringConfig(context, "off_id", "");
				SharedPreferencesConfig.saveStringConfig(context, "off_name", "");
				Intent intent = new Intent(context, DoctorListActivity.class);
				intent.putExtra("Offices_id",childList.get(groupPosition).get(arg2).getId()+"");
				SharedPreferencesConfig.saveStringConfig(context, "off_id",childList.get(groupPosition).get(arg2).getId()+"");
				intent.putExtra("offices",childList.get(groupPosition).get(arg2).getName()+ "");
			    SharedPreferencesConfig.saveStringConfig(context, "off_name", childList.get(groupPosition).get(arg2).getName()+"");
			    context.startActivity(intent);
			    ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
			
		});

		return convertView;
	}

	class GroupHolder {
		TextView textView;
		ImageView imageView;
	}

	public final class ChildHolder {
		TextView textName;
		TextView textAge;
	public  MyGridView toolbarGrid;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	
}
