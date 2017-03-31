package zhang.snapply.speechtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zhang.snapply.speechtest.Util.LogTool;
import zhang.snapply.speechtest.Util.MainContext;
import zhang.snapply.speechtest.data.Data;
import zhang.snapply.speechtest.message.Message;
import zhang.snapply.speechtest.net.Link;

public class MainActivity extends Activity {
    private ListView listView;
    private EditText editText;
    private String sendMessage;
    private zhang.snapply.speechtest.message.Adapter adapter;
    private List<Message> list = new ArrayList<>();

    /*
    SharedPreferences sendinfo = new MainContext().getContext().getSharedPreferences("send_mag",MODE_PRIVATE);
    SharedPreferences receiveinfo = new MainContext().getContext().getSharedPreferences("receive_msg",MODE_PRIVATE);
    SharedPreferences.Editor sendEditor = sendinfo.edit();
    SharedPreferences.Editor receiveEditor = receiveinfo.edit();
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_for_main);
        listView = (ListView) findViewById(R.id.main_list_area);
        editText = (EditText)findViewById(R.id.send_message);
        Button button = (Button) findViewById(R.id.send_button);
        init();
        adapter = new zhang.snapply.speechtest.message.Adapter(MainActivity.this,R.layout.layout_for_listview,list);
        listView.setAdapter(adapter);
        LogTool.print("Step 1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogTool.print("Send Button启动");
                sendMessage = editText.getText().toString();
                LogTool.print("发送内容:"+sendMessage);
                //new Data(sendMessage,Message.Send);
                list.add(new Message(sendMessage,Message.Send));
                adapter.notifyDataSetChanged();
                LogTool.print("新线程入口");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogTool.print("线程启动");
                        Link link = new Link(sendMessage);
                        Message message = link.Connect();
                        list.add(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                LogTool.print("刷新点");
                editText.setText("");
                listView.setSelection(list.size());
                editText.setNextFocusUpId(R.id.send_button);
            }
        });
    }
    private void init(){
        LogTool.print("进入init");
        String initMsg = "{\"code\" : 100000," + "\"text\" : \"" + getString(R.string.initInfo) + "\"}";
        /*
        TextView startText = (TextView)findViewById(R.id.receive_text);
        startText.setText(getString(R.string.initInfo));
        LinearLayout sendArea = (LinearLayout)findViewById(R.id.send_text_area);
        LinearLayout receiveArea = (LinearLayout)findViewById(R.id.receive_text_area);
        LinearLayout urlArea = (LinearLayout)findViewById(R.id.url_area);
        sendArea.setVisibility(View.GONE);
        urlArea.setVisibility(View.GONE);;
        receiveArea.setVisibility(View.VISIBLE);
        */
        LogTool.print("init message==" + initMsg);
        Message message = new Message(initMsg,Message.Receive);
        list.add(message);
        for (Message message1 : list){
            LogTool.print(message1.getMsg()+"\n");
        }
    }
}

