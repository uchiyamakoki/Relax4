package relax.sn.com.relax4.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import butterknife.Bind;
import relax.sn.com.relax4.R;

/**
 * 录音的 Service
 *
 * Created by developerHaoz on 2017/8/12.
 */

public class RecordingService extends Service {

    private static final String LOG_TAG = "RecordingService";

    private String mFileName = null;//名称
    private String mFilePath = null;//路径
    private String mFilePath2 = null;//路径

    private MediaRecorder mRecorder = null;//基于文件录音

    private long mStartingTimeMillis = 0;//开始录音时间
    private long  mElapsedMillis = 0;//录音时长
    private TimerTask mIncrementTimerTask = null;

   /* @Bind(R.id.add_diary_tv_tag)
    TextView mTvTag2;*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null) {
            stopRecording();
        }
        super.onDestroy();
    }
    // 开始录音
    public void startRecording() {
        setFileNameAndPath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//录音文件保存的格式，这里保存为 mp4
        mRecorder.setOutputFile(mFilePath);// 设置录音文件的保存路径
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        mRecorder.setAudioSamplingRate(44100);// 设置录音文件的清晰度
        mRecorder.setAudioEncodingBitRate(192000);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();//录音开始时间
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    // 设置录音文件的名字和保存路径
    public void setFileNameAndPath() {
        int count = 0;
        File f;

        do {
            count++;
            mFileName = getString(R.string.default_file_name)
                    + "_" + (System.currentTimeMillis()) + ".mp4";//默认+当前计算机时间
            //mTvTag2.setText(mFileName);

            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();//创建目录
            mFilePath2=mFilePath+ "/SoundRecorder/";
            mFilePath += "/SoundRecorder/" + mFileName;
            f = new File(mFilePath);
        } while (f.exists() && !f.isDirectory());
    }
    // 停止录音
    public void stopRecording() {
        mRecorder.stop();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.release();

        String mElapsedMillis2=String.valueOf(mElapsedMillis).toString();
        mFileName=mElapsedMillis2+mFileName;

        getSharedPreferences("sp_name_audio", MODE_PRIVATE)
                .edit()
                .putString("audio_path", mFilePath)
                .putString("audio_name",mFileName)
                .putString("audio_path2",mFilePath2)
                .putLong("elpased", mElapsedMillis)
                .apply();
        if (mIncrementTimerTask != null) {
            mIncrementTimerTask.cancel();
            mIncrementTimerTask = null;
        }

        mRecorder = null;
    }

}
