package zhang.snapply.speechtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhang.snapply.speechtest.message.Message;
import zhang.snapply.speechtest.net.Link;

public class MainActivity extends Activity {
    private ListView listView;
    private EditText editText;
    private Button button;
    private String sendMessage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_for_main);
        listView = (ListView) findViewById(R.id.main_list_area);
        editText = (EditText)findViewById(R.id.send_message);
        button = (Button)findViewById(R.id.send_button);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage = editText.getText().toString();
                if (!(sendMessage.equals(null) || sendMessage.equals(" "))){
                    new Link(sendMessage);
                }
            }
        });
    }
    private void init(){
        List<Message> initinfo = new ArrayList<>();
        Message info = new Message(getString(R.string.initIngo),Message.Receive) ;
        initinfo.add(info);
        class initAdapter extends ArrayAdapter<Message>{

            protected int resourseId;

            public initAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Message> objects) {
                super(context, resource, objects);
                resourseId = resource;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Message message = getItem(position);
                View view = LayoutInflater.from(getContext()).inflate(resourseId,null);
                TextView text = (TextView) view.findViewById(R.id.receive_text);
                text.setText(message.getMsg());
                return view;
            }
        }
        initAdapter initAdapter = new initAdapter(MainActivity.this,R.layout.layout_for_listview,initinfo);
        listView.setAdapter(initAdapter);
    }
}

