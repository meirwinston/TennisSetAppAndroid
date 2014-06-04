package com.tennissetapp.ui;

import java.util.Map;
import android.widget.ImageView;
import android.widget.TextView;
import com.tennissetapp.R;
import com.tennissetapp.rest.Client;
import com.tennissetapp.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TennisMatesListViewAdapter extends ArrayAdapter<Map<String, Object>> {
	
	public TennisMatesListViewAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
//		super.addAll(objects);
//		Log.i(C.LogTag,"ADDING ITEMS TO ADAPTER SIZE: " + this.getCount());
	}

	@Override
	public long getItemId(int position) {
		Map<String, Object> item = getItem(position);
		return Long.valueOf((Integer)item.get("userAccountId"));
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	static class ViewHolder {
		protected TextView nameTextView,contentTextView;
		protected ImageView profileImageView,mailImageView;
	}
	
	//Optimized getView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflater.inflate(R.layout.listitem_mates_player_profile, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.profileImageView = (ImageView) rowView.findViewById(R.id.tennis_mate_profile_photo);
			viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.item_title);
			viewHolder.contentTextView = (TextView)rowView.findViewById(R.id.item_content);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		//--
		
		Map<String, Object> item = getItem(position);
		uploadImage((String)item.get("profileImageUrl"),holder.profileImageView);
		
		ImageView mailImageView = (ImageView)rowView.findViewById(R.id.tennis_mate_mail_imageview);
		mailImageView.setImageResource(R.drawable.envelope);
		
		holder.contentTextView.setText(item.get("levelOfPlay") + "  |  " + item.get("administrativeAreaLevel1") + ", " + item.get("country"));
		holder.nameTextView.setText(item.get("firstName") + " " + item.get("lastName"));

		return rowView;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Map<String, Object> item = getItem(position);
//		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.tennis_mates_listitem, parent, false);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.tennis_mate_profile_photo);
//		TextView nameTextView = (TextView)rowView.findViewById(R.id.item_title);
//		TextView contentTextView = (TextView)rowView.findViewById(R.id.item_content);
//		
//		
//		//--
//		
//		uploadImage((String)item.get("profileImageUrl"),imageView);
//		
//		ImageView mailImageView = (ImageView)rowView.findViewById(R.id.tennis_mate_mail_imageview);
//		mailImageView.setImageResource(R.drawable.mail_37x30);
//		
//		contentTextView.setText(item.get("levelOfPlay") + "|" + item.get("administrativeAreaLevel1") + ", " + item.get("country"));
//		nameTextView.setText(item.get("firstName") + " " + item.get("lastName"));
//		
//		return rowView;
//	}
	
	private void uploadImage(String url, final ImageView image){
		Client.getInstance().downloadImageSource(url, new Client.OnDownloadImageComplete() {
			@Override
			public void onComplete(Bitmap bitmap) {
				if(bitmap != null){
					Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 60);
					image.setImageBitmap(roundBitmap);
				}
				else{
					image.setImageResource(R.drawable.circle_no_profile_image);
				}
			}
		});
	}
}