package com.example.clusteringdemo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyLocations implements ClusterItem {
	public final String name;
	private final LatLng mPosition;

	public MyLocations(double lat, double lng, String name) {
		mPosition = new LatLng(lat, lng);
		this.name = name;
	}

	@Override
	public LatLng getPosition() {
		return mPosition;
	}

	

}
