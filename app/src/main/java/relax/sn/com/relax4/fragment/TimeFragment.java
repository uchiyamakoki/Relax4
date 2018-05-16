package relax.sn.com.relax4.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.control.RetrofitApi;
import relax.sn.com.relax4.entity.TimeInfo;
import relax.sn.com.relax4.view.TestActivity;
import relax.sn.com.relax4.view.TimeActivity;
import relax.sn.com.relax4.view.TimeAddActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by John on 2018/5/15.
 */
public class TimeFragment extends Fragment{

    @Bind(R.id.eyes)
    ImageView meyes;
    @Bind(R.id.email)
    ImageView memails;
    @Bind(R.id.timecount)
    TextView mTimeCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_timemachine,container,false);
        ButterKnife.bind(this,view);
        requestApiByRetrofit_RxJava();

        meyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),TimeActivity.class);
                startActivity(intent);
            }
        });
        memails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TimeAddActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    //获得日记数api
    private void requestApiByRetrofit_RxJava(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.7:8080/blog/time_info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitApi api=retrofit.create(RetrofitApi.class);

        api.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage,Throwable::printStackTrace);

    }

    private void handleResponseMessage(List<TimeInfo> timeInfo) {
        if (timeInfo==null) return;
        int i=timeInfo.size();
        System.out.println(i);
        mTimeCount.setText("已经寄出"+timeInfo.size()+"个时间胶囊");
    }


}
