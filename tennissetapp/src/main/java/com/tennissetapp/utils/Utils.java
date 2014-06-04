package com.tennissetapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.tennissetapp.R;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Utils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MMM dd",Locale.getDefault());
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("K:mm a",Locale.getDefault());
    private static final String TAG = Utils.class.getSimpleName();
//	private static SimpleDateFormat dateFormatFull = new SimpleDateFormat("E, MMM dd yyyy",Locale.getDefault());
	
	public static void setBackIconVisible(Activity activity, boolean visible){
		if(visible){
			((ImageView)activity.findViewById(R.id.action_bar_back_icon)).setVisibility(ImageView.VISIBLE);
			((View)activity.findViewById(R.id.seperator)).setVisibility(ImageView.VISIBLE);
		}
		else{
			((ImageView)activity.findViewById(R.id.action_bar_back_icon)).setVisibility(ImageView.INVISIBLE);
			((View)activity.findViewById(R.id.seperator)).setVisibility(ImageView.INVISIBLE);
		}
	}
	
	public static void hideKeyboard(TextView textView){
		InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
	}
	
	public static void setLogoVisible(Activity activity, boolean visible){
		if(visible){
			((ImageView)activity.findViewById(R.id.tennissetapp_logo_imageview)).setVisibility(ImageView.VISIBLE);
		}
		else{
			((ImageView)activity.findViewById(R.id.tennissetapp_logo_imageview)).setVisibility(ImageView.GONE);
		}
	}

    public static void cleanPersistentCredentials(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constants.AttributeKeys.CREDENTIALS_SHARE_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
	public static long getUserAccountId(Context context){
//		SharedPreferences preferences = context.getSharedPreferences(Constants.AttributeKeys.CREDENTIALS_SHARE_KEY,Context.MODE_PRIVATE);
//		long userAccountId =  preferences.getLong("userAccountId",0);
//        Log.d(TAG,"getUserAccountId1 " + userAccountId);
//        if(userAccountId == 0){
//            ServiceResponse response = Client.getInstance().getUserAccountId();
//            userAccountId = (Long)response.get("userAccountId");
//            Log.d(TAG,"getUserAccountId2 " + userAccountId);
//        }
//        Log.d(TAG,"getUserAccountId3 " + userAccountId);
//        return userAccountId;

        //--
        ServiceResponse response = Client.getInstance().getUserAccountId();
        long userAccountId = ((Number)response.get("userAccountId")).longValue();
        Log.d(TAG,"getUserAccountId1 " + userAccountId);
        return userAccountId;
	}

    public static void persist(Context ctx, String username, String password,Long userAccountId){
        SharedPreferences preferences = ctx.getSharedPreferences(Constants.AttributeKeys.CREDENTIALS_SHARE_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.AttributeKeys.USERNAME, username);
        editor.putString(Constants.AttributeKeys.PASSWORD, password);
        editor.putLong("userAccountId", userAccountId);
        editor.putLong(Constants.AttributeKeys.DATETIME, System.currentTimeMillis());
        editor.commit();

        //get the vlaue
        //String saved_value=sp.getString("share_key",null);
    }

    public static String getUserGcmRegistrationId(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constants.AttributeKeys.GCM_REGISTRATION,Context.MODE_PRIVATE);
        return preferences.getString(Constants.GCM.REGISTRATION_ID, null);
    }
	
	public static String formatDate(int year,int month,int dayOfMonth){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		
		return dateFormat.format(calendar.getTime());
	}
	
	public static String formatDateFull(Long millis){
		return formatDateFull(new Date(millis));
	}
	
	public static String formatDateFull(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String dayNumberSuffix = getDayNumberSuffix(cal.get(Calendar.DAY_OF_MONTH));
		SimpleDateFormat dateFormatFull = new SimpleDateFormat("E, MMM dd'" + dayNumberSuffix + "' yyyy",Locale.getDefault());
		return dateFormatFull.format(date);
	}
	public static String formatTime(int hour, int minute){
		return hour + ":" + String.format("%02d", minute);
	}
	
	public static String formatTime(long millis){
		Date date = new Date(millis);
		
		Calendar c1 = Calendar.getInstance(); // today
		c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date); // your date

		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
			return "Yesterday";
		}
		else if (c1.get(Calendar.YEAR) >= c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) > c2.get(Calendar.DAY_OF_YEAR)) {
			return "Old";
		}
		else{
			return formatTime(date);
		}
		
	}
	
	public static String formatTime(Date date){
		return timeFormat.format(date);
	}
	
	private static String getDayNumberSuffix(int day) {
	    if (day >= 11 && day <= 13) {
	        return "th";
	    }
	    switch (day % 10) {
	    case 1:
	        return "st";
	    case 2:
	        return "nd";
	    case 3:
	        return "rd";
	    default:
	        return "th";
	    }
	}
	
	public static void checkUnauthorized(ServiceResponse response, Activity activity){
		if(response.containsKey("exception")){
			if("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))){
//				Intent intent = new Intent(activity, LoginActivity.class);
//				activity.startActivity(intent);
			}
			
		}
		else if(response.containsKey("errors")){
			Utils.popupErrors(activity, response);
		}
	}
	public static Bitmap decodeImageResource(Resources resources, int id){
		//Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources, id,o);

		//The new size we want to scale to
		final int REQUIRED_SIZE=70;

		//Find the correct scale value. It should be the power of 2.
		int scale=1;
		while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
			scale*=2;

		//Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize=scale;
		return BitmapFactory.decodeResource(resources, id,o2);
	}

	public static String getMimeType(String url) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		if (extension != null) {
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(extension);
		}
		return type;
	}

	// not in use
	static int IMAGE_MAX_SIZE = 200;

	public static Bitmap decodeImageFile(File f) throws IOException {
		Bitmap b = null;

		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;

		FileInputStream fis = new FileInputStream(f);
		BitmapFactory.decodeStream(fis, null, o);
		fis.close();

		int scale = 1;
		if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
			scale = (int) Math.pow(
					2,
					(int) Math.round(Math.log(IMAGE_MAX_SIZE
							/ (double) Math.max(o.outHeight, o.outWidth))
							/ Math.log(0.5)));
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		fis = new FileInputStream(f);
		b = BitmapFactory.decodeStream(fis, null, o2);
		fis.close();

		return b;
	}

	public static Bitmap decodeImageSteam(InputStream in) throws IOException {
		Bitmap b = null;

		// Decode image size
//		BitmapFactory.Options o = new BitmapFactory.Options();
//		o.inSampleSize = 4; //power of 2 numbers
//		b = BitmapFactory.decodeStream(in, null, o);
		b = BitmapFactory.decodeStream(in);
		in.close();

		return b;
	}

	
	public static Bitmap getCroppedBitmap(Bitmap bitmap, int diameter) {
		Bitmap scaledBitmap;
		if(bitmap == null) return null;
		int size = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
		
		//this may throw OutOfMemoryError
		int x = (bitmap.getWidth()/2)-size;
		int y = (bitmap.getHeight()/2)-size;
		x = (x >= 0 ? x : 0);
		y = (y >= 0 ? y : 0);
		bitmap = Bitmap.createBitmap(bitmap, x, y, size, size);
		
//		if (bitmap.getWidth() != diameter || bitmap.getHeight() != diameter){
//			scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, false);
//		}
//		else{
//			scaledBitmap = bitmap;
//		}
		scaledBitmap = bitmap;
		//+1 to avoid clipping the right border
		Bitmap output = Bitmap.createBitmap(scaledBitmap.getWidth()+1, scaledBitmap.getHeight(),Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

//		final int color = 0xffa19774;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
//		paint.setStrokeWidth(0);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.WHITE);
		
		//draws the image 
		canvas.drawCircle(scaledBitmap.getWidth() / 2 + 0.7f,
				scaledBitmap.getHeight() / 2 + 0.7f, scaledBitmap.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledBitmap, rect, rect, paint);
		
		//border
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3f);
		canvas.drawCircle(scaledBitmap.getWidth() / 2 + 0.7f,
				scaledBitmap.getHeight() / 2 + 0.7f, scaledBitmap.getWidth() / 2 + 0.1f, paint);
		
		return output;
	}

	public static String joinStrings(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                        + separator.length());

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
	
	public static String joinStrings(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return joinStrings(array, separator, 0, array.length);
    }
	
	public static String joinStrings(Iterator<?> iterator, char separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? "" : first.toString();
        }

        // two or more elements
        StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

	public static String joinStrings(Collection<?> collection, char separator) {
		if (collection == null) {
			return null;
		}
		return joinStrings(collection.iterator(), separator);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void popupErrors(Context c, ServiceResponse response){
		Map<String,String> errors = (Map<String,String>)response.get("errors");
		if(errors != null){
			
			StringBuilder sb = new StringBuilder();
			for(String err : errors.values()){
				sb.append("* ");
				sb.append(err);
				sb.append("\n");
			}
			
			if(sb.length() > 0){
				Toast.makeText(c, sb.toString(), Toast.LENGTH_LONG).show();
			}
		}
//		else if("com.tennissetapp.exception.DuplicateRecordException".equals(response.get("code"))){
//			Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
//		}
		else if( response.containsKey("exception")){
			Toast.makeText(c, (String)response.get("exception"), Toast.LENGTH_LONG).show();
		}
	}
	
	public static void toastServerIsDown(Context context){
		Toast.makeText(context, "The server is down, please try again later!",  Toast.LENGTH_LONG).show();
	}
	
	public static void toastNoGpsConnection(Context context){
		Toast.makeText(context, "Could not retreive connection!",  Toast.LENGTH_LONG).show();
	}
	
	public static Location getDeviceLocation(Context context){
		Location location = null;
		GpsTracker gps = new GpsTracker(context);
		if(gps.canGetLocation()){
			location = gps.getLocation();
		}
		else{
			gps.showSettingsAlert();
		}
		if(Constants.env != Constants.Env.PRODUCTION){
			location = new Location(LocationManager.PASSIVE_PROVIDER);
			location.setLatitude(37.4219988);
			location.setLongitude(-122.083954); 
		}
		return location;
	}
	
	public static int dpToPixels(Resources resources, int sizeInDp){
		float scale = resources.getDisplayMetrics().density;
		int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
		return dpAsPixels;
	}
	
	public static Date lastSunday(){
		return lastSunday(new Date());
	}
	
	public static Date lastSunday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add( Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK)-1)); 
		return cal.getTime();
	}
	
	public static Date nextSunday(){
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.DAY_OF_WEEK, (8 - cal.get(Calendar.DAY_OF_WEEK))); 
		return cal.getTime();
	}
	
	public static void removeUser(List<Map<String,Object>> list, long userAccountId){
		Iterator<Map<String,Object>> itr = list.iterator();
		while(itr.hasNext()){
			Map<String,Object> item = itr.next();
			if(((Number)item.get("userAccountId")).longValue() == userAccountId){
				itr.remove();
				return;
			}
		}
	}
	
//	public static char celciusChar(){
//		return '\u2103';
//	}
	
//	public static void showMessageDialog(Context context, String html){
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setPositiveButton(android.R.string.ok,
//			new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//				}
//			});
//		if(this.dialogTitle != null){
//			builder.setTitle(this.dialogTitle);
//		}
//		
//		builder.setOnCancelListener(this);
//		builder.show();
//		return true;
//	}
}
