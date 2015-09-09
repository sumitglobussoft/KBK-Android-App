package com.kbk.mobile_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.kbk.mobile_app.R;

public class SplashScreenActivity extends FragmentActivity 
{

	SharedPreferences.Editor editor1;
	public ImageView image;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		

		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash_activity);
		SharedPreferences preferences = this.getSharedPreferences("KookBook", Context.MODE_PRIVATE);
		editor1 = preferences.edit();
		handler = new Handler();

		image = (ImageView) findViewById(R.id.kookbookimage);

		TranslateAnimation translation;
		translation = new TranslateAnimation(0f, 0f, -200f, getDisplayWidth() / 2f);
		translation.setStartOffset(100);
		translation.setDuration(2000);
		translation.setFillAfter(true);
		translation.setInterpolator(new BounceInterpolator());
		image.startAnimation(translation);

		Thread background = new Thread() 
		{
			public void run() 
			{

				try 
				{
					sleep(1 * 3000);
					Intent in1 = new Intent(SplashScreenActivity.this,StartScreen_Activity.class);
					startActivity(in1);
					SplashScreenActivity.this.finish();

				} catch (Exception e) 
				{
				}
			}
		};

		background.start();
	}

	private int getDisplayWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	@Override
	protected void onResume() {

		super.onResume();
	}
}
