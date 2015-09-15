package com.kbk.mobile_app.Activities;

import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.kbk.mobile_app.R;

public class StartScreen_Activity extends Activity implements
		OnItemClickListener, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,OnClickListener, GoogleApiClient.ServerAuthCodeCallbacks {

	public ImageView loginWithfb, loginWithGplus;
	public Button signup_btn, login_btn;
	SharedPreferences.Editor editor, editor1;
	public TextView skip;

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/*
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;
	
	SignInButton button;
	AlertDialog.Builder alert;
	
	  /*
     * Tracks whether the sign-in button has been clicked so that we know to resolve all issues
     * preventing sign-in without waiting.
     */
    private boolean mSignInClicked;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
	//	mGoogleApiClient = buildGoogleApiClient(false);
		
		loginWithfb = (ImageView) findViewById(R.id.loginWithfb);
		
		button= (SignInButton) findViewById(R.id.loginWithGplus);
		//button.setOnClickListener(this);
		
		signup_btn = (Button) findViewById(R.id.signup_btn);
		login_btn = (Button) findViewById(R.id.login_btn);
		skip = (TextView) findViewById(R.id.skip);

		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		editor = sharedPref.edit();

		SharedPreferences preferences = this.getSharedPreferences("KookBook",
				Context.MODE_PRIVATE);
		editor1 = preferences.edit();

		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in1 = new Intent(StartScreen_Activity.this,
						HomeActivity.class);
				startActivity(in1);

			}
		});

		login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				System.out.println("SplashScreenActivity.gps"+SplashScreenActivity.gps);
				if(!SplashScreenActivity.gps){
					final Dialog dialog = new Dialog(StartScreen_Activity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.gps_dialog);
					Button ok = (Button) dialog.findViewById(R.id.ok);
					Button cancel = (Button) dialog.findViewById(R.id.cancel);
					alert = new AlertDialog.Builder(StartScreen_Activity.this);
					ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View paramView) {
							SplashScreenActivity.gps = true;
							dialog.dismiss();
							
						}
					});
					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View paramView) {
							dialog.dismiss();
							SplashScreenActivity.gps = false;
							Intent in1 = new Intent(StartScreen_Activity.this, KookBook_Login_Activity.class);
							startActivity(in1);
							
						}
					});
					dialog.show();
				}else{
					Intent in1 = new Intent(StartScreen_Activity.this, KookBook_Login_Activity.class);
					startActivity(in1);
				}

			}
		});

		signup_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in2 = new Intent(StartScreen_Activity.this,
						KookBook_SignUp_Activity.class);
				startActivity(in2);

			}
		});
	}

	/*private GoogleApiClient buildGoogleApiClient(boolean useProfileScope) {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this);

        String serverClientId = getString(R.string.server_client_id);

        if (!TextUtils.isEmpty(serverClientId)) {
            builder.requestServerAuthCode(serverClientId, this);
        }

        if (useProfileScope) {
            builder.addApi(Plus.API)
                    .addScope(new Scope("profile"));
        } else {
//            builder.addApi(Plus.API, Plus.PlusOptions.builder()
//                            .addActivityTypes(MomentUtil.ACTIONS).build())
//                    .addScope(Plus.SCOPE_PLUS_LOGIN);
        }

        return builder.build();
    }*/
	
	protected void onStart() {
		super.onStart();

		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		} else {
			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this).addApi(Plus.API)
					.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		}

	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		if (!mIntentInProgress && result.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(result.getResolution()
						.getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// We've resolved any connection errors. mGoogleApiClient can be used to
		// access Google APIs on behalf of the user.
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// mGoogleApiClient.connect();
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}

			super.onActivityResult(requestCode, responseCode, intent);
		}
	}

	@Override
	public void onClick(View v) {
		 switch(v.getId()) {
         case R.id.loginWithGplus:
             if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                 mSignInClicked = true;
                 mGoogleApiClient.connect();
             }
             break;
         default:
               break;
		 }
	}

	@Override
	public CheckResult onCheckServerAuthorization(String arg0, Set<Scope> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onUploadServerAuthCode(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
