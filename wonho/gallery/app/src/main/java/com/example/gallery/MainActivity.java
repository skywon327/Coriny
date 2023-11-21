package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    ImageView imageView;  // 이미지를 표시하는 뷰
    Button button;  // 갤러리 열기 버튼
    String TAG = "MainActivity";  // 로그에 사용할 태그

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML에서 정의한 뷰들을 연결
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        // 갤러리 열기 버튼에 클릭 리스너 설정
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리를 열기 위한 인텐트 생성
                Intent intent = new Intent(Intent.ACTION_PICK);
                // 이미지 파일만 선택할 수 있도록 설정
                intent.setType("image/*");
                // 갤러리 열기 요청 코드(1)과 함께 인텐트 실행
                startActivityForResult(intent, 1);
            }
        });
    }


}
