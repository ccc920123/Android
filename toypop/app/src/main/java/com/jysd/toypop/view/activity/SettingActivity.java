package com.jysd.toypop.view.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.jysd.toypop.R;
import com.jysd.toypop.inter.OnShareListener;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.presenter.SettingPresenter;
import com.jysd.toypop.utils.AppUtils;
import com.jysd.toypop.utils.ContextUtils;
import com.jysd.toypop.utils.FrecsoUtils;
import com.jysd.toypop.utils.ScreenUtils;
import com.jysd.toypop.utils.SharedPreferencesUtils;
import com.jysd.toypop.utils.UserManager;
import com.jysd.toypop.view.impl.ISettingView;
import com.jysd.toypop.widget.AlertDialog;
import com.jysd.toypop.widget.CustomDialog;
import com.jysd.toypop.widget.ShareView;
import com.jysd.toypop.widget.ToggleButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 设置
 *
 *
 */
public class SettingActivity extends BaseActivity implements ISettingView,
        ToggleButton.OnToggleChanged {
    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void setActionBar() {

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @OnClick({R.id.clear_cache, R.id.feedback, R.id.remmend_firend, R.id.assess, R.id.logout})
    public void click(View arg0) {
        switch (arg0.getId()) {
            case R.id.clear_cache:
                // 清除缓存
                new AlertDialog(SettingActivity.this).builder().setTitle("清除缓存")
                        .setMsg("清除缓存后使用的流量可能会额外增加，确定清除？")
                        .setPositiveButton("确认", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FrecsoUtils.clear();
                            }
                        }).setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.feedback:
                // 反馈意见
                showCommentDialog();
                break;
            case R.id.remmend_firend:
                ShareView shareView = new ShareView(this);
                shareView.setOnShareListener(new OnShareListener() {
                    @Override
                    public void onShareData(ShareAction action) {
                        action.withTitle("故事会")
                                .withText("小时候我们都喜欢大人给我们讲故事，现在我们的童年已去，但故事还在。")
                                .withTargetUrl("www.baidu.com").share();
                    }

                    @Override
                    public void onShareSuccess(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onShareFailed(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onCancle() {
                        if (shareDialog != null && shareDialog.isShowing())
                            shareDialog.dismiss();
                    }

                    @Override
                    public void onShareCancle(SHARE_MEDIA platform) {

                    }
                });
                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                shareView.setLayoutParams(layoutParam);
                shareDialog = new CustomDialog(this, shareView, ScreenUtils.getInstance(this).getWidth() - ContextUtils.dip2px(this, 20), ContextUtils.dip2px(this, 420), Gravity.CENTER);
                shareDialog.show();
                break;
            case R.id.assess:
                // 赏个好评
                AppUtils.goMarket(SettingActivity.this);
                break;
            case R.id.logout:
                if (UserManager.getInstance().isLogin()) {
                    // 退出登录
                    new AlertDialog(SettingActivity.this).builder()
                            .setTitle("退出当前账号").setMsg("退出当前帐号可能会导致一些功能无法使用，确定退出？")
                            .setPositiveButton("确认退出", new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UserManager.getInstance().logout();
                                    logout.setText("登录");
                                    EventBus.getDefault().post("remove");
                                }
                            }).setNegativeButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                } else {
                    Toast.makeText(SettingActivity.this, "please login first in Home-Page !", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    private CustomDialog shareDialog;

    private void showCommentDialog() {
        View view = ContextUtils.inflate(this, R.layout.send_comment_dialog);
        final EditText et_content = ButterKnife.findById(view, R.id.et_content);
        et_content.setHint("朋友，你的意见，将会让我们把产品做得更好。");
        final TextView tv_title = ButterKnife.findById(view, R.id.tv_title);
        tv_title.setText("意见反馈");
        final TextView tv_length = ButterKnife.findById(view, R.id.tv_length);
        final Button comment_button = ButterKnife.findById(view, R.id.comment_button);
        comment_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(SettingActivity.this, "请填写你宝贵的建议", Toast.LENGTH_LONG).show();
                    return;
                }
                mail(content);
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("class", "Feedback");
//                params.put("content", content);
//                ((SettingPresenter) mPresenter).sendFeedback(params);

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null) {
                    tv_length.setText("0/70");
                } else {
                    tv_length.setText(charSequence.toString().length() + "/70");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog = new CustomDialog(this, view, ScreenUtils.getInstance(this).getWidth() - ContextUtils.dip2px(this, 20), ContextUtils.dip2px(this, 280), Gravity.CENTER);
        dialog.show();
    }
    private void mail(String strjh) {

        // 必须明确使用mailto前缀来修饰邮件地址,如果使用
        // intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
        Uri uri = Uri.parse("mailto:" + "645503254@qq.com"); // 616707902@qq.com
        // mailto:
        // String[] email = {"3802**92@qq.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        // intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "意见或建议"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, strjh); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));


    }
    private CustomDialog dialog;
    @Bind(R.id.prompt__not_wifi)
    public ToggleButton prompt__not_wifi;
//    @Bind(R.id.auto_play)
//    public ToggleButton auto_play;
    @Bind(R.id.logout)
    public Button logout;

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public BasePresenter getPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        boolean auto = SharedPreferencesUtils.getBoolean(this, "auto_play",
                false);
//        auto_play.setChecked(auto);
        boolean wifi = SharedPreferencesUtils.getBoolean(this,
                "prompt__not_wifi", true);
        prompt__not_wifi.setChecked(wifi);

        if (UserManager.getInstance().isLogin()) {
            logout.setText("注销");
        } else {
            logout.setText("登录");
        }
//        auto_play.setOnToggleChanged(this);
        prompt__not_wifi.setOnToggleChanged(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onToggle(ToggleButton toggleButton, boolean on) {
        SharedPreferencesUtils.setBoolean(this, (String) toggleButton.getTag(), on);
    }

    @Override
    public void feedback(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(this, "感谢您给我们的反馈!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "对不起，您的反馈没有发送成功!", Toast.LENGTH_LONG).show();
        }
    }
}
