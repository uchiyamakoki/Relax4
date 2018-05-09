package relax.sn.com.relax4.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.adapter.ChatMessageAdapter;
import relax.sn.com.relax4.constant.TulingParams;
import relax.sn.com.relax4.control.RetrofitApi;
import relax.sn.com.relax4.entity.MessageEntity;
import relax.sn.com.relax4.entity.ResultBean;
import relax.sn.com.relax4.utils.IsNullOrEmpty;
import relax.sn.com.relax4.utils.KeyBoardUtil;
import relax.sn.com.relax4.utils.TimeUtil;
import relax.sn.com.relax4.view.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TulingActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar; //就是那个万恶的app_base_toolbar
    @Bind(R.id.lv_message)
    ListView lvMessage; //那个对话的listView
    @Bind(R.id.et_msg)
    EditText etMsg;//发送消息框
    @Bind(R.id.iv_send_msg)
    ImageView ivSendMsg;//发送的那个图片按钮
    @Bind(R.id.rl_msg)
    RelativeLayout r1Msg;//最下面那个相对布局
    @Bind(R.id.gotovoice)
    ImageView mgovoice;

    private List<MessageEntity> msgList=new ArrayList<>();//用来接收消息
    private ChatMessageAdapter msgAdapter; //消息适配器

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuling);
        ButterKnife.bind(this);
        //requestApiByRetrofit("你好");
        //requestApiByRetrofit_RxJava("讲个笑话");
        initData();
        initView();
        SpeechUtility.createUtility(TulingActivity.this, SpeechConstant.APPID+"=58057ac8");
        mGson=new Gson();
        initListener();

    }

    private void initData() {
        if (msgList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！俺是图灵机器人！\n咱俩聊点什么呢？\n你有什么要问的么？");
            msgList.add(entity);
        }
        msgAdapter = new ChatMessageAdapter(this, msgList);
        lvMessage.setAdapter(msgAdapter);
        lvMessage.setSelection(msgAdapter.getCount());
    }

    private void initView() {
        toolbar.setTitle("TulingR"); //设置toolbar
        setSupportActionBar(toolbar);
        mgovoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TulingActivity.this,VoiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListener() {
        //发送消息按钮点击监听
        ivSendMsg.setOnClickListener(v -> sendMessage());

        lvMessage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                KeyBoardUtil.hideKeyboard(mActivity);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    public void sendMessage(){
        String msg=etMsg.getText().toString().trim();//将文本转换成string并且去掉空格
        //判断只有空格或者是null
        if(!IsNullOrEmpty.isEmpty(msg)){
            MessageEntity entity=new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(),msg);
            msgList.add(entity);
            msgAdapter.notifyDataSetChanged(); //用notifyDataSetChanged更新主界面
            etMsg.setText(""); //清空输入框文本

            requestApiByRetrofit_RxJava(msg);
        }
    }

    // 请求图灵API接口，发送info,获得问答信息
    private void requestApiByRetrofit(String info){
        //步骤4:创建Retrofit对象
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(TulingParams.TULING_URL) // 设置 网络请求 Url（Retrofit2.0拥有优秀的拼接功能,而不用自己写拼接方法）
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build(); //自动设置超时为10s，修改则要自己建一个OkHttpClient

        // 步骤5:创建 网络请求接口 的实例
        RetrofitApi api=retrofit.create(RetrofitApi.class);
        //对 发送请求 进行封装
        Call<MessageEntity> call=api.getTuringInfo(TulingParams.TULING_KEY,info);
        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<MessageEntity>() {
            //请求成功时候的回调
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                response.body().show();//直接在entity中加个show方法来测试
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                System.out.println("连接失败");
            }
        });
    }

    // 请求图灵API接口，获得问答信息 retrofit+rxjava
    private void requestApiByRetrofit_RxJava(String info){
        //步骤4:创建Retrofit对象
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(TulingParams.TULING_URL)// 设置 网络请求 Url（Retrofit2.0拥有优秀的拼接功能,而不用自己写拼接方法）
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 针对rxjava2.x
                .build();
        // 步骤5:创建 网络请求接口 的实例
        RetrofitApi api=retrofit.create(RetrofitApi.class);

        //对 发送请求 进行封装
        api.getTuringInfoByRxJava(TulingParams.TULING_KEY,info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);
    }

    private void handleResponseMessage(MessageEntity entity){
        //entity.show();
        if (entity == null) return;

        entity.setTime(TimeUtil.getCurrentTimeMillis());
        entity.setType(ChatMessageAdapter.TYPE_LEFT);

        //开多服务后有个switch

        msgList.add(entity);//把得到的entity放入msgList
        msgAdapter.notifyDataSetChanged();//通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容
    }

    public void onRecognise(View view) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, null);
        //2.设置accent(口音)、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//简体中文
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
        //4.显示dialog，接收语音输入
        mDialog.show();
    }
    private RecognizerDialogListener mRecognizerDialogListener =  new RecognizerDialogListener() {

        /**
         *
         * @param recognizerResult 语音识别结果
         * @param b true表示是标点符号
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            // Toast.makeText(MainActivity.this, recognizerResult.getResultString(), Toast.LENGTH_LONG).show();
            if (b) {
                return;
            }
            ResultBean resultBean = mGson.fromJson(recognizerResult.getResultString(), ResultBean.class);
            List<ResultBean.WsBean> ws = resultBean.getWs();
            String w = "";
            for (int i = 0; i < ws.size(); i++) {
                List<ResultBean.WsBean.CwBean> cw = ws.get(i).getCw();
                for (int j = 0; j < cw.size(); j++) {
                    w += cw.get(j).getW();
                }
            }
            Toast.makeText(TulingActivity.this, w, Toast.LENGTH_SHORT).show();
            etMsg.setText(w);
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };



}
