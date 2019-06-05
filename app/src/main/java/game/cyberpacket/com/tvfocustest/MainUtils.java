package game.cyberpacket.com.tvfocustest;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/***
 * 作者 ： 于德海
 * 时间 ： 2019/5/7 18:29
 * 描述 ： MainActivity对应的Utils类
 */
public class MainUtils {
    private static String TAG = "MainActivity";
    private static Context mContext;
    private static View img_msg_tip;
    private static boolean hasNewMsg;
    public static void init(Context context){
        mContext = context;
    }



    /***
     * 重置Title状态
     * @param ll_title
     */
    public static void resetTitle(LinearLayout ll_title) {
//        LogService.i("MainActivity","重置title状态");
        if(ll_title == null)
            return;
        for (int i = 0 ; i < ll_title.getChildCount();i++){
            ((Button)ll_title.getChildAt(i)).setTextColor(mContext.getResources().getColor(R.color.title_grey));
        }
    }

    /***
     *
     * @param frags
     * @param
     */
    public static void resetFrag(BasePortalFragment[] frags, FragmentTransaction fragmentTransaction) {
//        LogService.i("MainActivity","重置Fragment状态");
        for (int i = 0 ;i < frags.length; i++){
            if(frags[i] == null)
                continue;
            fragmentTransaction.hide(frags[i]);
        }
    }







    public static String getTitleName(LinearLayout ll_title, int index){
        if(ll_title == null || ll_title.getChildCount() == 0 || ll_title.getChildCount() <= index){
            return "Null";
        }else
            return ((TextView)ll_title.getChildAt(index)).getText().toString();

    }


    /***
     * 顶部快速启动栏是否拥有焦点
     * @param ll_quick
     * @return
     */
    public static boolean isFocusinMenu(LinearLayout ll_quick) {
        return isFocusInTitle(ll_quick);
    }
    /***
     * 导航是否拥有焦点
     * @param ll_title
     * @return
     */
    public static boolean isFocusInTitle(LinearLayout ll_title) {
        int count = ll_title.getChildCount();
        for (int i = 0;i < count;i++){
            if(ll_title.getChildAt(i).isFocused())
                return true;
        }
        return false;
    }

//    /***
//     * 用户行为打印
//     * @param titleName
//     */
//    public static void uploadUserBehavior(String titleName, int index, int director) {
//
//        ActionService.getInstance().UploadKeyBehavior(ActionService.PG_NAME_PORTAL,
//                "移动磁铁","","&pg_columnname="+titleName+"&td=td"+index+"&director="+director);
//    }

