package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.hospital;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SelectHospitalAdapte extends BaseAdapter {
	
   private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	List<hospital> hospitals;
	Context context;
	
	public SelectHospitalAdapte(List<hospital> hos3pital, Context context) {
	
		if (hos3pital!=null) {
			this.hospitals = hos3pital;
		}
		else
		{
			hospitals=new ArrayList<hospital>();
		}
		
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hospitals.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return hospitals.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.item_list_hospital, null);
			holder = new ViewHolder();
			holder.ivPic = (ImageView) arg1
					.findViewById(R.id.img_item_hospital);
			holder.tvName = (TextView) arg1
					.findViewById(R.id.tv_item_hospital_name);
			holder.tvLevel = (TextView) arg1
					.findViewById(R.id.tv_item_hospital_level);
			holder.tvDistance = (TextView) arg1
					.findViewById(R.id.tv_item_hospital_diatance);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.tvName.setText(hospitals.get(arg0).getName());
		holder.tvLevel.setText(hospitals.get(arg0).getType());
		holder.tvDistance.setText(hospitals.get(arg0).getDistance());
     	//Urlto_bitmip bitmip=new Urlto_bitmip();
		BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+hospitals.get(arg0).getImg(), holder.ivPic, 
		BaseActivity.options, animateFirstListener);
		//holder.ivPic.setImageBitmap(bitmip.getbimp("http://wx.zgcainiao.com/Uploads/Images/"+hospitals.get(arg0).getImg()+""));
		return arg1;
	}

	static class ViewHolder {
		ImageView ivPic;
		TextView tvName;
		TextView tvLevel;
		TextView tvDistance;

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


