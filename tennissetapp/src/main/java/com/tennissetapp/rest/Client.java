package com.tennissetapp.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.tennissetapp.Constants;
import com.tennissetapp.form.UpdateTokenForm;
import com.tennissetapp.utils.Utils;
import com.tennissetapp.form.CreatePlayerProfileForm;
import com.tennissetapp.form.CreateTennisCourtForm;
import com.tennissetapp.form.CreateTennisMatchForm;
import com.tennissetapp.form.FindByLocationForm;
import com.tennissetapp.form.PlayerCalendarForm;
import com.tennissetapp.form.PostMessageForm;
import com.tennissetapp.form.ScrollForm;
import com.tennissetapp.form.ScrollMateConversationForm;
import com.tennissetapp.form.SearchTennisCourtsForm;
import com.tennissetapp.form.SearchTennisMatchesForm;
import com.tennissetapp.form.SearchByNameOrEmailForm;
import com.tennissetapp.form.SearchTennisMatesForm;
import com.tennissetapp.form.SearchTennisTeachersForm;
import com.tennissetapp.form.SignupForm;
import com.tennissetapp.form.UpdateAccountPrimaryForm;
import com.tennissetapp.form.UpdateTennisDetailsForm;
import com.tennissetapp.json.GeoCodeResult;
import com.tennissetapp.json.JacksonUtils;
import com.tennissetapp.json.WorldWeatherOnlineResponse;

public class Client {
	private static Client client = new Client();
	private final HttpContext localContext = new BasicHttpContext();
	private final HttpClient httpClient = new DefaultHttpClient();
    private static final String TAG = Client.class.getSimpleName();

	private Client(){
	}
	
	public static Client getInstance(){
		return client;
	}
	
	public ServiceResponse updateAccountPrimaryFields(UpdateAccountPrimaryForm form){
		RestTaskPost task = new RestTaskPost();
		ServiceResponse response = null;
		try {
			Log.i(TAG, "updateAccountPrimaryFields: " + "/service/account/updatePrimary?" + URLEncodedUtils.format(form.nameValueList(), "UTF-8"));
			task = (RestTaskPost)task.execute(new String[]{"/service/account/updatePrimary?" + URLEncodedUtils.format(form.nameValueList(), "UTF-8")});
			response = task.get();
			
			Log.i(this.getClass().getSimpleName(),"updateAccountPrimaryFields response: " + response);
		} 
		catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return response;
	}
	
	public ServiceResponse updatePlayerTennisDetails(UpdateTennisDetailsForm form){
		RestTaskPost task = new RestTaskPost();
		ServiceResponse response = null;
		try {
			Log.i(Constants.LogTag, "createPlayerProfile: " + "/service/profile/player/updateTennisDetails?" + URLEncodedUtils.format(form.nameValueList(), "UTF-8"));
			task = (RestTaskPost)task.execute(new String[]{"/service/profile/player/updateTennisDetails?" + URLEncodedUtils.format(form.nameValueList(), "UTF-8")});
			response = task.get();
			
//			Log.i(Constants.LogTag,"updatePlayerTennisDetails response: " + response);
		} 
		catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return response;
	}
	
