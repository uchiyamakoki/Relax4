package relax.sn.com.relax4.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import relax.sn.com.relax4.R;
import relax.sn.com.relax4.adapter.DiaryAdapter;
import relax.sn.com.relax4.bean.DiaryBean;
import relax.sn.com.relax4.utils.DiaryDatabaseHelper;
import relax.sn.com.relax4.utils.GetDate;
import relax.sn.com.relax4.view.AddDiaryActivity;

/**
 * Created by John on 2018/4/3.
 */
public class ScreamFragment extends Fragment{
   /* @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;*/
    @Bind(R.id.main_iv_circle)
    ImageView mMainIvCircle;
    @Bind(R.id.main_tv_date)
    TextView mMainTvDate;
    @Bind(R.id.main_tv_content)
    TextView mMainTvContent;
    @Bind(R.id.item_ll_control)
    LinearLayout mItemLlControl;

    @Bind(R.id.main_rv_show_diary)
    RecyclerView mMainRvShowDiary;
    @Bind(R.id.main_fab_enter_edit)
    FloatingActionButton mMainFabEnterEdit;
    @Bind(R.id.main_rl_main)
    RelativeLayout mMainRlMain;
    @Bind(R.id.item_first)
    LinearLayout mItemFirst;
    @Bind(R.id.main_ll_main)
    LinearLayout mMainLlMain;
    private List<DiaryBean> mDiaryBeanList;

    private DiaryDatabaseHelper mHelper;

    private static String IS_WRITE = "true";

    private int mEditPosition = -1;

    private static final String DB_DIARY_NAME = "Diary.db";

    public static ScreamFragment newInstance() {
        return new ScreamFragment();
    }

    /**
     * 标识今天是否已经写了日记
     */
    private boolean isWrite = false;
    private static TextView mTvTest;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scream_main, container, false);
        ButterKnife.bind(this, view);
        mHelper = new DiaryDatabaseHelper(getActivity(), DB_DIARY_NAME, null, 1);
        getDiaryBeanList();
        initView();
        return view;
    }

    private void initView() {
        mMainTvDate.setText(GetDate.getDate());
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainRvShowDiary.setAdapter(new DiaryAdapter(getActivity(), mDiaryBeanList));
    }

    private void getDiaryBeanList() {
        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqliteDatabase.query("Diary", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                    break;
                }

            } while (cursor.moveToNext());
        }

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mDiaryBeanList.add(new DiaryBean(date, title, content, tag));

            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }
        mDiaryBeanList = diaryList;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDiaryBeanList();
        initView();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.main_fab_enter_edit)
    public void onViewClicked() {
        AddDiaryActivity.startActivity(getActivity());
    }
}
