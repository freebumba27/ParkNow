package com.adnTrading.parknow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.adnTrading.utils.ReuseableClass;

public class BookingHistory extends Activity {

	private ProgressDialog dialog;
	ListView myListView;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_history);

		myListView = (ListView)findViewById(R.id.myListView);


		adapter = new SimpleAdapter(this, data,
				R.layout.simplerow, 
				new String[] {"First Line", "Second Line",  "Third Line"}, 
				new int[] {R.id.rowTextView, R.id.rowTextView2, R.id.rowTextView3 });

		myListView.setOnItemClickListener(new OnItemClickListener() 
		{ 
			@Override 
			public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) 
			{ 
				Log.d("TAG", "Parking Spot Id: " + data.get(position).get("Parking Spot Id"));
				Intent myIntent = new Intent(BookingHistory.this, GiveReviewPopup.class);
				myIntent.putExtra("parking_spot_id", data.get(position).get("Parking Spot Id"));
				startActivity(myIntent);
			} 
		}); 

		if(ReuseableClass.haveNetworkConnection(this))
		{
			dialog = new ProgressDialog(BookingHistory.this);
			dialog.setMessage(BookingHistory.this.getString(R.string.wait_a_min_dialog_message));
			dialog.show();

			new GetBookingHistoryTask().execute(ReuseableClass.getFromPreference("user_id", BookingHistory.this));
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
	}

	private class GetBookingHistoryTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/get_booking_history.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("purchaser_user_id", values[0]));

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
			if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(BookingHistory.this, R.string.server_error, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(BookingHistory.this, SearchParkingSpaceActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO DATA"))
			{
				((TextView)findViewById(R.id.TextViewNoData)).setTypeface(ReuseableClass.getFontStyle(BookingHistory.this));
				((TextView)findViewById(R.id.TextViewNoData)).setVisibility(View.VISIBLE);
			}
			else
			{
				try 
				{
					JSONArray jsonarray = new JSONArray(result);

					for (int i = 0; i < jsonarray.length(); i++) 
					{
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						String parking_spot_id = jsonobject.getString("parking_spot_id");
						String address = jsonobject.getString("address");
						String spot_booking_from = jsonobject.getString("spot_booking_from");
						String spot_booking_upto = jsonobject.getString("spot_booking_upto");
						String price = jsonobject.getString("price");


						Map<String, String> datum = new HashMap<String, String>(3);
						datum.put("Parking Spot Id", parking_spot_id);
						datum.put("First Line", "Address: " + address);
						datum.put("Second Line","Duration: From-" + spot_booking_from + " To-" + spot_booking_upto);
						datum.put("Third Line","Total paid amount: EUR" + price);
						data.add(datum);
					}
					myListView.setAdapter(adapter);
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
					Toast.makeText(BookingHistory.this, R.string.other_error, Toast.LENGTH_SHORT).show();
				}
			}
			dialog.dismiss();
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(this, SearchParkingSpaceActivity.class);
		startActivity(i);
		finish();
	}
}
