package com.tennissetapp.activities;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tennissetapp.R;
import com.tennissetapp.form.PostMessageForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerProfileActivity extends BaseFragmentActivity {
	protected final int REQUEST_CODE_RESULT_LOAD_IMAGE = 1;
	private String uploadedImageFileId;
	private ImageView profileImageView;
	private TextView levelOfPlayTextView,typeOfPlayTextView,availabilityTextView;
	private Map<String,Object> profileInfo;
//	private TextView titleBarTextView;
	private TextView locationTextView;
	private Button addToMatesButton,messageButton;
	private ViewGroup actionButtonsLayout;
    private static final String TAG = PlayerProfileActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_player_profile);
		super.onCreate(savedInstanceState,R.string.title_activity_player_profile,false,true);

		actionButtonsLayout = (ViewGroup)findViewById(R.id.action_buttons_layout);
		locationTextView = (TextView)findViewById(R.id.player_profile_location);
		addToMatesButton = (Button)findViewById(R.id.add_to_mates_button);
		messageButton = (Button)findViewById(R.id.message_button);
		profileImageView = (ImageView)findViewById(R.id.player_profile_photo);
		levelOfPlayTextView = (TextView)findViewById(R.id.player_profile_levelOfPlay);
		typeOfPlayTextView = (TextView)findViewById(R.id.player_profile_typeOfPlay);
		availabilityTextView = (TextView)findViewById(R.id.player_profile_availability);

        long userAccountId = getIntent().getLongExtra("userAccountId",0);
        if(userAccountId != 0){
            fetchUserAccount(userAccountId);
        }
        else{
            Log.e(TAG,"userAccountId is 0 check the intent extra");
        }

        getActivityUtils().initDrawer();
		
		addToMatesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mateAccountId = getIntent().getStringExtra("userAccountId");
				Log.d(getClass().getSimpleName(),"onclick add to mates button " + mateAccountId);
				ServiceResponse response = Client.getInstance().addTennisMate(mateAccountId);
				if(response.containsKey("mateAccountId")){
					disableAddToMatesButton();
				}
				Log.d(getClass().getSimpleName(),"the response is " + response);
			}
		});
		
		messageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openMateConversationActivity();
			}
		});
