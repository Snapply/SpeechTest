package zhang.snapply.speechtest.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import zhang.snapply.speechtest.Util.LogTool;
import zhang.snapply.speechtest.message.Message;

/**
 * Created by Snapply Zhang on 2017/3/25.
 */

public class Link {
    private static final String Address = "http://www.tuling123.com/openapi/api";

    private String info;

    public Link(String sendMessage) {
        info = sendMessage;
    }

    public Message  Connect(){
        LogTool.print("进入Link-Tool方法");
        Message response = null;
        try {
            URL url = new URL(Address+"?key="+new credit().getAPIKey()+"&info="+info);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            LogTool.print(reader.toString());
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            response = new Message(builder.toString(),Message.Receive);
            LogTool.print(response.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
