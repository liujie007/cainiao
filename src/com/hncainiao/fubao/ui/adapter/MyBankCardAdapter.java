
package com.hncainiao.fubao.ui.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午12:30:38
 * 
 *          我的银行卡列表
 */

public class MyBankCardAdapter extends Adapter<Map<String, Object>> {
	
   private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
   private LayoutInflater inflater;
 
	int arrs[];
	 
	
	HashMap<String, Object> map;

	
	public MyBankCardAdapter(Context mContext) {
		super(mContext);
		this.inflater = LayoutInflater.from(mContext);
	}

	public final  class ViewHolder1 {
		ImageView ivPic;
		TextView tvName;
		TextView tvType;
		TextView tvCardId;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 map = (HashMap<String, Object>) mList.get(position);
	
		ViewHolder1 holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_mybank_card, null);
			holder = new ViewHolder1();
			holder.ivPic = (ImageView) convertView
					.findViewById(R.id.img_item_bank_logo);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_item_bank_name);
			holder.tvType = (TextView) convertView
					.findViewById(R.id.tv_item_card_type);
			holder.tvCardId = (TextView) convertView
					.findViewById(R.id.tv_item_card_id);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder1) convertView.getTag();
		}
	
	
		holder.tvName.setText(map.get("bank_name") + "");
		holder.tvType.setText(map.get("card_type") + "");
		holder.tvCardId.setText( "尾号为"+ map.get("card_id").toString().substring(map.get("card_id").toString().length()-4, map.get("card_id").toString().length())+"");
	
		
	/*	BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), holder.ivPic, 
		BaseActivity.options, animateFirstListener);
	*/	
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
