package com.kbk.mobile_app.Home_delivery.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Activities.HomeActivity;
import com.kbk.mobile_app.Utils.CommonMethods;

public class KookBook_Number_Verification_Fragment extends Fragment
{
	View rootView;
	EditText phone_edit;
	ImageView verify_img;
	public String c_phno;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.kookbook_verify_number, container, false);
		
		phone_edit = (EditText) rootView.findViewById(R.id.phone_edit);
		verify_img = (ImageView) rootView.findViewById(R.id.verify_img);
		
		phone_edit.setOnEditorActionListener(new OnEditorActionListener() 
		{

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) 
			{

				if (arg1 == EditorInfo.IME_ACTION_NEXT)
				{
					if (phone_edit.getText().toString().trim().equalsIgnoreCase(""))
						phone_edit.setError("Enter your e-mail");
					else 
					{
						CommonMethods.showMyToast(getActivity(), "Notnull");
					}
				}
				return false;
			}
		});
		
		verify_img.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				   boolean check = false;
				   check = validatInputs();
				   if(check)
				   {
					   CommonMethods.showMyToast(getActivity(), "You will receive code");
					   HomeActivity.fragmentmanager=getFragmentManager();
					   HomeActivity.SwipFragment(new Delivery_Details_Fragment());
				   }
			}
		});
		
		return rootView;
	}
	
	private boolean validatInputs() 
	{
		
		String phoneNumber;
		boolean validPhno = false;
		phoneNumber = phone_edit.getText().toString();

		if (phoneNumber.length() >0) 
		{
			if (phoneNumber.length() ==10) 
			{
				validPhno = true;
			}
			else
			{
				phone_edit.setError("Enter valid Phone Number");
				phone_edit.requestFocus();
				return false;
			}	
		} 
		else 
		{
//			CommonMethods.showMyToast(getActivity(), "Enter Phone Number");
			phone_edit.setError("Enter Phone Number");
			phone_edit.requestFocus();
			return false;
		}
		
		if (validPhno) 
		{
			return true;
		}
		//warninglayout.setVisibility(View.VISIBLE);
		
		return false;
	}
	

}
