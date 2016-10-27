package com.jysd.toypop.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jysd.toypop.R;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.presenter.SettingPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.Bind;

public class ImageWebActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.proweb)
    WebView proWeb;

    @Bind(R.id.looding)
    LinearLayout loodingPro;
    @Bind(R.id.download)
    LinearLayout downloadLinearLayout;


    private String urls = "";
    private Handler handler;
    private String path= Environment.getExternalStorageDirectory().getPath();

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void setActionBar() {

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {

        return new SettingPresenter();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("TITLE");
        urls = getIntent().getStringExtra("URLS");
        getSupportActionBar().setTitle(title);//设置title
        toolbar.setOnMenuItemClickListener(menuClick);

        proWeb.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings settings = proWeb.getSettings();//获取WebView里面设置对象
        settings.setAllowContentAccess(true);//设置允许访问内容
        settings.setAllowFileAccess(true);//设置允许访问文件
        settings.setLoadsImagesAutomatically(true);//设置自动下载图片
        settings.setSupportMultipleWindows(true);//支持多窗口
        settings.setJavaScriptEnabled(true);//设置支持JS
        settings.setBuiltInZoomControls(true); //显示放大缩小 controler
        settings.setSupportZoom(true); //可以缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        settings.setUseWideViewPort(true);// 调整图片适合于webview的大小m.webkfa.com/comm/lxh.html
        settings.setBlockNetworkImage(true);
        newlaodurl(proWeb, urls);


        proWeb.setWebViewClient(new myWebViewClient());//设置连接
        proWeb.setWebChromeClient(new myWebChromeClient());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:// 成功
                        proWeb.setVisibility(View.VISIBLE);
                        loodingPro.setVisibility(View.GONE);
                        break;

                    case 0:
                        proWeb.setVisibility(View.GONE);
                        loodingPro.setVisibility(View.VISIBLE);
                        break;
                }
                super.handleMessage(msg);

            }

        };

    }



    // 请求地址
    public void newlaodurl(final WebView view, final String url) {

        view.loadUrl(url);
    }

    private Toolbar.OnMenuItemClickListener menuClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int menuItemId = item.getItemId();
            if (menuItemId == R.id.action_sava) {
                downloadLinearLayout.setVisibility(View.VISIBLE);
                new SaveImage().execute();

            }

            return true;
        }
    };

    //分享单张图片,还未开发
    public void shareSingleImage(View view) {
        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    private  boolean isFile(String str)
    {
        File folder = new File(str);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_image_web;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    class myWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            newlaodurl(view, url);
            return true;
        }
    }

    class myWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                handler.sendEmptyMessage(1);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     *
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }

                int idx = urls.lastIndexOf(".");
                String ext = urls.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(urls);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            downloadLinearLayout.setVisibility(View.GONE);
            Toast.makeText(ImageWebActivity.this,result,Toast.LENGTH_SHORT).show();
        }
    }
}
