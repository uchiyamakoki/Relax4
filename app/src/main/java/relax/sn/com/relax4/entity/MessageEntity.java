package relax.sn.com.relax4.entity;

import java.io.Serializable;
import java.util.List;

public class MessageEntity implements Serializable {

    private int type;//输入还是输出类型吧，好像
    private long time;//时间
    private int code;//根据放回code判断类型吧
    private String text;//返回内容
    private String url;
    private List<NewsEntity> list;

    public MessageEntity() {
    }

    public MessageEntity(int type, long time) {
        this.type = type;
        this.time = time;
        this.text = text;
    }

    public MessageEntity(int type, long time, String text) {
        this.type = type;
        this.time = time;
        this.text = text;
    }

    public List<NewsEntity> getList() {
        return list;
    }

    public void setList(List<NewsEntity> list) {
        this.list = list;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "type=" + type +
                ", time=" + time +
                ", code=" + code +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", list=" + list +
                '}';
    }

    //定义 输出返回数据 的方法
    public void show() {
        System.out.println(code);

        System.out.println(text);
        //System.out.println(content.to);
        //System.out.println(content.vendor);
        //System.out.println(content.out);
        //System.out.println(content.errNo);
    }
}
