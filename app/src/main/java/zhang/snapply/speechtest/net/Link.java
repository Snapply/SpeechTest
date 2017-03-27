package zhang.snapply.speechtest.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Snapply Zhang on 2017/3/25.
 */

public class Link {
    private static final String Address = "http://www.tuling123.com/openapi/api";

    private String info;

    public Link(String msg){
        info = msg;
    }

    private void Connect(){
        try {
            URL url = new URL(Address+"?key="+new credit().getAPIKey()+"&info="+this.info);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
