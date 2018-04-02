package relax.sn.com.relax4.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import relax.sn.com.relax4.R;

/**
 * Created by John on 2018/3/23.
 * 2.新建一个启动事件（借鉴Relax）
 * ①继承AppCompatActivity
 * ②重写onCreate（1.创建start_main.xml和去掉上面那个/在Mainifests里改---修改主界面，theme(自带的),把MainActivity加回去 顺便换个图标）
 */
public class StartActivity extends AppCompatActivity {
    private final long SPLASH_LENGTH=3000; //设置启动画面事件3s 用final修饰不会再被更改
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //基本都有的
        setContentView(R.layout.start_main); //同上
        //延迟0.7秒后执行run方法中的页面跳转
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);//3秒后跳转至应用主界面MainActivity
    }


}