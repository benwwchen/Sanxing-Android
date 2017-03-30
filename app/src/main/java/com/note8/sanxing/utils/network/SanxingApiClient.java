package com.note8.sanxing.utils.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.note8.sanxing.models.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by BenWwChen on 2017/1/14.
 */

public class SanxingApiClient {

    private static SanxingApiClient mInstance;
    private static Context mCtx;
    CookieManager cookieManager;

    private String cookie;
    private User user = null;
    private String mMessage;
    private boolean isLogin = false;

    //private static final String BASE_URL = "http://192.168.1.135:3000"; // test
    private static final String BASE_URL = "http://api.sanxing.life"; // production

    // user related path
    private static final String USER_PATH = "user";
    private static final String SESSION_PATH = USER_PATH + "/session";
    private static final String USER_TAG_PATH = USER_PATH + "/tags";
    private static final String FAVORITE_QUESTIONS_PATH = USER_PATH + "/favorite/questions";
    private static final String FAVORITE_ANSWERS_PATH = USER_PATH + "/favorite/answers";

    // other paths
    private static final String QUESTIONS_PATH = "questions";
    private static final String ANSWER_PATH = "answers";
    private static final String TAG_PATH = "tags";
    private static final String ARTICLE_PATH = "articles";
    private static final String WEEKLY_PATH = "weeklies";
    private static final String IMAGE_PATH = "/images/";

    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = -1;

    private SanxingApiClient(Context context) {
        mCtx = context;
        // read cookie from storage if needed
        CookieStore cookieStore = new PersistentCookieStore(context);
        cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        // check if login still valid
        user = getUserInfo();
        if (!isLogin) {
            cookieManager.getCookieStore().removeAll();
        }
    }

    public static synchronized SanxingApiClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SanxingApiClient(context);
        }
        return mInstance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void logout() {
        cookieManager.getCookieStore().removeAll();
        isLogin = false;
    }

    private static void asyncJsonRequest(int method, String url, JSONObject requestBody,
                                         Response.Listener<JSONObject> listener,
                                         Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method, url, requestBody, listener, errorListener);
        VolleyUtil.getInstance(mCtx).addToRequestQueue(jsonObjectRequest);
    }

    private static JSONObject syncJsonRequest(int method, String url, JSONObject requestBody,
                                                 final Map<String, String> params) {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method, url, requestBody, future, future) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params == null) return super.getParams();
                return params;
            }
        };
        VolleyUtil.getInstance(mCtx).addToRequestQueue(jsonObjectRequest);

        JSONObject responseBody = null;
        try {
            responseBody = future.get();
        } catch (Exception e) {
            Log.d("ERROR", "error => " + e.toString());
        }
        return responseBody;
    }

    private static boolean isSuccess(JSONObject response) {
        try {
            if (response.getString("enmsg").equals("ok")) return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerAccount(final String username, final String password) {

        Map<String, String> request = new HashMap<String, String>();
        request.put("username", username);
        request.put("password", password);

        JSONObject requestBody = new JSONObject(request);

        // send request
        JSONObject responseBody = syncJsonRequest(Request.Method.POST, getAbsoluteUrl(USER_PATH), requestBody, null);

        try {
            if (isSuccess(responseBody)) {
                isLogin = true;

                // retrive user info
                this.user = getUserInfo();
                return user != null;
            } else {
                mMessage = responseBody.getString("cnmsg");
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return false;
    }

    public boolean loginAccount(final String username, final String password) {
        Map<String, String> request = new HashMap<String, String>();
        request.put("username", username);
        request.put("password", password);

        JSONObject requestBody = new JSONObject(request);

        // send request
        JSONObject responseBody = syncJsonRequest(Request.Method.POST, getAbsoluteUrl(SESSION_PATH), requestBody, null);

        try {
            if (isSuccess(responseBody)) {
                isLogin = true;

                // retrive user info
                this.user = getUserInfo();
                return user != null;
            } else {
                mMessage = responseBody.getString("cnmsg");
            }
        } catch (Exception e) {
            Log.d("ERROR", "error => " + e.toString());
        }
        return false;
    }

    private User getUserInfo() {

        JSONObject responseBody = syncJsonRequest(Request.Method.GET,
                getAbsoluteUrl(SESSION_PATH), null, null);
        try {
            if (isSuccess(responseBody)) {
                isLogin = true;

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                return gson.fromJson(responseBody.getString("data"), User.class);
            } else {
                mMessage = responseBody.getString("cnmsg");
            }
        } catch (Exception e) {
            Log.d("ERROR", "error => " + e.toString());
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return mMessage;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
