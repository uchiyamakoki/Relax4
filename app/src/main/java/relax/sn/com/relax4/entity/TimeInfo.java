package relax.sn.com.relax4.entity;

/**
 * Created by John on 2018/5/12.
 */
public class TimeInfo {
    private int id;
    private String content;//文章的中文简介
    private String end_time;//文章最后一次更新的系统时间
    private int status;//状态(1.是正常 0.回收站)

    public TimeInfo(int id, String content, String end_time, int status) {
        this.id = id;
        this.content = content;
        this.end_time = end_time;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "TimeInfo [id=" + id + ", content=" + content + ", end_time="
                + end_time + ", status=" + status + "]";
    }
}
