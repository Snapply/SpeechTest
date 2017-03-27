package zhang.snapply.speechtest.data;

import android.content.Context;
import android.content.SharedPreferences;

import zhang.snapply.speechtest.Util.MainContext;
import zhang.snapply.speechtest.message.Message;

/**
 * Created by Snapply Zhang on 2017/3/28.
 */

public class Data {
    public Data(String msg,int type){
        if (type == Message.Send){
            SharedPreferences sendSP = new MainContext().getContext().getSharedPreferences("send_msg", Context.MODE_PRIVATE);
            SharedPreferences.Editor sendEditor = sendSP.edit();
            sendEditor.putString("type","send");
            sendEditor.putString("info",msg);
            sendEditor.commit();
        }else if (type == Message.Receive){
        }
    }
}
