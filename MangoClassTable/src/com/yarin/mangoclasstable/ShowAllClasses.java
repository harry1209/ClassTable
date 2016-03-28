package com.yarin.mangoclasstable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowAllClasses extends Activity {
	private static final String[] array={"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
	
	LinearLayout myLinearLayout;
	TextView myTextView;
	ListView myListView;
	
	private ClassTable  table;    //课程表类 
	private ClassTableService classTableService;
	List<ClassTable> listtable;
	public void onCreate(Bundle savedInstanceState){
	
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.dialog);
		
		
		//从数据库读数据
		classTableService=new ClassTableService(this);
		listtable=classTableService.getData();
		//所有课程名保存至arraylist1
		ArrayList<String> arraylist1=new ArrayList();
		//DisplayToast("数据库有"+listtable.size());
	    for( ClassTable classtable:listtable){
	    	DisplayToast("详细信息："+classtable.getClassName());
	    	arraylist1.add(classtable.getClassName());
	    }
	    arraylist1.add("ceshi1");
	    arraylist1.add("ceshi2");
	    arraylist1.add("ceshi3");

	    
		//添加LinearLayout
		 myLinearLayout=new LinearLayout(this);
		myLinearLayout.setOrientation(LinearLayout.VERTICAL);
		//添加TextView
		myTextView=new TextView(this);
		LinearLayout.LayoutParams param1=new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		myTextView.setText("所有的课程列表：");
		myTextView.setTextColor(Color.GREEN);
		//将TextView添加到myLinearLayout
		myLinearLayout.addView(myTextView, param1);
		
		//添加ListView
		myListView=new ListView(this);
		LinearLayout.LayoutParams param2=new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		myListView.setBackgroundColor(android.graphics.Color.LTGRAY);
		//myListView添加到myLinearLayout
		myLinearLayout.addView(myListView, param2);
		//myLinaerlayout添加到Contentview
		myLinearLayout.setBackgroundResource(R.drawable.back);
		setContentView(myLinearLayout);
		
		//new ArrayAdapter对象并将array字符串数组传人
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.my_simple_list_item,arraylist1);
		//将ArrayAdapter添加到ListView对象中
		myListView.setAdapter(adapter);
		//myListView添加OnItemSelectedListener
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			myTextView.setText("你选了");	
			}
		
		});
		
	}
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,0,0,R.string.str_return);
		
		return true;
		
	}
	public boolean onOptionsItemSelected(MenuItem item){
		int item_id=item.getItemId();
		switch(item_id){
		case 0:
			Intent intent=new Intent();
			intent.setClass(ShowAllClasses.this, MangoClassTable.class);
			startActivity(intent);
			ShowAllClasses.this.finish();
		}
		return true;
		
	}
	public void DisplayToast(String str)
    {
    	Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
	@Override
	public void onDestroy()
    {
    	super.onDestroy();
    	if(classTableService!=null)
    	{
    		classTableService.close();
    	}
    }
}
