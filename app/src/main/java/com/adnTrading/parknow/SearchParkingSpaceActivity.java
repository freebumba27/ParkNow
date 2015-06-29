package com.adnTrading.parknow;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adnTrading.utils.ReuseableClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class SearchParkingSpaceActivity extends Activity {

	private GoogleMap googleMap;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_parking_space_activity);
		
		((Button)findViewById(R.id.buttonLogin)).setTypeface(ReuseableClass.getFontStyle(this));
		try 
		{
			initilizeMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setRotateGesturesEnabled(true);
			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			googleMap.setMyLocationEnabled(true);

			 
			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
	            @Override
	            public void onInfoWindowClick(Marker Pin) 
	            {
	               Intent intent = new Intent(SearchParkingSpaceActivity.this, ChooseDurationActivity.class); 
	               intent.putExtra("lat", Pin.getPosition().latitude+"");
	               intent.putExtra("lng", Pin.getPosition().longitude+"");
	               startActivity(intent);
	               finish();
	            }
	        });
			
			//Getting all the parking spots
			if(ReuseableClass.haveNetworkConnection(SearchParkingSpaceActivity.this))
			{

				dialog = new ProgressDialog(SearchParkingSpaceActivity.this);
				dialog.setMessage(SearchParkingSpaceActivity.this.getString(R.string.wait_a_min_dialog_message));
				dialog.show();

				new GetParkingSpotsTask().execute();
			}
			else
			{
				Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
			}
			
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, true);
			Location myLocation = locationManager.getLastKnownLocation(provider);

			double latitude = myLocation.getLatitude();
			double longitude = myLocation.getLongitude();

			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(12).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() 
	{
		if (googleMap == null) 
		{
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) 
			{
				Toast.makeText(getApplicationContext(), R.string.unable_to_create_map, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(this, ChooseUserTypeActivity.class);
		startActivity(i);
		finish();
	}

	private class GetParkingSpotsTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/get_parking_spots.php");
			try 
			{
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
				Toast.makeText(SearchParkingSpaceActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(SearchParkingSpaceActivity.this, ChooseUserTypeActivity.class);
				startActivity(i);
				finish();
			}
			else
			{
				try 
				{
					JSONArray jsonarray = new JSONArray(result);

					for (int i = 0; i < jsonarray.length(); i++) 
					{
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						String user_id = jsonobject.getString("user_id");
						String address = jsonobject.getString("address");
						String spaceDesc = jsonobject.getString("spaceDesc");
						String price = jsonobject.getString("price");
						String lat = jsonobject.getString("lat");
						String lng = jsonobject.getString("lng");

						MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title("Per Hour: €" + price) .snippet("Address: " + address);;
						marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
						Marker mar = googleMap.addMarker(marker);
					}
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
					Toast.makeText(SearchParkingSpaceActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
				}
			}
			dialog.dismiss();
		}
	}
	
	public void openBookingHistory(View v) 
	{
		Intent myIntent = new Intent(this, BookingHistory.class);
		finish();
		startActivity(myIntent);
		
	}
}
