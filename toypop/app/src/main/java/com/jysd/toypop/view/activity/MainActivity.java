package com.jysd.toypop.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.jysd.toypop.R;
import com.jysd.toypop.adapter.ArticleAdapter;
import com.jysd.toypop.adapter.ArticleChildAdapter;
import com.jysd.toypop.adapter.ArticleJokeAdapter;
import com.jysd.toypop.adapter.FragmentAdapter;
import com.jysd.toypop.adapter.JuzimiAdapter;
import com.jysd.toypop.bean.User;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.utils.ContextUtils;
import com.jysd.toypop.utils.ScreenUtils;
import com.jysd.toypop.utils.UserManager;
import com.jysd.toypop.view.impl.IMainView;
import com.jysd.toypop.widget.CustomDialog;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.pan.materialdrawer.AccountHeader;
import com.pan.materialdrawer.AccountHeaderBuilder;
import com.pan.materialdrawer.Drawer;
import com.pan.materialdrawer.DrawerBuilder;
import com.pan.materialdrawer.holder.BadgeStyle;
import com.pan.materialdrawer.holder.StringHolder;
import com.pan.materialdrawer.model.PrimaryDrawerItem;
import com.pan.materialdrawer.model.ProfileDrawerItem;
import com.pan.materialdrawer.model.interfaces.IDrawerItem;
import com.pan.materialdrawer.model.interfaces.IProfile;
import com.pan.materialdrawer.util.RecyclerViewCacheUtil;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements IMainView {

    private static final int PROFILE_SETTING = 1;

    //使用侧滑框架
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private IProfile profile;
    private int dId = 1;
    @Bind(R.id.viewpager)
    public ViewPager mViewPager;
    @Bind(R.id.tabs)
    public TabLayout mTabLayout;



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
        EventBus.getDefault().register(this);
        setupTextChildViewPager();//先启动儿童读物
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        if (UserManager.getInstance().isLogin()) {
            profile = new ProfileDrawerItem().withName(UserManager.getInstance().getUser().screen_name).withEmail("朋友，欢迎您回来 !").withIcon(Uri.parse(UserManager.getInstance().getUser().profile_image_url)).withIdentifier(100);
        } else {
            profile = new ProfileDrawerItem().withName("未登录").withEmail("朋友，欢迎您回来 !").withIcon(R.mipmap.ic_default).withIdentifier(100);
        }
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder().withOnlyMainProfileImageVisible(true).withSelectionListEnabled(false)
                .withActivity(this).withHeightDp(230)
                .withHeaderBackground(R.mipmap.slider_bg)
                .addProfiles(
                        profile
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                      /*  if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                                headerResult.setActiveProfile();
                            }
                        }*/
                        if (UserManager.getInstance().isLogin())
                            return true;
                        //加载loading等待框
                        View login_dialog = ContextUtils.inflate(MainActivity.this, R.layout.login_dialog);
                        final CustomDialog dialog = new CustomDialog(MainActivity.this, login_dialog,
                                ScreenUtils.getInstance(MainActivity.this).getWidth() - ContextUtils.dip2px(MainActivity.this, 40),
                                ContextUtils.dip2px(MainActivity.this, 260), Gravity.CENTER);
                        ButterKnife.findById(login_dialog, R.id.login_qq).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        ButterKnife.findById(login_dialog, R.id.login_sina).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        ButterKnife.findById(login_dialog, R.id.cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        //Create the drawer 侧滑内容
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(getToolbar())
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header /GoogleMaterial.Icon.gmd_videocam/FontAwesome.Icon.faw_picture_o/GoogleMaterial.Icon.gmd_text_format
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(R.mipmap.g).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(R.mipmap.s).withIdentifier(2).withSelectable(true).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(R.mipmap.h).withIdentifier(3).withSelectable(true),
//                        new DividerDrawerItem(),
                     /*   new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withSelectable(false),
                        new DividerDrawerItem(),*/
                        new PrimaryDrawerItem().withName(R.string.drawer_item_keyboard_util_drawer).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(5).withSelectable(false)

                        //                    new SwitchDrawerItem().withName("日间模式").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
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
                                setupTextChildViewPager();//开始儿童读物
                                //               intent = new Intent(MainActivity.this, TopicActivity.class);
                            } else if (drawerItem.getIdentifier() == 2 && dId != 2) {
                                dId = 2;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupTextJokeViewPager();
                            } else if (drawerItem.getIdentifier() == 3 && dId != 3) {
                                dId = 3;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupTextViewPager();//文章欣赏
                            } /*else if (drawerItem.getIdentifier() == 4) {
                                //                intent = new Intent(MainActivity.this, SmallGameActivity.class);
                            }*/ else if (drawerItem.getIdentifier() == 5) {
//                                intent = new Intent(MainActivity.this, SettingActivity.class);
//                                MainActivity.this.startActivity(intent);
                                Toast.makeText(MainActivity.this, "这里是设置界面", Toast.LENGTH_SHORT).show();
                                return true;
                            } else if (drawerItem.getIdentifier() == 6) {
                                //
                               /* if (mSweetSheet != null && !mSweetSheet.isShow()) {
                                    mSweetSheet.show();
                                    return false;
                                }*/
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
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
        //make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }

        result.updateBadge(4, new StringHolder(10 + ""));
    }

    /**
     * 图片欣赏
     */
    private void setupPictureViewPager() {
        String[] titles = getResources().getStringArray(R.array.pictrue_tab);
        JuzimiAdapter adapter =
                new JuzimiAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 视频浏览
     */
    private void setupVideoViewPager() {
        String[] titles = getResources().getStringArray(R.array.video_tab);
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 笑话
     */
    private  void  setupTextJokeViewPager()
    {
        String[] titles = getResources().getStringArray(R.array.pictrue_tab);
        ArticleJokeAdapter adapter =
                new ArticleJokeAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

    }


    /**
     * 文章阅读
     */
    private void setupTextViewPager() {
        String[] titles = getResources().getStringArray(R.array.text_tab);
        ArticleAdapter adapter =
                new ArticleAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 儿童阅读
     */
    private void setupTextChildViewPager() {
        String[] titles = getResources().getStringArray(R.array.video_tab);
        ArticleChildAdapter adapter =
                new ArticleChildAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    public void onEventMainThread(String remove) {
        if (headerResult != null && "remove".equals(remove)) {
            headerResult.removeProfile(profile);
            profile = new ProfileDrawerItem().withName("未登录").withEmail("朋友，欢迎您回来 !").withIcon(R.mipmap.ic_default).withIdentifier(100);
            headerResult.addProfiles(profile);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_sample_dark_toolbar;
    }

    @Override
    public void setUserInfo(User user) {
        if (headerResult != null) {
            headerResult.removeProfile(profile);
            profile = new ProfileDrawerItem().withName(user.screen_name).withEmail("朋友，欢迎您回来 !").withIcon(Uri.parse(user.profile_image_url)).withIdentifier(100);
            headerResult.addProfiles(profile);
        }
    }
}
