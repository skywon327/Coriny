package com.example.app_alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class acoount extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText("계정관리");
        ImageView image_name = (ImageView)findViewById(R.id.image_name);
        image_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(acoount.this, MainActivity.class);//넘겨받을 화면은 this, 이동할 화면은 class
                startActivity(intent);
            }
        });

        // XML 레이아웃 파일에서 정의한 ListView를 찾아옵니다.
        ListView listView = (ListView) findViewById(R.id.listView);
        // ListView에 표시할 항목들을 ArrayList에 추가합니다.
        ArrayList<String> items = new ArrayList<>(Arrays.asList("이메일", "SNS 연동", "휴대폰번호 변경", "비밀번호 변경", "회원탈퇴", "로그아웃"));
        // CustomAdapter 클래스를 사용하여 ListView에 항목을 표시할 어댑터를 생성합니다.
        acoount.CustomAdapter adapter = new acoount.CustomAdapter(this, 0, items);
        // ListView에 어댑터를 설정합니다.
        listView.setAdapter(adapter);
    }


    // CustomAdapter 클래스를 정의합니다.
    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;
        private Class<?> move;
        // CustomAdapter 생성자
        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        // ListView에 각 항목을 표시하기 위한 뷰를 생성하고 반환합니다.
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                // 뷰가 없는 경우, LayoutInflater를 사용하여 뷰를 생성합니다.
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listview_item, null);
            }
            FrameLayout list_frame = (FrameLayout) v.findViewById(R.id.list_frame);
            FrameLayout list_frame2 = (FrameLayout) v.findViewById(R.id.list_frame2);
            // 각 항목의 아이콘을 설정하기 위한 ImageView 인스턴스를 찾아옵니다.
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            // 각 항목의 텍스트를 설정하기 위한 TextView 인스턴스를 찾아옵니다.
            TextView textView = (TextView) v.findViewById(R.id.textView);
            // 각 항목의 버튼을 찾아옵니다.
            Button button = (Button) v.findViewById(R.id.button);
            TextView textView_sub = new TextView(getApplicationContext());
            if ("이메일".equals(items.get(position))){
                list_frame2.removeView(button);
                textView_sub.setText("skywon333@naver.com");
                list_frame2.addView(textView_sub);}
            else if ("SNS 연동".equals(items.get(position))){
                list_frame2.removeView(button);
                textView_sub.setText("naver");
                list_frame2.addView(textView_sub);}
            else if ("로그아웃".equals(items.get(position))){
                list_frame2.removeView(button);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        move = SubActivity.class;
                        // Intent를 사용하여 새로운 화면으로 이동합니다.
                        Intent intent = new Intent(acoount.this, move);
                        startActivity(intent);
                    }
                });}

            list_frame.removeView(imageView);


            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            int leftMarginInDp = 0;
            int leftMarginInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMarginInDp, getResources().getDisplayMetrics());
            layoutParams.leftMargin = leftMarginInPixels;
            textView.setLayoutParams(layoutParams);
            // TextView에 항목의 텍스트를 설정합니다.
            textView.setText(items.get(position));

            // 현재 항목의 텍스트를 가져와서 버튼 클릭 이벤트에서 사용하기 위해 저장합니다.
            final String text = items.get(position);

            if ("로그아웃".equals(items.get(position)))

                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭한 항목에 따라 이동할 화면의 클래스를 설정합니다.
                    if ("휴대폰번호 변경".equals(items.get(position))){
                        move = SubActivity.class;}
                    else if ("비밀번호 변경".equals(items.get(position))){
                        move = SubActivity.class;}
                    else if ("회원탈퇴".equals(items.get(position))){
                        move = SubActivity.class;}

                    // Intent를 사용하여 새로운 화면으로 이동합니다.
                    Intent intent = new Intent(acoount.this, move);
                    startActivity(intent);
                }
            });

            return v;
        }
    }
}