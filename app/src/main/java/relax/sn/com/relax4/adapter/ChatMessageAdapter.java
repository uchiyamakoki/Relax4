package relax.sn.com.relax4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextVew;


import java.util.List;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.constant.TulingParams;
import relax.sn.com.relax4.entity.MessageEntity;
import relax.sn.com.relax4.utils.SpecialViewUtil;
import relax.sn.com.relax4.utils.TimeUtil;

/**
 * Created by John on 2018/5/3.
 */
public class ChatMessageAdapter extends BaseListAdapter<MessageEntity> {

    private Context mContext;

    public static final int TYPE_LEFT = 0;//左边
    public static final int TYPE_RIGHT = 1;//右边

    public ChatMessageAdapter(Context context, List<MessageEntity> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        //判断是左边还是右边 type在Main中会设置，挺简单的
        if (getItem(position).getType()==TYPE_LEFT){
            return TYPE_LEFT;
        }
        return TYPE_RIGHT;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    //决定用哪个布局
    private View createViewByType(int position) {
        if (getItem(position).getType() == TYPE_LEFT) {
            return mInflater.inflate(R.layout.item_conversation_left, null);
        }
        return mInflater.inflate(R.layout.item_conversation_right, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=createViewByType(position);//返回一个view
        }

        final MessageEntity entity=getItem(position);//获得对应position的MessageEntity
        //通过用相同的id减少了代码
        TextView tvTime = ViewHolder.get(convertView, R.id.tv_time);//时间
        BubbleTextVew btvMessage = ViewHolder.get(convertView, R.id.btv_message);//文本框

        if (isDisplayTime(position)) {
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(TimeUtil.friendlyTime(mContext, entity.getTime()));
        } else {
            tvTime.setVisibility(View.GONE);
        }
        //处理不同的请求，具体在TulingCode里有介绍 然后设置文本
        switch (entity.getCode()) {
            case TulingParams.TulingCode.URL:
                btvMessage.setText(SpecialViewUtil.getSpannableString(entity.getText(), entity.getUrl()));
                break;
            case TulingParams.TulingCode.NEWS:
                btvMessage.setText(SpecialViewUtil.getSpannableString(entity.getText(), "点击查看"));
                break;
            default:
                btvMessage.setText(entity.getText());
                break;
        }
       //处理特殊情况的点击事件btvMessage.setOnClickListener

        //长按复制事件 btvMessage.setOnLongClickListener


        return convertView;//tag在自定义ViewHolder里已经完成
    }
    //  一分钟内的请求与回复不显示时间
    public boolean isDisplayTime(int position) {
        if (position > 0) {
            if ((getItem(position).getTime() - getItem(position-1).getTime()) > 60 * 1000) {
                return true; //大于一分钟
            } else {
                return false;
            }
        } else if (position == 0) {
            return true;//第一条记录
        } else {
            return false;
        }
    }



}
