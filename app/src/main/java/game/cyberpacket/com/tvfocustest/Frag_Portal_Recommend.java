package game.cyberpacket.com.tvfocustest;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/9 10:40
 * 描述 ： 对应模板 id 00000000001  精品推荐
 */
public class Frag_Portal_Recommend extends BasePortalFragment  {

    private PortalView imgs[] = new PortalView[20];
    private int top[] = new int[]{0,1,2,3,4,5,6};
    private int right[] = new int[]{6,10,19};
    private int left[] = new int[]{0,7,11};
    private int last_view_index = 19;
    @Override
    protected int getLayoutId() {
        return R.layout.frag_portal_recommend;
    }

    @Override
    protected void initView(View view) {
//        LogService.i(TAG,"20个item Portal 初始化");
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
            List<String> list = new ArrayList<>();
            if(i == 1){
                list.add("");
                list.add("");
                list.add("");
            }
            imgs[i].loadData(list);
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
//        LogService.i(TAG,"20个Item页面某一个item对应的id未找到");
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
