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

import java.util.ArrayList;
import java.util.List;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.bean.Question;
import relax.sn.com.relax4.utils.DBService;
import relax.sn.com.relax4.utils.ResDrawableImgUtil;

/**
 * Created by John on 2018/3/9.
 */
public class ExamActivity extends AppCompatActivity {
    private int count;
    private int current;
    private boolean wrongNode;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        DBService dbService=new DBService();
        final List<Question> list=dbService.getQuestion();

        count=list.size();
        current=0;
        wrongNode=false;

        final TextView tv_question=(TextView) findViewById(R.id.question);
        final RadioButton[] radioButtons=new RadioButton[2];
        radioButtons[0]=(RadioButton) findViewById(R.id.answerA);
        radioButtons[1]=(RadioButton) findViewById(R.id.answerB);
        //radioButtons[2]=(RadioButton) findViewById(R.id.answerC);
        //radioButtons[3]=(RadioButton) findViewById(R.id.answerD);
        Button btn_next=(Button) findViewById(R.id.btn_next);
        Button btn_previous=(Button) findViewById(R.id.btn_previous);
        final TextView tv_explaination=(TextView)findViewById(R.id.explaination);
        final RadioGroup radioGroup=(RadioGroup) findViewById(R.id.radioGroup);

        Question q=list.get(0);
        tv_question.setText(q.question);
        tv_explaination.setText(q.explaination);
        //radioButtons[0].setText(q.answerA);
        //radioButtons[0].setBackgroundResource(R.drawable.q1);
        //radioButtons[1].setText(q.answerB);
        //radioButtons[2].setText(q.answerC);
        //radioButtons[3].setText(q.answerD);

        String imgName = q.answerA+".png";
        int resId = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName);
        if(resId != -1){
            radioButtons[0].setBackgroundResource(resId);
        }
        String imgName1 = q.answerB+".png";
        int resId1 = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName1);
        if(resId1 != -1){
            radioButtons[1].setBackgroundResource(resId1);
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if(current<count-1){
                    current++;
                    Question q=list.get(current);//final 不然不能从内部类中访问
                    tv_question.setText(q.question);
                    //radioButtons[0].setText(q.answerA);
                    //radioButtons[1].setText(q.answerB);
                    //radioButtons[2].setText(q.answerC);
                    //radioButtons[3].setText(q.answerD);
                    tv_explaination.setText(q.explaination);

                    String imgName = q.answerA+".png";
                    int resId = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName);
                    if(resId != -1){
                        radioButtons[0].setBackgroundResource(resId);
                    }
                    String imgName1 = q.answerB+".png";
                    int resId1 = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName1);
                    if(resId1 != -1){
                        radioButtons[1].setBackgroundResource(resId1);
                    }

                    radioGroup.clearCheck();//记录之前的选择,-1为没选择
                    if (q.selectedAnswer!=-1){
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }else if(current==count-1&&wrongNode==true){
                    //温习错题时的检查
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("已经达到最后一题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }else {
                    //没有题目了，开始检测正确性
                    final List<Integer> wrongList=checkAnswer(list);
                    if (wrongList.size()==0){
                        new AlertDialog.Builder(ExamActivity.this)
                                .setTitle("提示")
                                .setMessage("恭喜你状态很好！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ExamActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                       // ExamActivity.this.finish();else的锅很重要
                    }else {
                        new AlertDialog.Builder(ExamActivity.this)
                            /*
                             .setMessage("您答对了"+(list.size()-wrongList.size())
                                    +"道题目，答错了"+wrongList.size()+"道题目。是否查看具体情况？")
                             */
                                .setTitle("提示")
                                .setMessage("您似乎有点压力，是否查看具体情况？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        wrongNode=true;
                                    /*
                                    1.将错题复制到newList中
                                     */
                                        List<Question> newList=new ArrayList<Question>();
                                        for (int i=0;i<wrongList.size();i++){
                                            newList.add(list.get(wrongList.get(i)));
                                        }
                                        list.clear();//将原来的list清空,将所有错的再加到list中
                                        for (int i=0;i<newList.size();i++){
                                            list.add(newList.get(i));
                                        }
                                        current=0;//重新设定一下c c
                                        count=list.size();

                                        Question q=list.get(current);//更新数据内容 copy前面就行
                                        tv_question.setText(q.question);
                                        // radioButtons[0].setText(q.answerA);
                                        //radioButtons[1].setText(q.answerB);
                                        // radioButtons[2].setText(q.answerC);
                                        //radioButtons[3].setText(q.answerD);
                                        String imgName = q.answerA+".png";
                                        int resId = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName);
                                        if(resId != -1){
                                            radioButtons[0].setBackgroundResource(resId);
                                        }
                                        String imgName1 = q.answerB+".png";
                                        int resId1 = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName1);
                                        if(resId1 != -1){
                                            radioButtons[1].setBackgroundResource(resId1);
                                        }

                                        tv_explaination.setText(q.explaination);
                                        tv_explaination.setVisibility(View.VISIBLE);//将隐藏的显示出来
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ExamActivity.this.finish();
                                    }
                                })
                                .show();
                    }

                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current>0){
                    current--;
                    Question q=list.get(current);//final 不然不能从内部类中访问
                    tv_question.setText(q.question);
                    //radioButtons[0].setText(q.answerA);
                    //radioButtons[1].setText(q.answerB);
                    //radioButtons[2].setText(q.answerC);
                    //radioButtons[3].setText(q.answerD);
                    String imgName = q.answerA+".png";
                    int resId = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName);
                    if(resId != -1){
                        radioButtons[0].setBackgroundResource(resId);
                    }
                    String imgName1 = q.answerB+".png";
                    int resId1 = ResDrawableImgUtil.getResourceIdByIdentifier(ExamActivity.this,imgName1);
                    if(resId1 != -1){
                        radioButtons[1].setBackgroundResource(resId1);
                    }

                    tv_explaination.setText(q.explaination);

                    radioGroup.clearCheck();
                    if (q.selectedAnswer!=-1){
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
            }
        });

        /*
        判断选项选中的监听事件
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(int i=0;i<2;i++){
                    if(radioButtons[i].isChecked()==true){
                        list.get(current).selectedAnswer=i;
                        break;
                    }
                }
            }
        });
    }

    /*
    判断是否答对
     */
    private List<Integer> checkAnswer(List<Question> list){
        List<Integer> wrongList=new ArrayList<Integer>(); //保存错误的下标
        for (int i=0;i<list.size();i++){
            //判断对错
            if(list.get(i).answer!=list.get(i).selectedAnswer ){
                wrongList.add(i);
            }
        }
        return wrongList;
    }
}
