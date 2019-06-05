package game.cyberpacket.com.tvfocustest;

import android.support.v4.view.ViewPager;
import android.view.View;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/15 0015 20:35
 * 描述 ： 
 */
public class CoustomTransFormer implements ViewPager.PageTransformer {

    private float mMaxAlpha = 1.0f;

    @Override
    public void transformPage(final View page, final float position) {
        if (position < -1 || position >=1) {
            other(page, position);
        } else if (position < 1) {
            if (position > -1 && position < 0.0f) {
                touch2Left(page, position);
            } else if (position >= 0.0f && position < 1.0f) {
                // (0,1]
                touch2Right(page, position);
            }
        }
    }

    public void touch2Left(View view, float position) {
        //设置旋转中心点
        view.setPivotX(view.getMeasuredWidth()* 0.5f);
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        if (Math.abs(position)<mMaxAlpha){
            view.setAlpha(1-Math.abs(position));
        }else{
            view.setAlpha(mMaxAlpha);
        }


    }

    public void touch2Right(View view, float position) {
        //设置旋转中心点
        view.setPivotX(view.getMeasuredWidth()* 0.5f);
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setAlpha(1-position);
    }

    public void other(View view, float position) {

    }



}
