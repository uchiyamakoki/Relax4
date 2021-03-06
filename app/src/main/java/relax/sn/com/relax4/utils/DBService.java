package relax.sn.com.relax4.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import relax.sn.com.relax4.entity.Question;

/**
 * Created by John on 2018/3/10.
 */
public class DBService {
    private SQLiteDatabase db;
    public DBService(){
        db=SQLiteDatabase.openDatabase("/data/data/relax.sn.com.relax4/databases/question.db",null,SQLiteDatabase.OPEN_READWRITE);
    }
    public List<Question> getQuestion(){
        List<Question> list=new ArrayList<Question>();//用来保存结果
        Cursor cursor=db.rawQuery("select * from question",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int  count=cursor.getCount();
            for(int i=0;i<count;i++){
                cursor.moveToPosition(i);
                Question question=new Question();
                question.question=cursor.getString(cursor.getColumnIndex("question"));
                question.answerA=cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB=cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC=cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD=cursor.getString(cursor.getColumnIndex("answerD"));
                question.answer=cursor.getInt(cursor.getColumnIndex("answer"));
                question.ID=cursor.getInt(cursor.getColumnIndex("ID"));
                question.explaination=cursor.getString(cursor.getColumnIndex("explaination"));
                question.selectedAnswer=-1;
                list.add(question);
            }
        }
        return list;
    }
}
