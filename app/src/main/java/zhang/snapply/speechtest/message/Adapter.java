package zhang.snapply.speechtest.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zhang.snapply.speechtest.MainActivity;
import zhang.snapply.speechtest.R;
import zhang.snapply.speechtest.Util.LogTool;
import zhang.snapply.speechtest.Util.MainContext;

/**
 * Created by Snapply Zhang on 2017/3/29.
 */

public class Adapter extends ArrayAdapter<Message> {
   private int resourceId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.send_area = (LinearLayout)view.findViewById(R.id.send_layout);
            viewHolder.receive_area = (LinearLayout)view.findViewById(R.id.receive_layout);
            viewHolder.url_area = (LinearLayout)view.findViewById(R.id.url_area);
            viewHolder.url_title = (TextView)view.findViewById(R.id.url_title);
            viewHolder.url_pic = (ImageView)view.findViewById(R.id.url_pic);
            viewHolder.url_source = (TextView)view.findViewById(R.id.url_source);
            viewHolder.send_text = (TextView)view.findViewById(R.id.send_text);
            viewHolder.receive_text = (TextView)view.findViewById(R.id.receive_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        if (msg.getType() == Message.Send) {
            LogTool.print("Adapter Send启动");
            viewHolder.send_area.setVisibility(View.VISIBLE);
            viewHolder.receive_area.setVisibility(View.GONE);
            viewHolder.url_area.setVisibility(View.GONE);
            viewHolder.send_text.setText(msg.getMsg());
        } else if (msg.getType() == Message.Receive) {
            LogTool.print("Adapter Receive启动");
            viewHolder.send_area.setVisibility(View.GONE);
            viewHolder.receive_area.setVisibility(View.VISIBLE);
            try {
                final JSONObject jsonObject = new JSONObject(msg.getMsg());
                int type = jsonObject.getInt("code");
                if (type == 100000){
                    LogTool.print("type=100000");
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                } else if (type == 40001) {
                    LogTool.print("type=40001");
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                } else if (type == 40002) {
                    LogTool.print("type=40002");
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                } else if (type == 40004) {
                    LogTool.print("type=40004");
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                } else if (type == 40007) {
                    LogTool.print("type=40007");
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                } else if (type == 200000) {
                    LogTool.print("type=200000");
                    LogTool.print("URL="+jsonObject.getString("url"));
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(jsonObject.getString("text")+"\n\n请点击链接"+jsonObject.getString("url"));
                   viewHolder.receive_area.setClickable(true);
                    viewHolder.receive_area.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(jsonObject.getString("url")));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               MainContext.getContext().startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if ((type == 302000)) {
                    LogTool.print("type=302000");
                    viewHolder.url_area.setVisibility(View.VISIBLE);
                    final int n = new Random().nextInt(jsonObject.getJSONArray("list").length()+1);
                    final JSONObject object = jsonObject.getJSONArray("list").getJSONObject(n);
                    LogTool.print("Object-->"+object.toString());
                    LogTool.print(String.valueOf(n));
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                    LogTool.print("Text:"+jsonObject.getString("text"));
                    viewHolder.url_title.setText(object.getString("article"));
                    LogTool.print("Article:"+object.getString("article"));
                    viewHolder.url_source.setText(object.getString("source"));
                    LogTool.print("Source:"+object.getString("source"));
                    try {
                        viewHolder.url_pic.setImageURI(Uri.parse(object.getString("icon")));
                    } catch (Exception e) {
                        LogTool.print("Url pic Exception-->"+e);
                    }
                    viewHolder.url_area.setClickable(true);
                    viewHolder.url_area.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String url = object.getString("detailurl");
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MainContext.getContext().startActivity(intent);
                            } catch (JSONException e) {
                                LogTool.print("Exception:"+e);
                            }
                        }
                    });
                } else if (type == 308000) {
                    LogTool.print("type=308000");
                    viewHolder.url_area.setVisibility(View.VISIBLE);
                    JSONArray array = jsonObject.getJSONArray("list");
                    int n = new Random().nextInt(array.length()+1);
                    final JSONObject object = jsonObject.getJSONArray("list").getJSONObject(n);
                    viewHolder.receive_text.setText(jsonObject.getString("text"));
                    viewHolder.url_title.setText(object.getString("name"));
                    viewHolder.url_source.setText(object.getString("info"));
                    try {
                        viewHolder.url_pic.setImageURI(Uri.parse(object.getString("icon")));
                    } catch (Exception e) {
                        LogTool.print("Url pic Exception-->"+e);
                    }
                    viewHolder.url_area.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String url = object.getString("detailurl");
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse(url));
                                MainContext.getContext().startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    viewHolder.url_area.setVisibility(View.GONE);
                    viewHolder.receive_text.setText(msg.getMsg());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    public Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    class ViewHolder{
        LinearLayout send_area;
        LinearLayout receive_area;
        LinearLayout url_area;
        TextView send_text;
        TextView receive_text;
        TextView url_title;
        ImageView url_pic;
        TextView url_source;
    }
}
