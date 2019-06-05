package game.cyberpacket.com.tvfocustest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/9 15:44
 * 描述 ： portal item 动画类
 */
public class FocusView extends View {
    private String TAG = FocusView.class.getSimpleName();
    private View mFocusView;
    private View mUnFocusView;
    private int focus_border_width;//扩容
    /***
     * 动画 比例
     */
    private final float SCALE_END = 1.05F;
    private final float SCALE_START = 1.0F;
    /***
     * 动画时间
     */
    private final int DEFAULT_DURATION = 200;
    ObjectAnimator anim = ObjectAnimator.ofFloat(this, "ScaleUp",
            SCALE_START, SCALE_END );
    ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "ScaleDown",
            new float[] { SCALE_END, SCALE_START }).setDuration(DEFAULT_DURATION);



    public FocusView(Context context) {
        super(context);
        init(context);
    }

    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context){
        focus_border_width = DisPlayUtils.dip2px(context,4);
        setFocusable(false);
        setBackgroundResource(R.drawable.shape_border);

    }






    public void setFocusView(View view){
//        LogService.i(TAG,"触发新焦点");
        mFocusView = view;
        mFocusView.bringToFront();
        bringToFront();
        runFocusAnim(mFocusView);
    }



    public void setUnFocusView(View view){
            anim1.end();
            mUnFocusView = view;
            anim1.start();
    }


    public void clear(){
        mFocusView = null;
        mUnFocusView = null;
    }

    public void runFocusAnim(View toView) {
        int width = (int) (toView.getWidth() * SCALE_END);
        int height = (int) (toView.getHeight() * SCALE_END);
        int deltaX = (int) ((toView.getWidth() * Math.abs(SCALE_END - 1)) / 2.0f);
        int deltaY = (int) ((toView.getHeight() * Math.abs(SCALE_END - 1)) / 2.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(ObjectAnimator.ofFloat(FocusView.this, "translationX", toView.getLeft()-getLeft()-deltaX-focus_border_width/2))
                .with(ObjectAnimator.ofFloat(FocusView.this, "translationY", toView.getTop()-getTop()-deltaY-focus_border_width/2))
                .with(ObjectAnimator.ofInt(FocusView.this, "TrueWidth", getWidth(), width+focus_border_width))
                .with(ObjectAnimator.ofInt(FocusView.this, "TrueHeight",getHeight(), height+focus_border_width))
                .with(anim);
        animSet.addListener(animListener);
        animSet.setDuration(DEFAULT_DURATION).start();
    }


    public void setScaleUp(float scale){
        if(mFocusView != null){
            mFocusView.setScaleX(scale);
            mFocusView.setScaleY(scale);
        }
    }


    public void setScaleDown(float scale){
        if(mUnFocusView != null){
            mUnFocusView.setScaleX(scale);
            mUnFocusView.setScaleY(scale);
        }
    }


    public void setTrueWidth(int width){
        getLayoutParams().width = width;
        setLayoutParams(getLayoutParams());
    }

    public int getTrueWidth(){
        return getLayoutParams().width;
    }
    public void setTrueHeight(int height){
        getLayoutParams().height = height;
        setLayoutParams(getLayoutParams());
    }

    public int getTrueHeight(){
        return getLayoutParams().height;
    }
    private Animator.AnimatorListener animListener = new Animator.AnimatorListener() {

        public void onAnimationCancel(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            FocusView.this.setVisibility(View.VISIBLE);
        }

        public void onAnimationRepeat(Animator arg0) {

        }

        public void onAnimationStart(Animator arg0) {
        }
    };

}
