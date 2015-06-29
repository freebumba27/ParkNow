package com.adnTrading.parknow;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adnTrading.utils.ReuseableClass;

public class LoginActivity extends Activity{

	EditText editTextUserName;
	EditText editTextPassword;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login_activity);
		
		TextView textViewTitle    = (TextView)findViewById(R.id.textViewTitle);
		TextView textViewRegister = (TextView)findViewById(R.id.textViewRegisterNow);

		editTextUserName = (EditText)findViewById(R.id.editTextUserName);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);

		((Button)findViewById(R.id.buttonLogin)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextUserName)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextPassword)).setTypeface(ReuseableClass.getFontStyle(this));

		textViewTitle.setTypeface(ReuseableClass.getFontStyle(LoginActivity.this));
		textViewRegister.setTypeface(ReuseableClass.getFontStyle(LoginActivity.this));
	}

	public void openingRegisterActivity(View v) 
	{
		Intent i = new Intent(this, RegisterActivity.class);
		startActivity(i);
		finish();
	}

	public void loginNow(View v) 
	{
		String userName = editTextUserName.getText().toString();
		String password = editTextPassword.getText().toString();
		if(ReuseableClass.haveNetworkConnection(LoginActivity.this))
		{
			if(userName.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase(""))
			{
				Toast.makeText(LoginActivity.this, R.string.all_field_mandatory, Toast.LENGTH_SHORT).show();
			}
			else 
			{
				dialog = new ProgressDialog(LoginActivity.this);
				dialog.setMessage(LoginActivity.this.getString(R.string.wait_a_min_dialog_message));
				dialog.show();

				new LoginTask().execute(userName, password);
			}
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
		
//		Intent i = new Intent(LoginActivity.this, ChooseUserTypeActivity.class);
//		startActivity(i);
//		finish();
	}

	private class LoginTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/login_user.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("username", values[0]));
				nameValuePairs.add(new BasicNameValuePair("password", values[1]));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				int responseCode = response.getStatusLine().getStatusCode();
				if(responseCode == 200)
				{
					responseBody = EntityUtils.toString(response.getEntity());
					Log.d("TAG", "value: " + responseBody);
				}
			} 
			catch (Exception t) 
			{
				Log.e("TAG", "Error: " + t);
			} 
			return responseBody;
		}

		protected void onPostExecute(String result) 
		{
			Log.d("TAG", "value: " + result);
			if(result.contains("user_id"))
			{
				ReuseableClass.saveInPreference("user_id", result.replace("user_id", ""), LoginActivity.this);

				Log.d("TAG", "user id: " + ReuseableClass.getFromPreference("user_id", LoginActivity.this));

				Intent i = new Intent(LoginActivity.this, ChooseUserTypeActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(LoginActivity.this, R.string.login_check_credentials_error, Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(LoginActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
