package com.mvp.chenpan.mvpdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chenpan.library.AccountHeader;
import com.example.chenpan.library.AccountHeaderBuilder;
import com.example.chenpan.library.Drawer;
import com.example.chenpan.library.DrawerBuilder;
import com.example.chenpan.library.holder.BadgeStyle;
import com.example.chenpan.library.holder.StringHolder;
import com.example.chenpan.library.model.DividerDrawerItem;
import com.example.chenpan.library.model.PrimaryDrawerItem;
import com.example.chenpan.library.model.ProfileDrawerItem;
import com.example.chenpan.library.model.interfaces.IDrawerItem;
import com.example.chenpan.library.model.interfaces.IProfile;
import com.example.chenpan.library.util.RecyclerViewCacheUtil;
import com.mvp.chenpan.mvpdemo.base.MVPBaseActivity;
import com.mvp.chenpan.mvpdemo.mvp.presenter.TelPresenter;
import com.mvp.chenpan.mvpdemo.mvp.view.TelView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class MainActivity extends MVPBaseActivity<TelView, TelPresenter> implements TelView {


    @Bind(R.id.tel)
    EditText tel;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.show)
    TextView show;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 侧滑头部
     */
    private AccountHeader headerResult = null;
    /**
     * 侧滑布局
     */
    private Drawer result = null;
    /**
     * 头部布局
     */
    private IProfile profile;

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        profile = new ProfileDrawerItem().withName("张三").withIcon(R.mipmap.ic_launcher).withEmail("你好").withIdentifier(100);
        headerResult = new AccountHeaderBuilder().withOnlyMainProfileImageVisible(true).withSelectionListEnabled(false)
                .withActivity(this).withHeightDp(230)
                .withHeaderBackground(R.drawable.side_bg)
                .addProfiles(
                        profile
                ).withSavedInstance(savedInstanceState).build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("视频").withIcon(R.mipmap.ic_launcher).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName("图片").withIcon(R.mipmap.ic_launcher).withIdentifier(2).withSelectable(true).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName("文章").withIcon(R.mipmap.ic_launcher).withIdentifier(3).withSelectable(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("设置").withIcon(R.mipmap.ic_launcher).withIdentifier(5).withSelectable(false)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    } /*{
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1 && dId != 1) {
                                dId = 1;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                // setupVideoViewPager();
                                //               intent = new Intent(MainActivity.this, TopicActivity.class);
                            } else if (drawerItem.getIdentifier() == 2 && dId != 2) {
                                dId = 2;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                // setupPictureViewPager();
                            } else if (drawerItem.getIdentifier() == 3 && dId != 3) {
                                dId = 3;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                //  setupTextViewPager();
                            } else if (drawerItem.getIdentifier() == 4) {
                                //                intent = new Intent(MainActivity.this, SmallGameActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                //  intent = new Intent(MainActivity.this, SettingActivity.class);
                                MainActivity.this.startActivity(intent);
                                return true;
                            } else if (drawerItem.getIdentifier() == 6) {
                                //
                               *//* if (mSweetSheet != null && !mSweetSheet.isShow()) {
                                    mSweetSheet.show();
                                    return false;
                                }*//*
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(MainActivity.this);
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }*/
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);
        if (savedInstanceState == null) {
            // result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }
        result.updateBadge(4, new StringHolder(10 + ""));

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = tel.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("key", "e59b086b3ef990049b3056d6a5d5e342");
                mPresenter.fetch(map);
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void showLoading() {
//加载过程的显示，可以显示进度条等
    }

    @Override
    public void showData(String result) {
//负责显示
        show.setText(result);
    }


    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public TelPresenter createPresenter() {
        return new TelPresenter();
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }
    }
}
