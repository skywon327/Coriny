package com.example.app_alarm;
// question.java

// 필요한 패키지 및 클래스 import 선언
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class question extends AppCompatActivity {
    // AlertDialog에 대한 참조 추가
    private AlertDialog imageSourceDialog;

    // 상수 정의: 카메라로부터 이미지 가져오기 및 갤러리에서 이미지 선택하기에 사용됨
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    // UI 요소 선언
    private ListView list_item;
    private ArrayAdapter<Item> adapter;
    private List<Item> itemList;
    boolean lastitemVisibleFlag = false;

    // 동적으로 생성된 아이템 이름을 위한 카운터
    private int itemCounter = 1;
    private static final int MAX_LENGTH = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

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

        // 상단 텍스트 및 이미지 설정
        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText("문의하기");
        ImageView image_name = (ImageView)findViewById(R.id.image_name);
        image_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(question.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // UI 요소 초기화
        list_item = findViewById(R.id.list_item);

        // 데이터 초기화
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<Item>(this, R.layout.list_item, R.id.textView, itemList) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                }

                // 리스트뷰 항목의 텍스트 설정
                TextView textView = convertView.findViewById(R.id.textView);
                textView.setText(getItem(position).getText());

                // 리스트뷰 항목의 이미지 설정
                ImageView imageView = convertView.findViewById(R.id.imageView);
                Bitmap imageBitmap = getItem(position).getImageBitmap();
                if (imageBitmap != null) {
                    imageView.setImageBitmap(imageBitmap);
                }

                // 빼기 버튼 클릭 시 해당 항목 제거
                Button btnRemove = convertView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(position);
                    }
                });

                return convertView;
            }
        };

        // 리스트뷰에 어댑터 설정
        list_item.setAdapter(adapter);

        // 이미지 추가 버튼 클릭 시 이벤트 처리
        ImageView fileAddButton = findViewById(R.id.file_add);
        fileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });
    }

    // 이미지 소스 다이얼로그 표시
    private void showImageSourceDialog() {
        // 다이얼로그에 사용될 사용자 정의 레이아웃을 인플레이트합니다.
        View dialogView = LayoutInflater.from(this).inflate(R.layout.question_file_choice, null);

        // AlertDialog 빌더를 만듭니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 빌더에 사용자 정의 레이아웃을 설정합니다.
        builder.setView(dialogView);

        // 사용자 정의 레이아웃 내의 ImageView에 대한 참조를 가져옵니다.
        ImageView cameraImageView = dialogView.findViewById(R.id.cameraImageView);
        ImageView galleryImageView = dialogView.findViewById(R.id.galleryImageView);

        // ImageView에 클릭 리스너를 설정합니다.
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카메라 아이콘 클릭 처리
                dispatchTakePictureIntent();
                imageSourceDialog.dismiss(); // 다이얼로그 닫기
            }
        });

        galleryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리 아이콘 클릭 처리
                openGallery();
                imageSourceDialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 다이얼로그를 생성하고 참조를 유지합니다.
        imageSourceDialog = builder.create();

        // 다이얼로그를 표시합니다.
        imageSourceDialog.show();
    }

    // 카메라 앱을 호출하여 이미지 캡처
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // 갤러리에서 이미지 선택
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryIntent, "사진 선택"), REQUEST_IMAGE_PICK);
    }

    // 이미지 캡처 또는 갤러리 선택 후 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                // 카메라로부터 이미지를 가져온 경우
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                addItem("첨부파일 " + itemCounter, imageBitmap);
                itemCounter++;
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                if (data.getClipData() != null) {
                    // 다중 이미지 선택
                    ClipData clipData = data.getClipData();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipData.getItemAt(i).getUri());
                            addItem("첨부파일 " + itemCounter, imageBitmap);
                            itemCounter++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (data.getData() != null) {
                    // 단일 이미지 선택
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        addItem("첨부파일 " + itemCounter, imageBitmap);
                        itemCounter++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 리스트에 새로운 항목 추가
    private void addItem(String text, Bitmap imageBitmap) {
        itemList.add(new Item(text, imageBitmap));
        adapter.notifyDataSetChanged();
    }

    // 리스트에서 항목 제거
    private void removeItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    // 리스트뷰에 표시될 아이템 클래스
    public class Item {
        private String text;
        private Bitmap imageBitmap;

        // 아이템 생성자
        public Item(String text, Bitmap imageBitmap) {
            this.text = text;
            this.imageBitmap = imageBitmap;
        }

        // 텍스트 반환 메서드
        public String getText() {
            return text;
        }

        // 이미지 반환 메서드
        public Bitmap getImageBitmap() {
            return imageBitmap;
        }
    }
}
