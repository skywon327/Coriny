package com.example.real_text;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_LENGTH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 레이아웃에서 EditText와 TextView를 찾아옵니다.
        EditText textbox = findViewById(R.id.textbox);
        final TextView charCount = findViewById(R.id.charCount);

        // 초기 상태에서 TextView에 "0/4"을 표시합니다.
        charCount.setText("0/" + MAX_LENGTH);

        // EditText에 텍스트 변경 감지자(TextWatcher)를 추가합니다.
        textbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // 이전 텍스트 변경 전에 호출됩니다.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 텍스트가 변경될 때 호출됩니다.

                // 현재 입력된 텍스트의 길이를 가져옵니다.
                int currentLength = charSequence.length();

                // 최대 길이를 초과한 경우, 초과된 부분을 삭제합니다.
                if (currentLength >= MAX_LENGTH) {
                    // 초과된 부분을 삭제합니다.
                    Editable editable = textbox.getText();
                    editable.delete(MAX_LENGTH, currentLength);
                    currentLength = charSequence.length();
                }

                // 길이를 TextView에 표시합니다.
                charCount.setText(currentLength + "/" + MAX_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // afterTextChanged 메서드는 더 이상 사용되지 않습니다.
                // 여기에 코드를 추가하려면 onTextChanged에서 처리하면 됩니다.
            }
        });
    }
}
