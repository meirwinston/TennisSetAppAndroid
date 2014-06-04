package com.tennissetapp.fragments;

import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tennissetapp.Constants;
import com.tennissetapp.EndlessScrollListener;
import com.tennissetapp.R;
import com.tennissetapp.activities.LoginActivity;
import com.tennissetapp.activities.PlayerProfileActivity;
import com.tennissetapp.activities.TennisDetailsActivity;
import com.tennissetapp.form.FindByLocationForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

public class NearbyMatesFragment extends Fragment{
	private ListView matesListView;
	private TennisMatesListViewAdapter tennisMatesListViewAdapter;
	private View noItemsMessageView;
	private static final String TAG = NearbyMatesFragment.class.getSimpleName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mates_nearby,container, false);
		matesListView = (ListView)rootView.findViewById(R.id.tennis_mates_listview);
		matesListView.setOnScrollListener(new ItemScrollListener());
		
		tennisMatesListViewAdapter = new TennisMatesListViewAdapter(container.getContext(), R.layout.listitem_tennis_mates);
		matesListView.setAdapter(tennisMatesListViewAdapter);
		
		matesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(NearbyMatesFragment.this.getActivity(),PlayerProfileActivity.class);
//				intent.putExtra("userAccountId", String.valueOf(id));
                intent.putExtra("userAccountId", id);
				startActivity(intent);
			}
		});
		
		noItemsMessageView = rootView.findViewById(R.id.no_items_message_linearlayout);
		refresh();
		return rootView;
	}
	
	private void refresh(){
		loadPage(0,5);
	}
	
	public void openTennisDetailsActivity(){
		Intent intent = new Intent(getActivity(),TennisDetailsActivity.class);
		this.getActivity().startActivity(intent);
	}
	
	class ItemScrollListener extends EndlessScrollListener {
		public ItemScrollListener(){
			super(5);
		}
		@Override
		public void loadPage(int firstIndex, int maxResults) {
			NearbyMatesFragment.this.loadPage(firstIndex, maxResults);
		}
		
	}
	
	@SuppressWarnings("unchecked")
//	private void loadPage(int firstIndex, int maxResults){
////		C.logd("NearbyMatesFragment.loadPage ");
//
//        //welcome message
//        ServiceResponse idResponse = Client.getInstance().getPlayerProfileId();
//        if(!idResponse.containsKey("playerProfileId")){
//            showWelcomeDialog();
//        }
//        else{
//            FindByLocationForm form = new FindByLocationForm();
//            form.maxResults = String.valueOf(maxResults);
//            form.firstResult = String.valueOf(firstIndex);
//            if(Utils.getDeviceLocation(getActivity()) != null){
//                form.latitude = String.valueOf(Utils.getDeviceLocation(getActivity()).getLatitude());
//                form.longitude = String.valueOf(Utils.getDeviceLocation(getActivity()).getLongitude());
//            }
//            else{
//                Log.d(getClass().getSimpleName(), "NearbyMatesFragment: Location is null");
//                return;
//            }
//            form.distance = String.valueOf(Constants.NEARBY_DISTANCE_KM);
//
//            ServiceResponse response = Client.getInstance().findNearbyTennisMates(form);
////		C.logd("NearbyMatesFragment.loadPage response: " + response);
////		C.logi("The response, nearby courts: " + response);
//            if(response != null){
//                if(response.containsKey(ServiceResponse.EXCEPTION)){
//                    if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        this.startActivity(intent);
//                    }
//                    else{
//                        Utils.popupErrors(getActivity(), response);
//                    }
//                }
//                else{
//                    List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
////				C.logd("NearbyMatesFragment.loadPage list " + list.size());
////				list.clear(); //TODO TEST ONLY
////				C.logi("Nearby Mates response: " + list);
//                    if(list.size() > 0){
//                        Utils.removeUser(list,Utils.getUserAccountId(this.getActivity()));
//                        noItemsMessageView.setVisibility(View.GONE);
//                        tennisMatesListViewAdapter.addAll(list);
//                    }
//                    else{
//                        noItemsMessageView.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//            else{
//                Utils.toastServerIsDown(getActivity());
//            }
//        }
//	}

    private void loadPage(int firstIndex, int maxResults){
//		C.logd("NearbyMatesFragment.loadPage ");

        //welcome message
        ServiceResponse idResponse = Client.getInstance().getPlayerProfileId();
        if(!idResponse.containsKey("playerProfileId")){
            showWelcomeDialog();
        }
        else{
            FindByLocationForm form = new FindByLocationForm();
            form.maxResults = String.valueOf(maxResults);
            form.firstResult = String.valueOf(firstIndex);
            if(Utils.getDeviceLocation(getActivity()) != null){
                form.latitude = String.valueOf(Utils.getDeviceLocation(getActivity()).getLatitude());
                form.longitude = String.valueOf(Utils.getDeviceLocation(getActivity()).getLongitude());
            }
            else{
                Log.d(getClass().getSimpleName(), "NearbyMatesFragment: Location is null");
                return;
            }
            form.distance = String.valueOf(Constants.NEARBY_DISTANCE_KM);

            Client.getInstance().findNearbyTennisMates(form,new Client.TaskProgress() {
                @Override
                public void onPostExecute(ServiceResponse response) {
                    if(response != null){
                        if(response.containsKey(ServiceResponse.EXCEPTION)){
                            if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Utils.popupErrors(getActivity(), response);
                            }
                        }
                        else{
                            List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
                            if(list.size() > 0){
                                Utils.removeUser(list,Utils.getUserAccountId(getActivity()));
                                noItemsMessageView.setVisibility(View.GONE);
                                tennisMatesListViewAdapter.addAll(list);
                            }
                            else{
                                noItemsMessageView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    else{
                        Utils.toastServerIsDown(getActivity());
                    }
                }
            });
        }
    }
	
	private void showWelcomeDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(NearbyMatesFragment.this.getActivity());
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_provide_tennis_details, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openTennisDetailsActivity();
				dialog.dismiss();
				getActivity().finish();
			}
		});
//        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
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
			protected ImageView playerImageView, profileImageView, messageImageView;
		}
		
		private void openProfileActivity(long userAccountId){
			Intent intent = new Intent(getContext(), PlayerProfileActivity.class);
//			intent.putExtra("userAccountId", String.valueOf(userAccountId));
            intent.putExtra("userAccountId", userAccountId);
			getContext().startActivity(intent);
		}
		
		//Optimized getView
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				rowView = inflater.inflate(R.layout.listitem_tennis_mates, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.playerImageView = (ImageView) rowView.findViewById(R.id.tennis_mates_image);
				viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.tennis_mates_name_textview);
				viewHolder.addressTextView = (TextView)rowView.findViewById(R.id.tennis_mates_address_textview);
				
				
//				viewHolder.profileImageView = (ImageView)rowView.findViewById(R.id.tennis_mates_profile_imageview);
//				viewHolder.messageImageView = (ImageView)rowView.findViewById(R.id.tennis_mates_envelope_imageview);
//				
//				viewHolder.nameTextView.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						openProfileActivity(getItemId(position));
//					}
//				});
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
		
			Map<String, Object> item = getItem(position);
			if(item.get("profilePhoto") != null && holder.playerImageView != null){
				uploadImage((String)item.get("profilePhoto"),holder.playerImageView);
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
			
			holder.addressTextView.setText(sb.toString());
			holder.nameTextView.setText(item.get("firstName") + " " + item.get("lastName"));

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