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
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ExpertsearchAdapter extends Adapter<Map<String, Object>> {
	 private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	 private Context mContext;

		private LayoutInflater inflater;

		public ExpertsearchAdapter(Context Context) {
			super(Context);
			this.mContext = Context;
			this.inflater = LayoutInflater.from(mContext);
		}
		static class ViewHolder {
			ImageView ivPic;
			TextView tvName;
			TextView doctor_zhiwei;
			TextView hisoital_name;
			TextView good_at;
			TextView zixun_num;
		}
		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HashMap<String, Object> map = (HashMap<String, Object>) mList
					.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.expertsearch_item, null);
				holder = new ViewHolder();
				holder.ivPic = (ImageView) convertView
						.findViewById(R.id.img_item_doctor);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tv_item_doctor_name);
				holder.doctor_zhiwei = (TextView) convertView
						.findViewById(R.id.doctor_zhiwei);
				holder.hisoital_name = (TextView) convertView
						.findViewById(R.id.tv_item_doctor_locate);
				holder.good_at = (TextView) convertView
						.findViewById(R.id.tv_item_doctor_level);
				holder.zixun_num = (TextView) convertView
						.findViewById(R.id.num);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvName.setText(map.get("name") + "");
			holder.doctor_zhiwei.setText(map.get("zhiwei") + "");
			holder.hisoital_name.setText(map.get("hospital_name") + "");
			holder.good_at.setText(map.get("good_at")+"");
			holder.zixun_num.setText(map.get("zixun_num")+"");
		
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
