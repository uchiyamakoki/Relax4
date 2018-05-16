package relax.sn.com.relax4.control;

import java.util.List;

import io.reactivex.Observable;
import relax.sn.com.relax4.entity.MessageEntity;
import relax.sn.com.relax4.entity.QuestionInfo;
import relax.sn.com.relax4.entity.TimeInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by John on 2018/4/30.
 */
public interface RetrofitApi {
    // 请求图灵API接口，获得问答信息
    @GET("api")
    Call<MessageEntity> getTuringInfo(@Query("key") String key, @Query("info") String info);

    //请求后台，获得时间胶囊总数
    @GET("count.action")
    Observable<List<TimeInfo>> getCount();

    //显示已经可以查看的时间胶囊
    @GET("times.action")
    Observable<List<TimeInfo>> getTimeInfoByRxJava();

    @GET("times.action")
    Call<List<TimeInfo>> getTimeInfo();

    // 请求图灵API接口，获得问答信息 Retrofit+RxJava
    @GET("api")
    Observable<MessageEntity> getTuringInfoByRxJava(@Query("key") String key, @Query("info") String info);

    @FormUrlEncoded
    @POST("save.action")
    Observable<TimeInfo> postTimeInfo(@Field("content") String content,@Field("end_time") String end_time);

    //心理测试接口
    @GET("question.action")
    Observable<List<QuestionInfo>> getQuestion();

}
