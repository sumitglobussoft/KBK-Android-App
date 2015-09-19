package com.kbk.mobile_app.Activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Utils.KBKCallBack;
import com.kbk.mobile_app.Utils.KBKPostRequest;

public class KookBook_Login_Activity extends Activity 
{

	public EditText email, password;
	SharedPreferences.Editor editor, editor1;
	public ImageView close, login;
	public TextView forgotPassword;
	public String status, message, c_password, c_email,id;
	ProgressDialog progressDialog;
	
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kookbook_login);

		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		editor = sharedPref.edit();

		SharedPreferences preferences = this.getSharedPreferences("KookBook", Context.MODE_PRIVATE);
		editor1 = preferences.edit();

		// Initialize the layout item identity
		login = (ImageView) findViewById(R.id.login);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);

		close = (ImageView) findViewById(R.id.close);
		forgotPassword = (TextView) findViewById(R.id.forgotPassword);

		close.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{

				Intent in1 = new Intent(KookBook_Login_Activity.this, StartScreen_Activity.class);
				startActivity(in1);
				KookBook_Login_Activity.this.finish();

			}
		});

		forgotPassword.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{

				Intent in1 = new Intent(KookBook_Login_Activity.this, Kookbook_ForgotPassword_Activity.class);
				startActivity(in1);
				KookBook_Login_Activity.this.finish();

			}
		});

		email.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
			{

				if (arg1 == EditorInfo.IME_ACTION_NEXT) 
				{
					if (email.getText().toString().trim().equalsIgnoreCase(""))
						email.setError("Enter your e-mail");
					else {
						Toast.makeText(getApplicationContext(), "Notnull",
								Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});
		password.setOnEditorActionListener(new OnEditorActionListener() 
		{
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
			{
				if (arg1 == EditorInfo.IME_ACTION_NEXT) 
				{
					if (password.getText().toString().trim().equalsIgnoreCase(""))
						password.setError("Enter your password");
					else {
						Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});

		// convert password field text into asterisk
		password.setTransformationMethod(PasswordTransformationMethod.getInstance());

		login.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {

				c_password = password.getText().toString();
				c_email = email.getText().toString();
				// check whether email or password fields are empty or not
				if (email.getText().toString().length() == 0) {

					email.setOnEditorActionListener(new OnEditorActionListener() {

						@Override
						public boolean onEditorAction(TextView arg0, int arg1,
								KeyEvent arg2) {

							if (arg1 == EditorInfo.IME_ACTION_NEXT) {
								if (email.getText().toString().trim()
										.equalsIgnoreCase(""))
									email.setError("Enter your e-mail");
								else
									Toast.makeText(getApplicationContext(),
											"Notnull", Toast.LENGTH_SHORT)
											.show();
							}

							return false;
						}
					});

					email.requestFocus();
					email.setError("Enter your e-mail");

				} else if (password.getText().toString().length() == 0) {

					password.setOnEditorActionListener(new OnEditorActionListener() {

						@Override
						public boolean onEditorAction(TextView arg0, int arg1,
								KeyEvent arg2) {
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) {
								if (password.getText().toString().trim()
										.equalsIgnoreCase(""))
									password.setError("Enter your password");
								else
									Toast.makeText(getApplicationContext(),
											"Notnull", Toast.LENGTH_SHORT)
											.show();
							}
							return false;
						}
					});

					password.requestFocus();
					password.setError("Enter your password");

				} else {
					boolean check = false;
					check = validatInputs();
					if (check) {
						SignIn();
					}
				}

			}
		});
		
	}

	private void SignIn(){
		
		progressDialog = new ProgressDialog(KookBook_Login_Activity.this);
		progressDialog.show();
		
		int responsecode = 0;
		Map<String,String> valuemap=null;
		
		Singleton.cur_email = c_email;
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

						editor1.putBoolean("login_status", true); /*("login_status", id);*/
						editor1.putString("cur_uid", id);
						editor1.putString("cur_email", c_email);
						editor1.putString("cur_pwd", c_password);
						editor1.commit();

						Toast t = Toast.makeText(getApplicationContext(),
								"Login Successful", Toast.LENGTH_LONG);
						t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0,
								0);
						View view = t.getView();

						t.show();
						FindCity();

//						Intent in1 = new Intent(KookBook_Login_Activity.this,HomeActivity.class);
//						startActivity(in1);
//						KookBook_Login_Activity.this.finish();

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
	
	private void FindCity()
	{

		progressDialog = new ProgressDialog(KookBook_Login_Activity.this);
		progressDialog.show();

		int responsecode = 0;
		Map<String, String> valuemap = null;

		KBKPostRequest kbkPostRequest = new KBKPostRequest(KookBook_Login_Activity.this);
		kbkPostRequest.executeRequest("http://www.appdemoz.com/kookbook/connector/config/get_all_cities", valuemap, new KBKCallBack() 
		{

							@Override
							public void onSuccess(JSONObject result)
							{

								if (null != progressDialog && progressDialog.isShowing()) {
									try 
									{
										progressDialog.dismiss();
										progressDialog = null;

									} catch (Exception e) {
									}
								}
								try
								{
									JSONObject jsonObject = result;
									status = jsonObject.getString("status");
									System.out.println("Status :" + status);

									if (status.equals("SUCCESS"))
									{

										JSONObject jsonarray = null;
										jsonarray = jsonObject.getJSONObject("data");
										System.out.println("jsonarray**********" + jsonarray);

										ArrayList<String> cities = new ArrayList<String>();
										Map<String, String> map = new HashMap<String, String>();
										Iterator iter = jsonarray.keys();
										while (iter.hasNext())
										{
											try 
											{
												String city_id = (String) iter.next();
												String city = jsonarray.getString(city_id);
												if (city.equals(Singleton.Cityname)) 
												{
													Intent in1 = new Intent(KookBook_Login_Activity.this,HomeActivity.class);
													startActivity(in1);
													KookBook_Login_Activity.this.finish();
													break;
												}else{
													Toast toast = Toast.makeText(getApplicationContext(), "Unable to find location, please type location", Toast.LENGTH_LONG);
													toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
													View view = toast.getView();
													Intent in1 = new Intent(KookBook_Login_Activity.this,TypeLocation_Activity.class);
													startActivity(in1);
													KookBook_Login_Activity.this.finish();
												}
											} catch (JSONException e) {
												// Something went wrong!
											}
										}

									}

								} catch (JSONException e) {
									e.printStackTrace();
								}

							}

							@Override
							public void onFailure(Exception exception) 
							{
								exception.printStackTrace();
								Toast toast = Toast.makeText(
										getApplicationContext(),
										"Exception in Request",
										Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER
										| Gravity.CENTER_HORIZONTAL, 0, 0);
								View view = toast.getView();
							}
						});
	}


	private boolean validatInputs() {

		String emailid;
		boolean validId = false;
		emailid = email.getText().toString();

		if (emailid.length() > 0) {
			if (isValidEmail(emailid)) {
				validId = true;

			} else {
				email.setError("Enter Valid Email-Id");
				email.requestFocus();
				return false;
			}
		} else {
			email.setError("Enter Email-Id");
			email.requestFocus();
			return false;
		}
		return validId;

	}

	public static boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
