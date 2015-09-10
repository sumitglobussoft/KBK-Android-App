package com.kbk.mobile_app.Utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kbk.mobile_app.Activities.AppController;

/*
 * this class is used for get JSON data from given web services url
 */

public class KBKRequest {

	// get JSON from given url

	KBKCallBack vcunnectCallBack;

	private String tag_json_obj = "jobj_req";

	Context activity;

	public KBKRequest(Context activity) {

		this.activity = activity;
	}

	public void executeRequest(String url, final KBKCallBack vcunnectCallBack) {

		this.vcunnectCallBack = vcunnectCallBack;

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						JSONObject jsonObject = response;

						if (jsonObject.has("status") || !jsonObject.has("Code")) {

							try {
								if (jsonObject.getString("status")
										.contains("1")) {

									vcunnectCallBack.onSuccess(jsonObject);

								} else {

									vcunnectCallBack.onFailure(new Exception(
											jsonObject.toString()));

								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						} else if (!jsonObject.has("status")
								|| jsonObject.has("Code")) {

							try {
								if (jsonObject.getString("Code")
										.contains("200")) {
									vcunnectCallBack.onSuccess(jsonObject);
								} else {
									vcunnectCallBack.onFailure(new Exception(
											jsonObject.toString()));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						} else {
							vcunnectCallBack.onFailure(new Exception(jsonObject
									.toString()));
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						vcunnectCallBack.onFailure(error);
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return super.getHeaders();
			}
		};

		// jsonObjectRequest.setRetryPolicy((RetryPolicy) new
		// DefaultRetryPolicy(
		// 2500,
		// DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
		// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		AppController.getInstance().addToRequestQueue(jsonObjectRequest,
				tag_json_obj);

	}

	// check whether network is availbale or not
	public boolean isNetworkAvailable(Context activity) {
		ConnectivityManager connectivity = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
