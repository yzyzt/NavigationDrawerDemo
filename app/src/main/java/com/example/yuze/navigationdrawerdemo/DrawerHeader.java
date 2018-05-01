package com.example.yuze.navigationdrawerdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuze.navigationdrawerdemo.dto.SignInResponse;
import com.example.yuze.navigationdrawerdemo.dto.UserNameResponse;
import com.example.yuze.navigationdrawerdemo.utils.HttpUtils;
import com.example.yuze.navigationdrawerdemo.utils.JsonUtils;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import okhttp3.Response;


@NonReusable
@Layout(R.layout.drawer_header)

public class DrawerHeader {

    @View(R.id.profileImageView)
    ImageView profileImage;

    @View(R.id.nameTxt)
    TextView nameTxt;

    @Resolve
    public void onResolved() {
        if (State.INSTANCE.sessionId == null) {
            nameTxt.setText("未登录");
        } else {
            new DrawerHeaderTask().execute(State.INSTANCE.sessionId);
        }
    }

    private class DrawerHeaderTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            final UserNameResponse userNameResponse  = JsonUtils.read(s,UserNameResponse.class);
            if (userNameResponse.getUserName() == null){
                Log.e("username","get userName err");
            } else {
                nameTxt.setText(userNameResponse.getUserName());
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            return HttpUtils.get(Constants.HOST + Constants.USERS + "/" + State.INSTANCE.userId,strings[0]);
        }
    }
}
