package com.cdjysdkj.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cdjysdkj.diary.adapter.BillExpandableAdapter;
import com.cdjysdkj.diary.application.BaseHomeActivity;
import com.cdjysdkj.diary.constant.Constant;
import com.cdjysdkj.diary.tab.DillTab;
import com.cdjysdkj.diary.tabhelper.DillTabHelper;
import com.cdjysdkj.diary.view.ZQExpandableListView;
import com.cdjysdkj.diary.widget.dialog.CustomProgressDialog;
import com.cdjysdkj.diary.widget.dialog.MyAlertDialog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class BillActivity extends BaseHomeActivity {
    private ZQExpandableListView expandableListView;
    private BillExpandableAdapter adapter;
    private CustomProgressDialog progressDialog;
    String[] groupItem;
    DillTab child[][] = new DillTab[100][100];
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        getView();
        preferences = getSharedPreferences(
                Constant.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        if (!preferences.getBoolean("isCH", false)) {

            MyAlertDialog alertDialog = new MyAlertDialog(BillActivity.this);
            alertDialog.builder().setTitle("温馨提示").setMsg("左滑可以删除数据哟——")
                    .setNegativeButton("知道了", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            SharedPreferences.Editor editor = preferences.edit();
                            // 存入数据
                            editor.putBoolean("isCH", true);
                            // 提交修改
                            editor.commit();
                        }
                    });
            alertDialog.show();


        }


        init();
    }

    /**
     * 查询数据库
     */
    private void init() {

        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                DillTabHelper dillTabHelper = DillTabHelper.getInstance();
                groupItem = dillTabHelper.queryByGroup();//查询到月
                if (groupItem != null) {
                    for (int i = 0; i < groupItem.length; i++) {
                        String timetom = groupItem[i];
                        List<DillTab> dillTabs = dillTabHelper.queryBytime(timetom);//查询月下面的具体天的数据
                        if (dillTabs != null && dillTabs.size() > 0) {
                            child[i] = (DillTab[]) dillTabs.toArray(new DillTab[dillTabs.size()]);
                        }
                    }
                }
                subscriber.onNext(0);
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                adapter = new BillExpandableAdapter(groupItem, child, BillActivity.this);
                expandableListView.setGroupIndicator(null);
                expandableListView.setAdapter(adapter);
            }
        });
    }

    /**
     * 删除天
     *
     * @param id
     */
    public void deletDay(final String id) {
        progressDialog = CustomProgressDialog.createDialog(this, "正在删除数据……");
        progressDialog.show();
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int succeed;
                DillTabHelper dillTabHelper = DillTabHelper.getInstance();
                succeed = dillTabHelper.deleteDesDays(id);

                groupItem = dillTabHelper.queryByGroup();//查询到月
                if (groupItem != null) {
                    for (int i = 0; i < groupItem.length; i++) {
                        String timetom = groupItem[i];
                        List<DillTab> dillTabs = dillTabHelper.queryBytime(timetom);//查询月下面的具体天的数据
                        if (dillTabs != null && dillTabs.size() > 0) {
                            child[i] = (DillTab[]) dillTabs.toArray(new DillTab[dillTabs.size()]);
                        }
                    }
                }
                subscriber.onNext(succeed);
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o > 0) {
                    //再次查询数据


                    adapter = new BillExpandableAdapter(groupItem, child, BillActivity.this);
                    adapter.notifyDataSetChanged();
                    expandableListView.setGroupIndicator(null);
                    expandableListView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }
        });


    }

    /**
     * 删除一整月
     */
    public void deletMoth(final String moth) {
        progressDialog = CustomProgressDialog.createDialog(this, "正在删除数据……");
        progressDialog.show();
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int succeed;
                DillTabHelper dillTabHelper = DillTabHelper.getInstance();
                succeed = dillTabHelper.deleteTime(moth);

                //再次查询数据
                groupItem = dillTabHelper.queryByGroup();//查询到月
                if (groupItem != null) {
                    for (int i = 0; i < groupItem.length; i++) {
                        String timetom = groupItem[i];
                        List<DillTab> dillTabs = dillTabHelper.queryBytime(timetom);//查询月下面的具体天的数据
                        if (dillTabs != null && dillTabs.size() > 0) {
                            child[i] = (DillTab[]) dillTabs.toArray(new DillTab[dillTabs.size()]);
                        }
                    }
                }

                subscriber.onNext(succeed);
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o > 0) {
                    adapter = new BillExpandableAdapter(groupItem, child, BillActivity.this);
                    adapter.notifyDataSetChanged();
                    expandableListView.setGroupIndicator(null);
                    expandableListView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void getView() {
        expandableListView = (ZQExpandableListView) findViewById(R.id.list_expandaable);
    }

    @Override
    protected String getActionBarTitle() {
        return "我的账单";
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onHomeActionClick() {
        goToActivity(this, HomeActivity.class, null);
        finish();
    }

    @Override
    protected void addActions() {

        actionBar.setRightLogo(R.drawable.ic_action_add);
        actionBar.setRightAction(new OnClickListener() {

            @Override
            public void onClick(View arg0) {// 跳转到新增账单界面
                goToActivity(BillActivity.this, AddBillActivity.class, null);
                finish();
            }
        });
        actionBar.setHideBeiFenVisibility(true);
        actionBar.setBeifenAction(new OnClickListener() {//跳转到备份
            @Override
            public void onClick(View view) {
                Intent itt = new Intent();
                itt.setClass(BillActivity.this, BackupCopyActivity.class);
                itt.putExtra("phone", "");
                startActivity(itt);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToActivity(this, HomeActivity.class, null);
        finish();
    }

}
