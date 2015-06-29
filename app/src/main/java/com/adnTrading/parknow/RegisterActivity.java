package com.adnTrading.parknow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adnTrading.utils.ReuseableClass;

public class RegisterActivity extends Activity {

	TextView textViewTitle;
	EditText name;
	EditText email_id;
	EditText mobile_no;
	EditText username;
	EditText password;
	EditText confirm_password;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_activity);

		textViewTitle 	 = (TextView)findViewById(R.id.textViewTitle);
		name 			 = (EditText)findViewById(R.id.editTextName);
		email_id 		 = (EditText)findViewById(R.id.editTextEmailAddress);
		mobile_no 		 = (EditText)findViewById(R.id.editTextMobileNo);
		username 		 = (EditText)findViewById(R.id.editTextUsername);
		password 		 = (EditText)findViewById(R.id.editTextPassword);
		confirm_password = (EditText)findViewById(R.id.editTextConfirmPassword);
		
		name.setTypeface(ReuseableClass.getFontStyle(this));
		email_id.setTypeface(ReuseableClass.getFontStyle(this));
		mobile_no.setTypeface(ReuseableClass.getFontStyle(this));
		username.setTypeface(ReuseableClass.getFontStyle(this));
		password.setTypeface(ReuseableClass.getFontStyle(this));
		confirm_password.setTypeface(ReuseableClass.getFontStyle(this));
		((Button)findViewById(R.id.buttonRegister)).setTypeface(ReuseableClass.getFontStyle(this));

		textViewTitle.setTypeface(ReuseableClass.getFontStyle(RegisterActivity.this));
	}

	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
		finish();
	}

	public void resgisteringUserData(View v) 
	{	
		String value_name 				= name.getText().toString();
		String value_email_id 			= email_id.getText().toString();
		String value_mobile_no 			= mobile_no.getText().toString();
		String value_username 			= username.getText().toString();
		String value_password 			= password.getText().toString();
		String value_confirm_password 	= confirm_password.getText().toString();

		if(ReuseableClass.haveNetworkConnection(RegisterActivity.this))
		{
			if(value_name.trim().equalsIgnoreCase("") || value_email_id.trim().equalsIgnoreCase("") || 
					value_mobile_no.trim().equalsIgnoreCase("") || value_username.trim().equalsIgnoreCase("") || 
					value_password.trim().equalsIgnoreCase("") || value_confirm_password.trim().equalsIgnoreCase(""))
			{
				Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(value_password.equalsIgnoreCase(value_confirm_password))
				{
					dialog = new ProgressDialog(RegisterActivity.this);
					dialog.setMessage(RegisterActivity.this.getString(R.string.wait_a_min_dialog_message));
					dialog.show();

					new UserRegistrationTask().execute(value_name, value_email_id, value_mobile_no, value_username, value_password);
				}
				else
				{
					Toast.makeText(this, R.string.password_missmatch_error, Toast.LENGTH_SHORT).show();
				}
			}
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
	}

	private class UserRegistrationTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/register_user.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("name", values[0]));
				nameValuePairs.add(new BasicNameValuePair("email_id", values[1]));
				nameValuePairs.add(new BasicNameValuePair("mobile_no", values[2]));
				nameValuePairs.add(new BasicNameValuePair("username", values[3]));
				nameValuePairs.add(new BasicNameValuePair("password", values[4]));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				int responseCode = response.getStatusLine().getStatusCode();
				if(responseCode == 200)
				{
					responseBody = EntityUtils.toString(response.getEntity());
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
			if(result.equalsIgnoreCase("YES"))
			{
				Toast.makeText(RegisterActivity.this, R.string.registration_successful_message, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(RegisterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
			}
			else if(result.equalsIgnoreCase("EXISTS"))
			{
				Toast.makeText(RegisterActivity.this, R.string.user_exists_error, Toast.LENGTH_SHORT).show();
			}

			else
			{
				Toast.makeText(RegisterActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
