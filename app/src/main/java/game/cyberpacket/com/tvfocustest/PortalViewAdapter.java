package game.cyberpacket.com.tvfocustest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/6/3 0003 11:11
 * 描述 ： 
 */
public class PortalViewAdapter extends PagerAdapter {
    private List<String> mListImgUrl;
    private Context mContext;
    public PortalViewAdapter(List<String> mListImgUrl, Context context) {
        this.mListImgUrl = mListImgUrl;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mListImgUrl == null)
            return 0;
        else
            return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int index = position % mListImgUrl.size();
        ImageView img = new ImageView(mContext);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.img_default);
//        ImageUtils.loadImage(mContext, mListImgUrl.get(index), img);
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
