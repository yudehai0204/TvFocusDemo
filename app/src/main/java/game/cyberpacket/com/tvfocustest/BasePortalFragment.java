package game.cyberpacket.com.tvfocustest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;


import java.util.List;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/8 20:36
 * 描述 ： 首页port页面的基类
 */
public abstract class BasePortalFragment extends Fragment implements ViewTreeObserver.OnGlobalFocusChangeListener {
    protected String TAG = BasePortalFragment.class.getSimpleName();
    protected View view;
    protected Context mContext;
    protected List<String> mList;
    protected boolean isFirstResume ;
    protected int frag_type = -1;//1为排行榜

    protected  boolean isHidden;//是否为隐藏状态
    /***
     * img对应的id
     */
    protected int mImgIds [] = new int[]{R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,
            R.id.img6,R.id.img7,R.id.img8,R.id.img9,R.id.img10,
            R.id.img11,R.id.img12,R.id.img13,R.id.img14,R.id.img15,
            R.id.img16,R.id.img17,R.id.img18,R.id.img19,R.id.img20};
    /***
     * 最后焦点索引
     */
    protected int focus_index;
    protected RelativeLayout mRoot;
    protected FocusView mFocusView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public int getFocus_index() {
        return focus_index;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            frag_type = getArguments().getInt("type",-1);
        }catch (Exception e){

        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstResume = true;
        if(view == null)
            view = inflater.inflate(getLayoutId(),container,false);
        initView(view);
        if(frag_type != 1) {
            mFocusView = view.findViewById(R.id.mFocusView);
            mRoot = view.findViewById(R.id.mRoot);
            mRoot.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        }
//        LogService.e(TAG,"initView()");
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);


    public abstract void upDateUI();
    public abstract boolean isOnTop();
    public abstract boolean isOnRight();
    public abstract boolean isOnLeft();

    @Override
    public void onResume() {
        super.onResume();

        if(isFirstResume){
            isFirstResume = false;
            upDateUI();

        }

    }




    /***
     * 子类获取焦点  0 为第一个  -1 为页面最右边右下角的数据
     * @param index
     */
    public abstract void childRequestFocus(int index);

    /***
     * 恢复焦点
     */
    public abstract void recoverFocus();

    public void resetFocusIndex(){
//        LogService.i(TAG,"ResetFocusIndex");
        focus_index = 0;
    }

    /***
     * 设置页面数据
     * @param list
     */
    public void setList(List<String> list){
//        LogService.i(TAG,"设置数据");
        this.mList = list;
    }



    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        if(newFocus.getParent() == mRoot){
            mFocusView.setFocusView(newFocus);
            if(oldFocus != null && oldFocus.getParent() == mRoot && newFocus.getParent() == mRoot){
                mFocusView.setUnFocusView(oldFocus);
            }
        }else {
            if(oldFocus != null && oldFocus.getParent() == mRoot){
                mFocusView.setUnFocusView(oldFocus);
                mFocusView.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public void onDestroyView() {
        if(mRoot != null)
            mRoot.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
        super.onDestroyView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
