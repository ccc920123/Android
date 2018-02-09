package com.jysd.toypop.view.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jysd.toypop.R;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.presenter.SettingPresenter;
import com.jysd.toypop.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.Bind;

/**
 * 查看图片详情
 */
public class ImageActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image)
    SubsamplingScaleImageView mImageView;
    @Bind(R.id.lodingview)
    TextView loding;
    @Bind(R.id.gifimage)
    ImageView gifImage;


    private String urls = "";
    private File file1;
    String sdcard = Environment.getExternalStorageDirectory().toString();

    private Menu mMenu;

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
        //执行下载功能
        new SaveImage().execute();

        if (urls.contains(".gif")) {
            gifImage.setVisibility(View.VISIBLE);
            loding.setVisibility(View.GONE);
            mImageView.setVisibility(View.GONE);
            Glide.with(this)
                    .load(urls)
                    .placeholder(R.drawable.loading)
                    .error(R.mipmap.ic_loading_error)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(gifImage);

        } else {
            loding.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);

            mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);

            mImageView.setMinScale(1.0F);
            Glide.with(this)
                    .load(urls).downloadOnly(new SimpleTarget<File>() {
                @Override
                public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                    // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                    loding.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                    mImageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
                }
            });
        }

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        mMenu = menu;
        mMenu.findItem(R.id.action_sava).setVisible(false);
        return true;
    }

    private Toolbar.OnMenuItemClickListener menuClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int menuItemId = item.getItemId();
            if (menuItemId == R.id.action_sava) {

//                String path = Environment.getExternalStorageDirectory() + File.separator;//sd根目录
//                File file = new File(path, "share" + ".jpg");//这里share.jpg是sd卡根目录下的一个图片文件

                Uri imageUri = Uri.fromFile(file1);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享图片"));


            }

            return true;
        }
    };

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     *
     */
    public class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
//                sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/TPDownload");
                if (!file.exists()) {
                    file.mkdirs();
                }

                int idx = urls.lastIndexOf(".");
                String ext = urls.substring(idx);
                file = new File(sdcard + "/TPDownload/" + new Date().getTime() + ext);
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
                file1 = file.getAbsoluteFile();
                result = "0";
            } catch (Exception e) {
//                result = "保存失败！" + e.getLocalizedMessage();
                result = "1";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

//            Toast.makeText(ImageActivity.this,result,Toast.LENGTH_SHORT).show();
            if ("0".equals(result)) {
                mMenu.findItem(R.id.action_sava).setVisible(true);
            } else {
                mMenu.findItem(R.id.action_sava).setVisible(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        FileUtil.deleteFilesByDirectory(new File(sdcard + "/TPDownload"));

        super.onDestroy();
    }
}
