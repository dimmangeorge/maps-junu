package com.example.clusteringdemo;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener;

public class MainActivity extends ActionBarActivity implements

OnClusterItemClickListener<MyLocations>, OnClusterClickListener<MyLocations>,
		OnMarkerClickListener {
	GoogleMap clusterMap;
	private ClusterManager<MyLocations> mClusterManager;

	double lat1, lng1;
	Random r = new Random();
	Cluster<MyLocations> cluster;
	ArrayList<MyLocations> list1;
	ArrayList<MyLocations> jinjin;

	OnCameraChangeListener Listener = new OnCameraChangeListener() {

		private float currentZoom = -1;

		@Override
		public void onCameraChange(CameraPosition arg0) {
			if (arg0.zoom != currentZoom)
				currentZoom = arg0.zoom;
			clusterMap.clear();
			jinjin.clear();
			addItems();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		clusterMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		clusterMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				8.502474, 76.9401085), 13.0f));

		addItems();
	}

	private void addItems() {

		mClusterManager = new ClusterManager<MyLocations>(this, clusterMap);

		clusterMap.setOnCameraChangeListener(mClusterManager);
		jinjin = new ArrayList<MyLocations>();

		clusterMap.setOnMarkerClickListener(mClusterManager);
		clusterMap.setOnMarkerClickListener(this);
		clusterMap.setOnInfoWindowClickListener(mClusterManager);
		mClusterManager.setOnClusterClickListener(this);
		mClusterManager.setOnClusterItemClickListener(this);
		clusterMap.setOnMarkerClickListener(mClusterManager);

		mClusterManager.cluster();

		double lat = 8.502474;
		double lng = 76.9401085;

		for (int i = 0; i < 6; i++) {
			double iter = i / 100d;
			lat = lat + iter;
			lng = lng + iter;
			MyLocations offsetItem = new MyLocations(lat, lng, i + "");

			jinjin.add(offsetItem);
			mClusterManager.addItem(offsetItem);
		}

		lat = lat + 1;
		lng = lng + 1;

		for (int i = 0; i < 6; i++) {
			double iter = i / 100d;
			lat = lat + iter;
			lng = lng + iter;
			MyLocations offsetItem = new MyLocations(lat, lng, i + "");
			jinjin.add(offsetItem);
			mClusterManager.addItem(offsetItem);
		}

	}

	@Override
	public boolean onClusterClick(Cluster<MyLocations> cluster) {
		int j;

		// this.cluster = cluster;
		ArrayList<LatLng> list = new ArrayList<LatLng>();
		list1 = new ArrayList<MyLocations>();
		list1.addAll(cluster.getItems());

		for (int i = 0; i < cluster.getSize(); i++) {
			list.add(list1.get(i).getPosition());
		}

		lat1 = list.get(list.size() / 2).latitude;
		lng1 = list.get(list.size() / 2).longitude;

		mClusterManager.clearItems();
		clusterMap.clear();

		for (int i = 0, j1 = 0; i < 360; i += (360 / list.size()), j1++) {

			clusterMap.addMarker(new MarkerOptions().position(
					new LatLng(lat1 + 1 * Math.cos(Math.toRadians(i)), lng1 + 1
							* Math.sin(Math.toRadians(i)))).title(
					list1.get(j1).name));

			clusterMap.setOnMarkerClickListener(this);

		}

		for (int i = 0; i < jinjin.size(); i++) {
			int k = 0;
			for (j = 0; j < list.size(); j++) {
				if (jinjin.get(i) != (list1.get(j))) {
					k++;
				}
			}
			if (k == list.size()) {
				mClusterManager.addItem(jinjin.get(i));
				mClusterManager.setOnClusterClickListener(this);
			}

		}
		mClusterManager.setOnClusterClickListener(this);
		mClusterManager.cluster();
		// LatLngBounds bounds = builder.build();
		// CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
		// clusterMap.moveCamera(cu);
		clusterMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(list.get((list.size() - 1)).latitude, list.get((list
						.size() - 1)).longitude), 7.0f));

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				cameraChanger();
			}
		}

		, 100);

		return true;
	}

	@Override
	public boolean onClusterItemClick(MyLocations item) {

		return false;
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub

		for (int i = 0; i < list1.size(); i++) {
			if (arg0.getTitle() != null && list1.get(i).name != null) {
				if (arg0.getTitle().equals(list1.get(i).name)) {
					clusterMap.addMarker(new MarkerOptions().position(
							new LatLng(list1.get(i).getPosition().latitude,
									list1.get(i).getPosition().longitude))
							.title(list1.get(i).name));
					arg0.remove();
					clusterMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(list1.get(i).getPosition().latitude,
									list1.get(i).getPosition().longitude), 15));

				}
			}
		}

		cameraChanger();

		return true;
	}

	public void cameraChanger() {
		clusterMap.setOnCameraChangeListener(Listener);
	}

}