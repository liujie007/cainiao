package com.hncainiao.fubao.ui.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午12:30:38
 * 
 *          我的银行卡列表
 */

public class BankCardAdapter extends Adapter<Map<String, Object>> {
	
	   private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


	private LayoutInflater inflater;
	
	public static Map<Integer,Boolean> Selected;
	
	int arrs[];
	 
	
	HashMap<String, Object> map;
	private int temp=-1;
	Activity activity;
	
	public BankCardAdapter(Context mContext) {
		super(mContext);
		this.inflater = LayoutInflater.from(mContext);
	}
	public BankCardAdapter(Context mContext ,Activity activity) {
		super(mContext);
		this.inflater = LayoutInflater.from(mContext);
		this.activity=activity;
	}
	
	public final  class ViewHolder1 {
		ImageView ivPic;
		TextView tvName;
		TextView tvType;
		TextView tvCardId;
       public RadioButton choose;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 map = (HashMap<String, Object>) mList.get(position);
		 Selected=new HashMap<Integer, Boolean>();
		for(int i=0;i<mList.size();i++){
			Selected.put(i, false);
		}
		
		ViewHolder1 holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_bank_card, null);
			holder = new ViewHolder1();
			holder.ivPic = (ImageView) convertView
					.findViewById(R.id.img_item_bank_logo);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_item_bank_name);
			holder.tvType = (TextView) convertView
					.findViewById(R.id.tv_item_card_type);
			holder.tvCardId = (TextView) convertView
					.findViewById(R.id.tv_item_card_id);
			holder.choose = (RadioButton) convertView
					.findViewById(R.id.iv_item_bank_check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder1) convertView.getTag();
		}
	
	
		holder.tvName.setText(map.get("bank_name") + "");
		holder.tvType.setText(map.get("card_type") + "");
		holder.tvCardId.setText(map.get("card_id") + "");
		holder.choose.setId(position);//对checkbox的id进行重新设置为当前的position
		holder.choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){//实现checkbox的单选功能,同样适用于radiobutton
					if(temp!=-1){
						//找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
						RadioButton tempCheckBox=(RadioButton)activity.findViewById(temp);
						if(tempCheckBox!=null)
							tempCheckBox.setChecked(false);
					}
					temp=buttonView.getId();//保存当前选中的checkbox的id值		
				}
			}
		});
		
		if(position==temp)//比对position和当前的temp是否一致
			holder.choose.setChecked(true);
		else 
			holder.choose.setChecked(false);
		
		BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), holder.ivPic, 
		BaseActivity.options, animateFirstListener);
		
		return convertView;
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
	}
  }
	
}
