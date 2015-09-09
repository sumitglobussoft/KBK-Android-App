package com.kbk.mobile_app.adapters;

import java.util.List;

import com.kbk.mobile_app.models.MyBookingModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyBookingRowAdapter extends ArrayAdapter<MyBookingModel>
{
	private Activity activity;
	private List<MyBookingModel> items;
	private MyBookingModel objBean;
	private int row;
	final Context context = getContext();
	private static LayoutInflater inflater = null;
	
	public MyBookingRowAdapter(Activity act, int resource,List<MyBookingModel> arrayList)
	{
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;

		System.out.println("QuestionRowAdapter*********************");
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if (convertView == null) 
		{

			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(row, null);

			holder = new ViewHolder();
			convertView.setTag(holder);	
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			objBean = items.get(position);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return convertView;
		
	}
	
	public class ViewHolder 
	{
		public TextView name, address,date,time,status;
	
	}

}
