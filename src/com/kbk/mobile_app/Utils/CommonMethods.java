package com.kbk.mobile_app.Utils;

import android.content.Context;
import android.widget.Toast;

public class CommonMethods
{
	
	public static void showMyToast(Context context,String text) 
	{
		
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		
	}

}
