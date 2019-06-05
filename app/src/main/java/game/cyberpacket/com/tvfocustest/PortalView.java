package game.cyberpacket.com.tvfocustest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;


import java.lang.reflect.Field;
import java.util.List;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/6/3 0003 10:13
 * 描述 ： 自定义PortalView
 */
public class PortalView extends FrameLayout {
    private String TAG  = PortalView.class.getSimpleName();
    private ImageView img_view;
    private ViewPager mViewPager;
    private boolean isStop;
    private int img_position;
    private boolean isVisible;
    private List<String> strs;
    private LinearLayout ll_dots;
    public PortalView(Context context) {
        super(context);
        initView(context);
    }

    public PortalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PortalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        setFocusable(true);
        img_view  = new ImageView(context);
        img_view.setFocusable(false);
        img_view.setScaleType(ImageView.ScaleType.FIT_XY);
        img_view.setImageResource(R.drawable.img_default);
        mViewPager = new ViewPager(context);
        mViewPager.setFocusable(false);
        mViewPager.setPageTransformer(false,new CoustomTransFormer());
        addView(img_view);
        addView(mViewPager);
        FrameLayout.LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        mViewPager.setLayoutParams(layoutParams);
        layoutParams = (LayoutParams) img_view.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        img_view.setLayoutParams(layoutParams);
        isStop = true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }


    public void loadData(List<String> str){
        strs = str;
        if(str == null || str.size() <= 1){
            img_view.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            if(str.size() == 1){
//                ImageUtils.loadImage(getContext(),str.get(0),DiskCacheStrategy.ALL,img_view);
                img_view.setImageResource(R.drawable.img_default);
            }
        }else {
//            LogService.i(TAG,"PortalView 为ViewPager类型:"+str);
            initDots();
            setViewPagerScroller();
            img_view.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            mViewPager.setAdapter(new PortalViewAdapter(str,getContext()));
            new Thread(runnable).start();
        }
    }



    /***
     * 初始化小点点
     */
    private void updateDots(int index){
        for (int i = 0 ; i < ll_dots.getChildCount(); i ++){
            ll_dots.getChildAt(i).setBackgroundResource(R.drawable.shape_dot_normal);
        }
        ll_dots.getChildAt(index).setBackgroundResource(R.drawable.shape_dot_focus);
    }

    private void initDots(){
        int dip_15 = DisPlayUtils.dip2px(getContext(),15);
        ll_dots = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.include_dots,this,false);
        addView(ll_dots);
        FrameLayout.LayoutParams params = (LayoutParams) ll_dots.getLayoutParams();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = dip_15;
        params.bottomMargin = dip_15/3;
        ll_dots.setLayoutParams(params);
//        LogService.i(TAG, "初始化轮播图对应的点:"+strs.size()+"个");
        for (int i = 0 ; i < strs.size(); i++){
            View view = new View(getContext());
            if(i == 0)
                view.setBackgroundResource(R.drawable.shape_dot_focus);
            else
                view.setBackgroundResource(R.drawable.shape_dot_normal);
            ll_dots.addView(view);
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) view.getLayoutParams();
            params1.width = dip_15;
            params1.height = dip_15;
            params1.rightMargin = dip_15/2;
            view.setLayoutParams(params1);
        }
    }

    public int getViewPagerPosition() {
        if (strs == null)
            return 0;
        return img_position % strs.size();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == View.VISIBLE){
            isVisible = true;
        }else {
            isVisible = false;
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (!AppConfig.isExitApp) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isVisible){
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img_position++;
//                            LogService.i(TAG, "当前图片索引：" + img_position);
                            if(mViewPager == null)
                                return;
                            mViewPager.setCurrentItem(img_position);
                            updateDots(img_position%strs.size());
                        }
                    });
                }

            }

        }
    };


    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * 6);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }


    public void updateParentState(boolean hidden) {
        isStop = hidden;
    }
}
