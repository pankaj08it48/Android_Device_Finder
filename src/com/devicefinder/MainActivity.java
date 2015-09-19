package com.devicefinder;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationListener {
	
	double latitude = 23.049442 ;
	double longitude = 72.524247;
	GoogleMap googleMap;
	public LocationManager locationManager ;
	public Location location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			initialization();
		} catch (Exception e) {
			Log.e("Loding Map", e.toString());
		}
	}
	public void initialization() {
		if(googleMap==null){
			googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setMyLocationEnabled(true);
			
			MarkerOptions fixMarker = new MarkerOptions().position(new LatLng(latitude, longitude))
									.title("SAL Hospital");
			googleMap.addMarker(fixMarker);
			
			currentLocation();
			
			if(googleMap==null){
				Toast.makeText(getApplicationContext(), "Soory unable to create maps", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void currentLocation() {
		try {
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			
			Criteria criteria = new Criteria();
			
			String provider = locationManager.getBestProvider(criteria, true);
			
			location = locationManager.getLastKnownLocation(provider);
			
			if(location != null){
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(provider,2000,0,this);
			
		} catch (Exception e) {
			
		}
	}
	
	
	@Override
	protected void onResume() {
		initialization();
		super.onResume();
	}
	
	@Override
	public void onLocationChanged(Location location) {
				//getting latitude of the current location
				double curLatitude = location.getLatitude();
				
				//getting longitude of the current location
				double curLongotude = location.getLongitude();
				
				//create a LatLag object of the current location
				LatLng latLng = new LatLng(curLatitude, curLongotude);
				
				//showing the current location in google map
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				
				//zoom in the google map
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
				
				MarkerOptions curMarker = new MarkerOptions().position(latLng)
						.title("Shuban Tech.");
				googleMap.addMarker(curMarker);
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
