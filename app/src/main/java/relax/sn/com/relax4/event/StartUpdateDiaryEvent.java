package relax.sn.com.relax4.event;

import relax.sn.com.relax4.entity.DiaryBean;

/**
 * 打开「修改日记」的界面
 * Created by 李 on 2017/1/26.
 */
public class StartUpdateDiaryEvent {

   /* private int position;

    public StartUpdateDiaryEvent(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }*/
   private DiaryBean mDiaryBean;

    public StartUpdateDiaryEvent(DiaryBean diaryBean) {
        mDiaryBean = diaryBean;
    }

    public DiaryBean getDiaryBean() {
        return mDiaryBean;
    }

   /* private DiaryBean mDiaryBean;

    public StartUpdateDiaryEvent(DiaryBean diaryBean) {
        mDiaryBean = diaryBean;
    }

    public DiaryBean getDiaryBean() {
        return mDiaryBean;
    }*/
}