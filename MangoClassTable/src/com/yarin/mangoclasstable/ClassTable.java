package com.yarin.mangoclasstable;
public class ClassTable 
{
	private int id;            
	private String className; 
	private int weekday;     
	private int startTime;      
	private int endTime;      
	private String address;     
	private String memo;       
	private String tName;// 老师名字
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettPhone() {
		return tPhone;
	}
	public void settPhone(String tPhone) {
		this.tPhone = tPhone;
	}
	private String tPhone;//老师电话
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getClassName() 
	{
		return className;
	}
	public void setClassName(String className) 
	{
		this.className = className;
	}
	public int getWeekday() 
	{
		return weekday;
	}
	public void setWeekday(int weekday) 
	{
		this.weekday = weekday;
	}
	public int getStartTime() 
	{
		return startTime;
	}
	public void setStartTime(int startTime) 
	{
		this.startTime = startTime;
	}
	public int getEndTime() 
	{
		return endTime;
	}
	public void setEndTime(int endTime) 
	{
		this.endTime = endTime;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	public String getMemo() 
	{
		return memo;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	public String toString()
	{
		return "ClassTable[id="+id+",className="+className+",weekday="+weekday
				+",startTime="+startTime+",endTime="+endTime+",address="+address+
				",memo="+memo+"]";
	}
	

}
