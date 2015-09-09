//package com.kbk.mobile_app.jsonparser;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.telephony.TelephonyManager;
//
//public class Utilss {
//	
//
//	// get Json from given url
//	public static String getJSONString(String url) 
//	{
//		String jsonString = null;
//		HttpURLConnection linkConnection = null;
//		try {
//			URL linkurl = new URL(url);
//			linkConnection = (HttpURLConnection) linkurl.openConnection();
//			int responseCode = linkConnection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) 
//			{
//				InputStream linkinStream = linkConnection.getInputStream();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				int j = 0;
//				while ((j = linkinStream.read()) != -1) 
//				{
//					baos.write(j);
//				}
//				byte[] data = baos.toByteArray();
//				jsonString = new String(data);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (linkConnection != null) {
//				linkConnection.disconnect();
//			}
//		}
//		return jsonString;
//	}
//
//	// check whether network is availbale or not
//	public static boolean isNetworkAvailable(Activity activity) 
//	{
//		ConnectivityManager connectivity = (ConnectivityManager) activity
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (connectivity == null)
//		{
//			return false;
//		} 
//		else 
//		{
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null) 
//			{
//				for (int i = 0; i < info.length; i++) 
//				{
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) 
//					{
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	public static NetworkInfo getNetworkInfo(Context context) 
//	{
//		ConnectivityManager cm = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		return cm.getActiveNetworkInfo();
//	}
//
//	public static boolean isConnectedFast(Context context) 
//	{
//		NetworkInfo info = Utils.getNetworkInfo(context);
//		
//		return (info != null && info.isConnected() && Utils.isConnectionFast(
//				info.getType(), info.getSubtype()));
//	}
//
//	public static boolean isConnectionFast(int type, int subType) 
//	{
//		if (type == ConnectivityManager.TYPE_WIFI) 
//		{
//			return true;
//		} 
//		else if (type == ConnectivityManager.TYPE_MOBILE) 
//		{
//			switch (subType) {
//			case TelephonyManager.NETWORK_TYPE_1xRTT:
//				return false; // ~ 50-100 kbps
//			case TelephonyManager.NETWORK_TYPE_CDMA:
//				return false; // ~ 14-64 kbps
//			case TelephonyManager.NETWORK_TYPE_EDGE:
//				return false; // ~ 50-100 kbps
//			case TelephonyManager.NETWORK_TYPE_EVDO_0:
//				return true; // ~ 400-1000 kbps
//			case TelephonyManager.NETWORK_TYPE_EVDO_A:
//				return true; // ~ 600-1400 kbps
//			case TelephonyManager.NETWORK_TYPE_GPRS:
//				return false; // ~ 100 kbps
//			case TelephonyManager.NETWORK_TYPE_HSDPA:
//				return true; // ~ 2-14 Mbps
//			case TelephonyManager.NETWORK_TYPE_HSPA:
//				return true; // ~ 700-1700 kbps
//			case TelephonyManager.NETWORK_TYPE_HSUPA:
//				return true; // ~ 1-23 Mbps
//			case TelephonyManager.NETWORK_TYPE_UMTS:
//				return true; // ~ 400-7000 kbps
//				/*
//				 * Above API level 7, make sure to set android:targetSdkVersion
//				 * to appropriate level to use these
//				 */
//			case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//				return true; // ~ 1-2 Mbps
//			case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//				return true; // ~ 5 Mbps
//			case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//				return true; // ~ 10-20 Mbps
//			case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//				return false; // ~25 kbps
//			case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//				return true; // ~ 10+ Mbps
//				// Unknown
//			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//			default:
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}
//
//
//}
