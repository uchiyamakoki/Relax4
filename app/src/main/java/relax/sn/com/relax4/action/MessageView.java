package relax.sn.com.relax4.action;

import android.content.Intent;

import relax.sn.com.relax4.view.VoiceActivity;


public class MessageView {
	private VoiceActivity mActivity;
	
	public MessageView(VoiceActivity activity){
		mActivity=activity;
	}
	
	public void start(){
		Intent intent=new Intent();
		intent.setClassName("com.android.mms","com.android.mms.ui.ConversationList");
		mActivity.startActivity(intent);
	}
}
