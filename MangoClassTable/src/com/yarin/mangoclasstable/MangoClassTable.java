package com.yarin.mangoclasstable;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;

public class MangoClassTable extends Activity {
	//自定义ImageButton上面显示的字体的大小
    //private float BTN_TEXTSIZE = 32f;
    //自定义ImageButton上面显示的字体的颜色
    //private int BTN_TEXTCOLOR = Color.BLACK;
	private ClassTableService classTableService;
	private ClassTable[][]table=new ClassTable[8][8];
    private String phonenumber;
    private int index=0;
    private LinearLayout[][] imageButton = new LinearLayout[8][8];  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mango_class_table);
        getActionBar().setDisplayHomeAsUpEnabled(true);         
        init();
       
        classTableService=new ClassTableService(this);
        List<ClassTable> list=classTableService.getData();
         Iterator<ClassTable> it=list.iterator();
         while(it.hasNext())
         {
         	int i=0;
         	int j=0;
         	ClassTable classTable=(ClassTable)it.next();
         	TextView text1=new TextView(this);
         	TextView text2=new TextView(this);
         	text1.setText(classTable.getClassName());
         	text2.setText(classTable.getAddress());
         	i=classTable.getWeekday();
         	if(classTable.getStartTime()==8)j=1;
         	if(classTable.getStartTime()==10)j=2;
         	if(classTable.getStartTime()==14)j=3;
         	if(classTable.getStartTime()==16)j=4;
         	imageButton[i][j].addView(text1);
         	imageButton[i][j].addView(text2);
         	table[i][j]=classTable;	

         }
         //设置触发事件
         for(int i=1;i<8;i++)
         {
         	for(int j=1;j<8;j++)
         	{   
         		String strName="";
         		String strAddress="";
         		String strTime="";
         		String strMemo="";
         		String strtName="";
         		String strtPhone="";
         		//phonenumber=null;
         	    if(table[i][j]!=null)
         	    {
         	       strName="课程："+table[i][j].getClassName();
         	       strAddress="教室："+table[i][j].getAddress();
         	       strTime="上课时间："+table[i][j].getStartTime()+"点--"+table[i][j].getEndTime()+"点";
         	       strMemo="备忘："+table[i][j].getMemo();
         	       strtName="任课老师："+table[i][j].gettName();
         	       strtPhone="老师电话："+table[i][j].gettPhone();
         	       //DisplayToast("table[i][j].gettPhone()"+table[i][j].gettPhone());
         	       phonenumber=table[i][j].gettPhone();
         	      //DisplayToast("phonenumber"+phonenumber);
         	    }
         	    else
         	    {
         	    strName="本节课自习！";
         	    }
         	    final String Msg=strName+"\n\n"+strAddress+"\n\n"+strTime+"\n\n"+strMemo+"\n\n"+strtName+"\n\n"+strtPhone;
         	    final String strAddress1=strAddress;
         		imageButton[i][j].setOnClickListener(new Button.OnClickListener()
         		{
         			

 					@Override
 					public void onClick(View v)
 					{
 						//DisplayToast(Msg);
 						new AlertDialog.Builder(MangoClassTable.this)
 						.setTitle("课程表")
 						.setMessage(Msg)
 						.setPositiveButton("拨打老师电话", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								try{
									if(strAddress1==""){
										
									}else{
										
									
									
									//DisplayToast("dianhai"+phonenumber);
									Intent myIntentDial=new Intent("android.intent.action.CALL",Uri.parse("tel:"+phonenumber));
									startActivity(myIntentDial);
									}
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						})
						.setNeutralButton("发送短讯", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(strAddress1==""){
									
								}else{
								final String[] strMsg={"生病了","家里有急事","外出参加活动"};
								new AlertDialog.Builder(MangoClassTable.this).setTitle("选择请假条")
								.setSingleChoiceItems(strMsg, 0, new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										index=which;	
									}
									
								})
								.setPositiveButton("确定", new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str="老师你好!,因为"+
												strMsg[index]+"需要请假。望老师批准！";
													DisplayToast(str);
										 String strDestAddress=phonenumber;
										  SmsManager smsManager=SmsManager.getDefault();
										  if(iswithin70(str)==true){
											  try{
												   //条件通过的情况下，发送短讯
												   //先构建PendingIntent对象并使用getBroadcast（）广播
												   //将PendingIntent，电话，短讯文字等参数
												   //传入sendTextMessage（）方法短讯
			                                       PendingIntent mPI=PendingIntent.getBroadcast(MangoClassTable.this, 0,new Intent(), 0);
			                                       smsManager.sendTextMessage(strDestAddress, null,str, mPI, null);
			                                       
											   }catch(Exception e){
												   e.printStackTrace();
											   }
											   DisplayToast("送出成功");
										  }else{
											   DisplayToast("短讯内容超过70个字，请删除部分");
										   }

									}
									
								})
								.setNeutralButton("取消", new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										
									}
									
								}).show();//选择请假条界面的show
								}



								// TODO Auto-generated method stub
								/*String strDestAddress=phonenumber;
								String strMessage="老师，我要请假";
							   SmsManager smsManager=SmsManager.getDefault();
							   if(iswithin70(strMessage)==true){
								   try{
									   //条件通过的情况下，发送短讯
									   //先构建PendingIntent对象并使用getBroadcast（）广播
									   //将PendingIntent，电话，短讯文字等参数
									   //传入sendTextMessage（）方法短讯
                                       PendingIntent mPI=PendingIntent.getBroadcast(MangoClassTable.this, 0,new Intent(), 0);
                                       smsManager.sendTextMessage(strDestAddress, null,strMessage, mPI, null);
                                       
								   }catch(Exception e){
									   e.printStackTrace();
								   }
								   DisplayToast("送出成功");
								   
							   }else{
								   DisplayToast("短讯内容超过70个字，请删除部分");
							   } */
							}

							private boolean iswithin70(String strMessage) {
								// TODO Auto-generated method stub
								if(strMessage.length()<=70)
								{
									return true;								
								}
								else{
								return false;}
							}
						})
    					    .show();

 						
 					}		//onclick	
         		});//setonclick
         	}
         }
       }
    public void init(){
    	 imageButton[1][1]=(LinearLayout) findViewById(R.id.imagebutton1_1);
         imageButton[1][2]=(LinearLayout)findViewById(R.id.imagebutton1_2);
         imageButton[1][3]=(LinearLayout)findViewById(R.id.imagebutton1_3);
         imageButton[1][4]=(LinearLayout)findViewById(R.id.imagebutton1_4);
         imageButton[1][5]=(LinearLayout)findViewById(R.id.imagebutton1_5);
         imageButton[1][6]=(LinearLayout)findViewById(R.id.imagebutton1_6);
         imageButton[1][7]=(LinearLayout)findViewById(R.id.imagebutton1_7);
         
         imageButton[2][1]=(LinearLayout)findViewById(R.id.imagebutton2_1);
         imageButton[2][2]=(LinearLayout)findViewById(R.id.imagebutton2_2);
         imageButton[2][3]=(LinearLayout)findViewById(R.id.imagebutton2_3);
         imageButton[2][4]=(LinearLayout)findViewById(R.id.imagebutton2_4);
         imageButton[2][5]=(LinearLayout)findViewById(R.id.imagebutton2_5);
         imageButton[2][6]=(LinearLayout)findViewById(R.id.imagebutton2_6);
         imageButton[2][7]=(LinearLayout)findViewById(R.id.imagebutton2_7);
         
         imageButton[3][1]=(LinearLayout)findViewById(R.id.imagebutton3_1);
         imageButton[3][2]=(LinearLayout)findViewById(R.id.imagebutton3_2);
         imageButton[3][3]=(LinearLayout)findViewById(R.id.imagebutton3_3);
         imageButton[3][4]=(LinearLayout)findViewById(R.id.imagebutton3_4);
         imageButton[3][5]=(LinearLayout)findViewById(R.id.imagebutton3_5);
         imageButton[3][6]=(LinearLayout)findViewById(R.id.imagebutton3_6);
         imageButton[3][7]=(LinearLayout)findViewById(R.id.imagebutton3_7);
         
         imageButton[4][1]=(LinearLayout)findViewById(R.id.imagebutton4_1);
         imageButton[4][2]=(LinearLayout)findViewById(R.id.imagebutton4_2);
         imageButton[4][3]=(LinearLayout)findViewById(R.id.imagebutton4_3);
         imageButton[4][4]=(LinearLayout)findViewById(R.id.imagebutton4_4);
         imageButton[4][5]=(LinearLayout)findViewById(R.id.imagebutton4_5);
         imageButton[4][6]=(LinearLayout)findViewById(R.id.imagebutton4_6);
         imageButton[4][7]=(LinearLayout)findViewById(R.id.imagebutton4_7);
         
         imageButton[5][1]=(LinearLayout)findViewById(R.id.imagebutton5_1);
         imageButton[5][2]=(LinearLayout)findViewById(R.id.imagebutton5_2);
         imageButton[5][3]=(LinearLayout)findViewById(R.id.imagebutton5_3);
         imageButton[5][4]=(LinearLayout)findViewById(R.id.imagebutton5_4);
         imageButton[5][5]=(LinearLayout)findViewById(R.id.imagebutton5_5);
         imageButton[5][6]=(LinearLayout)findViewById(R.id.imagebutton5_6);
         imageButton[5][7]=(LinearLayout)findViewById(R.id.imagebutton5_7);
         
         imageButton[6][1]=(LinearLayout)findViewById(R.id.imagebutton6_1);
         imageButton[6][2]=(LinearLayout)findViewById(R.id.imagebutton6_2);
         imageButton[6][3]=(LinearLayout)findViewById(R.id.imagebutton6_3);
         imageButton[6][4]=(LinearLayout)findViewById(R.id.imagebutton6_4);
         imageButton[6][5]=(LinearLayout)findViewById(R.id.imagebutton6_5);
         imageButton[6][6]=(LinearLayout)findViewById(R.id.imagebutton6_6);
         imageButton[6][7]=(LinearLayout)findViewById(R.id.imagebutton6_7);
         
         imageButton[7][1]=(LinearLayout)findViewById(R.id.imagebutton7_1);
         imageButton[7][2]=(LinearLayout)findViewById(R.id.imagebutton7_2);
         imageButton[7][3]=(LinearLayout)findViewById(R.id.imagebutton7_3);
         imageButton[7][4]=(LinearLayout)findViewById(R.id.imagebutton7_4);
         imageButton[7][5]=(LinearLayout)findViewById(R.id.imagebutton7_5);
         imageButton[7][6]=(LinearLayout)findViewById(R.id.imagebutton7_6);
         imageButton[7][7]=(LinearLayout)findViewById(R.id.imagebutton7_7);
    }
	public void DisplayToast(String str)
    {
    	Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mango_class_table, menu);
        menu.add(0, 0, 0, R.string.app_about);
        menu.add(0,1,1,R.string.str_exit);
        menu.add(0, 2, 2, R.string.app_setalert);
        menu.add(0,3,3,R.string.app_setclass);
        menu.add(0,4,4,R.string.class_list);
        menu.add(0,5,5,R.string.set_ring);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 0:
        	openOptionsDialog();
        	break;
        case 1:
        	openFinishDialog();
        	break;
        case 2:
        	//开启设置闹钟画面
        	Intent intent2=new Intent();
        	intent2.setClass(MangoClassTable.this,SetAlarm.class);
        	startActivity(intent2);
        	MangoClassTable.this.finish();
        	break;
        case 3:
        	Intent intent=new Intent();
        	//intent.setClass(MangoClassTable.class,AddClass.class);
        	intent.setClass(MangoClassTable.this,AddClass.class);
        	startActivity(intent);
        	MangoClassTable.this.finish();
        	break;
        case 4:
        	Intent intent4=new Intent();
        	intent4.setClass(MangoClassTable.this, ShowAllClasses.class);
        	startActivity(intent4);
        	MangoClassTable.this.finish();   	
        	break;
        case 5:
        	Intent intent5=new Intent();
        	intent5.setClass(MangoClassTable.this, AlarmAlert.class);
        	startActivity(intent5);
        	MangoClassTable.this.finish();
        	break;
        
        	
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void openFinishDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle(R.string.str_exit)
		.setMessage(R.string.str_exit_msg)
		.setNegativeButton(R.string.str_no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		})
		.show();
	}

	private void openOptionsDialog()//显示关于信息 {
		// TODO Auto-generated method stub
	{
	new AlertDialog.Builder(this)
	.setTitle(R.string.app_about)
	.setMessage(R.string.app_about_msg)
	.setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	}).show();
	
		
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
