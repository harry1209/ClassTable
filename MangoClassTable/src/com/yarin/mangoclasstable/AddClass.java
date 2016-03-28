package com.yarin.mangoclasstable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



public class AddClass extends Activity {

	private Button addButton;     //添加按钮
	private EditText editName;    //输入课程名
	private EditText editAddress; //输入上课地址
	private EditText editMemo;    //输入备忘录
	private EditText editTname;//老师姓名
	private EditText editTphone;//老师电话
	private TextView  text;
	
	int weekday;      //记录星期数
	int startTime;    //记录课程开始时间
	int endTime;      //记录课程结束时间
    String tNname;//老师姓名
    String tPhone;//老师手机
	private RadioButton zhouyi;  //周一
	private RadioButton zhouer;  //周二
	private RadioButton zhousan; //周三
	private RadioButton zhousi;  //周四
	private RadioButton zhouwu;  //周五
	private RadioButton zhouliu; //周六
	private RadioButton zhouri;  //周日
	private RadioButton start8;  //8点开课
	private RadioButton start10; //10点开课
	private RadioButton start14; //14点开课
	private RadioButton start16; //16点开课
	private RadioButton end10;   //10点下课
	private RadioButton end12;   //12点下课
	private RadioButton end16;   //16点下课
	private RadioButton end18;   //18点下课
	
	private ClassTable  table;    //课程表类 
	private ClassTableService classTableService;
	
	//private Button botton1;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		 classTableService=new ClassTableService(this);
	
		
         init();


		
		addButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				{
					if(zhouyi.isChecked())weekday=1;
					if(zhouer.isChecked())weekday=2;
					if(zhousan.isChecked())weekday=3;
					if(zhousi.isChecked())weekday=4;
					if(zhouwu.isChecked())weekday=5;
					if(zhouliu.isChecked())weekday=6;
					if(zhouri.isChecked())weekday=7;
					if(start8.isChecked())startTime=8;
					if(start10.isChecked())startTime=10;
					if(start14.isChecked())startTime=14;
					if(start16.isChecked())startTime=16;
					if(end10.isChecked())endTime=10;
					if(end12.isChecked())endTime=12;
					if(end16.isChecked())endTime=16;
					if(end18.isChecked())endTime=18;
				 }
				table=new ClassTable();
				table.setClassName(editName.getText().toString());
				table.setAddress(editAddress.getText().toString());
				table.setMemo(editMemo.getText().toString());
				table.setStartTime(startTime);
				table.setEndTime(endTime);
				table.setWeekday(weekday);
				table.settName(editTname.getText().toString());
				table.settPhone(editTphone.getText().toString());
				classTableService.insertData(table);
				Toast.makeText(AddClass.this, "添加成功！", 8000).show();


				
			}
			
		});
	}
	
	public void init(){//初始化单选项
		 addButton=(Button)findViewById(R.id.button1);
		 editName=(EditText)findViewById(R.id.editText1);
	     editAddress=(EditText)findViewById(R.id.editText2);
	     editMemo=(EditText)findViewById(R.id.editText3);
	     editTname=(EditText)findViewById(R.id.editText_tname);
	     editTphone=(EditText)findViewById(R.id.editText_tphone);
		zhouyi=(RadioButton)findViewById(R.id.radioButton_zhouyi);
		zhouer=(RadioButton)findViewById(R.id.radioButton_zhouer);
		zhousan=(RadioButton)findViewById(R.id.radioButton_zhousan);
		zhousi=(RadioButton)findViewById(R.id.radioButton_zhousi);
		zhouwu=(RadioButton)findViewById(R.id.radioButton_zhouwu);
		zhouliu=(RadioButton)findViewById(R.id.radioButton_zhouliu);
		zhouri=(RadioButton)findViewById(R.id.radioButton_zhouri);
		start8=(RadioButton)findViewById(R.id.radioButton1);
		start10=(RadioButton)findViewById(R.id.radioButton2);
		start14=(RadioButton)findViewById(R.id.radioButton3);
		start16=(RadioButton)findViewById(R.id.radioButton4);
		end10=(RadioButton)findViewById(R.id.radioButton5);
		end12=(RadioButton)findViewById(R.id.radioButton6);
		end16=(RadioButton)findViewById(R.id.radioButton7);
		end18=(RadioButton)findViewById(R.id.radioButton8);
		
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
			intent.setClass(AddClass.this, MangoClassTable.class);
			startActivity(intent);
			AddClass.this.finish();
		
		}
		return true;
		
	}
	public void onDestroy()
    {
    	super.onDestroy();
    	if(classTableService!=null)
    	{
    		classTableService.close();
    	}
    }

}
