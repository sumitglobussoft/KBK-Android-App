package com.kbk.mobile_app.Home_delivery.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kbk.mobile_app.R;

public class Post_Orders_Fragment extends Fragment
{

	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.post_orders, container, false);
		return rootView;
	}

}
