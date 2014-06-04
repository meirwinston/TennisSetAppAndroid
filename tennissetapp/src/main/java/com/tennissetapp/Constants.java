package com.tennissetapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mwinston on 5/17/2014.
 */
public class Constants {
    public interface Env {
        int PRODUCTION = 0;
        int LOCALHOST = 1;
        int PRODUCTION_TEST = 2;
    }

    public static int env = Env.LOCALHOST;
    public static String LogTag = "Tennis SetApp";
    public static final String APP_ENDPOINT;
    public static final double NEARBY_DISTANCE_KM = 200;

    static {
        if (env == Env.LOCALHOST) {
            APP_ENDPOINT = "http://10.0.2.2:8080/tennissetapp";
        } else {
            APP_ENDPOINT = "http://54.201.209.37:80";
        }
    }
    public static final String COURTS_IMAGES_URL = "images/TENNIS_COURTS";
    public static final String PROFILES_IMAGES_URL = "images/PROFILE_PHOTOS";
    public static final String REFRESH_DATA_INTENT = "REFRESH_DATA_INTENT";

    public interface GCM {
        String REGISTRATION_ID = "registration_id"; //determined by endroid dont change
        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        String PROJECT_NUMBER = "637067041525"; //SENDER_ID
    }

    public interface AttributeKeys {
        String CREDENTIALS_SHARE_KEY = "CREDENTIALS_SHARE_KEY";
        String GCM_REGISTRATION = "GCM_REGISTRATION";
        String USERNAME = "USERNAME";
        String PASSWORD = "PASSWORD";
        String DATETIME = "DATETIME";
        String APP_VERSION = "APP_VERSION";
    }

    public enum ArgumentKey {
        LAST_FRAGMENT_TAG,
        PLAYER_LEVEL
    }


    public static void applyCustomFont(ViewGroup list, Typeface customTypeface) {
        for (int i = 0; i < list.getChildCount(); i++) {
            View view = list.getChildAt(i);
            if (view instanceof ViewGroup) {
                applyCustomFont((ViewGroup) view, customTypeface);
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(customTypeface);
            }
        }
    }

    public static void logi(String info) {
        Log.i(Constants.LogTag, info);
    }

    public static void logd(String debug) {
        Log.d(Constants.LogTag, debug);
    }

    public static void loge(String error) {
        Log.e(Constants.LogTag, error);
    }

    public static void loge(String error, Throwable exp) {
        Log.e(Constants.LogTag, error, exp);
    }

    public static final class TYPEFACE {
        public static final Typeface LatoLight(Context ctx) {
            Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/Lato-Lig.ttf");
            return typeface;
        }

        public static final Typeface Lato(Context ctx) {
            Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/Lato-Reg.ttf");
            return typeface;
        }

        public static final Typeface LatoBold(Context ctx) {
            Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/Lato-Bol.ttf");
            return typeface;
        }
    }
}

