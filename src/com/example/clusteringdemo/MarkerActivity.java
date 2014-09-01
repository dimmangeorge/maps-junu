package com.example.clusteringdemo;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MarkerActivity extends Activity implements OnMarkerClickListener {

	ArrayList<LatLng> latlist;
	GoogleMap map;
	double lat, lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setOnMarkerClickListener(this);
		latlist = getIntent().getParcelableArrayListExtra("position");

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(latlist.get((latlist.size()) / 2).latitude,
						lng = latlist.get((latlist.size()) / 2).longitude),
				10.0f));
		for (int i = 0; i < latlist.size(); i++) {
			lat = latlist.get(i).latitude;
			lng = latlist.get(i).longitude;
			map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
		}

	}
	
	
//	for (int i = 0; i < list.size(); i++) {
//		lat1 = list.get(i).latitude;
//		lng1 = list.get(i).longitude;
//		if ((list.get(i).latitude != list.get(i + 1).latitude)
//				&& (list.get(i).longitude != list.get(i + 1).longitude)){
//			clusterMap.addMarker(new MarkerOptions().position(new LatLng(
//					lat1, lng1)));
//		}else{
//			MarkerOptions a = new MarkerOptions()
//		    .position(new LatLng(0,0));
//		Marker m = clusterMap.addMarker(a);
//		m.setPosition(new LatLng(lat1,lng1));
//		}
//	
	
	// clusterMap
			// .animateCamera(CameraUpdateFactory.newLatLngZoom(cluster
			// .getPosition(), (float) Math.floor(clusterMap
			// .getCameraPosition().zoom + 1)), 300, null);
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		map.moveCamera(CameraUpdateFactory.newLatLngZoom
				(marker.getPosition(), 15));
		return false;
	}
}
