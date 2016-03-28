package com.yarin.mangoclasstable;

import java.util.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClassTableService
{
	private MyDatabaseHelper dbHelper;
	//初始化数据库
	public ClassTableService(Context context)
	{
		dbHelper=new MyDatabaseHelper(context);
		
	}
	//插入数据
	public void insertData(ClassTable table)
	{
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		db.execSQL("insert into tb_classTable values(null,?,?,?,?,?,?,?,?)",new Object[]{table.getClassName(),
			table.getWeekday(),table.getStartTime(),table.getEndTime(),table.getAddress(),table.getMemo(),table.gettName(),table.gettPhone()});
	}
	//取出tb_classTable中全部记录
	public List<ClassTable> getData()
	{
		List<ClassTable> list=new ArrayList<ClassTable>();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from tb_classTable", null);
		while(cursor.moveToNext())
		{
			ClassTable table=new ClassTable();
			table.setId(cursor.getColumnIndex("_id"));
			table.setClassName(cursor.getString(1));
			table.setWeekday(cursor.getInt(2));
			table.setStartTime(cursor.getInt(3));
			table.setEndTime(cursor.getInt(4));
			table.setAddress(cursor.getString(5));
			table.setMemo(cursor.getString(6));
			table.settName(cursor.getString(7));
			table.settPhone(cursor.getString(8));
			list.add(table);
		}
		cursor.close();  //关闭连接
		return list;
	}
	//按某种条件取出数据库中的信息
	public ClassTable find(int id)
	{
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from tb_classTable where _id="+id, null);
		ClassTable table=new ClassTable();
		if(cursor.moveToNext())
		{
			table.setId(cursor.getColumnIndex("_id"));
			table.setClassName(cursor.getString(1));
			table.setWeekday(cursor.getInt(2));
			table.setStartTime(cursor.getInt(3));
			table.setEndTime(cursor.getInt(4));
			table.setAddress(cursor.getString(5));
			table.setMemo(cursor.getString(6));
			table.settName(cursor.getString(7));
			table.settPhone(cursor.getString(8));
		}
		cursor.close();   //关闭连接
		return table;
	}
	//删除记录
	public boolean delete()
	{
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		return false;
	}
	//关闭数据库连接
	public void close()
	{
		if(dbHelper!=null){dbHelper.close();}
	}

}

