package com.tennissetapp.fragments;

import java.util.List;
import java.util.Map;
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

import com.tennissetapp.EndlessScrollListener;
import com.tennissetapp.R;
import com.tennissetapp.activities.BaseFragmentActivity;
import com.tennissetapp.activities.LoginActivity;
import com.tennissetapp.activities.PlayerProfileActivity;
import com.tennissetapp.form.ScrollForm;
import com.tennissetapp.form.SearchByNameOrEmailForm;
import com.tennissetapp.form.SearchTennisMatesForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

public class SearchResultsMatesFragment extends Fragment{
	private ListView matesListView;
	private TennisMatesListViewAdapter tennisMatesListViewAdapter;
//	private EditText searchEditText;
	private ScrollForm searchForm = null;
		
	public SearchResultsMatesFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mates_search_results,container, false);
		
//		((BaseFragmentActivity)getActivity()).getActivityUtils().setLeftButtonBackAction();
//		((BaseFragmentActivity)getActivity()).getActivityUtils().setBackStackFragment(getFragmentManager().findFragmentByTag(TennisMatchesActivity.SEARCH_FRAGMENT_TAG));
		
		matesListView = (ListView)rootView.findViewById(R.id.tennis_mates_listview);
		matesListView.setOnScrollListener(new ScrollListener());
		
		tennisMatesListViewAdapter = new TennisMatesListViewAdapter(container.getContext(), R.layout.listitem_tennis_mates);
		matesListView.setAdapter(tennisMatesListViewAdapter);
		matesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(SearchResultsMatesFragment.this.getActivity(),PlayerProfileActivity.class);
//				intent.putExtra("userAccountId", String.valueOf(id));
                intent.putExtra("userAccountId", id);
				startActivity(intent);
			}
			
		});
		
//		this.searchEditText = (EditText)getActivity().findViewById(R.id.tennis_mates_search_edittext);
//		this.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				submitSearch();
//				//if you return true this method will be called twice
//				return false;
//			}
//		});
//		refresh();
		
		
//		C.logd("onCreateView..... bundle is " + savedInstanceState + ", " + getArguments());
//		if(getArguments() != null){
//			this.searchForm = (ScrollForm)getArguments().get(ScrollForm.class.getName());
//		}
			
		loadPage();
			
		return rootView;
	}
	
//	public void setSearchForm(ScrollForm form){
//		this.searchForm = form;
//	}
	
	public boolean isActive() {
	    return isAdded() && !isDetached() && !isRemoving();
	}
	
//	private SearchByNameOrEmailForm toForm(){
//		SearchByNameOrEmailForm f = new SearchByNameOrEmailForm();
//		f.nameOrEmail = this.searchEditText.getText().toString();
//		return f;
//	}
//	
//	private void submitSearch(){
//		SearchByNameOrEmailForm f = toForm();
//		f.firstResult = "0";
//		f.maxResults = "10";
//		
//		loadPage(f);
//	}
	
	class ScrollListener extends EndlessScrollListener {
		public ScrollListener(){
			super(10);
		}
		@Override
		public void loadPage(int firstIndex, int maxResults) {
			SearchResultsMatesFragment.this.loadPage(firstIndex, maxResults);
		}
		
	}
	
	@SuppressWarnings("unchecked")
//	public void loadPage(SearchTennisMatesForm searchForm){
//		ServiceResponse response = Client.getInstance().searchTennisMates(searchForm);
////		C.logi("The response, search mates: " + response);
//		if(response != null){
//			if(response.containsKey(ServiceResponse.EXCEPTION)){
//				if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
//					Intent intent = new Intent(getActivity(), LoginActivity.class);
//					this.startActivity(intent);
//				}
//			}
//			else{
//				List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
//
//				if(list != null && list.size() > 0){
////                    for(Map<String,Object> m : list){
////                        Log.i(getClass().getSimpleName(),"****************search Mates response: " + m.get("userAccountId"));
////                    }
//					Utils.removeUser(list, Utils.getUserAccountId(this.getActivity()));
//					tennisMatesListViewAdapter.addAll(list);
//				}
//			}
//		}
//		else{
//			((BaseFragmentActivity)getActivity()).getActivityUtils().toastServerIsDown();
//		}
//	}

    public void loadPage(SearchTennisMatesForm searchForm){
        Client.getInstance().searchTennisMates(searchForm, new Client.TaskProgress(){
            @Override
            public void onPostExecute(ServiceResponse response) {
//		C.logi("The response, search mates: " + response);
                if(response != null){
                    if(response.containsKey(ServiceResponse.EXCEPTION)){
                        if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");

                        if(list != null && list.size() > 0){
//                    for(Map<String,Object> m : list){
//                        Log.i(getClass().getSimpleName(),"****************search Mates response: " + m.get("userAccountId"));
//                    }
                            Utils.removeUser(list, Utils.getUserAccountId(getActivity()));
                            tennisMatesListViewAdapter.addAll(list);
                        }
                    }
                }
                else{
                    ((BaseFragmentActivity)getActivity()).getActivityUtils().toastServerIsDown();
                }
            }
        });
    }
	
	@SuppressWarnings("unchecked")
	public void loadPage(SearchByNameOrEmailForm searchForm){
		ServiceResponse response = Client.getInstance().searchTennisMates(searchForm);
		Log.i(getClass().getSimpleName(), "The response, search mates by name: " + response);
		tennisMatesListViewAdapter.clear();
		if(response != null){
			if(response.containsKey(ServiceResponse.EXCEPTION)){
				if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					this.startActivity(intent);
				}
			}
			else{
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)response.get("list");
//				C.logi("search Mates response: " + list);
				
				if(list != null){
                    Utils.removeUser(list, Utils.getUserAccountId(this.getActivity()));
					tennisMatesListViewAdapter.addAll(list);
				}
			}
		}
		else{
			((BaseFragmentActivity)getActivity()).getActivityUtils().toastServerIsDown();
		}
	}
	
//	private void loadPage(Bundle bundle){
//		SearchTennisMatesForm searchForm  = (SearchTennisMatesForm)bundle.get(SearchTennisMatesForm.class.getName());
//		if(searchForm != null){
//			loadPage(searchForm);
//		}
//		else{
//			SearchByNameOrEmailForm searchByNameForm = (SearchByNameOrEmailForm)bundle.get(SearchByNameOrEmailForm.class.getName());
//			if(searchByNameForm != null){
//				loadPage(searchByNameForm);
//			}
//		}
//	}
	
	private void loadPage(){
		this.searchForm = (ScrollForm)this.getArguments().getSerializable(ScrollForm.class.getName());
		this.loadPage(0,10);
	}
	private void loadPage(int firstIndex, int maxResults){
		if( this.searchForm == null ) return;
		if(this.searchForm instanceof SearchTennisMatesForm){
			SearchTennisMatesForm searchForm  = (SearchTennisMatesForm)this.searchForm;
//			C.logd("loadPage(index,max)1:" + searchForm); 
			searchForm.firstResult = String.valueOf(firstIndex);
			searchForm.maxResults = String.valueOf(maxResults);
			loadPage(searchForm);	
		}
		else if(this.searchForm instanceof SearchByNameOrEmailForm){
			SearchByNameOrEmailForm searchByNameForm = (SearchByNameOrEmailForm)this.searchForm;
//			C.logd("loadPage(index,max)2:" + searchByNameForm);
			searchByNameForm.firstResult = String.valueOf(firstIndex);
			searchByNameForm.maxResults = String.valueOf(maxResults);
			
			loadPage(searchByNameForm);
		}
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
