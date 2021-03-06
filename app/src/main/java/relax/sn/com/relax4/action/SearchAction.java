package relax.sn.com.relax4.action;

import android.content.Intent;
import android.net.Uri;

import relax.sn.com.relax4.view.VoiceActivity;


public class SearchAction {
	VoiceActivity mActivity;
	String mKeyword;
	//String searchEngine;

	public SearchAction(String name, VoiceActivity activity)
	{
		mKeyword = name;
		mActivity=activity;
	}

	public void Search(){
		startWebSearch();
	}

	private void startWebSearch()
	{
		mActivity.speakAnswer("正在搜索："+mKeyword+"...");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://m.baidu.com/s?word="+mKeyword));
		intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");//设置为系统自带浏览器启动
		mActivity.startActivity(intent);
	}
}
