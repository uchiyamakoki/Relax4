package relax.sn.com.relax4.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.view.CeshiActivity;
import relax.sn.com.relax4.view.DetailActivity;
import relax.sn.com.relax4.view.DoodleViewActivity;
import relax.sn.com.relax4.view.ExamActivity;
import relax.sn.com.relax4.view.PingTuActivity;
import relax.sn.com.relax4.view.TestActivity;
import relax.sn.com.relax4.view.TulingActivity;

/**
 * Created by John on 2018/3/30.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int LENGTH = 5;//忘了这货了。
    private String[] names;
    private String[] des;
    private Drawable[] avatars;

    private static final int TYPE_ONE = 0;//
    private static final int TYPE_TWO = 1;//
    private static final int TYPE_THREE = 2;//
    private static final int TYPE_FORE = 3;//
    private static final int TYPE_FIVE = 4;//

    public MainAdapter(Context context) {
        Resources resources = context.getResources();
        names = resources.getStringArray(R.array.questions);
        des = resources.getStringArray(R.array.questions_desc);
        TypedArray a = resources.obtainTypedArray(R.array.questions_avator);
        avatars = new Drawable[a.length()];
        for(int i = 0;i<a.length();i++){
            avatars[i] = a.getDrawable(i);
        }
        a.recycle();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == MainAdapter.TYPE_ONE) {
            return MainAdapter.TYPE_ONE;
        }
        if (position == MainAdapter.TYPE_TWO) {
            return MainAdapter.TYPE_TWO;
        }
        if (position == MainAdapter.TYPE_THREE) {
            return MainAdapter.TYPE_THREE;
        }
        if (position == MainAdapter.TYPE_FORE) {
            return MainAdapter.TYPE_FORE;
        }
        if (position == MainAdapter.TYPE_FIVE) {
            return MainAdapter.TYPE_FIVE;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        switch (viewType) {
            case TYPE_ONE:
                return new ViewHolder2(LayoutInflater.from(parent.getContext()),parent);
            case TYPE_TWO:
                return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
            case TYPE_THREE:
                return new ViewHolder3(LayoutInflater.from(parent.getContext()),parent);
            case TYPE_FORE:
                return new ViewHolder4(LayoutInflater.from(parent.getContext()),parent);
            case TYPE_FIVE:
                return new ViewHolder5(LayoutInflater.from(parent.getContext()),parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.avatar.setImageDrawable(avatars[position % avatars.length]);
        //holder.name.setText(names[position % names.length]);
        //holder.des.setText(des[position % des.length]);
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                // ((NowWeatherViewHolder) holder).bind(mWeatherData);
                ((ViewHolder2)holder).weatherIcon.setImageDrawable(avatars[position % avatars.length]);
                ((ViewHolder2)holder).tempFlu.setText(names[position % names.length]);
               // ((ViewHolder2)holder).des.setText(des[position % des.length]);
                break;
            case TYPE_TWO:
                // ((HoursWeatherViewHolder) holder).bind(mWeatherData);
                ((ViewHolder)holder).avatar.setImageDrawable(avatars[position % avatars.length]);
                ((ViewHolder)holder).name.setText(names[position % names.length]);
                //((ViewHolder)holder).des.setText(des[position % des.length]);
                break;
            case TYPE_THREE:
                // ((SuggestionViewHolder) holder).bind(mWeatherData);
                ((ViewHolder3)holder).weatherIcon.setImageDrawable(avatars[position % avatars.length]);
                ((ViewHolder3)holder).tempFlu.setText(names[position % names.length]);
                break;
            case TYPE_FORE:
                //((ForecastViewHolder) holder).bind(mWeatherData);
                ((ViewHolder4)holder).weatherIcon.setImageDrawable(avatars[position % avatars.length]);
                ((ViewHolder4)holder).tempFlu.setText(names[position % names.length]);
                break;
            case TYPE_FIVE:
                //((ForecastViewHolder) holder).bind(mWeatherData);
                ((ViewHolder5)holder).weatherIcon.setImageDrawable(avatars[position % avatars.length]);
                ((ViewHolder5)holder).tempFlu.setText(names[position % names.length]);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return LENGTH;
    }
    /*
    问题的ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView name;
       // public TextView des;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_temperature,parent,false));
            avatar = (ImageView)itemView.findViewById(R.id.weather_icon);
            name = (TextView)itemView.findViewById(R.id.temp_flu);
           // des = (TextView)itemView.findViewById(R.id.card_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   // intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(), TulingActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(), ExamActivity.class);
                    v.getContext().startActivity(intent);
                }
            });*/
        }
    }
    /*
    问题的ViewHolder2
     */
    public static class ViewHolder2 extends RecyclerView.ViewHolder{
        public ImageView weatherIcon;
        public TextView tempFlu;
        //public TextView des;
        public ViewHolder2(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_temperature,parent,false));
            weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView)itemView.findViewById(R.id.temp_flu);
           // des = (TextView)itemView.findViewById(R.id.card_text);

       /*     itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),ExamActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    /*
  问题的ViewHolder2
   */
    public static class ViewHolder3 extends RecyclerView.ViewHolder{
        public ImageView weatherIcon;
        public TextView tempFlu;
        //public TextView des;
        public ViewHolder3(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_temperature,parent,false));
            weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView)itemView.findViewById(R.id.temp_flu);
            // des = (TextView)itemView.findViewById(R.id.card_text);

       /*     itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DoodleViewActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    /*
 问题的ViewHolder2魔术
  */
    public static class ViewHolder4 extends RecyclerView.ViewHolder{
        public ImageView weatherIcon;
        public TextView tempFlu;
        //public TextView des;
        public ViewHolder4(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_temperature,parent,false));
            weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView)itemView.findViewById(R.id.temp_flu);
            // des = (TextView)itemView.findViewById(R.id.card_text);

       /*     itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),PingTuActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    /*
问题的ViewHolder2魔术
*/
    public static class ViewHolder5 extends RecyclerView.ViewHolder{
        public ImageView weatherIcon;
        public TextView tempFlu;
        //public TextView des;
        public ViewHolder5(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_temperature,parent,false));
            weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView)itemView.findViewById(R.id.temp_flu);
            // des = (TextView)itemView.findViewById(R.id.card_text);

       /*     itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),CeshiActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
