package relax.sn.com.relax4.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.adapter.ChatMessageAdapter;
import relax.sn.com.relax4.bean.ChatMessage;
import relax.sn.com.relax4.utils.HttpUtils;


public class TalkActivity extends AppCompatActivity {

    public static String toMsg="";
    private ListView mMsgs;//listview
    private ChatMessageAdapter mAdapter;//适配器
    private List<ChatMessage> mDatas;//适配源

    private TextView tv_back;//应该是语音相关的吧

    public static EditText mInputMsg;
    private Button mSendMsg;

    private Button iv_voice;//那个声音按钮吧

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            // 等待接收，子线程完成数据的返回
            ChatMessage fromMessge = (ChatMessage) msg.obj;
            mDatas.add(fromMessge);//将message加入到listview的数据集
            mAdapter.notifyDataSetChanged();//更新适配器
            mMsgs.setSelection(mDatas.size()-1);
        };

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_right_in,R.anim.hold);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.talk_main);

        initView();
        initDatas();
        // 初始化事件
        initListener();
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg))
                {
                    Toast.makeText(TalkActivity.this, "发送消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //将输入的数据也封装成一个message
                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);//设置类型为发送出去的消息
                mDatas.add(toMessage);//把数据加入到listview的数据集中
                mAdapter.notifyDataSetChanged();//通知更新
                mMsgs.setSelection(mDatas.size()-1);

                mInputMsg.setText("");//最后将输入框文本清空
                //网络操作不应该在主线程,所以弄个子线程 并且更新对话界面 一般用异步或者handler
                new Thread()
                {
                    public void run()
                    {
                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);//子线程通过网络请求拿到chatmessage
                        Message m = Message.obtain(); //得到一个message
                        m.obj = fromMessage; //为message赋值
                        mHandler.sendMessage(m);//通过声明的handler将消息发送
                    };
                }.start();

            }
        });
        //语音相关
        tv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.hold,R.anim.push_right_out);
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);

        //语音相关
        iv_voice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 初始化，再加载
                //MyRecognizerDialogLister.text="";
                VoiceToWord voice = new VoiceToWord(TalkActivity.this,"534e3fe2");
                voice.GetWordFromVoice();
            }
        });

    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_input_msg);
        mSendMsg = (Button) findViewById(R.id.id_send_msg);
        tv_back=(TextView) findViewById(R.id.tv_back);
        iv_voice=(Button) findViewById(R.id.iv_voice);
    }
}
