package com.example.lemgo.myrobot;


import android.nfc.Tag;
import android.util.Log;

import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class SdkInit {

    public static  void  init(SDKInitBuilder builder, InitListener initListener){

        initListener  = new InitListener() {
            @Override
            public void onComplete() {
                TuringApiManager manager  = new TuringApiManager(MyApplication.getContext());
                manager.setHttpListener(new HttpConnectionListener() {
                    @Override
                    public void onError(ErrorMessage errorMessage) {
                        Log.d("mag",errorMessage.getMessage());
                    }

                    @Override
                    public void onSuccess(RequestResult requestResult) {
                        Log.d("TAG",requestResult.getContent().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(requestResult.getContent().toString());
                            if(jsonObject.has("text")){
                                Log.d("msg2",jsonObject.getString("text"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                manager.requestTuringAPI("你好");
            }
            @Override
            public void onFail(String s) {
                Log.d("msg","error!");
            }
        };
    }
}

