package com.adnTrading.parknow;

import java.util.ArrayList;
import java.util.List;

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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adnTrading.utils.ReuseableClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddParkingSpaceActivity extends Activity {
	private GoogleMap googleMap;
	double lat = 0.0;
	double lng = 0.0;
	EditText editTextAddress;
	EditText editTextDesc;
	EditText editTextPrice;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_parking_space_activity);

		TextView textViewTitle 	= (TextView)findViewById(R.id.textViewTitle);
		editTextAddress			= (EditText)findViewById(R.id.editTextAddress);
		editTextDesc			= (EditText)findViewById(R.id.editTextDesc);
		editTextPrice			= (EditText)findViewById(R.id.editTextPrice);

		editTextAddress.setTypeface(ReuseableClass.getFontStyle(this));
		editTextDesc.setTypeface(ReuseableClass.getFontStyle(this));
		editTextPrice.setTypeface(ReuseableClass.getFontStyle(this));
		((Button)findViewById(R.id.buttonSave)).setTypeface(ReuseableClass.getFontStyle(this));
		
		textViewTitle.setTypeface(ReuseableClass.getFontStyle(AddParkingSpaceActivity.this));

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

			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, true);
			try 
			{
				Location myLocation = locationManager.getLastKnownLocation(provider);
				double latitude = myLocation.getLatitude();
				double longitude = myLocation.getLongitude();

				CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(14).build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { 

				MarkerOptions marker = null;

				@Override 
				public void onMapClick(LatLng point) {         
					if (marker != null) 
					{
						googleMap.clear();
					}
					marker = new MarkerOptions()
					.position(new LatLng(point.latitude, point.longitude))
					.draggable(true)
					.visible(true)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));           
					googleMap.addMarker(marker);            

					lat = point.latitude ;
					lng = point.longitude ;

					Toast.makeText(AddParkingSpaceActivity.this, R.string.move_marker, Toast.LENGTH_SHORT).show();
				} 
			}); 
			
			googleMap.setOnMarkerDragListener(new OnMarkerDragListener() {
		        //LatLng temp = null;
		        @Override
		        public void onMarkerDragStart(Marker marker) {
		            // TODO Auto-generated method stub
		            //temp=marker.getPosition();
		        }

		        @Override
		        public void onMarkerDragEnd(Marker marker) {
		            // TODO Auto-generated method stub
		        	lat = marker.getPosition().latitude;
		        	lng = marker.getPosition().longitude;
		            //marker.setPosition(temp);
		        }

		        @Override
		        public void onMarkerDrag(Marker marker) {
		            // TODO Auto-generated method stub
		            //LatLng temp = marker.getPosition();
		            //marker.setPosition(temp);
		        }
		    }); 
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

	public void savingMySpace(View v) 
	{
		String address   = editTextAddress.getText().toString();
		String spaceDesc = editTextDesc.getText().toString();
		String price     = editTextPrice.getText().toString();

		if(ReuseableClass.haveNetworkConnection(AddParkingSpaceActivity.this))
		{
			if(address.trim().equalsIgnoreCase("") || spaceDesc.trim().equalsIgnoreCase("") || price.trim().equalsIgnoreCase(""))
			{
				Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(lat!=0.0 || lng !=0.0)
				{
					dialog = new ProgressDialog(AddParkingSpaceActivity.this);
					dialog.setMessage(AddParkingSpaceActivity.this.getString(R.string.wait_a_min_dialog_message));
					dialog.show();

					new AddParkingSpaceTask().execute(address, spaceDesc, price, lat+"", lng+"", ReuseableClass.getFromPreference("user_id", AddParkingSpaceActivity.this));
				}
				else
				{
					Toast.makeText(this, R.string.place_marker_error, Toast.LENGTH_SHORT).show();
				}
			}
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
	}


	private class AddParkingSpaceTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/add_parking_space.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("address", values[0]));
				nameValuePairs.add(new BasicNameValuePair("spaceDesc", values[1]));
				nameValuePairs.add(new BasicNameValuePair("price", values[2]));
				nameValuePairs.add(new BasicNameValuePair("lat", values[3]));
				nameValuePairs.add(new BasicNameValuePair("lng", values[4]));
				nameValuePairs.add(new BasicNameValuePair("user_id", values[5]));

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
				Toast.makeText(AddParkingSpaceActivity.this, R.string.parking_space_added_message, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(AddParkingSpaceActivity.this, ChooseUserTypeActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(AddParkingSpaceActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
			}

			else
			{
				Toast.makeText(AddParkingSpaceActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
