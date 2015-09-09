package com.kbk.mobile_app.Activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.kbk.Singleton.CONSTANT;
import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Utils.CommonMethods;
import com.kbk.mobile_app.Utils.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class Kookbook_ForgotPassword_Activity extends Activity 
{

	public ImageView close, submitbtn;
	public EditText email;
	public String c_email, status, message;
	ProgressDialog progressDialog;
	String emails = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kookbook_forgotpassword);

		close = (ImageView) findViewById(R.id.close);
		submitbtn = (ImageView) findViewById(R.id.submitbtn);
		email = (EditText) findViewById(R.id.email);

		close.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{

				Intent in1 = new Intent(Kookbook_ForgotPassword_Activity.this, KookBook_Login_Activity.class);
				startActivity(in1);
				Kookbook_ForgotPassword_Activity.this.finish();

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
					else 
					{
						Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});

		submitbtn.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				
				c_email = email.getText().toString();
				// check whether email or password fields are empty or not
				if (email.getText().toString().length() == 0) 
				{

					email.setOnEditorActionListener(new OnEditorActionListener() 
					{

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
						{
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) 
							{
								if (email.getText().toString().trim().equalsIgnoreCase(""))
									email.setError("Enter your e-mail");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}

							return false;
						}
					});

					email.requestFocus();
					email.setError("Enter your e-mail");

				} else 
				{
					boolean check = false;
					check = validatInputs();
					if (check)
					{

						new FrogetPwd().execute();
					}
				}

			}
		});
	}

	public class FrogetPwd extends AsyncTask<Void, Void, String> 
	{

		JSONParser jsonParser = new JSONParser();
		JSONObject json;
		HttpResponse response;
		HttpPost httpPost;
		ProgressDialog progressDialog;

		@Override
		protected String doInBackground(Void... params) 
		{
			JSONParser jsonParser = new JSONParser();
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		    Singleton.cur_email = c_email;
		
		    JSONObject jsonObject = new JSONObject();
		
		    try {
			    jsonObject.put("user_email", c_email);
			    nameValuePairs.add(new BasicNameValuePair("data", jsonObject.toString()));
			 
		    } catch (JSONException e1) {
			   e1.printStackTrace();
		}
		JSONObject Result=jsonParser.getJSONFromUrlByPost("http://www.appdemoz.com/kookbook/connector/customer/forgot_password", nameValuePairs);
		System.out.println(Result + " resulttttttttttttttttttt    ");
		return Result.toString();}

		@Override
		protected void onPostExecute(String result)
		{
			try
			{
				if (result != null) 
				{
					JSONObject jsonObject = new JSONObject(result);

					status = jsonObject.getString("status");

					if (status.equals("SUCCESS")) 
					{
						CommonMethods.showMyToast(Kookbook_ForgotPassword_Activity.this,"You will recieve a Email soon, regarding your password");
						Intent i = new Intent(Kookbook_ForgotPassword_Activity.this,KookBook_Login_Activity.class);
						startActivity(i);
						Kookbook_ForgotPassword_Activity.this.finish();
					} else if (status.equals("FAIL")) 
					{
						CommonMethods.showMyToast(Kookbook_ForgotPassword_Activity.this, "Customer is not exist");
						Intent i = new Intent(Kookbook_ForgotPassword_Activity.this,StartScreen_Activity.class);
						startActivity(i);
						Kookbook_ForgotPassword_Activity.this.finish();
					}
				} else
				{
					CommonMethods.showMyToast(Kookbook_ForgotPassword_Activity.this,"please try again later.....!");
					Intent i = new Intent(Kookbook_ForgotPassword_Activity.this,StartScreen_Activity.class);
					startActivity(i);
					Kookbook_ForgotPassword_Activity.this.finish();
				}

			} catch (Exception e) {

			}
			if (null != progressDialog && progressDialog.isShowing()) 
			{

				try {
					progressDialog.dismiss();
					progressDialog = null;

				} catch (Exception e) {

				}
				// --------//
			}
			super.onPostExecute(result);
		}

	}

	private boolean validatInputs() 
	{

		String emailid;
		boolean validId = false;
		emailid = email.getText().toString();

		if (emailid.length() > 0) 
		{
			if (isValidEmail(emailid))
			{
				validId = true;

			} else 
			{
				email.setError("Enter Valid Email-Id");
				email.requestFocus();
				return false;
			}
		} else 
		{
			email.setError("Enter Email-Id");
			email.requestFocus();
			return false;
		}
		return validId;

	}

	public static boolean isValidEmail(String email) 
	{
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
