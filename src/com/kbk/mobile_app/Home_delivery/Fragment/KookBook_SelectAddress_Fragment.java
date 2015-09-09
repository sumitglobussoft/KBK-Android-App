package com.kbk.mobile_app.Home_delivery.Fragment;

import com.kbk.mobile_app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KookBook_SelectAddress_Fragment extends Fragment
{
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.kookbook_select_address, container, false);
		return rootView;
	}

}
