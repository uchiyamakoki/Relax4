package relax.sn.com.relax4.entity;

/**
 * Created by John on 2018/4/16.
 */
public class Message {

    private long code;
    private String text;

    public Message() {
    }

    public Message(long code, String text) {
        this.code = code;
        this.text = text;
    }

    public void setCode(long code) {
        this.code = code;
    }
    public long getCode() {
        return code;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", text='" + text + '\'' +
                '}';
    }
}
