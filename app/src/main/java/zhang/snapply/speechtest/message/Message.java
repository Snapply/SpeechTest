package zhang.snapply.speechtest.message;

/**
 * Created by Snapply Zhang on 2017/3/25.
 */

public class Message {
    public static final int Send = 0;
    public static final int Receive = 1;

    private String info;
    private int type;

    public Message(String msg,int code){
        info = msg;
        type = code;
    }

    public String getMsg(){
        return info;
    }

    public int getType(){
        return type;
    }
}
