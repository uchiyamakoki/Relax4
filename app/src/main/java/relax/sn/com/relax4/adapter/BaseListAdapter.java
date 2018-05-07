package relax.sn.com.relax4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<E> extends BaseAdapter {

    private List<E> mList = new ArrayList<E>();
    protected Context mContext;
    protected LayoutInflater mInflater; //压栈item布局

    public BaseListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public BaseListAdapter(Context context, List<E> list) {
        this(context);
        mList = list;
    }
    //返回list的长度
    @Override
    public int getCount() {
        return mList.size();
    }
    //清空list
    public void clearAll() {
        mList.clear();
    }
    //设置数据，先清空再add
    public void setData(List<E> list) {
        clearAll();
        addALL(list);
    }

    public List<E> getData() {
        return mList;
    }
    //addALL方法
    public void addALL(List<E> list){
        if(list == null || list.size() == 0){
            return ;
        }
        mList.addAll(list);
    }
    public void add(E item){
        mList.add(item);
    }
    //返回位置
    @Override
    public E getItem(int position) {
        return (E) mList.get(position); //获得对应position index的list数据 Object
    }
    //直接返回位置
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeEntity(E e){
        mList.remove(e);
    }

}
