package relax.sn.com.relax4.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.LongDef;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.entity.RecordingItem;
import relax.sn.com.relax4.event.RefreshViewEvent;
import relax.sn.com.relax4.fragment.PlaybackDialogFragment;
import relax.sn.com.relax4.utils.AppManager;
import relax.sn.com.relax4.utils.DiaryDatabaseHelper;
import relax.sn.com.relax4.utils.GetDate;
import relax.sn.com.relax4.utils.StatusBarCompat;
import relax.sn.com.relax4.widget.LinedEditText;



public class UpdateDiaryActivity extends AppCompatActivity {

    @Bind(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @Bind(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @Bind(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;
    @Bind(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @Bind(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @Bind(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
    @Bind(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @Bind(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;
    @Bind(R.id.update_diary_tv_tag)
    TextView mTvTag;

    @Bind(R.id.main_btn_play_sound)
    ShineButton mBtnPlayAudio;

    @Bind(R.id.update_layout)
    LinearLayout mUpdate;



    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);

        /*String tag=mTvTag.getText().toString();
        System.out.println(tag);
        if(tag.equals("sdafaf")){
           // mUpdate.removeView(mBtnPlayAudio);
            ViewGroup parent=(ViewGroup)mBtnPlayAudio.getParent();
            parent.removeView(mBtnPlayAudio);
        }*/

       /* SharedPreferences sharePreferences = getSharedPreferences("sp_name_audio", MODE_PRIVATE);
        String filePath = sharePreferences.getString("audio_name", "");
        if(filePath==null){
            mUpdate.removeView(mBtnPlayAudio);
        }*/

        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        initTitle();
        StatusBarCompat.compat(this, Color.parseColor("#161414"));


        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));
        String tag=mTvTag.getText().toString();
        System.out.println(tag);
        if(tag.equals("")||tag==null){
            // mUpdate.removeView(mBtnPlayAudio);
            ViewGroup parent=(ViewGroup)mBtnPlayAudio.getParent();
            parent.removeView(mBtnPlayAudio);
        }

        mBtnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordingItem recordingItem = new RecordingItem();
                //SharedPreferences sharePreferences = getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                //final String filePath = sharePreferences.getString("audio_path2", "");
                String filePath3= Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SoundRecorder/";
                //SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                String tag=mTvTag.getText().toString();
               /* System.out.println(tag);
                if(tag==null||tag==""){
                    return;
                }*/

                int index1=tag.indexOf("M");
                String r1=tag.substring(index1);
                String filePath2=filePath3+r1;
                //long elpased = sharePreferences.getLong("elpased", 0);
                //System.out.println(elpased);

                //String s="My6998";
                //int index=s.indexOf("y");
                //String r=s.substring(index+1);
                //long l=Long.valueOf(r).longValue();
                //long elpased=l;
                String r2=tag.substring(0,index1);
                long l= Long.valueOf(r2).longValue();
                long elpased=l;

                recordingItem.setFilePath(filePath2);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });

    }

    private void initTitle() {
       // ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        mCommonTvTitle.setText("修改日记");
    }

    @OnClick({R.id.common_iv_back, R.id.update_diary_tv_date, R.id.update_diary_et_title, R.id.update_diary_et_content, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                //ScreamActivity.startActivity(this);
                finish();
            case R.id.update_diary_tv_date:
                break;
            case R.id.update_diary_et_title:
                break;
            case R.id.update_diary_et_content:
                break;
            case R.id.update_diary_fab_back:
                /*android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        String title = mUpdateDiaryEtTitle.getText().toString();
                        String tag = mTvTag.getText().toString();
                        SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                        dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                        ScreamActivity.startActivity(UpdateDiaryActivity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();*/
                showTips();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                //ScreamActivity.startActivity(this);
                finish();
                break;
            case R.id.update_diary_fab_delete:
                //ScreamActivity.startActivity(this);
                finish();
                break;
        }
    }

    private void showTips() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tag = mTvTag.getText().toString();
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                finish();
                MainActivity.startActivity(UpdateDiaryActivity.this);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
        EventBus.getDefault().post(new RefreshViewEvent());
    }

    @OnClick(R.id.common_tv_title)
    public void onClick() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ScreamActivity.startActivity(this);
        finish();
    }
}