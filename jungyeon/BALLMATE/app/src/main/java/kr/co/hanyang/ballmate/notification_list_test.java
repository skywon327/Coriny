package kr.co.hanyang.ballmate;

public class notification_list_test {


//    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
//        private List<NotificationItem> notificationList; // 알림 내역 데이터 리스트
//
//        // 생성자에서 알림 내역 데이터 리스트를 받아옴
//        public NotificationAdapter(List<NotificationItem> notificationList) {
//            this.notificationList = notificationList;
//        }
//
//        // ViewHolder 클래스 정의 (아이템 레이아웃의 뷰들을 참조)
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            public ImageView notificationImage;
//            public TextView notificationText;
//            public TextView notificationTimestamp;
//            public ImageView deleteButton;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                notificationImage = itemView.findViewById(R.id.notificationImage);
//                notificationText = itemView.findViewById(R.id.notificationText);
//                notificationTimestamp = itemView.findViewById(R.id.notificationTimestamp);
//                deleteButton = itemView.findViewById(R.id.deleteButton);
//            }
//        }
//
//        // onCreateViewHolder: 새로운 뷰 홀더를 만들 때 호출됨
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.notification_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        // onBindViewHolder: 뷰 홀더의 뷰에 데이터를 바인딩함
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            NotificationItem item = notificationList.get(position);
//
//            // 데이터를 뷰에 바인딩
//            holder.notificationText.setText(item.getText());
//            holder.notificationTimestamp.setText(item.getTimestamp());
//
//            // 원형 이미지를 설정
//            // 예: Glide 라이브러리를 사용한 이미지 로딩
//            Glide.with(holder.notificationImage.getContext())
//                    .load(item.getImageUrl())
//                    .circleCrop() // 이미지를 원형으로 잘라서 표시
//                    .into(holder.notificationImage);
//
//            // 삭제 버튼 클릭 이벤트 처리
//            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 삭제 버튼 클릭 시 수행할 작업 구현
//                    // 예: 해당 알림 삭제
//                    removeItem(position);
//                }
//            });
//        }
//
//        // getItemCount: 데이터 아이템의 수 반환
//        @Override
//        public int getItemCount() {
//            return notificationList.size();
//        }
//
//        // 데이터 아이템 삭제 메서드
//        private void removeItem(int position) {
//            notificationList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

}
