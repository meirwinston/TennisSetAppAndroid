package com.tennissetapp.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tennissetapp.Constants;
import com.tennissetapp.EndlessScrollListener;
import com.tennissetapp.R;
import com.tennissetapp.form.PostMessageForm;
import com.tennissetapp.form.ScrollMateConversationForm;
import com.tennissetapp.rest.Client;
import com.tennissetapp.rest.ServiceResponse;
import com.tennissetapp.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MateConversationActivity extends BaseActivity {
    private ListView messagesListView;
    private MessagesListViewAdapter messagesListViewAdapter;
    private ImageView messageImageView;
    private EditText messageEditText;
    private static final String TAG = MateConversationActivity.class.getSimpleName();
    private NotificationsReceiver notificationsReceiver;

    @Override
    public void onResume() {
        super.onResume();
        if (notificationsReceiver == null) {
            notificationsReceiver = new NotificationsReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(Constants.REFRESH_DATA_INTENT);
        registerReceiver(notificationsReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        if (notificationsReceiver != null) {
            unregisterReceiver(notificationsReceiver);
        }
        super.onPause();
    }

    private class NotificationsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.REFRESH_DATA_INTENT)) {
                //Do stuff - maybe update my view based on the changed DB contents
                if (Constants.env != Constants.Env.PRODUCTION) {
                    Log.i(TAG, "-------------onReceive " + intent);
                }
                refresh();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, getIntent().getStringExtra("userFirstName") + " " + getIntent().getStringExtra("userLastName"), false, true);
        setContentView(R.layout.activity_mate_conversation);

        messagesListView = (ListView) findViewById(R.id.messages_listview);
        messagesListView.setOnScrollListener(new ItemScrollListener());
        messagesListViewAdapter = new MessagesListViewAdapter(this, R.layout.listitem_conversation);
        messagesListView.setAdapter(messagesListViewAdapter);

        this.messageImageView = (ImageView) findViewById(R.id.message_imageview);
        this.messageEditText = (EditText) findViewById(R.id.message_edittext);

        this.messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
//				C.logd("messageButton.onClick " + message);
                if (!StringUtils.isEmpty(message)) {
                    postMessage(message);
                    messageEditText.setText(null);
                }
            }
        });

        getActivityUtils().initDrawer();
        refresh();
    }

    private void refresh() {
        loadPage(0, 2);
    }

    //static int ss = 0;
    @SuppressWarnings("unchecked")
    private void loadPage(int firstIndex, int maxResults) {
        ScrollMateConversationForm form = new ScrollMateConversationForm();
        form.maxResults = String.valueOf(maxResults);
        form.firstResult = String.valueOf(firstIndex);
        form.mateAccountId = String.valueOf(getIntent().getLongExtra("mateAccountId", -1));

        int count = messagesListViewAdapter.getCount();
        if (count > 0) {
            Map<String, Object> item = messagesListViewAdapter.getItem(count - 1);
            if (item != null && item.get("postedOn") != null) {
                form.startDate = item.get("postedOn").toString();
            }
        }
        ServiceResponse response = Client.getInstance().scrollMateConversation(form);
		Log.i(TAG, "The response, messages: " + response);
        if (response != null) {
            if (response.containsKey(ServiceResponse.EXCEPTION)) {
                if ("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))) {
//					if(ss == 0){
                    Intent intent = new Intent(this, LoginActivity.class);
                    this.startActivity(intent);
//						ss++;
//					}

                }
            } else {
                final List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("list");
//                Log.i(getClass().getSimpleName(), "Messages response: " + list);
                if (!list.isEmpty()) {
                    MateConversationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messagesListViewAdapter.addAll(list);
                            messagesListView.setSelection(messagesListViewAdapter.getCount() - 1);
                        }
                    });
                }
            }
        } else {
            Utils.toastServerIsDown(this);
        }
    }

    class ItemScrollListener extends EndlessScrollListener {
        public ItemScrollListener() {
            super(5);
        }

        @Override
        public void loadPage(int firstIndex, int maxResults) {
//			NearbyMatesFragment.this.loadPage(firstIndex, maxResults);
        }
    }

    class MessagesListViewAdapter extends ArrayAdapter<Map<String, Object>> {
        Map<Long, Bitmap> imagesMap = new HashMap<Long, Bitmap>();

        public MessagesListViewAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public long getItemId(int position) {
            Map<String, Object> item = getItem(position);
            return Long.valueOf((Integer) item.get("userPostId"));
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        class ViewHolder {
            protected TextView nameTextView, messageTextView, dateTextView;
            protected ImageView playerImageView;
        }

        //Optimized getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            final Map<String, Object> item = getItem(position);

            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                rowView = inflater.inflate(R.layout.listitem_conversation, null);
                rowView.findViewById(R.id.details_imageview).setVisibility(View.INVISIBLE);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.playerImageView = (ImageView) rowView.findViewById(R.id.player_profile_imageview);
                viewHolder.nameTextView = (TextView) rowView.findViewById(R.id.player_name_textview_);
                viewHolder.messageTextView = (TextView) rowView.findViewById(R.id.notification_message_textview);
                viewHolder.dateTextView = (TextView) rowView.findViewById(R.id.notification_time_textview);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            if (item.get("userProfileImage") != null && holder.playerImageView != null) {
                long userAccountId = ((Number)item.get("userAccountId")).longValue();
                uploadImage(Constants.PROFILES_IMAGES_URL + "/" + item.get("userProfileImage"), holder.playerImageView,userAccountId);
            }
            holder.nameTextView.setText(item.get("userFirstName") + " " + item.get("userLastName"));
            if (item.containsKey("content")) {
                holder.messageTextView.setText((String) item.get("content"));
            }
            if (item.containsKey("postedOn") && holder.dateTextView != null) {
                holder.dateTextView.setText(Utils.formatTime((Long) item.get("postedOn")));
            }
            return rowView;
        }

        private void uploadImage(final String url, final ImageView image, final long userAccountId) {
            Bitmap b = imagesMap.get(userAccountId);
            if(b == null){
                Client.getInstance().downloadImageSource(url, new Client.OnDownloadImageComplete() {
                    @Override
                    public void onComplete(Bitmap bitmap) {
                        if (bitmap != null) {
                            Bitmap roundBitmap = Utils.getCroppedBitmap(bitmap, 75);
                            image.setImageBitmap(roundBitmap);

                            if(imagesMap.size() < 20){ //limit the size of the map
                                imagesMap.put(userAccountId,roundBitmap);
                            }

                        } else {
                            image.setImageResource(R.drawable.circle_no_profile_image);
                        }
                    }
                });
            }
            else{
                image.setImageBitmap(b);
            }

        }


//        long userAccountId = ((Number)item.get("userAccountId")).longValue();
//        Bitmap bitmap = imagesMap.get(userAccountId);
//        if(bitmap == null){
//            uploadImage(Constants.PROFILES_IMAGES_URL + "/" + item.get("userProfileImage"), holder.playerImageView,userAccountId);
//        }
//        else{
//            holder.playerImageView.setImageBitmap(bitmap);
//        }
    }

    private void postMessage(String message) {
        PostMessageForm form = new PostMessageForm();
        form.message = message;
        form.toUserAccountId = String.valueOf(getIntent().getLongExtra("mateAccountId", -1));

        form.token = Utils.getUserGcmRegistrationId(this);
//		Log.d(getClass().getSimpleName(),"postMessage: " + form);
        if (form.token != null) {
            ServiceResponse response = Client.getInstance().postMessageToUser(form);

            if (response != null) {
                if (response.containsKey(ServiceResponse.EXCEPTION)) {
                    if ("com.tennissetapp.exception.NotAuthorizedException".equals(response.get("code"))) {

                    }
                } else {
                    refresh();
                }
            }
//        Log.d(getClass().getSimpleName(),"postMessage response: " + response);
        } else {
            Log.e(TAG, "token is null");
        }

    }
}
