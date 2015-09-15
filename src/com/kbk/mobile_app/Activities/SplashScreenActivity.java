package com.kbk.mobile_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Utils.AppLocationService;
import com.kbk.mobile_app.Utils.LocationAddress;

public class SplashScreenActivity extends FragmentActivity implements
		LocationListener {
	double longitude;
	double latitude;

	SharedPreferences.Editor editor1;
	public ImageView image;
	Handler handler;

	private String provider;
	Location location;
	AppLocationService appLocationService;
	public static boolean gps=false;

	private LocationManager locationManager;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.splash_activity);
		SharedPreferences preferences = this.getSharedPreferences("KookBook",
				Context.MODE_PRIVATE);
		editor1 = preferences.edit();
		handler = new Handler();

		appLocationService = new AppLocationService(SplashScreenActivity.this);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {

			gps = true;
			onLocationChanged(location);

		} else {
			gps = false;
			System.out.println("location null");
		}

		image = (ImageView) findViewById(R.id.kookbookimage);
		final boolean login_status = preferences.getBoolean("login_status",
				false);

		TranslateAnimation translation;
		translation = new TranslateAnimation(0f, 0f, -200f,
				getDisplayWidth() / 2f);
		translation.setStartOffset(100);
		translation.setDuration(2000);
		translation.setFillAfter(true);
		translation.setInterpolator(new BounceInterpolator());
		image.startAnimation(translation);

		Thread background = new Thread() {
			public void run() {

				try {
					sleep(1 * 3000);
					if (login_status) {
						Intent in1 = new Intent(SplashScreenActivity.this,
								HomeActivity.class);
						startActivity(in1);
						SplashScreenActivity.this.finish();
					} else {
						Intent in1 = new Intent(SplashScreenActivity.this,
								StartScreen_Activity.class);
						startActivity(in1);
						SplashScreenActivity.this.finish();
					}

				} catch (Exception e) {
				}
			}
		};

		background.start();
	}

	private int getDisplayWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	@Override
	protected void onResume() {

		super.onResume();
		getLocation();

		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	public Location getLocation() {
		try {
			boolean canGetLocation;
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	@Override
	public void onLocationChanged(Location location) {
		float lat = (float) (location.getLatitude());
		float lng = (float) (location.getLongitude());
		System.out.println("LAT " + lat);
		System.out.println("LONG " + lng);

		Singleton.latitude = lat;
		Singleton.longitude = lng;

		Location location1 = appLocationService
				.getLocation(LocationManager.GPS_PROVIDER);

		// you can hard-code the lat & long if you have issues with getting it
		// remove the below if-condition and use the following couple of lines
		// double latitude = 37.422005;
		// double longitude = -122.084095

		if (location != null) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			// LocationAddress locationAddress = new LocationAddress();
			LocationAddress.getAddressFromLocation(latitude, longitude,
					getApplicationContext(), new GeocoderHandler());
		} else {
			System.out.println(" NULLLL ");
		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
			case 1:
				Bundle bundle = message.getData();
				locationAddress = bundle.getString("address");
				System.out.println("ADDRESS>>>>>>>>>>" + locationAddress);

				break;
			default:
				locationAddress = null;
			}
		}
	}

}
