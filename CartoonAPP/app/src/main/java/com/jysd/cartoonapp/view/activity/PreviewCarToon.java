package com.jysd.cartoonapp.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jysd.cartoonapp.MyStringRequest;
import com.jysd.cartoonapp.PanApplication;
import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.adapter.PreviewAdapter;
import com.jysd.cartoonapp.presenter.BasePresenter;
import com.jysd.cartoonapp.view.impl.IArticleActView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @类名: PreviewCarToon
 * @功能描述: 漫画预览界面
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class PreviewCarToon extends BaseActivity implements IArticleActView {


    private String url;
    private String page;
    @Bind(R.id.viewpager)
    public ViewPager mViewPager;
    private PreviewAdapter adapter;
//    private Bitmap cursor;
//    private int bmWidth;
//    private int offSet;
//    private int currentItem;
//    private Matrix matrix = new Matrix();

    //    @Bind(R.id.fl_loading)
//    LoadingView fl_loading;
//    @Bind(R.id.image)
//    SubsamplingScaleImageView imageView;
    private List<String> urlList;//url地址

    private List<View> lists = new ArrayList<View>();//viewpage控件

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

        getDataList();

        getPageUrl();




    }

    /**
     * 收集网络数据
     */
    private void getDataList() {
        urlList=new ArrayList<String>();
//        for(int i=1;;i++)
//        {
           final String newUrl= url + "#ipg" + 1;
            MyStringRequest myRequse=new MyStringRequest(Request.Method.GET,newUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
//                    urlList.add(newUrl);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    return;
                }
            });
        PanApplication.getQueues().add(myRequse);
        PanApplication.getQueues().start();
//        }


    }

    /**
     * 请求url数据
     * #ipg2
     */
    private void getPageUrl() {

      for (int i=0;i<urlList.size();i++)
      {
         View view= getLayoutInflater().inflate(R.layout.previewlayout, null);
          lists.add(view);
          MyStringRequest stringRequset=new MyStringRequest(Request.Method.GET, urlList.get(i), new Response.Listener<String>() {
              @Override
              public void onResponse(String s) {

                  Document result = Jsoup.parse(s);


              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError volleyError) {

              }
          });

          PanApplication.getQueues().add(stringRequset);
          PanApplication.getQueues().start();

      }
        adapter = new PreviewAdapter(lists);
        mViewPager.setAdapter(adapter);




    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_previewcartoon;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        url=this.getIntent().getStringExtra("URL");
        page= this.getIntent().getStringExtra("PAGE");


    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle("第"+page+"章");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }
//    /**
//     * 计算滑动的图片的位置
//     */
//    private void initeCursor() {
//        cursor = BitmapFactory.decodeResource(getResources(),R.drawable.viewpager_img);
//        bmWidth = cursor.getWidth();
//        DisplayMetrics dm;
//        dm = getResources().getDisplayMetrics();
//        offSet = (dm.widthPixels - 2 * bmWidth) / 4;
//        matrix.setTranslate(offSet, 0);
//        imageView.setImageMatrix(matrix); // 需要iamgeView的scaleType为matrix
//        currentItem = 0;
//    }
}
