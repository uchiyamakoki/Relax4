package relax.sn.com.relax4.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.adapter.MainAdapter;
import relax.sn.com.relax4.view.DetailActivity;

public class MainContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_view,container,false);
        MainAdapter adapter = new MainAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

   /* public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView name;
        public TextView des;
        public ViewHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.item_main,parent,false));
            avatar = (ImageView)itemView.findViewById(R.id.card_image);
            name = (TextView)itemView.findViewById(R.id.card_title);
            des = (TextView)itemView.findViewById(R.id.card_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    intent.setClass(v.getContext(),DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }*/
   /* //RecyclerView.Adapter<RecyclerView.ViewHolder>和RecyclerView.Adapter<ViewHolder>参数注意，坑一
    public static class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final int LENGTH = 1;
        private String[] names;
        private String[] des;
        private Drawable[] avatars;

        private static final int TYPE_ONE = 0;//
        private static final int TYPE_TWO = 1;//
        private static final int TYPE_THREE = 2;//
        private static final int TYPE_FORE = 3;//

        public ContentAdapter(Context context) {
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
            if (position == ContentAdapter.TYPE_ONE) {
                return ContentAdapter.TYPE_ONE;
            }
            if (position == ContentAdapter.TYPE_TWO) {
                return ContentAdapter.TYPE_TWO;
            }
            if (position == ContentAdapter.TYPE_THREE) {
                return ContentAdapter.TYPE_THREE;
            }
            if (position == ContentAdapter.TYPE_FORE) {
                return ContentAdapter.TYPE_FORE;
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //holder.avatar.setImageDrawable(avatars[position % avatars.length]);
            //holder.name.setText(names[position % names.length]);
            //holder.des.setText(des[position % des.length]);
            int itemType = getItemViewType(position);
            switch (itemType) {
                case TYPE_ONE:
                   // ((NowWeatherViewHolder) holder).bind(mWeatherData);
                    ((ViewHolder)holder).avatar.setImageDrawable(avatars[position % avatars.length]);
                    ((ViewHolder)holder).name.setText(names[position % names.length]);
                    ((ViewHolder)holder).des.setText(des[position % des.length]);
                    break;
                case TYPE_TWO:
                   // ((HoursWeatherViewHolder) holder).bind(mWeatherData);
                    break;
                case TYPE_THREE:
                   // ((SuggestionViewHolder) holder).bind(mWeatherData);
                    break;
                case TYPE_FORE:
                    //((ForecastViewHolder) holder).bind(mWeatherData);
                    break;
                default:
                    break;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
            switch (viewType) {
                case TYPE_ONE:
                    return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
                case TYPE_TWO:
                    return null;
                case TYPE_THREE:
                    return null;
                case TYPE_FORE:
                    return null;
            }
            return null;
        }

    }*/
}