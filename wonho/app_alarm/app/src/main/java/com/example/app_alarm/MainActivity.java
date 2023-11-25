package com.example.app_alarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 레이아웃 파일에서 정의한 ListView를 찾아옵니다.
        ListView listView = (ListView) findViewById(R.id.listView);
        // ListView에 표시할 항목들을 ArrayList에 추가합니다.
        ArrayList<String> items = new ArrayList<>(Arrays.asList("계정관리", "알림설정", "버디관리", "공지사항", "문의하기", "정보"));
        // CustomAdapter 클래스를 사용하여 ListView에 항목을 표시할 어댑터를 생성합니다.
        CustomAdapter adapter = new CustomAdapter(this, 0, items);
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

            // 각 항목의 아이콘을 설정하기 위한 ImageView 인스턴스를 찾아옵니다.
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

            // 각 항목에 따라 이미지를 설정합니다.
            if ("계정관리".equals(items.get(position))){
                imageView.setImageResource(R.drawable.account);
            }
            else if ("알림설정".equals(items.get(position))){
                imageView.setImageResource(R.drawable.alarm);
            }
            else if ("버디관리".equals(items.get(position))){
                imageView.setImageResource(R.drawable.friend);
            }
            else if ("공지사항".equals(items.get(position))){
                imageView.setImageResource(R.drawable.notification);
            }
            else if ("문의하기".equals(items.get(position))){
                imageView.setImageResource(R.drawable.question);
            }
            else if ("정보".equals(items.get(position))){
                imageView.setImageResource(R.drawable.imformation);
            }

            // 각 항목의 텍스트를 설정하기 위한 TextView 인스턴스를 찾아옵니다.
            TextView textView = (TextView) v.findViewById(R.id.textView);
            // TextView에 항목의 텍스트를 설정합니다.
            textView.setText(items.get(position));
            // 현재 항목의 텍스트를 가져와서 버튼 클릭 이벤트에서 사용하기 위해 저장합니다.
            final String text = items.get(position);
            // 각 항목의 버튼을 찾아옵니다.
            Button button = (Button) v.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭한 항목에 따라 이동할 화면의 클래스를 설정합니다.
                    if("계정관리".equals(items.get(position)))
                        move = acoount.class;
                    else if("알림설정".equals(items.get(position)))
                        move = alarm.class;
                    else if("버디관리".equals(items.get(position)))
                        move = friend.class;
                    else if("공지사항".equals(items.get(position)))
                        move = notification.class;
                    else if("문의하기".equals(items.get(position)))
                        move = question.class;
                    else if("정보".equals(items.get(position)))
                        move = imformation.class;

                    // Intent를 사용하여 새로운 화면으로 이동합니다.
                    Intent intent = new Intent(MainActivity.this, move);
                    startActivity(intent);
                }
            });

            return v;
        }
    }
}
