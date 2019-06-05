package game.cyberpacket.com.tvfocustest;

import android.view.View;


import java.util.ArrayList;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/10 10:05
 * 描述 ： 对应portalid: 00000004 游戏分类
 */
public class Frag_Portal_Classify extends BasePortalFragment {
    private PortalView imgs[] = new PortalView[4];
    private int top[] = new int[]{0,1,2,3};
    private int right[] = new int[]{3};
    private int left[] = new int[]{0};
    private int last_view_index = 3;
    @Override
    protected int getLayoutId() {
        return R.layout.frag_portal_classify;
    }

    @Override
    protected void initView(View view) {
//        LogService.i(TAG,"00000004 Portal 初始化");
        for (int i = 0 ; i < imgs.length; i++){
            imgs[i] = view.findViewById(mImgIds[i]);
            imgs[i].setOnClickListener(onClickListener);
            imgs[i].setOnFocusChangeListener(focusChangeListener);
        }
    }

    @Override
    public void upDateUI() {
        int size = 0;
        if(mList != null)
            size = mList.size();
        if(size > imgs.length)
            size = imgs.length;
        for(int i = 0 ; i < size;i++){
//            PortalItemBean bean = mList.get(i);
//            LogService.i(TAG,"更新UI:"+bean.getNormalImg());
            imgs[i].loadData(new ArrayList<String>());
//            ImageUtils.loadImage(mContext,bean.getNormalImg(),imgs[i]);
        }
    }


    @Override
    public boolean isOnTop() {
        for (int i : top){
            if(imgs[i].isFocused())
                return true;
        }
        return false;
    }

    @Override
    public boolean isOnRight() {
        for (int i : right){
            if(imgs[i].isFocused())
                return true;
        }
        return false;
    }

    @Override
    public boolean isOnLeft() {
        for (int i : left){
            if(imgs[i].isFocused())
                return true;
        }
        return false;
    }

    @Override
    public void childRequestFocus(int index) {
        if(index == -1){
            imgs[last_view_index].requestFocus();
        }else
            imgs[index].requestFocus();

    }

    @Override
    public void recoverFocus() {
        imgs[focus_index].requestFocus();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = getIdIndex(v.getId());
            if(i == -1){
//                ToastUtils.showToast("数据异常");
                return;
            }
//            IntentUtils.fromHome2Other(mActivity,mList.get(i),imgs[i].getViewPagerPosition());
        }
    };

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus)
                return;
            focus_index = getIdIndex(v.getId());


        }
    };



    private int getIdIndex(int id){
        for (int i = 0 ; i < imgs.length; i++){
            if(id == imgs[i].getId()){
                return i;
            }
        }
//        LogService.i(TAG,"00000004 Portal 某一个item对应的id未找到");
        return -1;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        LogService.i(TAG,"状态改变:"+hidden+ this);
        isHidden = hidden;
        try {
            if(mList != null && !isFirstResume){
                for (int i = 0; i< imgs.length ; i++){
                    imgs[i].updateParentState(hidden);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
//            LogService.e(TAG,e.getMessage());
        }
    }
}
