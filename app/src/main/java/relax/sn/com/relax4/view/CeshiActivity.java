package relax.sn.com.relax4.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;



import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.control.RetrofitApi;
import relax.sn.com.relax4.entity.QuestionInfo;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CeshiActivity extends AppCompatActivity {

    private int count;
    private int current;
    private boolean wrongNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);

        requestApiByRetrofit_RxJava();
    }

    //测试接口rxjava
    private void requestApiByRetrofit_RxJava(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.7:8080/blog/question_info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api2=retrofit.create(RetrofitApi.class);

        api2.getQuestion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage,Throwable::printStackTrace);
    }

    private void handleResponseMessage(List<QuestionInfo> questionInfos) {
          count=questionInfos.size();
          current=0;
        wrongNode=false;

        final TextView tv_question=(TextView) findViewById(R.id.question);
        final RadioButton[] radioButtons=new RadioButton[4];
        radioButtons[0]=(RadioButton) findViewById(R.id.answerA);
        radioButtons[1]=(RadioButton) findViewById(R.id.answerB);
        radioButtons[2]=(RadioButton) findViewById(R.id.answerC);
        radioButtons[3]=(RadioButton) findViewById(R.id.answerD);
        Button btn_next=(Button) findViewById(R.id.btn_next);
        Button btn_previous=(Button) findViewById(R.id.btn_previous);
        final TextView tv_explaination=(TextView)findViewById(R.id.explaination);
        final RadioGroup radioGroup=(RadioGroup) findViewById(R.id.radioGroups);

        QuestionInfo q=questionInfos.get(0);
        tv_question.setText(q.getQuestion());
        tv_explaination.setText(q.getExplaination());
        radioButtons[0].setText(q.getAnswera());
        radioButtons[1].setText(q.getAnswerb());
        radioButtons[2].setText(q.getAnswerc());
        radioButtons[3].setText(q.getAnswerd());

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current<count-1){
                    current++;
                    QuestionInfo q=questionInfos.get(current);
                    tv_question.setText(q.getQuestion());
                    tv_explaination.setText(q.getExplaination());
                    radioButtons[0].setText(q.getAnswera());
                    radioButtons[1].setText(q.getAnswerb());
                    radioButtons[2].setText(q.getAnswerc());
                    radioButtons[3].setText(q.getAnswerd());

                    radioGroup.clearCheck();
                    if (q.getSelectedAnswer()!=-1){
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
                    }
                }else if(current==count-1&&wrongNode==true){
                    new AlertDialog.Builder(CeshiActivity.this)
                            .setTitle("提示")
                            .setMessage("已经达到最后一题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CeshiActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
                else {
                    new AlertDialog.Builder(CeshiActivity.this)
                            .setTitle("提示")
                            .setMessage("是否查看解析")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    wrongNode=true;
                                    current=0;
                                    count=questionInfos.size();

                                    QuestionInfo q=questionInfos.get(current);
                                    tv_question.setText(q.getQuestion());
                                    tv_explaination.setText(q.getExplaination());
                                    radioButtons[0].setText(q.getAnswera());
                                    radioButtons[1].setText(q.getAnswerb());
                                    radioButtons[2].setText(q.getAnswerc());
                                    radioButtons[3].setText(q.getAnswerd());

                                    tv_explaination.setVisibility(View.VISIBLE);

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CeshiActivity.this.finish();
                                }
                            })
                            .show();
                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current>0){
                    current--;
                    QuestionInfo q=questionInfos.get(current);
                    tv_question.setText(q.getQuestion());
                    tv_explaination.setText(q.getExplaination());
                    radioButtons[0].setText(q.getAnswera());
                    radioButtons[1].setText(q.getAnswerb());
                    radioButtons[2].setText(q.getAnswerc());
                    radioButtons[3].setText(q.getAnswerd());

                    radioGroup.clearCheck();
                    if (q.getSelectedAnswer()!=-1){
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<4;i++){
                    if(radioButtons[i].isChecked()==true){
                        questionInfos.get(current).setSelectedAnswer(i);
                        break;
                    }
                }
            }
        });


    }

}
