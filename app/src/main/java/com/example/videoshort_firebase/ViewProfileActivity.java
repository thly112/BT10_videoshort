package com.example.videoshort_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewProfileActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_PICK = 1001;
    private Uri videoUri;

    private Button btnUploadVideo;
    private FirebaseStorage storage;
    private DatabaseReference videosRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        btnUploadVideo = findViewById(R.id.btnUploadVideo);
        storage = FirebaseStorage.getInstance();
        videosRef = FirebaseDatabase.getInstance().getReference("videos");
        auth = FirebaseAuth.getInstance();

        btnUploadVideo.setOnClickListener(v -> {
            // Mở Intent để chọn video
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            startActivityForResult(intent, REQUEST_VIDEO_PICK);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
            uploadVideoToFirebase();
        }
    }

    private void uploadVideoToFirebase() {
        if (videoUri == null) return;

        // Lấy ID người dùng
        String userId = auth.getCurrentUser().getUid();
        // Tạo tên file video dựa trên timestamp
        String fileName = System.currentTimeMillis() + ".mp4";

        // Tham chiếu đến Firebase Storage
        StorageReference storageRef = storage.getReference().child("videos").child(userId).child(fileName);

        // Tải video lên Firebase Storage
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải video...");
        progressDialog.show();

        storageRef.putFile(videoUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String videoUrl = uri.toString();
                        String videoId = videosRef.push().getKey();

                        // Tạo đối tượng model video
                        VideoModel video = new VideoModel();
                        video.setUrl(videoUrl);
                        video.setTitle("Tiêu đề video");  // Có thể mở thêm dialog để người dùng nhập tiêu đề
                        video.setDesc("Mô tả video");  // Mô tả video có thể để trống hoặc mở dialog cho người dùng nhập
                        video.setUserEmail(auth.getCurrentUser().getEmail());
                        video.setUserId(userId);

                        // Lưu thông tin video vào Realtime Database
                        if (videoId != null) {
                            videosRef.child(videoId).setValue(video)
                                    .addOnCompleteListener(task -> {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(this, "Tải video thành công!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(this, "Lỗi khi lưu video: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Lỗi tải video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