    /***
     *
     * @
     *   示例：
     *
     *  <td id="td20" style="width: 310px; height: 150px;" width="100px" height="100px" data-left="td19" data-right="td21" data-up="td2" data-type="100225" data-url="/cloud/detial" data-styletype="1" data-param="70000572" data-specialtype="" data-href="http://10.0.2.138:11011/activity/gamelistraffle" data-tilesshowtype="1" data-slidetime="3"> \n
     *
     *     <div class="tile pt-perspective" style="margin: 5px; width: inherit; height: inherit;">\n
     *
     *         <div class="pt-page-tileitem pt-page-current" style="width: inherit; height: inherit;" data-type="100201" data-url="/cloud/detial" data-styletype="1" data-param="70000572" data-specialtype="" data-tilesshowtype="1"> \n
     *
     *             <img src="/Images/invis.gif" data-original="/ResourceImage/game/1280x720/tiles/310x150花样三国(2)_20190110150844267.png?_v=1556087483970" class="lazy" data-src="http://192.168.17.187:21700/ResourceImage/game/1280x720/tiles/310x150花样三国(2)_20190110150844267.png?_v=1556087483970" /> \n
     *
     *         </div>\n
     *
     *         <div class="pt-page-tileitem" style="width: inherit; height: inherit;" data-type="100209" data-url="/cloud/anthology" data-param="100209" data-specialtype="" data-tilesshowtype="1"> \n
     *
     *             <img src="/Images/invis.gif" data-original="/ResourceImage/game/1280x720/tiles/310x150_02小猪闯迷宫_20190110150839680.png?_v=1558689694869" class="lazy" data-src="http://192.168.17.187:21700/ResourceImage/game/1280x720/tiles/310x150_02小猪闯迷宫_20190110150839680.png?_v=1558689694869" /> \n
     *
     *         </div>\n
     *
     *         <div class="pt-page-tileitem" style="width: inherit; height: inherit;" data-type="100201" data-url="/cloud/detial" data-styletype="1" data-param="70001367" data-specialtype="" data-tilesshowtype="1"> \n
     *
     *             <img src="/Images/invis.gif" data-original="/ResourceImage/game/1280x720/tiles/310x150-橙摩尔卡丁车_20180308175715904.png?_v=1558689763576" class="lazy" data-src="http://192.168.17.187:21700/ResourceImage/game/1280x720/tiles/310x150-橙摩尔卡丁车_20180308175715904.png?_v=1558689763576" /> \n
     *
     *         </div>\n
     *
     *     </div>
     * </td>
     *
     *
     * @return
//     */
//    public static List<PortalItemBean> parseHtml(String tilesInfo, String nav_name) {
//        Document html = Jsoup.parse(tilesInfo);
//        Elements tds = html.select("td");
//        List<PortalItemBean> list = new ArrayList<>();
//        LogService.i(TAG,"解析title:"+nav_name+"对应的html");
//        for(int j = 0;j < tds.size();j++){
//            Elements imgs = tds.get(j).select("img");
//            Elements divs = tds.get(j).select("div");
//            PortalItemBean itemBean = new PortalItemBean();
//            itemBean.setParent_name(nav_name);
//            List<String> img_list = new ArrayList<>();
//            List<String> type_list = new ArrayList<>();
//            List<String> param_list = new ArrayList<>();
//            List<String> url_list = new ArrayList<>();
//            if(imgs.size() <= 1){//只有一个图片
//                img_list.add(imgs.get(0).attr("data-src"));
//                type_list.add(tds.get(j).attr("data-type"));
//                param_list.add(tds.get(j).attr("data-param"));
//                url_list.add(tds.get(j).attr("data-href"));
//            }else {
//                for (int k = 0; k < imgs.size() ; k ++){
//                    img_list.add(imgs.get(k).attr("data-src")+"");
//                    // div 第一个为最外层 无参数 故从第二个开始
//                    type_list.add(divs.get(k+1).attr("data-type")+"");
//                    param_list.add(divs.get(k+1).attr("data-param")+"");
//                    url_list.add(divs.get(k+1).attr("data-href")+"");
//                }
//            }
//            itemBean.setNormalImg(img_list);
//            LogService.i(TAG,j+":Portal图片数量:"+imgs.size());
//            itemBean.setType(type_list);
//            itemBean.setParmas(param_list);
//            itemBean.setUrl(url_list);
//            itemBean.setIndex(j);
//            list.add(itemBean);
//        }
//        return list;
//    }



    public static void  destory(){
        mContext = null;
        TAG = null;
        img_msg_tip = null;
    }


    public static BasePortalFragment getFragment(int i) {
        switch (i){
            case 0:
                return new Frag_Portal_Recommend();
            case 1:
                return new Frag_Portal_Classify();
            case 2:
                return new Frag_Portal_VIP();
            default:
                return new Frag_Portal_User();
        }
    }

    public static List<String> getList(int i) {

        List<String> str = new ArrayList<>();

        switch (i){
            case 0:
                 i = 20;
                 break;
            case 1:
                i = 4;
                break;
            case 2:
                i = 8;
                break;
            default:
                i = 8;
                break;
        }
        for (int j = 0 ;j < i ;j++){
            str.add("");
        }
        return str;
    }
}
