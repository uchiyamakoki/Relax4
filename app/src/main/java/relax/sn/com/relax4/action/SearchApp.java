package relax.sn.com.relax4.action;

import android.content.Intent;
import android.net.Uri;

import relax.sn.com.relax4.view.VoiceActivity;


public class SearchApp {
	private String mName;
	VoiceActivity mActivity;

	public SearchApp(String name, VoiceActivity activity){
		mName=name;
		mActivity=activity;
	}

	public void start(){
		mActivity.speakAnswer("正在搜索...");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q="+mName));
		mActivity.startActivity(intent);
	}
}
