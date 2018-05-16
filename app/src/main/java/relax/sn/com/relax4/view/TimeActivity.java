package relax.sn.com.relax4.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.adapter.TimeAdapter;
import relax.sn.com.relax4.control.RetrofitApi;
import relax.sn.com.relax4.entity.TimeInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeActivity extends AppCompatActivity {

    @Bind(R.id.main_rv_show_time)
    RecyclerView mMainRvShowTime;

    private List<TimeInfo> timeInfoList=new ArrayList<>();
    private TimeAdapter timeAdapter;

    TimeInfo timeInfo1=new TimeInfo(1,"44岁了\n" +
            " 这些年辛苦了","寄往2035年",1);
    TimeInfo timeInfo2=new TimeInfo(2,"五月天，我一定要去一次你们的演唱会","寄往2021年",1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesee);
        ButterKnife.bind(this);
       // getSupportActionBar().hide();
       // requestApiByRetrofit();
        requestApiByRetrofit_RxJava();



    }

    //测试接口rxjava
    private void requestApiByRetrofit_RxJava(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.7:8080/blog/time_info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api2=retrofit.create(RetrofitApi.class);

        api2.getTimeInfoByRxJava()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage,Throwable::printStackTrace);
    }

    //测试接口
    private void requestApiByRetrofit(){
        //步骤1.创建创建Retrofit对象
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.7:8080/blog/time_info/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //步骤二
        RetrofitApi api2=retrofit.create(RetrofitApi.class);

        //步骤三
        Call<List<TimeInfo>> call=api2.getTimeInfo();

        call.enqueue(new Callback<List<TimeInfo>>() {
            @Override
            public void onResponse(Call<List<TimeInfo>> call, Response<List<TimeInfo>> response) {
                List<TimeInfo> timeInfos=response.body();
                for (int i=0;i<timeInfos.size();i++){
                    System.out.println(timeInfos.get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<TimeInfo>> call, Throwable t) {
                System.out.println("连接失败");
            }
        });
    }

    private void handleResponseMessage(List<TimeInfo> timeInfos){
        if (timeInfos == null) return ;
       /* for (int i=timeInfos.size();i>=0;i--){
            timeInfoList.add(timeInfos.get(i));
        }*/
       /* for (int i=0;i<timeInfos.size();i++){
            System.out.println(timeInfos.get(i).toString());
        }*/
        timeAdapter=new TimeAdapter(this,timeInfos);
        mMainRvShowTime.setLayoutManager(new LinearLayoutManager(this));
        mMainRvShowTime.setAdapter(timeAdapter);
       // timeAdapter.notifyDataSetChanged();
    }
}
