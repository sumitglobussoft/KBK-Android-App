package com.kbk.mobile_app.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kbk.Singleton.Singleton;
import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Home_delivery.Fragment.Delivery_Details_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Dummy_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Frequently_Asked_Questions_Fragmnet;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_Hotel_List_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_Number_Verification_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_Order_Received_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_SelectAddress_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.My_Address_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.My_Booking_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Post_Orders_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Settings_Fragment;
import com.kbk.mobile_app.Utils.CommonMethods;
import com.kbk.mobile_app.Utils.KBKCallBack;
import com.kbk.mobile_app.Utils.KBKPostRequest;
import com.kbk.mobile_app.adapters.NavDrawerListAdapter;
import com.kbk.mobile_app.models.NavDrawerItemModel;
import com.kbk.mobile_app.models.RestaurantsModel;

public class HomeActivity extends ActionBarActivity 
{
	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	RelativeLayout drawerViewlayout;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItemModel> navDrawerItems;
	private NavDrawerListAdapter adapter;

	public static FragmentManager fragmentmanager;
	FrameLayout frameLayout;

	ViewGroup headerRight;
	SharedPreferences preferences;
	SharedPreferences.Editor editor1;
	ProgressDialog progressDialog;
	public String status;
	
	public RestaurantsModel objItem;
	public List<RestaurantsModel> arrayOfList = new ArrayList<RestaurantsModel>();
	
	private static final String RestaurantName = "name";
	private static final String Address = "address";
	private static final String HotelStar = "hotel_star";
	private static final String MinCost = "minimum_order_amount";
	private static final String Time = "approx_delivery_time";
	private static final String CostForTwo ="table_booking";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);

		preferences = this.getSharedPreferences("KookBook", Context.MODE_PRIVATE);
		editor1 = preferences.edit();

		frameLayout = (FrameLayout) findViewById(R.id.container);
		if (Edit_Address_Activity.isFromEditAddress) 
		{
			fragmentmanager = getSupportFragmentManager();

			FragmentTransaction ftran = fragmentmanager.beginTransaction();
			ftran.replace(R.id.container, new My_Address_Fragment()).commit();
			HomeActivity.this.finish();
		} else 
		{

			fragmentmanager = getSupportFragmentManager();

			FragmentTransaction ftran = fragmentmanager.beginTransaction();
			ftran.replace(R.id.container, new KookBook_Number_Verification_Fragment()).commit();
		}

		System.out.println("Before city");
