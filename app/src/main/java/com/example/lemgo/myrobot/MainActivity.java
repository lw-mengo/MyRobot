package com.example.lemgo.myrobot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class MainActivity extends Activity {
    private ListView msgListView;
    private EditText editText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    private TuringApiManager m = null;
    private static final String KEY = "7b2fc8d27a294814b86b93752c99c9e3";
    private static final String SECRET = "f56ca31f6cb0c8e4";
    private static final String UNIQUEID = "159357";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.avtivity_main);
        initMsgs();

        //初始化机器人
        SDKInitBuilder builder = new SDKInitBuilder(MainActivity.this).setTuringKey(KEY).setSecret(SECRET).setUniqueId(UNIQUEID);//这三个参数必须要
        SDKInit.init(builder, new InitListener() {
            @Override
            public void onComplete() {
                m = new TuringApiManager(MainActivity.this);
            }

            @Override
            public void onFail(String s) {
                Log.d("msg22", s);
            }
        });
        adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
        editText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                m.setHttpListener(httpConnectionListener);
                m.requestTuringAPI(content);//传递消息过去
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();//   当有新消息时，刷新listview的显示
                    msgListView.setSelection(msgList.size());//将listview定位到最后一行
                    editText.setText("");//清空输入框
                }
            }
        });
    }

    HttpConnectionListener httpConnectionListener = new HttpConnectionListener() {
        @Override
        public void onError(ErrorMessage errorMessage) {
            Log.d("msg22", errorMessage.getMessage());
        }

        @Override
        public void onSuccess(RequestResult requestResult) {
            if (requestResult != null) {
                try {
                    JSONObject jsonObject = new JSONObject(requestResult.getContent().toString());
                    if (jsonObject.has("text")) {
                        Log.d("msg223", jsonObject.getString("text"));
                    }
                    String content = jsonObject.getString("text");
                    Msg msg = new Msg(content, Msg.TYPE_RECEIVED);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void initMsgs() {
        Msg msg1 = new Msg("你好，有什么需要帮助的吗？", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }


}
