package com.jysd.cartoonapp.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jysd.cartoonapp.MyStringRequest;
import com.jysd.cartoonapp.PanApplication;
import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.adapter.GridViewAdapter;
import com.jysd.cartoonapp.adapter.ViewPagerAdapter;
import com.jysd.cartoonapp.bean.ChapterBean;
import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.LoadingState;
import com.jysd.cartoonapp.inter.OnRetryListener;
import com.jysd.cartoonapp.presenter.BasePresenter;
import com.jysd.cartoonapp.view.impl.IArticleActView;
import com.jysd.cartoonapp.widget.LoadingView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.jysd.cartoonapp.R.id.viewpager;

/**
 * @类名: $classname$
 * @功能描述:
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class MorePicActivity extends BaseActivity implements IArticleActView {

    private String carUrltoonURL;
    private Juzimi data;
    @Bind(R.id.fl_loading)
    LoadingView fl_loading;
    @Bind(R.id.imagemore)
    SimpleDraweeView imageView;
    @Bind(R.id.detail)
    TextView mTextViewDetail;
    @Bind(viewpager)
    ViewPager mPager;
    @Bind(R.id.ll_dot)
    LinearLayout mLlDot;
    @Bind(R.id.relatdata)
    LinearLayout mRelativeLayoutData;
    /**
     * 图片的url
     */
    private String mImageUrl;

    private List<ChapterBean> chapterList;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 24;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    private List<View> mPagerList;
    private LayoutInflater inflater;
    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        mRelativeLayoutData.setVisibility(View.GONE);
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.mipmap.disk_file_filter_pic_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.mipmap.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.mipmap.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                //点击等框的确定事件
                getUrlData();
            }
        }).build();
        if (Build.VERSION.SDK_INT >= 23) {
//            chenckPermission();
        } else {
            getUrlData();
        }
    }


    @Override
    public void getIntentValue() {
        super.getIntentValue();
        data = this.getIntent().getParcelableExtra("PIC");
        carUrltoonURL = data.url;
    }

    /**
     * c处理逻辑数据
     */
    private void getUrlData() {

        if(carUrltoonURL.contains("\\"))
        {
            carUrltoonURL=  carUrltoonURL.replaceAll("\\\\","/");
        }

        MyStringRequest stringRequset = new MyStringRequest(Request.Method.GET, carUrltoonURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                fl_loading.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Document result = Jsoup.parse(s);
                Element divImage = result.getElementsByClass("coverForm").get(0);//封面图片
                Element img = divImage.getElementsByTag("img").get(0);//找到图片img标签
                String imageUrl = img.attr("src");//得到图片的url

                imageView.setImageURI(imageUrl);

                Element divContent = result.getElementsByClass("detailContent").get(0);//说明
                Element p = divContent.getElementsByTag("p").get(0);//找到说明说明标签
                String ptext = p.text();//得到类容
                mTextViewDetail.setText(getString(R.string.messagemore)+ptext);

                ///一下得到具体章节

                Element divchapters = result.getElementsByClass("chapters").get(0);//最新章节
                Elements elements = divchapters.select("li");//得到li
                chapterList = new ArrayList<ChapterBean>();
                for (Element e : elements) {
                    ChapterBean chapter = new ChapterBean();
                    Element a = e.getElementsByTag("a").get(0);
                    String href = a.attr("href");
                    chapter.setChapterUrl("http://www.1kkk.com" + href);
                    String page = a.text();
                    chapter.setChapterPage(page);
                    chapterList.add(chapter);
                }
                getChapter(chapterList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                fl_loading.setState(LoadingState.STATE_EMPTY);
            }
        });

        PanApplication.getQueues().add(stringRequset);
        PanApplication.getQueues().start();
    }

    /**
     *处理viewpager
     */
    private  void  getChapter(final List<ChapterBean> chapterList)
    {
        mRelativeLayoutData.setVisibility(View.VISIBLE);//显示数据
        fl_loading.setVisibility(View.GONE);//隐藏加载框
        inflater = LayoutInflater.from(this);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(chapterList.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, mPager, false);
            gridView.setAdapter(new GridViewAdapter(this, chapterList, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(MorePicActivity.this, chapterList.get(pos).getChapterUrl(), Toast.LENGTH_SHORT).show();
//                    Intent itt=new Intent(MorePicActivity.this,PreviewCarToon.class);
//                    itt.putExtra("URL", chapterList.get(pos).getChapterUrl());
//                    itt.putExtra("PAGE",chapterList.get(pos).getChapterPage());
//                    startActivity(itt);

                }
            });
        }
        //设置适配器
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();


    }
    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        if(pageCount==1)
        {
            return;
        }

        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }



    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(data.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_morepic;
    }

    @Override
    public void setContent(String content) {

    }
}
