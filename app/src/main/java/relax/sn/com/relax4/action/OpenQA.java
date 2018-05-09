package relax.sn.com.relax4.action;


import relax.sn.com.relax4.view.VoiceActivity;

public class OpenQA {

	private String mText;
	VoiceActivity mActivity;
	
	public OpenQA(String text, VoiceActivity activity){
		mText=text;
		mActivity=activity;
	}
	
	public void start(){
		mActivity.speakAnswer(mText);
	}
	
}
