package com.kbk.mobile_app.Home_delivery.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Activities.HomeActivity;
import com.kbk.mobile_app.Activities.KookBook_Login_Activity;

public class Delivery_Details_Fragment extends Fragment
{
	View rootView;
	public TextView login_txt;
	public ImageView radio1,radio2,edit_contact_num,change_btn;
	public RelativeLayout rel7;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.kookbook_delivery_details, container, false);
		login_txt = (TextView) rootView.findViewById(R.id.login_box);
		radio1 = (ImageView) rootView.findViewById(R.id.radio1);
		radio2 = (ImageView) rootView.findViewById(R.id.radio2);
		rel7 = (RelativeLayout) rootView.findViewById(R.id.rel7);
		edit_contact_num = (ImageView) rootView.findViewById(R.id.edit_contact_num);
		change_btn = (ImageView) rootView.findViewById(R.id.change_btn);
		
		String text = "Already a kookbook member? <b>Login</b> here";
		login_txt.setText(Html.fromHtml(text));
		
		login_txt.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				Intent i = new Intent(getActivity(), KookBook_Login_Activity.class);
				startActivity(i);
				
				
			}
		});
		
		radio1.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				radio1.setImageResource(R.drawable.select);
				radio2.setImageResource(R.drawable.unselect);
				rel7.setVisibility(View.GONE);
				
				
			}
		});
		radio2.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				radio2.setImageResource(R.drawable.select);
				radio1.setImageResource(R.drawable.unselect);
				rel7.setVisibility(View.VISIBLE);
				
				
			}
		});
		
		edit_contact_num.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				 HomeActivity.fragmentmanager=getFragmentManager();
				 HomeActivity.SwipFragment(new KookBook_Number_Verification_Fragment());
				
				
			}
		});
		change_btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				 HomeActivity.fragmentmanager=getFragmentManager();
				 HomeActivity.SwipFragment(new KookBook_SelectAddress_Fragment());
				
				
			}
		});
		
		return rootView;
	}
	
}
