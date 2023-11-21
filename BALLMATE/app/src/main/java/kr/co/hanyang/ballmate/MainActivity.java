package kr.co.hanyang.ballmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    // fragment 선언 및 네이게이션 바 사이에 이동 구현
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private  MainCalendar mainCalendar;
    private MainChat mainChat;
    private MainHome mainHome;
    private MainPeople mainPeople;
    private MainStadium mainStadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int v = item.getItemId();
                // 아이템 선택 처리
                if (v == R.id.menu_home) {
                    setFrag(0);
                } else if (v == R.id.menu_calender) {
                    setFrag(1);
                } else if (v == R.id.menu_icon_chat) {
                    setFrag(2);
                } else if (v == R.id.menu_icon_stadium) {
                    setFrag(3);
                } else if (v == R.id.menu_icon_people) {
                    setFrag(4);
                }
                return true;
            }
        });

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // 아이템 선택 처리
//                if (item.getItemId() == R.id.menu_home) {
//                    setFrag(0);
//                } else if (item.getItemId() == R.id.menu_calender) {
//                    setFrag(1);
//                } else if (item.getItemId() == R.id.menu_icon_chat) {
//                    setFrag(2);
//                } else if (item.getItemId() == R.id.menu_icon_stadium) {
//                    setFrag(3);
//                } else if (item.getItemId() == R.id.menu_icon_people) {
//                    setFrag(4);
//                }
//                return true;
//            }
//        });


        mainCalendar = new MainCalendar();
        mainChat = new MainChat();
        mainHome = new MainHome();
        mainPeople = new MainPeople();
        mainStadium = new MainStadium();
        setFrag(0); // 첫 화면을 무엇으로 지정해줄 것인지 선택
    }

    // fregment 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.fl_main, mainHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.fl_main, mainCalendar);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.fl_main, mainChat);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.fl_main, mainStadium);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.fl_main, mainPeople);
                ft.commit();
                break;
        }
    }


}