package com.cdjysdkj.diary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdjysdkj.diary.application.BaseHomeActivity;
import com.cdjysdkj.diary.utils.NetUtils;
import com.cdjysdkj.diary.utils.ToastFactory;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;


public class ResetPaswordActivity extends BaseHomeActivity {

    private boolean isfirst;

    private TextView registSendCode;
    private EditText registPhoneNo;
    private EditText registCheckcode;
    private Button registSubmit;
    private String phoneNo;
    /**
     * 手机号码匹配((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}
     */
    public static final String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(17[0,0-9])|(18[0,0-9]))\\d{8}$";
    /**
     * 验证码格式检查
     */
    public static final String CHECK_CODE_PARTTEN = "^[0-9]{6}$";
    private SendCodeCountDownTimer timer;
    private String checkCode;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pasword);
        Intent intent = getIntent();
        isfirst = intent.getBooleanExtra("first", false);
        from=intent.getStringExtra("from");
        findView();
        init();

    }


    /**
     * z找控件
     */
    private void findView() {
        registSendCode = (TextView) findViewById(R.id.regist_send_code);
        registPhoneNo = (EditText) findViewById(R.id.regist_phone_no);
        registCheckcode = (EditText) findViewById(R.id.regist_checkcode);
        registSubmit = (Button) findViewById(R.id.regist_submit);
    }

    /**
     * 处理逻辑
     */
    private void init() {
        registSendCode.setOnClickListener(click);
        registSubmit.setOnClickListener(click);
        BmobSMS.initialize(this, "72d3a56f816540829df1606789191117");//初始化短信
    }


    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.regist_send_code:
                    if (!phoneNoCheck()) {// 检查手机号码是否输入正确
                        return;
                    }
                    if (!NetUtils.isConnected(ResetPaswordActivity.this)) {
                        ToastFactory.getToast(ResetPaswordActivity.this, "无网络，请检查网络连接");
                        return;
                    }
                    BmobSMS.requestSMSCode(ResetPaswordActivity.this, phoneNo, "dlive", new RequestSMSCodeListener() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            // TODO Auto-generated method stub
                            if (ex == null) {//验证码发送成功
                                sendCodeAction();
                                Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                            }
                        }
                    });

                    break;

                case R.id.regist_submit:
                    if (!inputCheck()) {
                        return;
                    }
                    //TODO提交注册信息
                    BmobSMS.verifySmsCode(ResetPaswordActivity.this, phoneNo, checkCode, new VerifySMSCodeListener() {

                        @Override
                        public void done(BmobException ex) {
                            // TODO Auto-generated method stub
                            if (ex == null) {//短信验证码已验证成功
                                Intent itt = new Intent();
                                if(from.equals("password")) {

                                    itt.setClass(ResetPaswordActivity.this, PasswordActivity.class);
                                    itt.putExtra("first", true);//让他重新设置密码

                                }else{
                                    itt.setClass(ResetPaswordActivity.this, BackupCopyActivity.class);
                                    itt.putExtra("phone",phoneNo);

                                }
                                startActivity(itt);
                                finish();
                                Log.i("bmob", "验证通过");
                            } else {
                                Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                ToastFactory.getToast(ResetPaswordActivity.this, "请输入正确的验证码");
                            }
                        }
                    });
                    break;


            }


        }
    };

    /**
     * @return
     * @Description 检查用户输入数据
     */
    private boolean inputCheck() {
        checkCode = registCheckcode.getText().toString().trim();
        if (!phoneNoCheck()) {
            return false;
        }
        if (checkCode == null || "".equals(checkCode)) {
            ToastFactory.getToast(ResetPaswordActivity.this, "验证码不能为空，请输入短息验证码");
            return false;
        }
        if (!checkCode.matches(CHECK_CODE_PARTTEN)) {
            ToastFactory.getToast(ResetPaswordActivity.this, "验证码格式不正确，请输入正确的验证码");
            return false;
        }

        return true;
    }

    /**
     * @Description 开启倒计时
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendCodeAction() {
        // 开启重新发送验证码倒计时，按钮不可点击
        registSendCode.setClickable(false);
        registSendCode.setBackground(this.getResources().getDrawable(
                R.drawable.btn_organge));
        timer = new SendCodeCountDownTimer(99000, 1000);
        timer.start();
    }

    /**
     * @return
     * @Description 检查手机号码格式
     */
    private boolean phoneNoCheck() {
        phoneNo = registPhoneNo.getText().toString().trim();
        if (phoneNo == null || "".equals(phoneNo)) {
            Toast.makeText(this, "手机号不能为空，请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!phoneNo.matches(PHONE_PATTERN)) {
            Toast.makeText(this, "您输入的手机号格式不正确，请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        Intent intent = getIntent();
        isfirst = intent.getBooleanExtra("first", false);
        from=intent.getStringExtra("from");
        return from.equals("password")?"重置密码":"备份验证";
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onHomeActionClick() {

    }

    @Override
    protected void addActions() {
        actionBar.hideLeftBtn(true);
    }

    @Override
    public void onBackPressed() {

        Intent itt = new Intent();
        if(from.equals("password")) {
            itt.setClass(ResetPaswordActivity.this, PasswordActivity.class);
            itt.putExtra("first", isfirst);
        }else{

            itt.setClass(ResetPaswordActivity.this, BackupCopyActivity.class);
            itt.putExtra("phone","");
        }
        startActivity(itt);
        finish();

        super.onBackPressed();
    }


    private class SendCodeCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture
         * @param countDownInterval
         */
        public SendCodeCountDownTimer(long millisInFuture,
                                      long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /*
         * <p>Title onTick</p> <p>Description </p>
         *
         * @param millisUntilFinished
         *
         * @see android.os.CountDownTimer#onTick(long)
         */
        @Override
        public void onTick(long millisUntilFinished) {
            // 已秒为单位倒计时
            registSendCode.setText(String.valueOf(millisUntilFinished / 1000));
        }

        /*
         * <p>Title onFinish</p> <p>Description </p>
         *
         * @see android.os.CountDownTimer#onFinish()
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onFinish() {
            // 倒计时结束，Button变为重新发送，并且可点击
            setSendCodeStyle();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void setSendCodeStyle() {
            registSendCode.setBackground(ResetPaswordActivity.this.getResources().getDrawable(
                    R.drawable.btn_organgepre));
            registSendCode.setText("重新发送");
            registSendCode.setClickable(true);
        }
    }

//    /**
//     * bomb短信回调
//     */
//    class MySMSCodeListener implements SMSCodeListener {
//
//        @Override
//        public void onReceive(String content) {
//            if (registCheckcode != null) {
//                registCheckcode.setText(content);
//            }
//        }
//
//    }
}
