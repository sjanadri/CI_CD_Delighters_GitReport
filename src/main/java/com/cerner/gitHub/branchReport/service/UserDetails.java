package com.cerner.gitHub.branchReport.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cerner.gitHub.branchReport.model.User;

/**
 * @author SJ064201
 *
 */
public class UserDetails {

	/**
	 * @param user JSON Data
	 * @return List with User names
	 * @throws JSONException
	 */
	public ArrayList<User> getUserInfo(String userJSONData) throws JSONException {
		ArrayList<User> userList = new ArrayList<User>();

		JSONArray jsonArray = new JSONArray(userJSONData);
		for (int i = 0; i < jsonArray.length(); i++) {
			User user = new User();
			JSONObject jsonStatusObject = jsonArray.getJSONObject(i);
			user.setLogin(jsonStatusObject.getString("login"));
			userList.add(user);
		}

		return userList;
	}

}
