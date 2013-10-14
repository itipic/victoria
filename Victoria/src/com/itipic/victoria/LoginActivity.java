package com.itipic.victoria;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import comm.itipic.victoria.utils.MyToolBox;

public class LoginActivity extends Activity {
	private String mUserEmail;
	private String mUserPassword;
	protected static String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void login(View button) {
	    EditText userEmailField = (EditText) findViewById(R.id.userEmail);
	    mUserEmail = userEmailField.getText().toString();
	    EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
	    mUserPassword = userPasswordField.getText().toString();

	    if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
	        // input fields are empty
	        Toast.makeText(this, "Please complete all the fields",
	            Toast.LENGTH_LONG).show();
	        return;
	    } else {
	    	networkLoginWithJson();
	       // LoginTask loginTask = new LoginTask(LoginActivity.this);
	        //loginTask.setMessageLoading("Logging in...");
	        //loginTask.execute(LOGIN_API_ENDPOINT_URL);
	    }
	}
	
	private void networkLoginWithJson() {
		
		Uri.Builder uri = new Uri.Builder();
		uri.scheme("https");
		uri.authority("development.itipic.com");
		uri.path("/user_sessions");
		String url = uri.build().toString();
		JSONObject userObj = new JSONObject();
		try {
		userObj.put("email", mUserEmail);
		userObj.put("password", mUserPassword);
		
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userObj,
			       new Response.Listener<JSONObject>() {
			           @Override
			           public void onResponse(JSONObject response) {
			               try {
			                   VolleyLog.v("Response:%n %s", response.toString(4));
			                   //String text = response.toString(4);
			                   //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			                   Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
			               } catch (JSONException e) {
			                   e.printStackTrace();
			               }
			           }
			       }, new Response.ErrorListener() {
			           @Override
			           public void onErrorResponse(VolleyError error) {
			               VolleyLog.e("Error: ", error.getMessage());
			               Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_LONG).show();
			           }
			       }) {
			@Override
		       public Map<String, String> getHeaders() throws AuthFailureError {
		           HashMap<String, String> headers = new HashMap<String, String>();
		           headers.put("Accept", "application/json");
		           headers.put("Content-Type", "application/json");
		           return headers;
		       }
		};
		MyToolBox.trustEveryone();
		requestQueue.add(request);
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSON", "" + e);
		}
	}
	

}
