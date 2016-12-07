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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class MainActivity extends Activity {
    private ListView msgListView;
    private EditText editText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList  = new ArrayList<Msg>();

    private static final String KEY = "7b2fc8d27a294814b86b93752c99c9e3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.avtivity_main);
        initMsgs();

        SDKInitBuilder builder = new SDKInitBuilder(MyApplication.getContext()).setTuringKey(KEY).setUniqueId("18796015450");
        SDKInit init = new SDKInit();
        init.init(builder, new InitListener() {
            @Override
            public void onComplete() {
               TuringApiManager m = new TuringApiManager(MainActivity.this);


            }

            @Override
            public void onFail(String s) {
                Log.d("msg22","chucuole");

            }
        });

        adapter =new MsgAdapter(MainActivity.this,R.layout.msg_item,msgList);
        editText  = (EditText) findViewById(R.id.input_text);
        send  = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                if(!"".equals(content)){
                    Msg msg =new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();//   当有新消息时，刷新listview的显示
                    msgListView.setSelection(msgList.size());//将listview定位到最后一行
                    editText.setText("");//清空输入框
                }
            }
        });

    }

    private void initMsgs() {
        Msg msg1 = new Msg("主人你好，有什么需要帮助的吗？",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }
}
