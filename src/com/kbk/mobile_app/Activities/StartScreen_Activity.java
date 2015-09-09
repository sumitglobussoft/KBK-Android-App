package com.kbk.mobile_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbk.mobile_app.R;

public class StartScreen_Activity extends FragmentActivity implements OnItemClickListener
{

	public ImageView loginWithfb,loginWithGplus;
	public Button signup_btn,login_btn;
	SharedPreferences.Editor editor,editor1;
	public TextView skip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		loginWithfb = (ImageView) findViewById(R.id.loginWithfb);
		loginWithGplus = (ImageView) findViewById(R.id.loginWithGplus);
		signup_btn = (Button) findViewById(R.id.signup_btn);
		login_btn = (Button) findViewById(R.id.login_btn);
		skip = (TextView) findViewById(R.id.skip);
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		editor = sharedPref.edit();

		SharedPreferences preferences= this.getSharedPreferences("KookBook", Context.MODE_PRIVATE);
		editor1 = preferences.edit();
		
		skip.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
					Intent in1 = new Intent(StartScreen_Activity.this, HomeActivity.class);
					startActivity(in1);
				
				
			}
		});
		
		login_btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
					Intent in1 = new Intent(StartScreen_Activity.this, KookBook_Login_Activity.class);
					startActivity(in1);
				
				
			}
		});

		signup_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent in2 = new Intent(StartScreen_Activity.this,KookBook_SignUp_Activity.class);
				startActivity(in2);
				 
			}
		});
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
