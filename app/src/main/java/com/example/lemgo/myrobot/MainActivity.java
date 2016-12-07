package com.example.lemgo.myrobot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.avtivity_main);
        initMsgs();

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
