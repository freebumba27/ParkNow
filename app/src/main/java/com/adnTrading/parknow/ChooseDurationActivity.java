package com.adnTrading.parknow;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

import android.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.adnTrading.parknow.R.id;
import com.adnTrading.utils.ReuseableClass;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class ChooseDurationActivity extends Activity {

	private ProgressDialog dialog;
	ListView ListViewReview;

	CustomAdapter adapter;

	TextView editTextHourlyRateValue;
	TextView editTextHourlyAddressValue;
	TextView editTextHourlyDescValue;
	EditText editTextBookingFromTimeValue;
	EditText editTextBookingToTimeValue;
	EditText editTextBookingFromDateValue;
	EditText editTextBookingToDateValue;
	TextView editTextTotalValue;
	
	String userName[] 	= null;
	String review[] 	= null;
	String rating[] 	= null;

	String parkingSpotId = "";
	//set the environment for production/sandbox/no netowrk
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

	private static final String CONFIG_CLIENT_ID = "AWqVIiUIDb4wBui10by9S6BedYsfyFGVgBtlI0SzM9QRWcCzOZai7LBmWXBifbqC5UZBkIUVU7FtBrL4";

	private static final int REQUEST_PAYPAL_PAYMENT = 1;

	private static PayPalConfiguration config = new PayPalConfiguration()
	.environment(CONFIG_ENVIRONMENT)
	.clientId(CONFIG_CLIENT_ID)
	// The following are only used in PayPalFuturePaymentActivity.
	.merchantName("ADN Trading")
	.merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
	.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_duration);

		//call pay pal services
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);


		editTextHourlyRateValue 		= (TextView)findViewById(R.id.editTextHourlyRateValue);
		editTextHourlyAddressValue 		= (TextView)findViewById(R.id.editTextHourlyAddressValue);
		editTextHourlyDescValue 		= (TextView)findViewById(R.id.editTextHourlyDescValue);
		editTextBookingFromTimeValue 	= (EditText)findViewById(R.id.editTextBookingFromValue);
		editTextBookingToTimeValue		= (EditText)findViewById(R.id.editTextBookingToValue);
		editTextBookingFromDateValue	= (EditText)findViewById(R.id.editTextBookingFromDateValue);
		editTextBookingToDateValue		= (EditText)findViewById(R.id.editTextBookingToDateValue);
		editTextTotalValue				= (TextView)findViewById(R.id.editTextTotalValue);

		((TextView)findViewById(R.id.textViewBookingTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.TextViewReview)).setTypeface(ReuseableClass.getFontStyle(this));
		
		editTextHourlyRateValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextHourlyAddressValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextHourlyDescValue.setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyRateTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyAddressTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyDescTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyBookingFromDateTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyBookingFromTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyBookingToDateTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyBookingToTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.editTextHourlyTotalTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		editTextBookingFromTimeValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextBookingToTimeValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextBookingFromDateValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextBookingToDateValue.setTypeface(ReuseableClass.getFontStyle(this));
		editTextTotalValue.setTypeface(ReuseableClass.getFontStyle(this));

		editTextBookingFromTimeValue.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {calculatingTotal();}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 

		editTextBookingToTimeValue.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {calculatingTotal();}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 

		editTextBookingFromDateValue.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {calculatingTotal();}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 

		editTextBookingToDateValue.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {calculatingTotal();}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 


		Bundle extras = getIntent().getExtras(); 
		String lat = null;
		String lng = null;

		if (extras != null) 
		{
			lat = extras.getString("lat");
			lng = extras.getString("lng");

			//Getting all the parking spots details
			if(ReuseableClass.haveNetworkConnection(this))
			{

				dialog = new ProgressDialog(this);
				dialog.setMessage(this.getString(R.string.wait_a_min_dialog_message));
				dialog.show();

				new GetParkingSpotsDetailsTask().execute(lat, lng);
			}
			else
			{
				Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
			}
		}

		//Review ListView
		ListViewReview = (ListView)findViewById(R.id.ListViewReview);
		ListViewReview.setOnTouchListener(new OnTouchListener() 
		{ 
		    @Override 
		    public boolean onTouch(View v, MotionEvent event) 
		    {
		    	v.getParent().requestDisallowInterceptTouchEvent(true);
		    	return false; 
		    }
		}); 
	}


	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(this, SearchParkingSpaceActivity.class);
		startActivity(i);
		finish();
	}

	private class GetParkingSpotsDetailsTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/get_parking_spots.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("lat", values[0]));
				nameValuePairs.add(new BasicNameValuePair("lng", values[1]));

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
				Toast.makeText(ChooseDurationActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(ChooseDurationActivity.this, SearchParkingSpaceActivity.class);
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
						parkingSpotId = jsonobject.getString("id");
						String user_id = jsonobject.getString("user_id");
						String address = jsonobject.getString("address");
						String spaceDesc = jsonobject.getString("spaceDesc");
						String price = jsonobject.getString("price");
						String lat = jsonobject.getString("lat");
						String lng = jsonobject.getString("lng");

						editTextHourlyRateValue.setText("EUR " + price);
						editTextHourlyAddressValue.setText(address);
						editTextHourlyDescValue.setText(spaceDesc);

					}
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
					Toast.makeText(ChooseDurationActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
				}
			}
			dialog.dismiss();

			//Getting all the parking spots details
			if(ReuseableClass.haveNetworkConnection(ChooseDurationActivity.this))
			{

				dialog = new ProgressDialog(ChooseDurationActivity.this);
				dialog.setMessage(ChooseDurationActivity.this.getString(R.string.wait_a_min_dialog_message));
				dialog.show();

				new GetReviewDetailsTask().execute(parkingSpotId);
			}
			else
			{
				Toast.makeText(ChooseDurationActivity.this, R.string.check_network, Toast.LENGTH_SHORT).show();
			}
		}
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Get Review Data
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private class GetReviewDetailsTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/get_user_review.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("parkingSpotId", values[0]));

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
				Toast.makeText(ChooseDurationActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(ChooseDurationActivity.this, SearchParkingSpaceActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO DATA"))
			{
				//No Review Found
			}
			else
			{
				try 
				{
					JSONArray jsonarray = new JSONArray(result);

					userName 	= new String[jsonarray.length()];
					review 		= new String[jsonarray.length()];
					rating	 	= new String[jsonarray.length()];
					
					for (int i = 0; i < jsonarray.length(); i++) 
					{
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						String user_name = jsonobject.getString("username");
						String reviewText = jsonobject.getString("review");
						String review_star = jsonobject.getString("review_star");

						userName[i] = user_name;
						review[i] 	= reviewText;
						rating[i] 	= review_star;
					}
					adapter = new CustomAdapter(ChooseDurationActivity.this, R.layout.review_row, userName);
					ListViewReview.setAdapter(adapter);
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
					Toast.makeText(ChooseDurationActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
				}
			}
			dialog.dismiss();
		}
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//END Get Review Data
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Time picker
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void timePickerStartingRental(final View v) 
	{
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(ChooseDurationActivity.this, new TimePickerDialog.OnTimeSetListener() 
		{
			@Override
			public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) 
			{
				String selectedMinuteStr = selectedMinute + "";
				if(selectedMinuteStr.length()<2)
				{
					selectedMinuteStr = 0 + selectedMinuteStr;
				}

				String selectedHourStr = selectedHour + "";
				if(selectedHourStr.length()<2)
				{
					selectedHourStr = 0 + selectedHourStr;
				}

				((EditText)v).setText( "" + selectedHourStr + ":" + selectedMinuteStr);
			}
		}, hour, minute, true);
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//END Time picker
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Date picker
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void datePicker(final View v) 
	{
		Calendar mcurrentDate=Calendar.getInstance();
		int mYear=mcurrentDate.get(Calendar.YEAR);
		int mMonth=mcurrentDate.get(Calendar.MONTH);
		int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog mDatePicker=new DatePickerDialog(ChooseDurationActivity.this, new OnDateSetListener() 
		{                  
			public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) 
			{
				String selectedmonthModified = (selectedmonth+1)/10==0?("0"+(selectedmonth+1)): String.valueOf((selectedmonth+1));

				((EditText)v).setText(new StringBuilder()
				.append(selectedday).append("-")
				.append(selectedmonthModified).append("-")
				.append(selectedyear).append(" "));
			}
		},mYear, mMonth, mDay);
		mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
		mDatePicker.setTitle("Select date");                
		mDatePicker.show();  
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//END Date picker
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//PayPal Integration
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void goingToPay(View v) 
	{

		String fromTime = editTextBookingFromTimeValue.getText().toString();
		String toTime 	= editTextBookingToTimeValue.getText().toString(); 
		String fromDate = editTextBookingFromDateValue.getText().toString(); 
		String toDate 	= editTextBookingToDateValue.getText().toString(); 

		if(!fromTime.trim().equalsIgnoreCase("") && !toTime.trim().equalsIgnoreCase("") && !fromDate.trim().equalsIgnoreCase("") && !toDate.trim().equalsIgnoreCase(""))
		{
			PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(editTextTotalValue.getText().toString().substring(4)),"EUR", "Parking Spot - From: "+fromDate + " " + fromTime+" To:  "+toDate + " " + toTime, PayPalPayment.PAYMENT_INTENT_SALE);

			Intent intent = new Intent(ChooseDurationActivity.this, PaymentActivity.class);
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
			startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);	
		}
		else
		{
			Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == REQUEST_PAYPAL_PAYMENT) 
		{
			if (resultCode == Activity.RESULT_OK) 
			{
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) 
				{
					try 
					{
						System.out.println("Responseeee"+confirm);
						Log.i("paymentExample", confirm.toJSONObject().toString());


						JSONObject jsonObj=new JSONObject(confirm.toJSONObject().toString());

						String paymentId = jsonObj.getJSONObject("response").getString("id");
						System.out.println("payment id: " + paymentId);

						//Toast.makeText(getApplicationContext(), paymentId, Toast.LENGTH_LONG).show();  


						String fromTime = editTextBookingFromTimeValue.getText().toString();
						String toTime 	= editTextBookingToTimeValue.getText().toString(); 
						String fromDate = editTextBookingFromDateValue.getText().toString(); 
						String toDate 	= editTextBookingToDateValue.getText().toString(); 

						//saving order details
						if(ReuseableClass.haveNetworkConnection(this))
						{
							dialog = new ProgressDialog(ChooseDurationActivity.this);
							dialog.setMessage(ChooseDurationActivity.this.getString(R.string.wait_a_min_dialog_message));
							dialog.show();

							new AddOrderDetailsTask().execute(parkingSpotId, ReuseableClass.getFromPreference("user_id", ChooseDurationActivity.this), paymentId, fromDate + " " + fromTime, toDate + " " + toTime, editTextTotalValue.getText().toString().substring(4));
						}
						else
						{
							Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
						}
					} 
					catch (JSONException e) 
					{
						Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} 
			else if (resultCode == Activity.RESULT_CANCELED) 
			{
				Log.i("paymentExample", "The user canceled.");
			} 
			else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) 
			{
				Log.i("paymentExample", "An invalid Payment was submitted. Please see the docs.");
			}
		} 
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//END PayPal Integration
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void calculatingTotal() 
	{
		String fromTime = editTextBookingFromTimeValue.getText().toString();
		String toTime 	= editTextBookingToTimeValue.getText().toString(); 
		String fromDate = editTextBookingFromDateValue.getText().toString(); 
		String toDate 	= editTextBookingToDateValue.getText().toString(); 

		if(!fromTime.trim().equalsIgnoreCase("") && !toTime.trim().equalsIgnoreCase("") && !fromDate.trim().equalsIgnoreCase("") && !toDate.trim().equalsIgnoreCase(""))
		{
			//25-04-2015  22:50
			long fromMiliSec = dateTimeToMilisec(fromDate + " " + fromTime);
			long toMiliSec   = dateTimeToMilisec(toDate + " " + toTime);

			long diffInHour = TimeUnit.MILLISECONDS.toHours(toMiliSec - fromMiliSec);
			float totalPrice = diffInHour * Float.parseFloat(editTextHourlyRateValue.getText().toString().substring(4));

			System.out.println("Diffrent in min :: " + diffInHour);
			if(totalPrice >0)
			{
				editTextTotalValue.setText("EUR " + totalPrice);
			}
			else
			{
				Toast.makeText(this, R.string.error_on_date_time, Toast.LENGTH_LONG).show();
				editTextBookingFromTimeValue.setText("");
				editTextBookingToTimeValue.setText("");
				editTextBookingFromDateValue.setText("");
				editTextBookingToDateValue.setText(""); 
				editTextTotalValue.setText(""); 
			}
		}
		else
		{
			//Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_LONG).show();
		}
	}

	public long dateTimeToMilisec(String givenDateString) 
	{
		long timeInMilliseconds = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try 
		{
			Date mDate = sdf.parse(givenDateString);
			timeInMilliseconds = mDate.getTime();
			System.out.println("Date Time in milli :: " + timeInMilliseconds);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return timeInMilliseconds;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Order Details
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private class AddOrderDetailsTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/add_payment_details.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("parking_spot_id", values[0]));
				nameValuePairs.add(new BasicNameValuePair("purchaser_user_id", values[1]));
				nameValuePairs.add(new BasicNameValuePair("paypal_payment_id", values[2]));
				nameValuePairs.add(new BasicNameValuePair("spot_booking_from", values[3]));
				nameValuePairs.add(new BasicNameValuePair("spot_booking_upto", values[4]));
				nameValuePairs.add(new BasicNameValuePair("price", values[5]));

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
				Toast.makeText(ChooseDurationActivity.this, R.string.order_details_inserted_successful_message, Toast.LENGTH_SHORT).show();

				Intent i = new Intent(ChooseDurationActivity.this, SearchParkingSpaceActivity.class);
				startActivity(i);
				finish();
			}
			else if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(ChooseDurationActivity.this, R.string.payment_done_but_server_error, Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(ChooseDurationActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Order Details
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	class CustomAdapter extends ArrayAdapter<String> {
		Context ctx;
		public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
			ctx = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if(row==null)
			{ 
				LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.review_row, parent, false);
			}

			TextView textViewUsername 	= (TextView) row.findViewById(R.id.TextViewUsername);
			TextView rowTextViewReview 	= (TextView) row.findViewById(R.id.rowTextViewReview);
			RatingBar ratingBarReview 	= (RatingBar) row.findViewById(R.id.ratingBarReview);

			textViewUsername.setText(userName[position]);
			rowTextViewReview.setText(review[position]);
			ratingBarReview.setRating(Float.parseFloat(rating[position]));
			
			return row;
		}
	}
}
