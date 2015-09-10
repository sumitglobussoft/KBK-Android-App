package com.kbk.mobile_app.Activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Home_delivery.Fragment.My_Address_Fragment;
import com.kbk.mobile_app.Utils.KBKCallBack;
import com.kbk.mobile_app.Utils.KBKPostRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class Edit_Address_Activity extends Activity 
{
	public static String neighborhood,address,nickName,instructions;
	public static boolean isFromEditAddress = false;
	public EditText neigborhood_edit,address_edit,nickname_edit,instructioins_edit;
	public Button save_btn;
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_address);
		
		neigborhood_edit = (EditText) findViewById(R.id.neigborhood_edit);
		address_edit = (EditText) findViewById(R.id.address_edit);
		nickname_edit = (EditText) findViewById(R.id.nickname_edit);
		instructioins_edit = (EditText) findViewById(R.id.instructioins_edit);
		save_btn = (Button) findViewById(R.id.save_btn);
		
		save_btn.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				
				neighborhood = neigborhood_edit.getText().toString();
				address = address_edit.getText().toString();
				nickName = nickname_edit.getText().toString();
				instructions = instructioins_edit.getText().toString();
				// check whether email or password fields are empty or not
				if (neigborhood_edit.getText().toString().length() == 0) 
				{

					neigborhood_edit.setOnEditorActionListener(new OnEditorActionListener() 
					{

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
						{
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) 
							{
								if (neigborhood_edit.getText().toString().trim().equalsIgnoreCase(""))
									neigborhood_edit.setError("Please fill neighborhood");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}

							return false;
						}
					});

					neigborhood_edit.requestFocus();
					neigborhood_edit.setError("Please fill neighborhood");

				} else if (address_edit.getText().toString().length() == 0) {
					// password.setFocusableInTouchMode(true);

					address_edit.setOnEditorActionListener(new OnEditorActionListener() {

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) {
								if (address_edit.getText().toString().trim().equalsIgnoreCase(""))
									address_edit.setError("Please enter address");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}
							return false;
						}
					});

					address_edit.requestFocus();
					address_edit.setError("Enter your password");

				} else if (nickname_edit.getText().toString().length() == 0) {
					// password.setFocusableInTouchMode(true);

					nickname_edit.setOnEditorActionListener(new OnEditorActionListener() {

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) {
								if (nickname_edit.getText().toString().trim().equalsIgnoreCase(""))
									nickname_edit.setError("Please enter nickname");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}
							return false;
						}
					});

					nickname_edit.requestFocus();
					nickname_edit.setError("Please enter nickname");

				} else if (instructioins_edit.getText().toString().length() == 0) {
					// password.setFocusableInTouchMode(true);

					instructioins_edit.setOnEditorActionListener(new OnEditorActionListener() {

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) {
								if (instructioins_edit.getText().toString().trim().equalsIgnoreCase(""))
									instructioins_edit.setError("Please fill instructions");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}
							return false;
						}
					});

					instructioins_edit.requestFocus();
					instructioins_edit.setError("Please fill instructions");

				} else
				{
					isFromEditAddress = true;
//					Intent i = new Intent(Edit_Address_Activity.this,HomeActivity.class);
//					startActivity(i);
//					Edit_Address_Activity.this.finish();
					SaveAddress();
				}

			}
		});
		
	}	
	
    private void SaveAddress(){
		
		progressDialog = new ProgressDialog(Edit_Address_Activity.this);
		progressDialog.show();
		
		int responsecode = 0;
		Map<String,String> valuemap=null;
		
		JSONObject jsonObject = new JSONObject();
		 
		 try {
			 jsonObject.put("user_email", c_email);
			 jsonObject.put("user_password", c_password);
			 System.out.println("JSOn of signin "+jsonObject.toString());
			 valuemap  = new HashMap<String, String>();
			 valuemap.put("data", jsonObject.toString());
			 
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		 
		 KBKPostRequest kbkPostRequest=new KBKPostRequest(KookBook_Login_Activity.this);
		 kbkPostRequest.executeRequest("http://www.appdemoz.com/kookbook/connector/customer/sign_in",valuemap , new KBKCallBack(
				 ) {
			
			@Override
			public void onSuccess(JSONObject result) {

				if (null != progressDialog && progressDialog.isShowing()) {
					try {
						progressDialog.dismiss();
						progressDialog = null;

					} catch (Exception e) {
					}
				}
				try {
					JSONObject jsonObject = result;

					status = jsonObject.getString("status");

					if (status.equals("SUCCESS")) {
						
						JSONArray jsonarray = null;
						jsonarray = jsonObject.getJSONArray("data");
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject objJson = jsonarray.getJSONObject(i);

						    Singleton.cur_email = objJson.getString("user_email");
						    Singleton.cur_Name = objJson.getString("user_name");
						    Singleton.cur_id = objJson.getString("user_id");
						}

						System.out.println("Email***" + Singleton.cur_email);
						System.out.println("cur_Name***"
								+ Singleton.cur_Name);
						System.out.println("cur_id***"
								+ Singleton.cur_id);

						Toast t = Toast.makeText(getApplicationContext(),
								"Login Successful", Toast.LENGTH_LONG);
						t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0,
								0);
						View view = t.getView();

						t.show();

						Intent in1 = new Intent(KookBook_Login_Activity.this,HomeActivity.class);
						startActivity(in1);
						KookBook_Login_Activity.this.finish();

					} else if (status.equals("FAIL")) {

						Toast toast = Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
						View view = toast.getView();

						toast.show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			
			}
			
			@Override
			public void onFailure(Exception exception) {
				exception.printStackTrace();
				Toast toast = Toast.makeText(getApplicationContext(), "Exception in Request", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
				View view = toast.getView();
			}
		});
	}
	

}
