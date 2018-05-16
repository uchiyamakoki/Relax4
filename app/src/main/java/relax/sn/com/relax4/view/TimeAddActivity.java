package relax.sn.com.relax4.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.control.RetrofitApi;
import relax.sn.com.relax4.entity.TimeInfo;
import relax.sn.com.relax4.widget.LinedEditText;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by John on 2018/5/15.
 */
public class TimeAddActivity extends AppCompatActivity{
    @Bind(R.id.fasong)
    ImageView mFasong;
    @Bind(R.id.add_time_et_content)
    LinedEditText mAddTime;
    @Bind(R.id.add_time_et_title)
    EditText mAddEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtime);
        ButterKnife.bind(this);

        mFasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=mAddTime.getText().toString()+"";
                String end_time=mAddEt.getText().toString()+"-06-01 16:37:30.0";
                requestApiByRetrofit_RxJava3(content,end_time);
                finish();
            }
        });


    }

    private void requestApiByRetrofit_RxJava3(String content,String end_time){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.7:8080/blog/time_info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api3=retrofit.create(RetrofitApi.class);

        api3.postTimeInfo(content,end_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage,Throwable::printStackTrace);

    }

    private void handleResponseMessage(TimeInfo timeInfo) {

    }


}
