package com.tennissetapp.activities;

import java.util.List;
import java.util.Map;

import com.tennissetapp.Constants;
import com.tennissetapp.EndlessScrollListener;
import com.tennissetapp.R;
import com.tennissetapp.form.ScrollForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends BaseActivity {
	private ListView messagesListView;
	private MessagesListViewAdapter messagesListViewAdapter;
	private View noItemsMessageView;
	private static final String TAG = MessagesActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		
		messagesListView = (ListView)findViewById(R.id.messages_listview);
		messagesListView.setOnScrollListener(new ItemScrollListener());
		messagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> item = messagesListViewAdapter.getItem(position);
//                Log.d(TAG,"ITEM IS " + item);
                if(item != null){
					Intent intent = new Intent(MessagesActivity.this, MateConversationActivity.class);
					intent.putExtra("mateAccountId", ((Number)item.get("userAccountId")).longValue());
                    intent.putExtra("userFirstName", (String)item.get("userFirstName"));
                    intent.putExtra("userLastName",(String)item.get("userLastName"));
					startActivity(intent);
				}
//				C.logd("CLICK " + position + ", " + id + ", " + messagesListViewAdapter.getItem(position));
			}
			
		});
		messagesListViewAdapter = new MessagesListViewAdapter(this, R.layout.listitem_conversation);
		messagesListView.setAdapter(messagesListViewAdapter);
		noItemsMessageView = findViewById(R.id.no_items_message_linearlayout);
		
		getActivityUtils().initDrawer();
		refresh();
	}
	
	private void refresh(){
		loadPage(0,5);
	}

	@SuppressWarnings("unchecked")
	private void loadPage(int firstIndex, int maxResults){
		ScrollForm form = new ScrollForm();
    	form.maxResults = String.valueOf(maxResults);
    	form.firstResult = String.valueOf(firstIndex);
    	
		ServiceResponse response = Client.getInstance().scrollMessages(form);
//		C.logi("The response, messages: " + response);
		if(response != null){
			if(response.containsKey(ServiceResponse.EXCEPTION)){
				if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
					Intent intent = new Intent(this, LoginActivity.class);
					this.startActivity(intent);
				}
			}
			else{
				List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
//				C.logi("Messages response: " + list);
				if(list.size() > 0){
					noItemsMessageView.setVisibility(View.GONE);
					messagesListViewAdapter.addAll(list);
				}
				else{
					noItemsMessageView.setVisibility(View.VISIBLE);
				}
				
			}
		}
		else{
			Utils.toastServerIsDown(this);
		}
	}
	class ItemScrollListener extends EndlessScrollListener {
		public ItemScrollListener(){
			super(5);
		}
		@Override
		public void loadPage(int firstIndex, int maxResults) {
//			NearbyMatesFragment.this.loadPage(firstIndex, maxResults);
		}
		
	}

	class MessagesListViewAdapter extends ArrayAdapter<Map<String, Object>> {
		public MessagesListViewAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}
		
		@Override
		public boolean areAllItemsEnabled(){
			return true;
		}
		
		@Override 
		public boolean isEnabled(int position){
			return true;
		}

		@Override
		public long getItemId(int position) {
			Map<String, Object> item = getItem(position);
			return Long.valueOf((Integer)item.get("userPostId"));
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		class ViewHolder {
			protected TextView nameTextView,messageTextView,dateTextView;
			protected ImageView playerImageView;
		}
		
		//Optimized getView
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			final Map<String, Object> item = getItem(position);
			
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				rowView = inflater.inflate(R.layout.listitem_message, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.playerImageView = (ImageView) rowView.findViewById(R.id.player_profile_imageview);
				viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.player_name_textview_);
				viewHolder.messageTextView = (TextView)rowView.findViewById(R.id.notification_message_textview);
				viewHolder.dateTextView = (TextView)rowView.findViewById(R.id.notification_time_textview);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
			if(item.get("userProfileImage") != null && holder.playerImageView != null){
				uploadImage(Constants.PROFILES_IMAGES_URL + "/" + item.get("userProfileImage"),holder.playerImageView);
			}

			holder.nameTextView.setText(item.get("userFirstName") + " " + item.get("userLastName"));
			if(item.containsKey("content")){
				holder.messageTextView.setText((String)item.get("content"));
			}
			if(item.containsKey("postedOn") && holder.dateTextView != null){
				holder.dateTextView.setText(Utils.formatTime((Long)item.get("postedOn")));
			}
			return rowView;
		}

		private void uploadImage(String url, final ImageView image){
			Client.getInstance().downloadImageSource(url, new Client.OnDownloadImageComplete() {
				@Override
				public void onComplete(Bitmap bitmap) {
					if(bitmap != null){
						Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 75);
						image.setImageBitmap(roundBitmap);
					}
					else{
						image.setImageResource(R.drawable.circle_no_profile_image);
					}
				}
			});
		}
	}

}
