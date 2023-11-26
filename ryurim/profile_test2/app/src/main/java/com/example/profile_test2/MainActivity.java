package com.example.profile_test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 뒤로가기 버튼
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로가기 동작 정의
            }
        });

        // 설정 버튼
        View settingsButton = findViewById(R.id.settingButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정 버튼 동작 정의
            }
        });

        // 채팅 버튼
        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 채팅 버튼 동작 정의
            }
        });

        // 팔로우 버튼
        Button followButton = findViewById(R.id.followButton);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팔로우 버튼 동작 정의
            }
        });

        // 프로필 사진
        ImageView profilePicture = findViewById(R.id.profilePicture);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프로필 사진 클릭 동작 정의
            }
        });

        // 사용자 소개 EditText
        EditText userIntroductionEditText = findViewById(R.id.userintro);

        // 친구 목록 RecyclerView
        RecyclerView friendsRecyclerView = findViewById(R.id.friendsRecyclerView);
        LinearLayoutManager friendsLayoutManager = new LinearLayoutManager(this);
        friendsRecyclerView.setLayoutManager(friendsLayoutManager);

        // 임의의 친구 목록 데이터를 생성하여 어댑터에 전달
        // 실제로는 서버에서 데이터를 받아와 함!!
        String[] friendsData = {"친구 1", "친구 2", "친구 3"};
        FriendsAdapter friendsAdapter = new FriendsAdapter(friendsData);
        friendsRecyclerView.setAdapter(friendsAdapter);

        // 게시글 목록 RecyclerView
        RecyclerView postsRecyclerView = findViewById(R.id.postsRecyclerView);
        LinearLayoutManager postsLayoutManager = new LinearLayoutManager(this);
        postsRecyclerView.setLayoutManager(postsLayoutManager);

        // 임의의 게시글 목록 데이터를 생성하여 어댑터에 전달
        // 실제로는 서버에서 데이터를 받아와 함!!
        String[] postsData = {"게시글 1", "게시글 2", "게시글 3"};
        PostsAdapter postsAdapter = new PostsAdapter(postsData);
        postsRecyclerView.setAdapter(postsAdapter);

    }
}