package com.tennissetapp.fragments;

import java.util.List;
import java.util.Map;

import com.tennissetapp.EndlessScrollListener;
import com.tennissetapp.R;
import com.tennissetapp.activities.LoginActivity;
import com.tennissetapp.activities.PlayerProfileActivity;
import com.tennissetapp.form.ScrollForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FavoritesMatesFragment extends Fragment {
	private ListView matesListView;
	private TennisMatesListViewAdapter tennisMatesListViewAdapter;
	private View noItemsMessageView;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mates_favorite,container, false);
//		((BaseFragmentActivity)getActivity()).getBackImageView().setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				FragmentTransaction t = getFragmentManager().beginTransaction();
//				t.replace(getId(), new SearchMatesFragment());
//				t.addToBackStack(null);
//				t.commit();				
//			}
//		});
		matesListView = (ListView)rootView.findViewById(R.id.tennis_mates_listview);
		matesListView.setOnScrollListener(new ScrollListener());
		
		tennisMatesListViewAdapter = new TennisMatesListViewAdapter(container.getContext(), R.layout.listitem_tennis_mates);
		matesListView.setAdapter(tennisMatesListViewAdapter);
		matesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(FavoritesMatesFragment.this.getActivity(),PlayerProfileActivity.class);
//				intent.putExtra("userAccountId", String.valueOf(id));
                intent.putExtra("userAccountId", id);
				startActivity(intent);
			}
		});
		
		noItemsMessageView = rootView.findViewById(R.id.no_items_message_linearlayout);
		loadPage();
			
		return rootView;
	}
	
	class ScrollListener extends EndlessScrollListener {
		public ScrollListener(){
			super(10);
		}
		@Override
		public void loadPage(int firstIndex, int maxResults) {
			FavoritesMatesFragment.this.loadPage(firstIndex, maxResults);
		}
		
	}
	
	private void loadPage(){
		this.loadPage(0,10);
	}
	
	private void showNoItemsMessage(){
		noItemsMessageView.setVisibility(View.VISIBLE);
	}
	
	@SuppressWarnings("unchecked")
//	private void loadPage(int firstIndex, int maxResults){
//		ScrollForm f = new ScrollForm();
//		f.firstResult = String.valueOf(firstIndex);
//		f.maxResults = String.valueOf(maxResults);
//		ServiceResponse response = Client.getInstance().scrollUserTennisMates(f);
//		if(response != null){
//			if(response.containsKey(ServiceResponse.EXCEPTION)){
//				Intent intent = new Intent(getActivity(), LoginActivity.class);
//				getActivity().startActivity(intent);
//			}
//			else{
//				List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
//
//
//				if(list.size() > 0){
//					noItemsMessageView.setVisibility(View.GONE);
//					tennisMatesListViewAdapter.addAll(list);
//				}
//				else{
//					showNoItemsMessage();
//				}
//			}
//		}
//		else{
//			Utils.toastServerIsDown(getActivity());
//		}
//	}

    private void loadPage(int firstIndex, int maxResults){
        ScrollForm f = new ScrollForm();
        f.firstResult = String.valueOf(firstIndex);
        f.maxResults = String.valueOf(maxResults);
        Client.getInstance().scrollUserTennisMates(f,new Client.TaskProgress() {
            @Override
            public void onPostExecute(ServiceResponse response) {
                if(response != null){
                    if(response.containsKey(ServiceResponse.EXCEPTION)){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                    else{
                        List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");


                        if(list.size() > 0){
                            noItemsMessageView.setVisibility(View.GONE);
                            tennisMatesListViewAdapter.addAll(list);
                        }
                        else{
                            showNoItemsMessage();
                        }
                    }
                }
                else{
                    Utils.toastServerIsDown(getActivity());
                }
            }
        });

    }
	
	static class TennisMatesListViewAdapter extends ArrayAdapter<Map<String, Object>> {
		
		public TennisMatesListViewAdapter(Context context, int textViewResourceId) {
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
			return Long.valueOf((Integer)item.get("userAccountId"));
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		static class ViewHolder {
			protected TextView nameTextView,addressTextView,detailsTextView;
			protected ImageView profileImageView;
		}
		
		//Optimized getView
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				rowView = inflater.inflate(R.layout.listitem_tennis_mates, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.profileImageView = (ImageView) rowView.findViewById(R.id.tennis_mates_image);
				viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.tennis_mates_name_textview);
				viewHolder.addressTextView = (TextView)rowView.findViewById(R.id.tennis_mates_address_textview);
				
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
		
			Map<String, Object> item = getItem(position);
			if(item.get("profilePhoto") != null && holder.profileImageView != null){
				uploadImage((String)item.get("profilePhoto"),holder.profileImageView);
			}
			
			
			StringBuilder sb = new StringBuilder();
			if(item.containsKey("levelOfPlay")){
				sb.append(item.get("levelOfPlay"));
				sb.append(" | ");
			}
			if(item.containsKey("locality")){
				sb.append(item.get("locality"));
				if(item.containsKey("administrativeAreaLevel1ShortName")){
					sb.append(", ");
					sb.append(item.get("administrativeAreaLevel1ShortName"));
				}
			}
//			C.logd("************ item " + item);
			holder.addressTextView.setText(sb.toString());
			holder.nameTextView.setText(item.get("firstName") + " " + item.get("lastName"));

			return rowView;
		}

		private void uploadImage(String url, final ImageView image){
			Client.getInstance().downloadImageSource(url, new Client.OnDownloadImageComplete() {
				@Override
				public void onComplete(Bitmap bitmap) {
					if(bitmap != null){
						Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 90); //should match the size of the image in the layout otherwise it will look blurry if it is smaller
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