	public ServiceResponse createPlayerProfile(CreatePlayerProfileForm form){
		RestTaskPost task = new RestTaskPost();
		ServiceResponse response = null;
		try {
			Log.i(Constants.LogTag, "createPlayerProfile: " + URLEncodedUtils.format(form.nameValueList(), "UTF-8"));
			task = (RestTaskPost)task.execute(new String[]{"/service/profile/player/create?" + URLEncodedUtils.format(form.nameValueList(), "UTF-8")});
			response = task.get();
			
//			Log.i(Constants.LogTag,"createPlayerProfile response: " + response);
		} 
		catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return response;
	}
	
	
	public ServiceResponse getPlayerProfileId(){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/profile/player/id"});
			return task.get();
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse getUserAccountId(){ //the same as playerProfileId
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/accountId"});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse getPlayerProfile(long userAccountId){
		RestTaskGet task = new RestTaskGet();
		try {
			//TennisCourtsService - /service/courts/findnearby
			if(userAccountId == 0){
				task = (RestTaskGet)task.execute(new String[]{"/service/profile/player"});
			}
			else{
				task = (RestTaskGet)task.execute(new String[]{"/service/profile/player?userAccountId="+userAccountId});
			}
//			Constants.logd("getPlayerProfile " + userAccountId);
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	///
	
	public ServiceResponse scrollFavoriteTennisTeachers(ScrollForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/teachers/favorites?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse searchTennisTeachers(SearchTennisTeachersForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/teachers/search?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse searchTennisTeachers(SearchByNameOrEmailForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/teachers/searchByNameOrEmail?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse findNearbyTennisTeachers(FindByLocationForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/teachers/nearby?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse findNearbyTennisCenters(FindByLocationForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/courts/nearby?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse findNearbyTennisMates(FindByLocationForm form){
		RestTaskGet task = new RestTaskGet();
		try {
            Log.d(TAG,"findNearbyTennisMates:  " + "/service/mates/nearby?" + form.toRest());
			task = (RestTaskGet)task.execute(new String[]{"/service/mates/nearby?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}

    public interface TaskProgress {
        void onPostExecute(ServiceResponse response);
    }

    public void findNearbyTennisMates(FindByLocationForm form, TaskProgress p){
        RestTaskGet task = new RestTaskGet(p);
        try {
            Log.d(TAG,"findNearbyTennisMates:  " + "/service/mates/nearby?" + form.toRest());
            task = (RestTaskGet)task.execute(new String[]{"/service/mates/nearby?" + form.toRest()});

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
        }
    }
	
	public ServiceResponse findAllMatches(ScrollForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/matches/all?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return null;
	}
	
	public ServiceResponse findNearbyTennisMatches(FindByLocationForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/matches/nearby?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse findTennisMatch(String matchId){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/matches/find?matchId="+matchId});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse findTennisMatchMembers(String matchId){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/matches/findMembers?matchId="+matchId});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse searchTennisMatches(SearchTennisMatchesForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/matches/search?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse joinTennisMatch(String matchId){
		RestTaskPost task = new RestTaskPost();
		try {
			task = (RestTaskPost)task.execute(new String[]{"/service/matches/join?matchId=" + matchId});
			return task.get();
			
		} catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse postMessageToUser(PostMessageForm form){
		Constants.logd("postMessageToUser " + form);
		RestTaskPost task = new RestTaskPost();
		try {
			task = (RestTaskPost)task.execute(new String[]{"/service/messages/post?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		return null;
	}

    public ServiceResponse updateToken(UpdateTokenForm form){
        Constants.logd("updateToken " + form + ", " + "/service/updateToken?" + form.toRest());
        RestTaskPost task = new RestTaskPost();
        try {
            task = (RestTaskPost)task.execute(new String[]{"/service/updateToken?" + form.toRest()});
            return task.get();

        } catch (Exception e) {
            Constants.loge(e.getMessage(),e);
        }
        return null;
    }
	
	
	public ServiceResponse scrollMateConversation(ScrollMateConversationForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/messages/scrollByMate?" + form.toRest()});
			return task.get();
		} 
		catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	public ServiceResponse scrollMessages(ScrollForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/messages/scroll?" + form.toRest()});
			return task.get();
		} 
		catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse scrollNotifications(ScrollForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/notifications/scroll?" + form.toRest()});
			return task.get();
		} 
		catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse addTennisMate(String mateAccountId){
		RestTaskPost task = new RestTaskPost();
		try {
            Log.d(TAG,"addTennisMate:  " + "/service/mates/add?mateAccountId=" + mateAccountId);
			task = (RestTaskPost)task.execute(new String[]{"/service/mates/add?mateAccountId=" + mateAccountId});
			return task.get();
			
		} catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse getTennisMate(long mateAccountId){
		RestTaskPost task = new RestTaskPost();
		try {
            Log.d(TAG,"getTennisMate:  " + "/service/mates/get?mateAccountId=" + mateAccountId);
			task = (RestTaskPost)task.execute(new String[]{"/service/mates/get?mateAccountId=" + mateAccountId});
			return task.get();
			
		} catch (Exception e) {
			Constants.loge(e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse createTennisMatch(CreateTennisMatchForm form){
		RestTaskPost task = new RestTaskPost();
		try {
			task = (RestTaskPost)task.execute(new String[]{"/service/matches/create?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Constants.loge(e.getMessage(), e);
		} 
		
		return null;
	}
	
	public ServiceResponse searchTennisMates(SearchTennisMatesForm form){
		RestTaskGet task = new RestTaskGet();
		try {
            Log.d(TAG,"searchTennisMates: " + "/service/mates/search?" + form.toRest());
			task = (RestTaskGet)task.execute(new String[]{"/service/mates/search?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}

    public void searchTennisMates(SearchTennisMatesForm form, TaskProgress progress){
        RestTaskGet task = new RestTaskGet(progress);
        try {
            Log.d(TAG,"searchTennisMates: " + "/service/mates/search?" + form.toRest());
            task = (RestTaskGet)task.execute(new String[]{"/service/mates/search?" + form.toRest()});
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
        }
    }
	
	public ServiceResponse searchTennisMates(SearchByNameOrEmailForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/mates/searchByNameOrEmail?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse getCourts(String tennisCenterId){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/courts/getCourts?tennisCenterId=" + tennisCenterId});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse searchTennisCenters(SearchTennisCourtsForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/courts/search?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse createTennisCenter(CreateTennisCourtForm form){
		RestTaskPost task = new RestTaskPost();
		try {
			task = (RestTaskPost)task.execute(new String[]{"/service/courts/create?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
	public ServiceResponse scrollUserTennisMates(ScrollForm form){
		RestTaskGet task = new RestTaskGet();
		try {
            Log.d(TAG,"scrollUserTennisMates: " + "/service/mates/findByUser?" + form.toRest());
			task = (RestTaskGet)task.execute(new String[]{"/service/mates/findByUser?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}

    public void scrollUserTennisMates(ScrollForm form,TaskProgress progress){
        RestTaskGet task = new RestTaskGet(progress);
        try {
            Log.d(TAG,"scrollUserTennisMates: " + "/service/mates/findByUser?" + form.toRest());
            task = (RestTaskGet)task.execute(new String[]{"/service/mates/findByUser?" + form.toRest()});

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
        }
    }
	
	public ServiceResponse login(String email,String password){
		RestTaskPost task = new RestTaskPost();
		ServiceResponse response = null;
		try {
            Log.d(TAG,"login " + "/service/login?j_username=" + email + "&j_password=" + password);
			task = (RestTaskPost)task.execute(new String[]{"/service/login?j_username=" + email + "&j_password=" + password});
			response = task.get();
		} catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return response;
	}

    public ServiceResponse logout(){
        RestTaskPost task = new RestTaskPost();
        ServiceResponse response = null;
        try {
            task = (RestTaskPost)task.execute(new String[]{"/service/logout"});
            response = task.get();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
        }
        return response;
    }


	public ServiceResponse signup(SignupForm form){
		RestTaskPost task = new RestTaskPost();
		ServiceResponse response = null;
		try {
			Log.d(TAG,"signup " + "/service/signup?" + form.toRest());
			task = (RestTaskPost)task.execute(new String[]{"/service/signup?" + form.toRest()});
			response = task.get();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return response;
	}

	//params,progress,result
	private class RestTaskGet extends AsyncTask<String, Void, ServiceResponse> {
        private TaskProgress progress;
        public RestTaskGet(){}
        public RestTaskGet(TaskProgress p){
            progress = p;
        }
		@Override
		protected ServiceResponse doInBackground(String... params) {
			ServiceResponse result = null;
			try {
				HttpGet get = new HttpGet(Constants.APP_ENDPOINT + params[0]);
				get.setHeader("Content-surface", "application/json");
				HttpResponse response = httpClient.execute(get,localContext);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String s = reader.readLine();
				result = JacksonUtils.deserializeAs(s, ServiceResponse.class);

			} catch (Exception e) {
                Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
			return result;
		}

        @Override
        protected void onPostExecute(ServiceResponse serviceResponse) {
            if(progress != null){
                progress.onPostExecute(serviceResponse);
            }
        }
    }


	//params,progress,result
	private class RestTaskPost extends AsyncTask<String, Void, ServiceResponse> {
		@Override
		protected ServiceResponse doInBackground(String... params) {
			ServiceResponse result = null;
			try {
				;
				HttpPost post = new HttpPost(Constants.APP_ENDPOINT + params[0]);
//				URLEncodedUtils..format(parameters, encoding)(parameters, encoding)
				post.setHeader("Content-surface", "application/json");
				HttpResponse response = httpClient.execute(post,localContext);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String s = reader.readLine();
				result = JacksonUtils.deserializeAs(s, ServiceResponse.class);

			} catch (Exception e) {
                Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
			return result;
		}
	}
	///tennissetapp/service/upload-profile-photo/

	private class RestTaskUploadImage extends AsyncTask<File, Void, ServiceResponse> {
		@Override
		protected ServiceResponse doInBackground(File... files) {
			ServiceResponse result = null;
			try {
				HttpPost post = new HttpPost(Constants.APP_ENDPOINT + "/service/upload-profile-photo/");
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		    	builder.addBinaryBody("image", files[0]);
		        post.setEntity(builder.build());
				
				HttpResponse response = httpClient.execute(post,localContext);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String s = reader.readLine();
				result = JacksonUtils.deserializeAs(s, ServiceResponse.class);
			} catch (Exception e) {
                Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
			return result;
		}
	}
	
	public ServiceResponse uploadImage(File file){
		RestTaskUploadImage task = new RestTaskUploadImage();
		try {
			task = (RestTaskUploadImage)task.execute(new File[]{file});
			return task.get();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return null;
	}
	
	
	public void post(String url, List<NameValuePair> nameValuePairs) {
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(url);

	    try {
	    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        for(int index=0; index < nameValuePairs.size(); index++) {
	            if(nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
	                // If the key equals to "image", we use FileBody to transfer the data
	            	builder.addPart(nameValuePairs.get(index).getName(), new FileBody(new File (nameValuePairs.get(index).getValue())));
	            } else {
	                // Normal string data
	            	builder.addTextBody(nameValuePairs.get(index).getName(), nameValuePairs.get(index).getValue());
	            }
	        }
	        httpPost.setEntity(builder.build());
	        HttpResponse response = httpClient.execute(httpPost, localContext);
            Log.d(this.getClass().getSimpleName(),"THE IMAGE RESPONSE " + response.getEntity().toString());
	    } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
	    }
	}
	
	
	private class RestTaskGoogleApiMap extends AsyncTask<String, Void, GeoCodeResult> {
		@Override
		protected GeoCodeResult doInBackground(String... addressInput) {
			GeoCodeResult result = null;
			try {
				result = googleGeocode(addressInput[0]);
			} catch (Exception e) {
                Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
			return result;
		}
	}
	
	public GeoCodeResult getGoogleLocationObject(String addressInput){
		RestTaskGoogleApiMap task = new RestTaskGoogleApiMap();
		try {
			task = (RestTaskGoogleApiMap)task.execute(new String[]{addressInput});
			return task.get();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return null;
	}
	
//http://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false
//	http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=40.7215618,-73.95387
	public GeoCodeResult googleGeocode(String input) {
		GeoCodeResult geoCode = null;
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder("http://maps.googleapis.com/maps/api/geocode/json?sensor=false");
			sb.append("&address=" + URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(getClass().getSimpleName(), "Error processing Places API URL", e);
			return geoCode;
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "Error connecting to Places API", e);
			return geoCode;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			geoCode = JacksonUtils.deserializeAs(jsonResults.toString(), GeoCodeResult.class);
		} catch (Exception e) {
			Log.e(Constants.LogTag, "Cannot process JSON results", e);
		}
		return geoCode;
	}

	private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    protected Bitmap doInBackground(String... urls) {
	        String imageUrl = urls[0];
	        Bitmap bitmap = null;
	        try {
	            InputStream in = new URL(imageUrl).openStream();
//	            bitmap = BitmapFactory.decodeStream(in);
	            bitmap = Utils.decodeImageSteam(in);
	        } catch (Exception exp) {
//	        	throw new RuntimeException(exp.getMessage(),exp);
	            Log.e(Constants.LogTag, "HERE I SHOULD STREAM THE DEFAULT IMAGE " + exp.getMessage());
	        }
	        return bitmap;
	    }
	}
	
	public Bitmap downloadImageSource(String url){
		DownloadImageTask task = new DownloadImageTask();
		Bitmap bitmap = null;
		try {
			task = (DownloadImageTask)task.execute(new String[]{Constants.APP_ENDPOINT + "/" + url});
			bitmap = task.get();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return bitmap;
	}
	
	public static interface OnDownloadImageComplete{
		void onComplete(Bitmap bitmap);
	}
	
	public void downloadImageSource(String url,final OnDownloadImageComplete o){
		if(url == null) {
			o.onComplete(null);
		}
		else{
			DownloadImageTask task = new DownloadImageTask(){
				protected void onPostExecute(Bitmap result) {
					super.onPostExecute(result);
					o.onComplete(result);
				}
			};
			try {
				task = (DownloadImageTask)task.execute(new String[]{Constants.APP_ENDPOINT + "/" + url});
				
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
		}
	}
	
	private class GetWorldWeatherOnline extends AsyncTask<String, Void, WorldWeatherOnlineResponse> {
		@Override
		protected WorldWeatherOnlineResponse doInBackground(String... params) {
			WorldWeatherOnlineResponse result = null;
			try {
				HttpGet get = new HttpGet("http://api.worldweatheronline.com/free/v1/weather.ashx?" + params[0]);
				get.setHeader("Content-surface", "application/json");
				HttpResponse response = httpClient.execute(get,localContext);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String s = reader.readLine();
//				Constants.logd("the weather response " + s);
				result = JacksonUtils.deserializeAs(s, WorldWeatherOnlineResponse.class);

			} catch (Exception e) {
                Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
			}
			return result;
		}
	}
	
//	http://api.worldweatheronline.com/free/v1/weather.ashx?q=40.424931%26-74.422184&format=json&num_of_days=1&date=2014-01-20&key=9kq4uwk5x77jabr8en762y9m
	//env http://developer.worldweatheronline.com/io-docs
	public WorldWeatherOnlineResponse worldWeatherOnline(double lat,double lng, Calendar cal) {
		WorldWeatherOnlineResponse response = null;
		GetWorldWeatherOnline task = new GetWorldWeatherOnline();
		try {
			StringBuilder sb = new StringBuilder("q=");
			sb.append(URLEncoder.encode(lat + ", " + lng, "utf8"));
			sb.append("&format=json&num_of_days=1");
			if(cal != null){
				sb.append("&date=");
				sb.append(cal.get(Calendar.YEAR));
				sb.append("-");
				sb.append(cal.get(Calendar.DATE));
				sb.append("-");
				sb.append(cal.get(Calendar.MONTH));
			}
			sb.append("&key=9kq4uwk5x77jabr8en762y9m");
			task = (GetWorldWeatherOnline)task.execute(new String[]{sb.toString()});
			response = task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return response;
	}
	
	public ServiceResponse findCalendarEvents(PlayerCalendarForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/calendar/events?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		return null;
	}
	
	public ServiceResponse findNextCalendarEvents(PlayerCalendarForm form){
		RestTaskGet task = new RestTaskGet();
		try {
			task = (RestTaskGet)task.execute(new String[]{"/service/calendar/nextEvent?" + form.toRest()});
			return task.get();
			
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
		} 
		
		return null;
	}
	
}


//public ArrayList<String> autocomplete(String input) {
//	ArrayList<String> resultList = null;
//	HttpURLConnection conn = null;
//	StringBuilder jsonResults = new StringBuilder();
//	try {
//		StringBuilder sb = new StringBuilder("http://maps.googleapis.com/maps/api/geocode/json?sensor=false");
//		sb.append("&address=" + URLEncoder.encode(input, "utf8"));
//
//		URL url = new URL(sb.toString());
//		conn = (HttpURLConnection) url.openConnection();
//		InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//		// Load the results into a StringBuilder
//		int read;
//		char[] buff = new char[1024];
//		while ((read = in.read(buff)) != -1) {
//			jsonResults.append(buff, 0, read);
//		}
//		System.out.println("autocomplete RESPONSE HERE " + jsonResults);
//	} catch (MalformedURLException e) {
//		Log.e(Constants.LogTag, "Error processing Places API URL", e);
//		return resultList;
//	} catch (IOException e) {
//		Log.e(Constants.LogTag, "Error connecting to Places API", e);
//		return resultList;
//	} finally {
//		if (conn != null) {
//			conn.disconnect();
//		}
//	}
//
//	try {
//		// Create a JSON object hierarchy from the results
//		JSONObject jsonObj = new JSONObject(jsonResults.toString());
//		JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
//
//		// Extract the Place descriptions from the results
//		resultList = new ArrayList<String>(predsJsonArray.length());
//		for (int i = 0; i < predsJsonArray.length(); i++) {
//			resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//		}
//	} catch (JSONException e) {
//		Log.e(Constants.LogTag, "Cannot process JSON results", e);
//	}
//	resultList.add("548 Ryders Lene");
//	resultList.add("548 homeland");
//	return resultList;
//}




//public void postImage(File file) {
//HttpClient httpClient = new DefaultHttpClient();
//HttpContext localContext = new BasicHttpContext();
//HttpPost httpPost = new HttpPost(Constants.APP_ENDPOINT +  "/service/upload-profile-photo/");
//
//try {
//	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//	builder.addBinaryBody("image", file);
//	
//    httpPost.setEntity(builder.build());
//    HttpResponse response = httpClient.execute(httpPost, localContext);
//    Constants.logger.info("THE IMAGE RESPONSE " + response.getEntity().toString());
//} catch (IOException e) {
//    e.printStackTrace();
//}
//}
