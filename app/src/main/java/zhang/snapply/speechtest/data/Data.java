package zhang.snapply.speechtest.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import zhang.snapply.speechtest.Util.MainContext;
import zhang.snapply.speechtest.message.Message;

/**
 * Created by Snapply Zhang on 2017/3/28.
 */

public class Data {
    SharedPreferences sendSP = new MainContext().getContext().getSharedPreferences("send_msg", Context.MODE_PRIVATE);
    SharedPreferences receiveSP = new MainContext().getContext().getSharedPreferences("receive_msg",Context.MODE_PRIVATE);
    SharedPreferences.Editor sendEditor = sendSP.edit();
    SharedPreferences.Editor receiveEditor = receiveSP.edit();
    public Data(String msg,int type){
        if (type == Message.Send){
            sendEditor.putString("type","send");
            sendEditor.putString("info",msg);
            sendEditor.commit();
        }else if (type == Message.Receive){
            try {
                JSONObject jsonObject = new JSONObject(msg);
                int code = jsonObject.getInt("code");
                switch (code) {
                    case 100000: text_info(msg);
                        break;
                    case 200000: link_info(msg);
                        break;
                    case 302000: news_info(msg);
                        break;
                    case 308000: cookbook_info(msg);;
                        break;
                    case 40001: text_info(msg);
                        break;
                    case 40002: text_info(msg);
                        break;
                    case 40004: text_info(msg);
                        break;
                    case 40007: text_info(msg);
                        break;
                    default:
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void text_info(String info){
        try {
            JSONObject jsonObject = new JSONObject(info);
            int code = jsonObject.getInt("code");
            String text = jsonObject.getString("text");
            receiveEditor.putInt("code",code);
            receiveEditor.putString("text",text);
            receiveEditor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void link_info(String info){
        try {
            JSONObject jsonObject = new JSONObject(info);
            int code = jsonObject.getInt("code");
            String text = jsonObject.getString("text");
            String url = jsonObject.getString("url");
            receiveEditor.putInt("code",code);
            receiveEditor.putString("text",text);
            receiveEditor.putString("url",url);
            receiveEditor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void news_info(String info){
        try {
            JSONObject jsonObject = new JSONObject(info);
            int code = jsonObject.getInt("code");
            String text = jsonObject.getString("text");
            receiveEditor.putInt("code",code);
            receiveEditor.putString("text",text);
            JSONArray list = jsonObject.getJSONArray("list");
            for(int i = 0;i < 5;i++){
                JSONObject object = list.getJSONObject(i);
                String article = object.getString("article");
                String source = object.getString("source");
                String iconurl = object.getString("icon");
                String detailurl = object.getString("detailurl");
                Set<String> strings = new HashSet<>();
                strings.add(article);
                strings.add(source);
                strings.add(iconurl);
                strings.add(detailurl);
                receiveEditor.putStringSet("news"+i,strings);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void cookbook_info(String info){
        try {
            JSONObject jsonObject = new JSONObject(info);
            int code = jsonObject.getInt("code");
            String text = jsonObject.getString("text");
            receiveEditor.putInt("code",code);
            receiveEditor.putString("text",text);
            JSONArray list = jsonObject.getJSONArray("list");
            for(int i = 0;i < 10;i++){
                JSONObject object = list.getJSONObject(i);
                String article = object.getString("name");
                String source = object.getString("info");
                String iconurl = object.getString("icon");
                String detailurl = object.getString("detailurl");
                Set<String> strings = new HashSet<>();
                strings.add(article);
                strings.add(source);
                strings.add(iconurl);
                strings.add(detailurl);
                receiveEditor.putStringSet("book"+i,strings);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
