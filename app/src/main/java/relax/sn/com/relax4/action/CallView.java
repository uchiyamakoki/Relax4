package relax.sn.com.relax4.action;

import android.content.Intent;

import relax.sn.com.relax4.view.VoiceActivity;


public class CallView {

    private VoiceActivity mActivity;

    public CallView(VoiceActivity activity) {
        mActivity = activity;
    }

    public void start() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL_BUTTON);
        mActivity.startActivity(intent);
    }
}
