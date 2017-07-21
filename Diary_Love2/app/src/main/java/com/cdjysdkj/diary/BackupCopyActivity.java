package com.cdjysdkj.diary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cdjysdkj.diary.application.BaseHomeActivity;
import com.cdjysdkj.diary.tab.DillTab;
import com.cdjysdkj.diary.tabhelper.DillTabHelper;
import com.cdjysdkj.diary.widget.dialog.CustomProgressDialog;
import com.cdjysdkj.diary.widget.dialog.MyAlertDialog;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 备份
 */
public class BackupCopyActivity extends BaseHomeActivity {


    private Button mButtonUpdata, mButtonLoaing;
    private CustomProgressDialog progressDialog;
    private String phone = "";
    private int selectCont;
    private int insetCont;
    private boolean isDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_copy);
        Bmob.initialize(this, "72d3a56f816540829df1606789191117");
        phone = getIntent().getStringExtra("phone");
        mButtonUpdata = (Button) findViewById(R.id.updata);
        mButtonLoaing = (Button) findViewById(R.id.loaing);
        mButtonUpdata.setOnClickListener(click);
        mButtonLoaing.setOnClickListener(click);


    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.updata://上传到云
                    //先验证手机

                    if (phone != null && !"".equals(phone)) {
                        progressDialog = CustomProgressDialog.createDialog(
                                BackupCopyActivity.this, "正在处理数据……");
                        progressDialog.show();
                        delectNetData();//先清空数据

                    } else {//先去验证手机号

                        Intent itt = new Intent();
                        itt.setClass(BackupCopyActivity.this, ResetPaswordActivity.class);
                        itt.putExtra("from", "backup");
                        BackupCopyActivity.this.startActivity(itt);
                        finish();


                    }


                    break;

                case R.id.loaing://下载到本地
                    if (phone != null && !"".equals(phone)) {
                        //下载本地
                        progressDialog = CustomProgressDialog.createDialog(
                                BackupCopyActivity.this, "正在下载数据……");
                        progressDialog.show();
                        upLoaing();


                    } else {
                        Intent itt = new Intent();
                        itt.setClass(BackupCopyActivity.this, ResetPaswordActivity.class);
                        itt.putExtra("from", "backup");
                        startActivity(itt);
                        finish();
                    }
                    break;


            }

        }

    };

    /**
     * 下载到本地
     */
    private void upLoaing() {

        BmobQuery<DillTab> query = new BmobQuery<DillTab>();
        query.addWhereEqualTo("telPhone", phone);
        query.findObjects(new FindListener<DillTab>() {
            @Override
            public void done(List<DillTab> objects, BmobException e) {
                if (e == null) {
                    //查询成功
                    // 开始插入到本地
                    for (int i = 0; i < objects.size(); i++) {
                        DillTabHelper dillTabHelper = DillTabHelper.getInstance();//初始化数据库
                        dillTabHelper.insert(objects.get(i));
                        objects.get(i).delete(new UpdateListener() {//备份完成删除数据

                            @Override
                            public void done(BmobException e) {

                            }
                        });
                        if (i == objects.size() - 1) {
                            progressDialog.dismiss();//表示写入本地完成
                            onBackPressed();
                        }
                    }

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    progressDialog.dismiss();
                    MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                    alertDialog.builder().setTitle("提示").setMsg("下载失败，请稍后再试")
                            .setNegativeButton("确定", new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                }
                            });
                    alertDialog.show();

                }
            }
        });

    }

    /**
     * 先清空数据
     */
    private void delectNetData() {
        BmobQuery<DillTab> query = new BmobQuery<DillTab>();
        query.addWhereEqualTo("telPhone", phone);
        query.findObjects(new FindListener<DillTab>() {
            @Override
            public void done(List<DillTab> objects, BmobException e) {
                if (e == null) {
                    //查询成功
                    // 批量删除   初始化你的云储存

                    if (objects != null && objects.size() > 0) {
                        selectCont = objects.size();
                        for (int j = 0; j < objects.size(); j++) {
                            objects.get(j).delete(new UpdateListener() {

                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        selectCont = selectCont - 1;
                                        if (selectCont <= 0) {
                                            progressDialog.dismiss();
                                            MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                                            alertDialog.builder().setTitle("提示").setMsg("你的云模块初始化成功，可以备份数据了，是否确定备份！")
                                                    .setNegativeButton("确定", new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View arg0) {
                                                            selectlocastData();//查询本地数据
                                                        }
                                                    }).setPositiveButton("取消", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            });
                                            alertDialog.show();

                                        }
                                    } else {
                                        if (!isDialog) {
                                            progressDialog.dismiss();
                                            MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                                            alertDialog.builder().setTitle("提示").setMsg("你的云储存模块初始化失败")
                                                    .setNegativeButton("确定", new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View arg0) {
                                                            isDialog = false;
                                                        }
                                                    });
                                            isDialog = true;
                                            alertDialog.show();
                                        }

                                    }
                                }

                            });

                        }
                    } else {

                        progressDialog.dismiss();
                        MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                        alertDialog.builder().setTitle("提示").setMsg("你的云模块初始化成功，可以备份数据了，是否确定备份！")
                                .setNegativeButton("确定", new View.OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        progressDialog = CustomProgressDialog.createDialog(
                                                BackupCopyActivity.this, "正在处理数据……");
                                        progressDialog.show();
                                        selectlocastData();//查询本地数据
                                    }
                                }).setPositiveButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        alertDialog.show();


                    }

                } else {
                    progressDialog.dismiss();
                    MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                    alertDialog.builder().setTitle("提示").setMsg("你的云储存模块初始化失败")
                            .setNegativeButton("确定", new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                }
                            });
                    alertDialog.show();

                }
            }
        });


    }

    /**
     * 查询本地数据
     */
    private void selectlocastData() {
        progressDialog = CustomProgressDialog.createDialog(
                BackupCopyActivity.this, "正在处理数据……");
        progressDialog.show();
        DillTabHelper dillTabHelper = DillTabHelper.getInstance();//初始化数据库
        List<DillTab> dillTabs = dillTabHelper.queryAll();
        if (dillTabs.size() > 0) {
            insetCont = dillTabs.size();
            for (int o = 0; o < dillTabs.size(); o++) {
                dillTabs.get(o).setTelPhone(phone);

                dillTabs.get(o).save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
//                            toast("添加数据成功，返回objectId为："+objectId);
                            insetCont = insetCont - 1;
                            if (insetCont <= 0) {
                                progressDialog.dismiss();
                                MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                                alertDialog.builder().setTitle("提示").setMsg("数据备份完成，你可以到另一手机端下载该数据了！")
                                        .setNegativeButton("确定", new View.OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {

                                                onBackPressed();

                                            }
                                        }).setPositiveButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                                alertDialog.show();

                            }


                        } else {
                            if (!isDialog) {
                                progressDialog.dismiss();
                                MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
                                alertDialog.builder().setTitle("提示").setMsg("备份失败！")
                                        .setNegativeButton("确定", new View.OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {
                                                isDialog = false;
                                            }
                                        });
                                isDialog = true;
                                alertDialog.show();

                            }
                        }
                    }
                });


            }
        } else {
            progressDialog.dismiss();
            MyAlertDialog alertDialog = new MyAlertDialog(BackupCopyActivity.this);
            alertDialog.builder().setTitle("提示").setMsg("无数据备份")
                    .setNegativeButton("确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                        }
                    });
            alertDialog.show();

        }


    }

    @Override
    protected String getActionBarTitle() {
        return "云端备份";
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onHomeActionClick() {
        onBackPressed();

    }

    @Override
    protected void addActions() {

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(BackupCopyActivity.this, TabBillActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