//		FindCity();

		// mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItemModel>();

		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[11], navMenuIcons.getResourceId(11, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[12], navMenuIcons.getResourceId(12, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter

		LayoutInflater inflater = getLayoutInflater();
		headerRight = (ViewGroup) inflater.inflate(R.layout.header, mDrawerList, false);
		mDrawerList.addHeaderView(headerRight, null, true);

		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);

		mDrawerList.setAdapter(adapter);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setHomeButtonEnabled(true);

		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.kookbook_text_orrange)));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.white_back_btn, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view)
			{
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) 
			{
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				super.onDrawerSlide(drawerView, slideOffset);
				frameLayout.setTranslationX(slideOffset * drawerView.getWidth());
				mDrawerLayout.bringChildToFront(drawerView);
				mDrawerLayout.requestLayout();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) 
		{
			// on first time display view for first nav item
			displayView(0);
		}
	}

	public static void SwipFragment(Fragment fragment) 
	{
		FragmentTransaction ftran = fragmentmanager.beginTransaction();
		ftran.setCustomAnimations(R.anim.right_in, R.anim.left_out);
		ftran.replace(R.id.container, fragment).addToBackStack(null).commit();
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements ListView.OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			System.out.println("Position : " + position);
			displayView(position - 1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) 
		{
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId())
		{
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "Settings ", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) 
	{
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position)
		{
		case 0:
			fragment = new KookBook_Hotel_List_Fragment();
			break;
		case 1:
			fragment = new My_Booking_Fragment();
			break;
		case 2:
			fragment = new My_Address_Fragment();
			break;
		case 3:
			fragment = new Delivery_Details_Fragment();
			break;
		case 4:
			fragment = new Post_Orders_Fragment();
			break;
		case 5:
			fragment = new KookBook_Order_Received_Fragment();
			break;
		case 6:
			fragment = new Dummy_Fragment();
			break;
		case 7:
			fragment = new KookBook_SelectAddress_Fragment();
			break;
		case 8:
			fragment = new Delivery_Details_Fragment();
			break;
		case 9:
			fragment = new Frequently_Asked_Questions_Fragmnet();
			break;
		case 10:
			fragment = new Settings_Fragment();
			break;
		case 11:
			fragment = new Delivery_Details_Fragment();
			break;
		case 12:
			fragment = setLoginStatus();
			break;

		default:
			break;
		}

		if (fragment != null)
		{
			FragmentTransaction ftran = fragmentmanager.beginTransaction();
			ftran.replace(R.id.container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else 
		{
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	private Fragment setLoginStatus()
	{
		Singleton.cur_email = preferences.getString("cur_email", null);

		Logout();
		return null;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void Logout()
	{

		progressDialog = new ProgressDialog(HomeActivity.this);
		progressDialog.show();

		int responsecode = 0;
		Map<String, String> valuemap = null;
		JSONObject jsonObject = new JSONObject();

		try
		{
			jsonObject.put("user_email", Singleton.cur_email);
			valuemap = new HashMap<String, String>();
			valuemap.put("data", jsonObject.toString());

		} catch (JSONException e1)
		{
			e1.printStackTrace();
		}

		KBKPostRequest kbkPostRequest = new KBKPostRequest(HomeActivity.this);
		kbkPostRequest.executeRequest("http://www.appdemoz.com/kookbook/connector/customer/sign_out", valuemap, new KBKCallBack()
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

							} catch (Exception e) {
							}
						}
						try 
						{
							JSONObject jsonObject = result;

							status = jsonObject.getString("status");

							if (status.equals("SUCCESS")) 
							{
								editor1.putBoolean("login_status", false); 
								editor1.putString("cur_uid", "");
								editor1.putString("cur_email", "");
								editor1.putString("cur_pwd", "");
								editor1.commit();
								CommonMethods.showMyToast(HomeActivity.this, "Your Logged out ");
								Intent i = new Intent(HomeActivity.this, StartScreen_Activity.class);
								startActivity(i);
								finish();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Exception exception) 
					{
						exception.printStackTrace();
						Toast toast = Toast.makeText(getApplicationContext(),
								"Exception in Request", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER
								| Gravity.CENTER_HORIZONTAL, 0, 0);
						View view = toast.getView();
					}
				});
	}

	private void FindCity()
	{

		progressDialog = new ProgressDialog(HomeActivity.this);
		progressDialog.show();

		int responsecode = 0;
		Map<String, String> valuemap = null;

		KBKPostRequest kbkPostRequest = new KBKPostRequest(HomeActivity.this);
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
												if (city.equals("Ahmedabad")) 
												{
													Singleton.CityId = city_id;
													System.out.println("key : @@@@@@@@@@@@@@@@@********"+ Singleton.CityId);
													GetRestaurantList();
													break;
												}
												map.put(city_id, city);
												// System.out.println("key : ********"+city_id);
												// System.out.println("value : ********"+city);
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

	private void GetRestaurantList()
	{

		progressDialog = new ProgressDialog(HomeActivity.this);
		progressDialog.show();

		int responsecode = 0;
		Map<String, String> valuemap = null;
		JSONObject jsonObject = new JSONObject();
		arrayOfList.clear();

		try
		{
			jsonObject.put("city_id", Singleton.CityId);
			System.out.println("JSOn of signin " + jsonObject.toString());
			valuemap = new HashMap<String, String>();
			valuemap.put("data", jsonObject.toString());

		} catch (JSONException e1)
		{
			e1.printStackTrace();
		}

		KBKPostRequest kbkPostRequest = new KBKPostRequest(
				HomeActivity.this);
		kbkPostRequest.executeRequest("http://www.appdemoz.com/kookbook/connector/config/get_restaurant_list", valuemap, new KBKCallBack()
				{

					@Override
					public void onSuccess(JSONObject result) 
					{

						if (null != progressDialog && progressDialog.isShowing())
						{
							try {
								progressDialog.dismiss();
								progressDialog = null;

							} catch (Exception e) {
							}
						}
						try {
							JSONObject jsonObject = result;

							status = jsonObject.getString("status");

							if (status.equals("SUCCESS"))
							{

								JSONArray jsonarray = null;
								jsonarray = jsonObject.getJSONArray("data");
								System.out.println("length%%%%%%"+jsonarray.length());
								for (int i = 0; i < jsonarray.length(); i++) 
								{

									JSONObject objJson = jsonarray.getJSONObject(i);
									
									objItem = new RestaurantsModel();
									objItem.setName(objJson.optString(RestaurantName));
									objItem.setAddress(objJson.optString(Address));
									objItem.setHotelStar(objJson.optString(HotelStar));
									objItem.setCostForTwo(objJson.optString(CostForTwo));
									objItem.setDeliverTime(objJson.optString(Time));
									objItem.setMinimumCost(objJson.optString(MinCost));
									
									arrayOfList.add(objItem);
								}

								

							} 
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
						setAdapterToListview();

						if (null != progressDialog && progressDialog.isShowing()) 
						{

							try 
							{
								progressDialog.dismiss();
								progressDialog = null;
								System.out.println("progress dismissed 1");
							} 
							catch (Exception e) 
							{System.out.println("progress dismissed error  "+e);
							}

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
	public void setAdapterToListview()
	{
		// call adapter class for view
//		objAdapter = new QuestionRowAdapter(getActivity(),R.layout.item, arrayOfList);
//		listView.setAdapter(objAdapter);

		// Debug.stopMethodTracing();
	}

}