//        Log.d(TAG,"THE ACCOUNT ID: " + userAccountId + ", " + Utils.getUserAccountId(this));
		if(userAccountId == Utils.getUserAccountId(this)){
			actionButtonsLayout.setVisibility(View.GONE);
		}
	}
	
	private void postMessage(String message){
//		String mateAccountId = getIntent().getStringExtra("userAccountId");
//		ServiceResponse response = Client.getInstance().postMessageToUser(mateAccountId, "ABC");
//		if(response.containsKey("mateAccountId")){
//			disableAddToMatesButton();
//		}
		PostMessageForm form = new PostMessageForm();
		form.message = message;
        long toUserAccountId = getIntent().getLongExtra("userAccountId",0);
		form.toUserAccountId = String.valueOf(toUserAccountId != 0 ? toUserAccountId : null);
		
		ServiceResponse response = Client.getInstance().postMessageToUser(form);
	}
	
	private void openMateConversationActivity(){
		Intent intent = new Intent(PlayerProfileActivity.this, MateConversationActivity.class);
        long mateAccountId = getIntent().getLongExtra("userAccountId",0);
        if(mateAccountId != 0){
            intent.putExtra("mateAccountId", mateAccountId);
        }

		startActivity(intent);
	}
	
	private void disableAddToMatesButton(){
		addToMatesButton.setEnabled(false);
		addToMatesButton.setText("Added");
	}
	
	private void fetchUserAccount(long userAccountId){
		Log.d(TAG,"fetchUserAccount " + userAccountId);
		ServiceResponse response = Client.getInstance().getTennisMate(userAccountId);
		if(response.containsKey("item")){
			disableAddToMatesButton();
		}
		
		response = Client.getInstance().getPlayerProfile(userAccountId);
		
		if(response.containsKey("exception")){
			if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
				Intent intent = new Intent(PlayerProfileActivity.this, LoginActivity.class);
				intent.putExtra("class", this.getClass().getName());
				PlayerProfileActivity.this.startActivity(intent);
			}
		}
		else{
			profileInfo = (Map<String,Object>)response.get("profile");
            if(profileInfo != null){
                refresh();
            }
            else{
                Log.e(TAG,"profileInfo is null, check why? HERE is the response" + response);
            }

		}
		
	}
	
	private void populateTypeOfPlay(){
		StringBuilder sb = new StringBuilder();
		
		if(profileInfo.get("playSingles").equals(Boolean.TRUE)){
			sb.append("Singles");
		}
		if(profileInfo.get("playDoubles").equals(Boolean.TRUE)){
			if(sb.length() > 0) sb.append(", ");
			sb.append("Doubles");
		}
		if(profileInfo.get("playFullMatch").equals(Boolean.TRUE)){
			if(sb.length() > 0) sb.append(", ");
			sb.append("Full Match");
		}
		if(profileInfo.get("playHittingAround").equals(Boolean.TRUE)){
			if(sb.length() > 0) sb.append(", ");
			sb.append("Hitting Ground");
		}
		if(profileInfo.get("playPoints").equals(Boolean.TRUE)){
			if(sb.length() > 0) sb.append(", ");
			sb.append("Play Points");
		}
		typeOfPlayTextView.setText(Html.fromHtml("<b>Singles, Doubles</b><br/><font color='#999999'>" + sb +"<font>"));
	}
	
	private void populateAvailability(){
		StringBuilder sb = new StringBuilder("<b>Week-days</b><br/><font color='#999999'>");
		boolean nextAddComma = false;
		if(profileInfo.get("availableWeekdayMorning").equals(Boolean.TRUE)){
			sb.append("Morning");
			nextAddComma = true;
		}
		if(profileInfo.get("availableWeekdayAfternoon").equals(Boolean.TRUE)){
			if(nextAddComma) sb.append(", ");
			sb.append("Afternoon");
			nextAddComma = true;
		}
		if(profileInfo.get("availableWeekdayEvening").equals(Boolean.TRUE)){
			if(nextAddComma) sb.append(", ");
			sb.append("Evening");
			nextAddComma = true;
		}
		sb.append("</font><br />");
		nextAddComma = false;
		sb.append("<b>Week-ends</b><br/><font color='#999999'>");
		if(profileInfo.get("availableWeekendMorning").equals(Boolean.TRUE)){
			sb.append("Morning");
			nextAddComma = true;
		}
		if(profileInfo.get("availableWeekendAfternoon").equals(Boolean.TRUE)){
			if(nextAddComma) sb.append(", ");
			sb.append("Afternoon");
			nextAddComma = true;
		}
		if(profileInfo.get("availableWeekendEvening").equals(Boolean.TRUE)){
			if(nextAddComma) sb.append(", ");
			sb.append("Evening");
			nextAddComma = true;
		}
		
		availabilityTextView.setText(Html.fromHtml(sb.toString()));
	}
	
	private void refresh(){
//        Log.d(TAG, "PLAYER PRIFILE REFRESH " + profileInfo);
        if(profileInfo != null){
            getActivityUtils().setActionBarTitle(StringUtils.upperCase((String)profileInfo.get("firstName")) + " " + StringUtils.upperCase((String)profileInfo.get("lastName")));
            levelOfPlayTextView.setText(Html.fromHtml("<b>Level </b><font color='#999999'>" + profileInfo.get("levelOfPlay") +"<font>"));
            locationTextView.setText(profileInfo.get("administrativeAreaLevel1") + ", " + profileInfo.get("country"));
            populateTypeOfPlay();
            populateAvailability();

            refreshImage();
        }
        else{
            Log.e(TAG,"ProfileInfo is null, check why");
        }

	}
	
	private void refreshImage(){
//		Log.d(getClass().getSimpleName(), "PlayerProfileActivity.refreshImage " + profileInfo.get("profileImageUrl") + ", " + profileInfo);

        if(Utils.getUserAccountId(this) != 0 && getIntent().getLongExtra("userAccountId",0) == Utils.getUserAccountId(this)){
        	profileImageView.setOnClickListener(
    			new View.OnClickListener() {
    				@Override
    				public void onClick(View view) {
    					Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    					startActivityForResult(i, REQUEST_CODE_RESULT_LOAD_IMAGE);
    				}
    			}
    		);
        }
        
        Client.getInstance().downloadImageSource((String)profileInfo.get("profileImageUrl"), new Client.OnDownloadImageComplete() {
			@Override
			public void onComplete(Bitmap bitmap) {
//				C.logd("THE DOWNLOADED IMAGE IS: " + bitmap);
				//these to avoid out of memory error after loading image twice or more
		        profileImageView.setImageBitmap(null);
		        profileImageView.destroyDrawingCache();
		        
				if(bitmap != null){
					Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 150);
					profileImageView.setImageBitmap(roundBitmap);
				}
				else{
					profileImageView.setImageResource(R.drawable.camera);
				}			
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_RESULT_LOAD_IMAGE && resultCode == FragmentActivity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = this.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			////response: {imageUrl=http://localhost:8080/tennissetapp/imageservice?id=637607a7-3b4b-4de3-9189-a73b27fb1dba, imageId=637607a7-3b4b-4de3-9189-a73b27fb1dba}
			ServiceResponse response = Client.getInstance().uploadImage(new File(picturePath));
			if(response != null){
				if(response.containsKey("exception")){
					if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
						Intent intent = new Intent(this, LoginActivity.class);
						intent.putExtra("class", this.getClass().getName());
						this.startActivity(intent);
						this.finish();
					}
				}
				else{
					this.uploadedImageFileId = (String)response.get("imageId");
				}
			}
			profileImageView.setImageBitmap(null);
			profileImageView.destroyDrawingCache();

			//compress the image
			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inSampleSize = 5;
			Bitmap bitmap = BitmapFactory.decodeFile(picturePath,options);

			try {
				Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 150);
				this.profileImageView.setImageBitmap(roundBitmap);
			} 
			catch (Throwable exp) { //OutOfMemoryError
				Toast.makeText(this, "There was a problem loading your image", Toast.LENGTH_LONG).show();
			}
		}
	}
}
