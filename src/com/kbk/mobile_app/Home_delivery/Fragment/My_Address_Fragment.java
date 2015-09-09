package com.kbk.mobile_app.Home_delivery.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Activities.Edit_Address_Activity;
import com.kbk.mobile_app.Activities.KookBook_Login_Activity;

public class My_Address_Fragment extends Fragment
{

	public TextView Name1,Name2,address1,address2;
	public ImageView name1_edit_icon,name2_edit_icon;
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.my_address, container, false);
		
		Name1 = (TextView) rootView.findViewById(R.id.Name1);
		Name2 = (TextView) rootView.findViewById(R.id.Name2);
		address1 = (TextView) rootView.findViewById(R.id.address1);
		address2 = (TextView) rootView.findViewById(R.id.address2);
		name1_edit_icon = (ImageView) rootView.findViewById(R.id.name1_edit_icon);
		name2_edit_icon = (ImageView) rootView.findViewById(R.id.name2_edit_icon);
		
//		System.out.println("Edit_Address_Activity.isFromEditAddress"+Edit_Address_Activity.isFromEditAddress);
//		System.out.println("Edit_Address_Activity.neighborhood"+Edit_Address_Activity.neighborhood);
//		if(Edit_Address_Activity.isFromEditAddress){
//		      address1.setText(Edit_Address_Activity.neighborhood);
//		}
		
		name1_edit_icon.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				Intent i = new Intent(getActivity(), Edit_Address_Activity.class);
				startActivity(i);
				
				
			}
		});
		name2_edit_icon.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				Intent i = new Intent(getActivity(), Edit_Address_Activity.class);
				startActivity(i);
				
				
			}
		});
		
		return rootView;
	}

}
