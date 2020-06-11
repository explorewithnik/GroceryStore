package com.example.niket.grocerystore.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.niket.grocerystore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class Profile extends AppCompatActivity {

    FancyButton editButton, updateProfile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceForUserProfile, databaseReferenceForUserName, databaseReferenceForUserImage;
    SharedPreferences sharedPreferences;

    boolean ifClicked = false;

    //user profile
    String userMobile;
    String uName, uMobile, uImage;
    CircleImageView imageViewProfileNav;
    EditText editName;
    TextView showMobile;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", null);

        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        editName = findViewById(R.id.name_edit_profilename);
        showMobile = findViewById(R.id.mobile_profile);
        imageViewProfileNav = findViewById(R.id.edit_profileImage);

        editName = findViewById(R.id.name_edit_profilename);
        editName.setSelection(editName.length());
        editName.setEnabled(false);

        editButton = findViewById(R.id.edit_button);
        updateProfile = findViewById(R.id.update_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating...");

        editName();
        showUserData();
        chooseImage();
        updateUserDetails();
    }

    private void chooseImage() {
        imageViewProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
    }

    private void showUserData() {

        databaseReferenceForUserProfile = firebaseDatabase.getReference("data").child("users");

        databaseReferenceForUserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean userFound = false;

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (userMobile.equals(data.child("mobile").getValue(String.class))) {

                        uName = data.child("name").getValue(String.class);
                        uMobile = data.child("mobile").getValue(String.class);
                        uImage = data.child("image_URL").getValue(String.class);
                        userFound = true;
                        break;
                    }
                }
                if (userFound) {

                    editName.setText(uName);
                    showMobile.setText(uMobile);
                    Glide.with(getApplicationContext()).load(uImage).into(imageViewProfileNav);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUserDetails() {

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filePath != null) {

                    progressDialog.show();
                    //getFile Name
                    String path = filePath.getPath();

                    File file = null;
                    if (path != null) file = new File(path);

                    Bitmap bitmap;
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

                        byte[] data = outputStream.toByteArray();
                        StorageReference ref=null;
                        if (file != null) ref = storageReference.child("Banner Images/" + file.getName());

                        if(ref!=null)
                        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String downloadUrl = uri.toString();

                                                databaseReferenceForUserName = firebaseDatabase.getReference("data").child("users").child(userMobile).child("name");
                                                databaseReferenceForUserName.setValue(editName.getText().toString());

                                                databaseReferenceForUserImage = firebaseDatabase.getReference("data").child("users").child(userMobile).child("image_URL");
                                                databaseReferenceForUserImage.setValue(Objects.requireNonNull(downloadUrl));

                                                progressDialog.dismiss();
                                                Toast.makeText(Profile.this, "Updated", Toast.LENGTH_SHORT).show();

                                                // imageViewProfileNav.setImageBitmap(null);
                                                editName.setEnabled(false);
                                                filePath = null;
                                                ifClicked = false;
                                            }
                                        });
                                    }
                                }

                            }
                        }
                        ).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Profile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploading " + (int) progress + "%");
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (ifClicked && editName.length() != 0) {
                    progressDialog.show();
                    databaseReferenceForUserName = firebaseDatabase.getReference("data").child("users").child(userMobile).child("name");
                    databaseReferenceForUserName.setValue(editName.getText().toString());

                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "Name Updated... ", Snackbar.LENGTH_SHORT);
                    //to change the background color of snackbar
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.greendark));
                    //to change the text size of snackbar
                    TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    sbTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    //to change the text alignment of text
                    sbTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    //to set the height of snackbar
                    sbView.setMinimumHeight(100);
                    snackbar.show();

                    editName.setEnabled(false);

                    ifClicked = false;

                    //Toast.makeText(Register.this, "Please set the Profile image", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                } else if (editName.length() == 0) {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "Name can't be empty ", Snackbar.LENGTH_SHORT);
                    //to change the background color of snackbar
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.greendark));
                    //to change the text size of snackbar
                    TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    sbTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    //to change the text alignment of text
                    sbTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    //to set the height of snackbar
                    sbView.setMinimumHeight(100);
                    snackbar.show();

                    ifClicked = false;

                } else if (filePath == null) {
                    //hide window soft key
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "No Changes to be Made...", Snackbar.LENGTH_SHORT);
                    //to change the background color of snackbar
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.greendark));
                    //to change the text size of snackbar
                    TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    sbTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    //to change the text alignment of text
                    sbTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    //to set the height of snackbar
                    sbView.setMinimumHeight(100);
                    snackbar.show();

                    //Toast.makeText(Register.this, "Please set the Profile image", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();

                    ifClicked = false;
                }
            }
        });
    }

    private void editName() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setEnabled(true);
                ifClicked = true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                imageViewProfileNav.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
