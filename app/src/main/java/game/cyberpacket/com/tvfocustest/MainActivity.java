package game.cyberpacket.com.tvfocustest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_title;
    private BasePortalFragment frags [];
    private FragmentManager fragmentManager;
    private int now_title_postion = -1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AppConstants.RESET_KEYABLE:
                    isKeyAble = true;
                    break;
            }
        }
    };
    private View view_focus;
    private boolean isKeyAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConfig.isExitApp = false;
        ll_title = findViewById(R.id.ll_title);
        view_focus = findViewById(R.id.view_focus);
        isKeyAble = true;
        fragmentManager = getFragmentManager();
        MainUtils.init(this);
        initData();
    }




    private void initData() {
        ll_title.removeAllViews();
        frags = new BasePortalFragment[4];
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0;i < 4; i++){
            Button btn = (Button) (LayoutInflater.from(this).inflate(R.layout.item_home_title,ll_title,false));
            if(i == 0)
                btn.setTextColor(Color.WHITE);
            btn.setId(AppConstants.HOME_TITLE_ID_START+i);
            btn.setOnFocusChangeListener(titleFocusChangeListener);
            btn.setText("Title"+i);
            ll_title.addView(btn);
//            LogService.i(TAG,"title id:"+btn.getId());
            /*解析html页面形式字符串，从td标签里找到td号，从img标签里找到图片下载地址*/

            frags [i] = MainUtils.getFragment(i);
            List<String>  list = MainUtils.getList(i);
            if(frags[i] != null){
                frags [i].setList(list);
                transaction.add(R.id.mContainer,frags[i]);
                transaction.hide(frags[i]);
            }
        }
        transaction.commitAllowingStateLoss();
        setTab(0);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frags[0].childRequestFocus(0);
            }
        },500);

    }


    private View.OnFocusChangeListener titleFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            for (int i = 0 ;i < ll_title.getChildCount(); i++){
                if(v.getId() == ll_title.getChildAt(i).getId()){
                    if(hasFocus){
                        setTab(i);
                        ((Button)v).setTextColor(Color.WHITE);
                    }else {
                        if(i != now_title_postion)
                            ((Button)v).setTextColor(getResources().getColor(R.color.title_grey));
                    }
                    break;
                }
            }
        }
    };

    /***
     * 选中的栏目
     * @param index
     */
    private void setTab(int index){
        if(frags[index] == null || now_title_postion == index)
            return;
        MainUtils.resetTitle(ll_title);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainUtils.resetFrag(frags,fragmentTransaction);
        fragmentTransaction.show(frags[index]);
        fragmentTransaction.commitAllowingStateLoss();
        now_title_postion = index;
        ((Button)ll_title.getChildAt(index)).setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void onDestroy() {
        AppConfig.isExitApp = true;
        MainUtils.destory();
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(view_focus.isFocused())
                return true;

            if(event.getRepeatCount() > 3 ){//重复3次 开启按键延时
                if(!isKeyAble){//不响应按键
                    return true;
                }else {//响应按键
                    isKeyAble = false;
                    mHandler.sendEmptyMessageDelayed(AppConstants.RESET_KEYABLE,AppConstants.KEYDOWN_DELAY);
                }
            }

            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_DPAD_LEFT://向左

                    if(ll_title.getChildAt(0).isFocused())
                        return true;

                    if(frags[now_title_postion].isOnLeft()){
                        if(now_title_postion != 0){
                            view_focus.requestFocus();
//                            ActionService.getInstance()
//                                    .UploadKeyBehavior(ActionService.PG_NAME_PORTAL,"左移栏目",ActionService.PG_NAME_PORTAL
//                                            ,"&pg_columnname="+MainUtils.getTitleName(ll_title,now_title_postion)
//                                                    +"&tp_columnname="+MainUtils.getTitleName(ll_title,now_title_postion-1));
                            setTab(now_title_postion - 1);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    frags[now_title_postion].childRequestFocus(-1);
                                }
                            },100);
                        }
                        return true;
                    }

                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                    if(ll_title.getChildCount() > 0 && ll_title.getChildAt(ll_title.getChildCount()-1).isFocused())
                        return true;

                    if(frags[now_title_postion].isOnRight()) {
                        if(now_title_postion < ll_title.getChildCount() - 1){
                            view_focus.requestFocus();
//                            ActionService.getInstance()
//                                    .UploadKeyBehavior(ActionService.PG_NAME_PORTAL,"右移栏目",ActionService.PG_NAME_PORTAL
//                                            ,"&pg_columnname="+MainUtils.getTitleName(ll_title,now_title_postion)
//                                                    +"&tp_columnname="+MainUtils.getTitleName(ll_title,now_title_postion+1));
                            setTab(now_title_postion + 1);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    frags[now_title_postion].childRequestFocus(0);
                                }
                            },100);
                        }
                        return true;
                    }

                    break;

            }
        }

        return super.dispatchKeyEvent(event);
    }
}
