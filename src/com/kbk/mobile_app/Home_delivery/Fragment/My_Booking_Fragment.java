package com.kbk.mobile_app.Home_delivery.Fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Utils.KBKCallBack;
import com.kbk.mobile_app.Utils.KBKPostRequest;

public class My_Booking_Fragment extends Fragment
{
	View rootView;
	ListView listview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.my_booking, container, false);
		listview = (ListView) rootView.findViewById(R.id.bookingList);
		GetBookingList();
		return rootView;
	}
	
	private void GetBookingList(){
		
		Map<String,String> valuemap=null;
		JSONObject jsonObject = new JSONObject();
		
		 try {
			 jsonObject.put("user_email", "");
			 jsonObject.put("user_password", "");
			 System.out.println("JSOn of signin "+jsonObject.toString());
			 valuemap  = new HashMap<String, String>();
			 valuemap.put("data", jsonObject.toString());
			 
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		KBKPostRequest kbkPostRequest=new KBKPostRequest(getActivity());
		kbkPostRequest.executeRequest("", valuemap, new KBKCallBack() {
			
			@Override
			public void onSuccess(JSONObject result) {
				
				System.out.println("RESULT == "+result);
				
			}
			
			@Override
			public void onFailure(Exception exception) {
				
			}
		});
	}
}
