package com.kbk.mobile_app.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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

import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Utils.JSONParser;
import com.kbk.mobile_app.Utils.KBKCallBack;
import com.kbk.mobile_app.Utils.KBKPostRequest;

public class KookBook_SignUp_Activity extends Activity
{
	public TextView account;
	public ImageView close, signUp;
	public EditText email, password, name;
	public String status, id, message, c_password, c_email, c_username;
	SharedPreferences.Editor editor, editor1;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kookbook_signup);

		close = (ImageView) findViewById(R.id.close);
		account = (TextView) findViewById(R.id.account);
		signUp = (ImageView) findViewById(R.id.signUp);
		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);

		String accounts = "Already have an account? <b>Login</b>";
		account.setText(Html.fromHtml(accounts));

		account.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{

				Intent in1 = new Intent(KookBook_SignUp_Activity.this,KookBook_Login_Activity.class);
				startActivity(in1);
				KookBook_SignUp_Activity.this.finish();

			}
		});
		close.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{

				Intent in1 = new Intent(KookBook_SignUp_Activity.this, StartScreen_Activity.class);
				startActivity(in1);
				KookBook_SignUp_Activity.this.finish();

			}
		});

		name.setOnEditorActionListener(new OnEditorActionListener() 
		{

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
			{

				if (arg1 == EditorInfo.IME_ACTION_NEXT) 
				{
					if (name.getText().toString().trim().equalsIgnoreCase(""))
						name.setError("Enter your name");
					else 
					{
						Toast.makeText(getApplicationContext(), "Notnull",
								Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});

		email.setOnEditorActionListener(new OnEditorActionListener() 
		{

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) 
			{

				if (arg1 == EditorInfo.IME_ACTION_NEXT) {
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
		password.setOnEditorActionListener(new OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
			{
				if (arg1 == EditorInfo.IME_ACTION_NEXT) 
				{
					if (password.getText().toString().trim().equalsIgnoreCase(""))
						password.setError("Enter your password");
					else
					{
						Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
						// password.setError("Enter Password");
					}
				}
				return false;
			}
		});

		// convert password field text into asterisk
		password.setTransformationMethod(PasswordTransformationMethod
				.getInstance());

		signUp.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				c_password = password.getText().toString();
				c_email = email.getText().toString();
				c_username = name.getText().toString();

				// check whether email or password fields are empty or not
				if (name.getText().toString().length() == 0) 
				{

					name.setOnEditorActionListener(new OnEditorActionListener()
					{

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) 
						{
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) 
							{
								if (name.getText().toString().trim().equalsIgnoreCase(""))
									name.setError("Enter your name");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}

							return false;
						}
					});

					name.requestFocus();
					name.setError("Enter your name");

				} else if (email.getText().toString().length() == 0) 
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

				} else if (password.getText().toString().length() == 0) 
				{
					// password.setFocusableInTouchMode(true);

					password.setOnEditorActionListener(new OnEditorActionListener()
					{

						@Override
						public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
						{
							// TODO Auto-generated method stub

							if (arg1 == EditorInfo.IME_ACTION_NEXT) 
							{
								if (password.getText().toString().trim().equalsIgnoreCase(""))
									password.setError("Enter your password");
								else
									Toast.makeText(getApplicationContext(), "Notnull", Toast.LENGTH_SHORT).show();
							}
							return false;
						}
					});

					password.requestFocus();
					password.setError("Enter your password");

				} else
				{
					boolean check = false;
					check = validatInputs();
					if (check) 
					{
						Register();
					}
				}

			}
		});
	}

	private void Register() 
	{
		progressDialog = new ProgressDialog(KookBook_SignUp_Activity.this);
		progressDialog.show();

		int responsecode = 0;
		Map<String, String> valuemap = null;

		Singleton.cur_email = c_email;
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("user_email", c_email);
			jsonObject.put("user_name", c_username);
			jsonObject.put("user_password", c_password);
			System.out.println("JSOn of signin "+jsonObject.toString());
			valuemap = new HashMap<String, String>();
			valuemap.put("data", jsonObject.toString());

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		KBKPostRequest kbkPostRequest = new KBKPostRequest(KookBook_SignUp_Activity.this);
		kbkPostRequest.executeRequest("http://www.appdemoz.com/kookbook/connector/customer/register", valuemap, new KBKCallBack() 
		{
					@Override
					public void onSuccess(JSONObject result)
					{

						if (null != progressDialog && progressDialog.isShowing()) 
						{
							try 
							{
								progressDialog.dismiss();
								progressDialog = null;

							} catch (Exception e)
							{
							}
						}
						try {

							JSONObject jsonObject = result;
							System.out.println("result================>%%" + result);

							status = jsonObject.getString("status");
							System.out.println("status================>"+ status);

							if (status.equals("SUCCESS"))
							{

								JSONArray jsonarray = null;
								jsonarray = jsonObject.getJSONArray("data");
								for (int i = 0; i < jsonarray.length(); i++) 
								{

									JSONObject objJson = jsonarray.getJSONObject(i);
									Singleton.cur_id = objJson.getString("user_id");
								}
								System.out.println("cur_id***"+ Singleton.cur_id);

								Toast t = Toast.makeText(getApplicationContext(), "Account Creation Successful", Toast.LENGTH_LONG);
								t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
								View view = t.getView();

								t.show();

								Intent in1 = new Intent(KookBook_SignUp_Activity.this, HomeActivity.class);
								startActivity(in1);
								KookBook_SignUp_Activity.this.finish();

							} else if (status.equals("FAIL")) 
							{

								Toast toast = Toast.makeText(getApplicationContext(), "Account is already exist", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
								View view = toast.getView();

								toast.show();
								
								Intent in1 = new Intent(KookBook_SignUp_Activity.this, KookBook_Login_Activity.class);
								startActivity(in1);
								KookBook_SignUp_Activity.this.finish();
							}

							// } else {
							// // respone is failed
							// }

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Exception exception)
					{
						exception.printStackTrace();
						Toast toast = Toast.makeText(getApplicationContext(), "Exception in Request", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
						View view = toast.getView();
					}
				});
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
				// CommonMethods.showMyToast(getActivity(),
				// "Enter Valid Email-Id");
				email.setError("Enter Valid Email-Id");
				email.requestFocus();
				return false;
			}
		} else 
		{
			// CommonMethods.showMyToast(getActivity(), "Enter Email-Id");
			email.setError("Enter Email-Id");
			email.requestFocus();
			return false;
		}
		return validId;

	}

	public static boolean isValidEmail(String email) 
	{
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
