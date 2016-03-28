package com.yarin.mangoclasstable;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
public class MyDatabaseHelper extends SQLiteOpenHelper 
{
	private static final String CREATE_TABLE="create table tb_classTable(_id integer primary key autoincrement,className text," +
			"weekday integer,startTime integer,endTime integer,address text,memo text,tName text,tPhone text)";
	private static final String NAME="mytable.db";
	private static final int version=1;
	public MyDatabaseHelper(Context context) 
	{
		super(context, NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS tb_classTable");
		onCreate(db);
		System.out.println("---------------onUpgrade Called-----------------"+oldVersion+"------>"+newVersion);
		
	}

}
