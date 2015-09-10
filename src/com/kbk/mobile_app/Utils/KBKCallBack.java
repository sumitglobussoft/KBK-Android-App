package com.kbk.mobile_app.Utils;

import org.json.JSONObject;

public interface KBKCallBack {

	public void onSuccess(JSONObject result);

	public void onFailure(Exception exception);

}
