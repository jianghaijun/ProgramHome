package pers.haijun.programhome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pers.haijun.programhome.R;
import pers.haijun.programhome.base.BaseActivity;
import pers.haijun.programhome.utils.ConstantUtil;
import pers.haijun.programhome.utils.ScreenManagerUtil;
import pers.haijun.programhome.utils.SpUtil;

/**
 *                     _ooOoo_
 *                    o8888888o
 *                    88" . "88
 *                    (| -_- |)
 *                    O\  =  /O
 *                 ____/`---'\____
 *               .'  \\|     |//  `.
 *              /  \\|||  :  |||//  \
 *             /  _||||| -:- |||||-  \
 *             |   | \\\  -  /// |   |
 *             | \_|  ''\---/''  |   |
 *             \  .-\__  `-`  ___/-. /
 *           ___`. .'  /--.--\  `. . __
 *        ."" '<  `.___\_<|>_/___.'  >'"".
 *       | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *       \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                     `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 			   佛祖保佑       永无BUG
 *       Created by HaiJun on 2017/11/9 10:18
 *       引导页
 */
public class GuidePagesActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.viewPageGuidePages)
    private ViewPager viewPageGuidePages;
    @ViewInject(R.id.llIndicator)
    private LinearLayout llIndicator;

    private Context mContext;
    // 图片集合
    private List<Integer> imgUrlList;
    // 圆点集合
    private List<ImageView> pointList;
    // 适配器
    private GuidePagesAdapter guidePagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_pages);
        x.view().inject(this);

        boolean isLogin = (boolean) SpUtil.get(this, ConstantUtil.isStart, false);
        if (!isLogin) {
            mContext = this;

            initData();

            guidePagesAdapter = new GuidePagesAdapter();
            viewPageGuidePages.setAdapter(guidePagesAdapter);
            viewPageGuidePages.setCurrentItem(0);
            viewPageGuidePages.addOnPageChangeListener(this);
        } else {
            startActivity(new Intent(this, StartupPageActivity.class));
            this.finish();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        imgUrlList = new ArrayList<>();
        imgUrlList.add(R.mipmap.guide_page_1);
        imgUrlList.add(R.mipmap.guide_page_1);
        imgUrlList.add(R.mipmap.guide_page_1);

        addIndicator(imgUrlList);
    }

    /**
     * 添加指示器
     */
    private void addIndicator(List<Integer> urls) {
        pointList = new ArrayList<>();
        int paddingDp = DensityUtil.dip2px(10);
        int pointSize = DensityUtil.dip2px(30);
        for (int i = 0; i < urls.size(); i++) {
            ImageView child = new ImageView(this);
            child.setImageResource(R.drawable.point_grey);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
            child.setLayoutParams(params);
            child.setPadding(paddingDp, 0, paddingDp, 0);
            pointList.add(child);
            llIndicator.addView(child);
        }
        setPointLight(0);
    }

    /**
     * 设置默认圆点位置
     * @param position
     */
    private void setPointLight(int position) {
        if (pointList.size() < 2) {
            return;
        }

        for (ImageView imageView : pointList) {
            imageView.setImageResource(R.drawable.point_grey);
        }

        ImageView imageView = pointList.get(position);
        imageView.setImageResource(R.drawable.point_white);
    }

    /**
     * 设置选中的tip的背景
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < pointList.size(); i++) {
            if (i == selectItems) {
                pointList.get(i).setImageResource(R.drawable.point_white);
            } else {
                pointList.get(i).setImageResource(R.drawable.point_grey);
            }
        }
    }

    /**
     * 引导页适配器
     */
    private class GuidePagesAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_guide_pages, null);
            ImageView imgGuidePage = (ImageView) view.findViewById(R.id.imgGuidePage);
            Button btnExperienceNow = (Button) view.findViewById(R.id.btnExperienceNow);

            if (position == imgUrlList.size() -1) {
                btnExperienceNow.setVisibility(View.VISIBLE);
            }

            imgGuidePage.setImageDrawable(ContextCompat.getDrawable(mContext, imgUrlList.get(position)));

            btnExperienceNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    GuidePagesActivity.this.finish();
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return imgUrlList == null ? 0 : imgUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
