package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-22下午4:45:05
 *   體檢人適配器
 */
public class Patient_Adape extends Adapter<Map<String, Object>> {

	private Context mContext;
	private Activity activity;

	private LayoutInflater inflater;
	public static Map<Integer, Boolean> isSelected;
	HashMap<String, Object> map;
	private int temp=-1;
	public static List<CheckBox> listcheck=new ArrayList<CheckBox>();

	public Patient_Adape(Context Context,Activity cActivity) {
		super(Context);
		this.mContext = Context;
		activity=cActivity;
		this.inflater = LayoutInflater.from(mContext);

	}
	public Patient_Adape(Context Context) {
		super(Context);
		this.mContext = Context;
	
		this.inflater = LayoutInflater.from(mContext);

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public final class ViewHolder {
		TextView tvName;
		TextView idcard;
		TextView phone;
		TextView cardno;
		public RadioButton choose;
		public RelativeLayout relative;
		TextView tv1,tv2,tv3;

	}
	@SuppressWarnings("unused")
	@SuppressLint("UseSparseArrays") @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		map = (HashMap<String, Object>) mList.get(position);
		isSelected= new HashMap<Integer, Boolean>();
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_patient_person, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.listpatient_name);
			holder.idcard = (TextView) convertView.findViewById(R.id.idcard);
			holder.cardno = (TextView) convertView.findViewById(R.id.cardno);
			holder.phone = (TextView) convertView.findViewById(R.id.listphone);
			holder.choose=(RadioButton) convertView.findViewById(R.id.patient_choose);
			holder.relative=(RelativeLayout)convertView.findViewById(R.id.rela);
			
			holder.tv1= (TextView) convertView.findViewById(R.id.tv1);
			holder.tv2= (TextView) convertView.findViewById(R.id.tv2);
			holder.tv3= (TextView) convertView.findViewById(R.id.tv3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	    
		holder.tvName.setText(map.get("name") + "");
		holder.idcard.setText(map.get("idnumber") + "");
		holder.cardno.setText(map.get("cardno") + "");
		holder.phone.setText(map.get("phone") + "");
		
		holder.choose.setId(position);//对checkbox的id进行重新设置为当前的position
		
		holder.choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){//实现checkbox的单选功能,同样适用于radiobutton
					if(temp!=-1){
						//找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
					
						if (activity!=null) {
							RadioButton tempCheckBox=(RadioButton)activity.findViewById(temp);
							if(tempCheckBox!=null)
										tempCheckBox.setChecked(false);
								}
						}
				
					temp=buttonView.getId();//保存当前选中的checkbox的id值		
				}
			}
		});
		
		
		if(position==temp)//比对position和当前的temp是否一致
		{
			holder.choose.setChecked(true);
			setChossPassed(holder);
		}
			
		else 
		{
			holder.choose.setChecked(false);
			setChossNomall(holder);
		}
			
		
		return convertView;

	}
	public void selet(int postion)
	{
		temp=postion;
	}
	/**
	 * 选中控件
	 */
	public void setChossPassed(ViewHolder holder)
	{
		holder.relative.setBackgroundResource(R.drawable.corners_bg);
		holder.tvName.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.idcard.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.cardno.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.phone.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.tv1.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.tv2.setTextAppearance(mContext,R.style.text_person_registrtion_person);
		holder.tv3.setTextAppearance(mContext,R.style.text_person_registrtion_person);
	}
	/**
	 * 去掉控件选中状态
	 */
	public void setChossNomall(ViewHolder holder)
	{
		holder.relative.setBackgroundResource(R.drawable.corners_bg_nomal);
		holder.tvName.setTextAppearance(mContext,R.style.text_person_phyinfo);
		holder.idcard.setTextAppearance(mContext,R.style.text_person_phyinfo);
		holder.cardno.setTextAppearance(mContext,R.style.text_person_phyinfo);
		holder.phone.setTextAppearance(mContext,R.style.text_person_phyinfo);
		
		holder.tv1.setTextAppearance(mContext,R.style.text_person_phyinfo);
		holder.tv2.setTextAppearance(mContext,R.style.text_person_phyinfo);
		holder.tv3.setTextAppearance(mContext,R.style.text_person_phyinfo);
	}


}
