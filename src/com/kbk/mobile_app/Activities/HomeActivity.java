package com.kbk.mobile_app.Activities;

import java.util.ArrayList;

import com.kbk.mobile_app.R;
import com.kbk.mobile_app.Home_delivery.Fragment.Delivery_Details_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Dummy_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Frequently_Asked_Questions_Fragmnet;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_Number_Verification_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_Order_Received_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.KookBook_SelectAddress_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.My_Address_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.My_Booking_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Post_Orders_Fragment;
import com.kbk.mobile_app.Home_delivery.Fragment.Settings_Fragment;
import com.kbk.mobile_app.adapters.NavDrawerListAdapter;
import com.kbk.mobile_app.models.NavDrawerItemModel;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItemModel> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	public static FragmentManager fragmentmanager;

	ViewGroup headerRight;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		if(Edit_Address_Activity.isFromEditAddress){
			fragmentmanager=getSupportFragmentManager();
			
			FragmentTransaction ftran=fragmentmanager.beginTransaction();
			ftran.replace(R.id.container, new My_Address_Fragment()).commit();
			HomeActivity.this.finish();
		}else {
		
		fragmentmanager=getSupportFragmentManager();
		
		FragmentTransaction ftran=fragmentmanager.beginTransaction();
		ftran.replace(R.id.container, new KookBook_Number_Verification_Fragment()).commit();
		}
		
//		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItemModel>();

		
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[9], navMenuIcons
				.getResourceId(9, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[10], navMenuIcons
				.getResourceId(10, -1)));
		navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[11], navMenuIcons
				.getResourceId(11, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter

		LayoutInflater inflater = getLayoutInflater();
		headerRight = (ViewGroup) inflater.inflate(R.layout.header,mDrawerList, false);
		mDrawerList.addHeaderView(headerRight, null, true);
		
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);

		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.rounded_icon, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}
	
	public static void SwipFragment(Fragment fragment)
	{
		FragmentTransaction ftran=fragmentmanager.beginTransaction();
		ftran.setCustomAnimations(R.anim.right_in, R.anim.left_out);
		ftran.replace(R.id.container, fragment).addToBackStack(null).commit();	
	}
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			System.out.println("Position : "+position);
			displayView(position-1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "Settings ",
					Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new My_Booking_Fragment();
			break;
		case 1:
			fragment = new My_Address_Fragment();
			break;
		case 2:
			fragment = new Delivery_Details_Fragment();
			break;
		case 3:
			fragment = new Post_Orders_Fragment();
			break;
		case 4:
			fragment = new KookBook_Order_Received_Fragment();
			break;
		case 5:
			fragment = new Dummy_Fragment();
			break;
		case 6:
			fragment = new KookBook_SelectAddress_Fragment();
			break;
		case 7:
			fragment = new Delivery_Details_Fragment();
			break;
		case 8:
			fragment = new Frequently_Asked_Questions_Fragmnet();
			break;
		case 9:
			fragment = new Settings_Fragment();
			break;
		case 10:
			fragment = new Delivery_Details_Fragment();
			break;
		case 11:
			fragment = new KookBook_Number_Verification_Fragment();
			break;	

		default:
			break;
		}

		if (fragment != null) {
			FragmentTransaction ftran=fragmentmanager.beginTransaction();
			ftran.replace(R.id.container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
}