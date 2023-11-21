package com.example.map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap googleMap;
    private EditText search_input;
    private Button bt;
    private ImageView search;
    private LinearLayout ll, shapeFrameLayout2, SearchValue;
    private FrameLayout map;
    private TextView SearchName;
    private float initialY, height;
    private boolean resizing = false;
    private String str;
    private List<String> itemList = new ArrayList<>(); // 검색해서 나온 결과들 저장할 리스트 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 화면 높이를 구하기
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        height = dm.heightPixels / 2;

        //구글 지도객체를 가져오기
        SupportMapFragment MapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        MapFragment.getMapAsync((OnMapReadyCallback) this);

        // 아이디 불러오기
        ll = findViewById(R.id.ll);
        map = findViewById(R.id.map);
        shapeFrameLayout2 = findViewById(R.id.shapeFrameLayout2);
        search_input = findViewById(R.id.search_input);
        SearchName = findViewById(R.id.SearchName);
        SearchValue = findViewById(R.id.SearchValue);

        onLoadListeners();  // 리스너 로드, 클릭 시 기능들

        map.getLayoutParams().height = (int) (height);
        map.requestLayout();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // 37.557667, 126.926546 홍대입구역
        LatLng latLng = new LatLng(37.715968, 127.1169024); //검색할 위도 경도
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)); // LatLng를 바라보는 카메라 시각으로 나타내기
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // 카메라 줌 정도 설정
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("검색위치"); // 마커 이름 설정
        googleMap.addMarker(markerOptions); // 마커를 지도에 표시하게
        // 사용자가 gps권한을 허용했는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void ingload() {

    }

    public void onLoadListeners() {
        bt = findViewById(R.id.bt);
        search = findViewById(R.id.search);

        // 터치했을 때
        bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getRawY();
                        resizing = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (resizing) {
                            float dy = event.getRawY() - initialY;
                            float newHeight1 = height + dy;
                            float newHeight2 = height - dy;
                            if (newHeight1 >= 100 && newHeight2 >= 300) { // 최소 크기 설정
                                map.getLayoutParams().height = (int) newHeight1;
                                map.requestLayout();
                                shapeFrameLayout2.getLayoutParams().height = (int) newHeight2;
                                shapeFrameLayout2.requestLayout();

                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        resizing = false;
                        break;
                }
                return true;
            }
        });
        //  돋보기 눌렀을 때 검색기능

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = search_input.getText().toString(); //여기에 str을 해줘야함, 클릭을 했을 때 기준으로 넘기기!
                itemList.clear();
                SearchValue.removeAllViews();
                SearchName.setText("\""+str+"\""+"검색 결과");
                // 랜덤한 개수의 항목 생성 (1에서 10까지)
                int minCount = 1;
                int maxCount = 3;
                Random random = new Random();
                int itemCount = random.nextInt(maxCount - minCount + 1) + minCount;

                for (int i = 0; i < itemCount; i++) {

                    itemList.add("itemCount");
                }

                // 리스트의 개수만큼 TextView를 생성하여 LinearLayout에 추가
                for (String item : itemList) {
                    TextView textView = new TextView(MainActivity.this); // 텍스트뷰 생성할 때 this만 쓰면 에러남 MainActivity.this로 써야함
                    textView.setText(item); // 각 TextView에 텍스트 설정
                    textView.setTextSize(16); // 텍스트 크기 설정 (옵션)
                    textView.setPadding(16, 16, 16, 16); // 텍스트 뷰의 패딩 설정 (옵션)
                    SearchValue.addView(textView); // SearchValue TextView 추가
                }
            }
        });
    }


}