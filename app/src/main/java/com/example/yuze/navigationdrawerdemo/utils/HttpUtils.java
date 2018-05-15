package com.example.yuze.navigationdrawerdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yuze.navigationdrawerdemo.State;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * post and return response
     *
     * @param url  URL
     * @param json request body
     * @return return response string if succeed, or null
     */
    public static String post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("HttpUtils", "POST Exception", e);
            return null;
        }
    }

    public static String post_with_session(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .header("session", State.INSTANCE.sessionId)
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("HttpUtils", "POST Exception", e);
            return null;
        }
    }

    public static String get(String url, String session) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("session", session)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("HttpUtil", "GET Exception", e);
            return null;
        }
    }

    public static Bitmap getBitmap(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return BitmapFactory.decodeStream(response.body().byteStream());
        } catch (IOException e) {
            Log.e("HttpUtil", "getBitmap", e);
            return null;
        }
    }

    public static void delete(String url) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get_with_session(String url, String session) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("session", session)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean delete_with_session(String url, String session) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("session", session)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            return JsonUtils.read(response.body().string(), Boolean.class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String put_with_session(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("session", State.INSTANCE.sessionId)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
