package com.cdjysdkj.diary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cdjysdkj.diary.tab.DillTab;
import com.cdjysdkj.diary.tab.ImageDiaryTab;
import com.cdjysdkj.diary.tab.TextDiaryTab;
import com.cdjysdkj.diary.tabhelper.DatabaseUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	private static final String DB_NAME = "diary.db";
	private static final int DB_VERSION = 2;//数据库版本
	@SuppressWarnings("unused")
	private static final String TAG = "DbHelper";

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			//建表
			TableUtils.createTable(connectionSource, TextDiaryTab.class);
			TableUtils.createTable(connectionSource, ImageDiaryTab.class);
			TableUtils.createTable(connectionSource, DillTab.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			//更新表
			DatabaseUtil.upgradeTable(database,connectionSource, DillTab.class,DatabaseUtil.OPERATION_TYPE.ADD);//添加字段
			TableUtils.createTable(connectionSource, TextDiaryTab.class);
			TableUtils.createTable(connectionSource, ImageDiaryTab.class);

			onCreate(database, connectionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
