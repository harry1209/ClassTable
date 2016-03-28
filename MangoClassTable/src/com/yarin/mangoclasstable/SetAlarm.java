package com.yarin.mangoclasstable;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity {
 
	TextView setTime1,textview_weekday,textview_starttime;
	Button mButton1;
	Button mButton2;
	EditText edittext1;
	Calendar c=Calendar.getInstance();
	int id;
	private ClassTableService classTableService;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		
		//对所有的闹钟的设置
		setTime1=(TextView) findViewById(R.id.number1);
		classTableService=new ClassTableService(this);
	
		textview_weekday=(TextView) findViewById(R.id.textView_week);
		textview_starttime=(TextView) findViewById(R.id.textView_starttime);
		
		mButton1=(Button)findViewById(R.id.mButton1);
		edittext1=(EditText)findViewById(R.id.timebefore);
		mButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<ClassTable> list=classTableService.getData();
				setTime1.setText(String.valueOf(list.size()));
				 Iterator<ClassTable> it=list.iterator();		
				//timebefore为提前多长时间闹钟
				//timemillisbefore为提前多少毫秒
				 //weeklongtime为一周的毫秒数
				int timebefore=Integer.parseInt(edittext1.getText().toString());
				long minutebefore=Long.parseLong(edittext1.getText().toString());
				long timemillisbefore=minutebefore*60*1000;
				long weeklongtime=7*24*60*60*1000;
				 id=0;
				 AlarmManager am;
				am=(AlarmManager) getSystemService(ALARM_SERVICE);
				 while(it.hasNext()){
					 
					 ClassTable classTable=(ClassTable)it.next();
					 textview_weekday.setText(String.valueOf(classTable.getWeekday()));
					 textview_starttime.setText(String.valueOf(classTable.getStartTime()));
					 //取得具体课程的时间。
					 c.setTimeInMillis(System.currentTimeMillis());
					 c.set(Calendar.DAY_OF_WEEK,classTable.getWeekday());
					 c.set(Calendar.HOUR_OF_DAY, classTable.getStartTime());
					 c.set(Calendar.MINUTE, 0);
					 c.set(Calendar.SECOND, 0);
					 c.set(Calendar.MILLISECOND, 0);
					//指定闹钟设置时间到时要运行CallAlarm.class
						Intent intent=new Intent(SetAlarm.this,CallAlarm.class);
						//创建pendingIntent
						PendingIntent sender=PendingIntent.getBroadcast(SetAlarm.this, id, intent, 0);
						id=id+1;
						
						am.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()+weeklongtime-timemillisbefore,weeklongtime, sender);
						DisplayToast(id+"闹钟设置成功，提前："+timebefore+"分钟");
						
				 }
				 
				
				
			}
		});
		
		//所有的闹钟的删除
		mButton2=(Button) findViewById(R.id.mButton2);
		mButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<ClassTable> list=classTableService.getData();
				Iterator<ClassTable> it=list.iterator();	
				id=id-1;
				AlarmManager am;
				am=(AlarmManager)getSystemService(ALARM_SERVICE);
				Intent intent=new Intent(SetAlarm.this,CallAlarm.class);
				while(id>=0){
					
				
				PendingIntent sender=PendingIntent.getBroadcast(SetAlarm.this, id, intent, 0);
				am.cancel(sender);
				DisplayToast(id+"闹钟已删除");
				id--;
				}
				
				/*
				 while(it.hasNext()){
					// TODO Auto-generated method stub
						Intent intent=new Intent(SetAlarm.this,CallAlarm.class);
						PendingIntent sender=PendingIntent.getBroadcast(SetAlarm.this, id, intent, 0);
						
						//从AlarmManager中删除
						
						am.cancel(sender);
					
						id=id+1;
						//以toast提示已删除信息，并更新显示的闹钟时间
						Toast.makeText(SetAlarm.this, id+"闹钟时钟删除", Toast.LENGTH_SHORT).show();
				 }
				*/
			
			setTime1.setText("当前无闹钟设置");
			}
		});
	}
	private String format(int x){
		String s=""+x;
		if(s.length()==1) s="0"+s;
		return s;
	}
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,0,0,R.string.str_return);
		return true;
		
	}
	public void DisplayToast(String str)
    {
    	Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
	public boolean onOptionsItemSelected(MenuItem item){
		int item_id=item.getItemId();
		switch(item_id){
		case 0:
			Intent intent=new Intent();
			intent.setClass(SetAlarm.this, MangoClassTable.class);
			startActivity(intent);
			SetAlarm.this.finish();
	
		}
		return true;
		
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
