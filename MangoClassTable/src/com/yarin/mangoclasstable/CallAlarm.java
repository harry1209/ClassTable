package com.yarin.mangoclasstable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CallAlarm extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

		//create intent，调用alarm。class‘
		Intent i=new Intent(arg0,AlarmAlert.class);
		Bundle bundleRet=new Bundle();
		bundleRet.putString("STR_CALLER", "");
		i.putExtras(bundleRet);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    arg0.startActivity(i);
	    
		
	}

}
