package com.cncyj.mostbrain.game.brainsharp;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.game.brainsharp.modle.BrainTwists;
import com.cncyj.mostbrain.game.brainsharp.modle.Canginfo;
import com.cncyj.mostbrain.game.brainsharp.spl.DbMananger;
import com.cncyj.mostbrain.game.brainsharp.utli.ContentData;
import com.cncyj.mostbrain.game.brainsharp.utli.DateUtil;
import com.cncyj.mostbrain.game.brainsharp.utli.SharedPreferencesForLogin;

public class ContentActivity extends Activity implements OnClickListener {

	private TextView tx_content;
	private Button btn_daan;
	private TextView tx_title;
	private Button content_shoucang_app;
	String type = "10001";
	String[] typecontent;
	StringBuffer str = null;

	private SharedPreferencesForLogin index_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_content);

		index_info = new SharedPreferencesForLogin(ContentActivity.this,
				"pageindex");

		Intent intent = getIntent();
		type = intent.getStringExtra("typedate");
		Log.i("type", type);

		tx_content = (TextView) this.findViewById(R.id.btn_content);
		btn_daan = (Button) this.findViewById(R.id.btn_daan);
		tx_title = (TextView) this.findViewById(R.id.content_title);
		init();
		setTitle(type);

	}

	public void setTitle(String type) {
		if (type.equals("10001")) {
			tx_title.setText("搞笑类型");
		} else if (type.equals("10002")) {
			tx_title.setText("幽默类型");
		} else if (type.equals("10003")) {
			tx_title.setText("趣味类型");
		} else if (type.equals("10004")) {
			tx_title.setText("动物类型");
		} else if (type.equals("10005")) {
			tx_title.setText("经典类型");
		} else if (type.equals("10006")) {
			tx_title.setText("整人类型");
		} else if (type.equals("10007")) {
			tx_title.setText("综合类型");
		} else if (type.equals("10008")) {
			tx_title.setText("特色类型");
		} else if (type.equals("10009")) {
			tx_title.setText("风趣类型");
		} else {
			tx_title.setText("脑筋急转弯");
		}
	}

	public void init() {

		int x = getTypeIndex(type); // 得到下标
		// 在这儿可以判断是否请求网络数据
		// typecontent =
		getTypeDataservice(type);

		// String[] typecontent = getTypeData(type);
		// String content = typecontent[x];
		// String[] valuedata = content.split("#"); // 得到某一个，问题和答案
		// tx_content.setText(valuedata[0]);
		//
		// findViewById(R.id.btn_left).setOnClickListener(this);
		// findViewById(R.id.btn_right).setOnClickListener(this);
		// findViewById(R.id.btn_content).setOnClickListener(this);
		// findViewById(R.id.fuzhi).setOnClickListener(this);
		// findViewById(R.id.fenxiang).setOnClickListener(this);
		// findViewById(R.id.btn_daan).setOnClickListener(this);
		// findViewById(R.id.content_shoucang_app).setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		switch (id) {
		case R.id.btn_left:
			int x = getTypeIndex(type); // 得到下标

			getContentleft(type, x); // 设置类型的下标

			int new_x = getTypeIndex(type); // 新的下标

			// String[] typecontent = getTypeData(type);

			String content = typecontent[new_x];

			String[] valuedata = content.split("#"); // 得到某一个，问题和答案

			tx_content.setText(valuedata[0]);

			btn_daan.setText("查看答案");

			break;
		case R.id.btn_right:

			int y = getTypeIndex(type);

			getContentright(type, y); // 设置类型的下标

			int new_y = getTypeIndex(type); // 新的下标

			// String[] rtypecontent = getTypeData(type);

			String rcontent = typecontent[new_y];

			String[] rvaluedata = rcontent.split("#"); // 得到某一个，问题和答案

			tx_content.setText(rvaluedata[0]); // 设置内容

			btn_daan.setText("查看答案");

			break;
		case R.id.btn_daan:
			int z = getTypeIndex(type); // 得到下标

			// String[] ztypecontent = getTypeData(type);

			String zcontent = typecontent[z];
			// String zcontent = ztypecontent[z];

			String[] zvaluedata = zcontent.split("#"); // 得到某一个，问题和答案

			btn_daan.setText(zvaluedata[1]);
			break;
		case R.id.fenxiang:

			int xx = getTypeIndex(type); // 得到下标
			// String[] xxtypecontent = getTypeData(type);

			String xxcontent = typecontent[xx];
			String[] xxvaluedata = xxcontent.split("#"); // 得到某一个，问题和答案

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			// intent.setType("image/png");
			// 添加图片
			// File f = new File(Environment.getExternalStorageDirectory() +
			// "/Pictures/2.png");
			// Uri u = Uri.fromFile(f);
			// intent.putExtra(Intent.EXTRA_STREAM, R.drawable.ic_launcher);

			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT, xxvaluedata[0] + "\n答案是 ："
					+ xxvaluedata[1]);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getTitle()));
			break;
		case R.id.content_shoucang_app: // 收藏

			int k = getTypeIndex(type); // 得到下标
			String[] ktypecontent = typecontent;
			String kcontent = ktypecontent[k];

			DbMananger db = new DbMananger(this);
			long count = db.getCount(type, String.valueOf(k));
			if (count == 0) {
				Canginfo cang = new Canginfo();
				cang.setAppcontent(kcontent);
				cang.setAppindex(String.valueOf(k));
				cang.setApptype(type);
				db.insertState(cang);
				Toast.makeText(ContentActivity.this, "收藏成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(ContentActivity.this, "已收藏", Toast.LENGTH_SHORT)
						.show();
			}

			// String[] kvaluedata=kcontent.split("#"); //得到某一个，问题和答案
			// String cangdata=index_info.getValue("shoucangData");
			// String[] ccang=cangdata.split("&");
			// for(int i=0;i<ccang.length;i++){
			// if(ccang[i].equals(kcontent)){
			//
			// }
			// }
			// String cangcontent=cangdata+"&"+kcontent;
			//
			// index_info.putValue("shoucangData", cangcontent);
			break;
		case R.id.fuzhi:
			int yy = getTypeIndex(type); // 得到下标
			String[] yytypecontent = typecontent;
			String yycontent = yytypecontent[yy];
			String[] yyvaluedata = yycontent.split("#"); // 得到某一个，问题和答案
			ClipboardManager cm = (ClipboardManager) this
					.getSystemService(Context.CLIPBOARD_SERVICE);
			// 将文本数据复制到剪贴板
			cm.setText(yyvaluedata[0] + "答案是：" + yyvaluedata[1]);
			Toast.makeText(ContentActivity.this, "复制成功", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
	}

	// 得到类型内容
	// 现在在此处得到查询的内容
	// public String[] getTypeData(String s) {
	// ContentData content = new ContentData();
	// String[] results = null;
	// if (s.equals("10001")) {
	// results = content.getGaoxiaodata();
	// } else if (s.equals("10002")) {
	// results = content.getYoumodata();
	// } else if (s.equals("10003")) {
	// results = content.getQuweidata();
	// } else if (s.equals("10004")) {
	// results = content.getDongwudata();
	// } else if (s.equals("10005")) {
	// results = content.getJindiandata();
	// } else if (s.equals("10006")) {
	// results = content.getZhengrendata();
	// } else if (s.equals("10007")) {
	// results = content.getZonghedata();
	// } else if (s.equals("10008")) {
	// results = content.getTesedata();
	// } else if (s.equals("10009")) {
	// results = content.getFengqudata();
	// }
	// return results;
	// }
@Override
public void onBackPressed() {
	if(str!=null)
	{
		str=null;
	}
	super.onBackPressed();
}
	/**
	 * 查询后台数据
	 * 
	 * @Description
	 * 
	 * @param @param type
	 * @param @return
	 * @return
	 * @throws
	 * @throws
	 * 
	 */
	private String[] getTypeDataservice(final String type) {

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				BmobQuery<BrainTwists> query = new BmobQuery<BrainTwists>();
				// 查询playerName叫“比目”的数据
				query.addWhereEqualTo("type", type);
				// 返回50条数据，如果不加上这条语句，默认返回10条数据
				query.setLimit(50);
				// 执行查询方法
				query.findObjects(new FindListener<BrainTwists>() {

					@Override
					public void done(List<BrainTwists> arg0, BmobException arg1) {
						if (arg1 == null) {
							str=new StringBuffer();
							for (int i = 0; i < arg0.size(); i++) {

								str.append(arg0.get(i).getContent());
								if (arg0.size() != i - 1) {
									str.append("&");
								}

							}
							bombdata(str.toString().split("&"));
						} else {
							ContentData content = new ContentData();
							String[] result = null;
							if (type.equals("10001")) {
								result = content.getGaoxiaodata();
							} else if (type.equals("10002")) {
								result = content.getYoumodata();
							} else if (type.equals("10003")) {
								result = content.getQuweidata();
							} else if (type.equals("10004")) {
								result = content.getDongwudata();
							} else if (type.equals("10005")) {
								result = content.getJindiandata();
							} else if (type.equals("10006")) {
								result = content.getZhengrendata();
							} else if (type.equals("10007")) {
								result = content.getZonghedata();
							} else if (type.equals("10008")) {
								result = content.getTesedata();
							} else if (type.equals("10009")) {
								result = content.getFengqudata();
							}
							bombdata(result);
						}

					}
				});

			}
		});

		// return getTypeData(type);
		return null;
	}

	/**
	 * 通过方法将数据传输出来
	 * 
	 * @Description
	 * 
	 * @param @param returnData
	 * @param @return
	 * @return
	 * @throws
	 * @throws
	 * 
	 */
	private void bombdata(String[] returnData) {
		typecontent = returnData;
		int x = getTypeIndex(type); // 得到下标
		String content = returnData[x];
		String[] valuedata = content.split("#"); // 得到某一个，问题和答案
		tx_content.setText(valuedata[0]);

		findViewById(R.id.btn_left).setOnClickListener(this);
		findViewById(R.id.btn_right).setOnClickListener(this);
		findViewById(R.id.btn_content).setOnClickListener(this);
		findViewById(R.id.fuzhi).setOnClickListener(this);
		findViewById(R.id.fenxiang).setOnClickListener(this);
		findViewById(R.id.btn_daan).setOnClickListener(this);
		findViewById(R.id.content_shoucang_app).setOnClickListener(this);
	}

	// 设置类型下标(下一个)
	public void getContentright(String type, int i) {
		if (typecontent.length == i + 1) {
			i = 0;
		}
		if (type.equals("10001")) {
			index_info.putValue("gaoxiao_index", String.valueOf(i + 1));
		}
		if (type.equals("10002")) {
			index_info.putValue("youmo_index", String.valueOf(i + 1));
		}
		if (type.equals("10003")) {
			index_info.putValue("quwei_index", String.valueOf(i + 1));
		}
		if (type.equals("10004")) {
			index_info.putValue("dongwu_index", String.valueOf(i + 1));
		}
		if (type.equals("10005")) {
			index_info.putValue("jindian_index", String.valueOf(i + 1));
		}
		if (type.equals("10006")) {
			index_info.putValue("zhengren_index", String.valueOf(i + 1));
		}
		if (type.equals("10007")) {
			index_info.putValue("zonghe_index", String.valueOf(i + 1));
		}
		if (type.equals("10008")) {
			index_info.putValue("tese_index", String.valueOf(i + 1));
		}
		if (type.equals("10009")) {
			index_info.putValue("fengqu_index", String.valueOf(i + 1));
		}
	}

	// 设置类型下标(上一个)
	public void getContentleft(String type, int i) {
		if (type.equals("10001")) {
			if (i == 0) {
				index_info.putValue("gaoxiao_index", String.valueOf(0));
			} else {
				index_info.putValue("gaoxiao_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10002")) {
			if (i == 0) {
				index_info.putValue("youmo_index", String.valueOf(0));
			} else {
				index_info.putValue("youmo_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10003")) {
			if (i == 0) {
				index_info.putValue("quwei_index", String.valueOf(0));
			} else {
				index_info.putValue("quwei_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10004")) {
			if (i == 0) {
				index_info.putValue("dongwu_index", String.valueOf(0));
			} else {
				index_info.putValue("dongwu_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10005")) {
			if (i == 0) {
				index_info.putValue("jindian_index", String.valueOf(0));
			} else {
				index_info.putValue("jindian_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10006")) {
			if (i == 0) {
				index_info.putValue("zhengren_index", String.valueOf(0));
			} else {
				index_info.putValue("zhengren_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10007")) {
			if (i == 0) {
				index_info.putValue("zonghe_index", String.valueOf(0));
			} else {
				index_info.putValue("zonghe_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10008")) {
			if (i == 0) {
				index_info.putValue("tese_index", String.valueOf(0));
			} else {
				index_info.putValue("tese_index", String.valueOf(i - 1));
			}
		} else if (type.equals("10009")) {
			if (i == 0) {
				index_info.putValue("fengqu_index", String.valueOf(0));
			} else {
				index_info.putValue("fengqu_index", String.valueOf(i - 1));
			}
		}
	}

	// 得到类型下标
	public int getTypeIndex(String type) {
		int index = 0;
		if (type.equals("10001")) {
			if (index_info.getValue("gaoxiao_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("gaoxiao_index"));
			}
		} else if (type.equals("10002")) {
			if (index_info.getValue("youmo_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("youmo_index"));
			}
		} else if (type.equals("10003")) {
			if (index_info.getValue("quwei_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("quwei_index"));
			}
		} else if (type.equals("10004")) {
			if (index_info.getValue("dongwu_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("dongwu_index"));
			}
		} else if (type.equals("10005")) {
			if (index_info.getValue("jindian_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("jindian_index"));
			}
		} else if (type.equals("10006")) {
			if (index_info.getValue("zhengren_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("zhengren_index"));
			}
		} else if (type.equals("10007")) {
			if (index_info.getValue("zonghe_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("zonghe_index"));
			}
		} else if (type.equals("10008")) {
			if (index_info.getValue("tese_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("tese_index"));
			}
		} else if (type.equals("10009")) {
			if (index_info.getValue("fengqu_index") == null) {
				index = 0;
			} else {
				index = Integer.parseInt(index_info.getValue("fengqu_index"));
			}
		}
		return index;
	}

}
