package com.jnu.booklistmainactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private MapView mapView;
    private Marker marker;

    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.baidu_map_view);

        LatLng cenpt = new LatLng(22.255925,113.541112);  //设定中心点坐标-暨南大学

        MapStatus.Builder builder = new MapStatus.Builder();  //设置缩放

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.jnu);
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(cenpt);
        marker = (Marker) mapView.getMap().addOverlay(markerOptions);
        
        //对 marker 添加点击相应事件
        mapView.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(rootView.getContext().getApplicationContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        //按钮控制添加删除 button
        Button button=rootView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(button.getText() == "添加Marker")
                {
                    marker = (Marker) mapView.getMap().addOverlay(markerOptions);
                    button.setText("删除Marker");
                }
                else
                {
                    marker.remove();
                    button.setText("添加Marker");
                }
            }
        });
        builder.zoom(18.0f).target(cenpt);
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}