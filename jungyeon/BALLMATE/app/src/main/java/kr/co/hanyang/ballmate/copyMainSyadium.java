//package kr.co.hanyang.ballmate;
//
//import static com.google.android.material.internal.ViewUtils.hideKeyboard;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class MainStadium extends Fragment implements OnMapReadyCallback {
//
//    private View view;
//
//    private GoogleMap googleMap;
//    private EditText search_input;
//    private Button bt;
//    private ImageView search;
//    private LinearLayout ll, shapeFrameLayout2, SearchValue;
//    private FrameLayout map;
//    private TextView SearchName;
//    private float initialY, height;
//    private boolean resizing = false;
//    private String str;
//    private List<String> itemList = new ArrayList<>(); // 검색해서 나온 결과들 저장할 리스트 생성
//
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.main_stadium, container, false);
//
//        return view;
//    }
//
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // 화면 높이를 구하기
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        height = dm.heightPixels / 2;
//
//        // 구글 지도 객체를 가져오기
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        mapFragment.getMapAsync(this);
//
//        // 아이디 불러오기
//        ll = view.findViewById(R.id.ll);
//        map = view.findViewById(R.id.map);
//        shapeFrameLayout2 = view.findViewById(R.id.shapeFrameLayout2);
//        search_input = view.findViewById(R.id.search_input);
//        SearchName = view.findViewById(R.id.SearchName);
//        SearchValue = view.findViewById(R.id.SearchValue);
//
//        onLoadListeners();  // 리스너 로드, 클릭 시 기능들
//
//        map.getLayoutParams().height = (int) (height);
//        map.requestLayout();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
//        LatLng latLng = new LatLng(37.715968, 127.1169024);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("검색위치");
//        googleMap.addMarker(markerOptions);
//        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//        } else {
//            checkLocationPermissionWithRationale();
//        }
//    }
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    private void checkLocationPermissionWithRationale() {
//        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
//                new AlertDialog.Builder(requireActivity())
//                        .setTitle("위치정보")
//                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        }).create().show();
//            } else {
//                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        googleMap.setMyLocationEnabled(true);
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//        }
//    }
//
//    public void ingload() {
//        // 이 부분에 필요한 기능을 추가하세요.
//    }
//
//    public void onLoadListeners() {
//        bt = view.findViewById(R.id.bt);
//        search = view.findViewById(R.id.search);
//
//        // 터치했을 때
//        bt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        initialY = event.getRawY();
//                        resizing = true;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (resizing) {
//                            float dy = event.getRawY() - initialY;
//                            float newHeight1 = height + dy;
//                            float newHeight2 = height - dy;
//                            if (newHeight1 >= 100 && newHeight2 >= 300) { // 최소 크기 설정
//                                map.getLayoutParams().height = (int) newHeight1;
//                                map.requestLayout();
//                                shapeFrameLayout2.getLayoutParams().height = (int) newHeight2;
//                                shapeFrameLayout2.requestLayout();
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        resizing = false;
//                        break;
//                }
//                return true;
//            }
//        });
//
//        // 돋보기 눌렀을 때 검색 기능
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                str = search_input.getText().toString();
//                itemList.clear();
//                SearchValue.removeAllViews();
//                SearchName.setText("\"" + str + "\"" + "검색 결과");
//                hideKeyboard(view);
//
//                // 여기에 실제 검색 및 결과 처리 코드를 추가하세요.
//
//                // 랜덤한 개수의 항목 생성 (1에서 10까지)
//                int minCount = 1;
//                int maxCount = 3;
//                Random random = new Random();
//                int itemCount = random.nextInt(maxCount - minCount + 1) + minCount;
//
//                for (int i = 0; i < itemCount; i++) {
//                    itemList.add("itemCount");
//                }
//
//                // 리스트의 개수만큼 TextView를 생성하여 LinearLayout에 추가
//                for (String item : itemList) {
//                    TextView textView = new TextView(requireActivity()); // 수정
//                    textView.setText(item);
//                    textView.setTextSize(16);
//                    textView.setPadding(16, 16, 16, 16);
//                    SearchValue.addView(textView);
//                }
//            }
//        });
//
//        // 검색 입력창에서 엔터 키를 눌렀을 때 키보드를 숨기는 리스너
//        search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    // 엔터를 눌렀을 때 키보드를 숨깁니다.
//                    hideKeyboard(view);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void hideKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(requireContext().INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//}