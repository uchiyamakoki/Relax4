package relax.sn.com.relax4.control;

import io.reactivex.Observable;
import relax.sn.com.relax4.entity.MessageEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by John on 2018/4/30.
 */
public interface RetrofitApi {
    // 请求图灵API接口，获得问答信息
    @GET("api")
    Call<MessageEntity> getTuringInfo(@Query("key") String key, @Query("info") String info);

    // 请求图灵API接口，获得问答信息 Retrofit+RxJava
    @GET("api")
    Observable<MessageEntity> getTuringInfoByRxJava(@Query("key") String key, @Query("info") String info);

}
