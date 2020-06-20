package com.example.niket.grocerystore.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.niket.grocerystore.Commons;
import com.example.niket.grocerystore.ItemsPOJO.Categories_POJO;
import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niket.grocerystore.HelperClass.SelectImageHelper;
import com.example.niket.grocerystore.POJO_Class.MyPojo;
import com.example.niket.grocerystore.R;
import com.example.niket.grocerystore.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    ProgressDialog dialog;
    SelectImageHelper helper;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReferenceForBanner, databaseReferenceForCartStatus;

    StorageReference storageReference;

    Random random;

    MyPojo myPojo;

    String emailPattern, mobilePattern, passwordPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailPattern = "^([_A-Za-z0-9-.]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

        mobilePattern = "^([0-9]{10})$";
        passwordPattern = "^([a-zA-Z0-9@#$%^&+=]{6,15})$";

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        //get FirebaseDatabase Instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        //get selectHelper class instance
        helper = new SelectImageHelper(this, activityRegisterBinding.circular);

        //get StorageReference Instance
        storageReference = FirebaseStorage.getInstance().getReference();

        //dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait.....");
        dialog.setMessage("registering data to Database");
        dialog.setCanceledOnTouchOutside(false);

        random = new Random();
        final String s1 = String.valueOf(random.nextInt(263443));

        activityRegisterBinding.circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //using SelectImageHelper class to browse image
                helper.selectImageOption();
            }
        });


        //making edittext cli
        activityRegisterBinding.editTextDOB.setFocusable(false);
        activityRegisterBinding.editTextDOB.setClickable(true);
        activityRegisterBinding.editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_DeviceDefault_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String dob = day + "/" + (month + 1) + "/" + year;
                            activityRegisterBinding.editTextDOB.setText(dob);
                        }
                    }, year, month, day);
                } else {
                    datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_Material_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String dob = day + "/" + (month + 1) + "/" + year;
                            activityRegisterBinding.editTextDOB.setText(dob);
                        }
                    }, year, month, day);
                }
                datePickerDialog.show();
            }

        });


        //register new user
        activityRegisterBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri file = helper.getURI_FOR_SELECTED_IMAGE();

                if (!activityRegisterBinding.editTextEmail.getText().toString().matches(emailPattern)) {
                    activityRegisterBinding.editTextEmail.setError("invalid email");
                }
                if (!activityRegisterBinding.editTextMobile.getText().toString().matches(mobilePattern)) {
                    activityRegisterBinding.editTextMobile.setError("mobile must be of 10 digits");
                }

                if (activityRegisterBinding.editTextPassword.getText() != null && !activityRegisterBinding.editTextPassword.getText().toString().matches(passwordPattern)) {
                    activityRegisterBinding.editTextPassword.setError("length range between 6-15");
                }

                if (activityRegisterBinding.cpassword.getText() != null && !activityRegisterBinding.cpassword.getText().toString().matches(activityRegisterBinding.editTextPassword.getText().toString())) {
                    activityRegisterBinding.cpassword.setError("password didn't match");
                }


                if (file == null
                        && TextUtils.isEmpty(activityRegisterBinding.editTextName.getText().toString())
                        && TextUtils.isEmpty(activityRegisterBinding.editTextEmail.getText().toString())
                        && TextUtils.isEmpty(activityRegisterBinding.editTextPassword.getText().toString())
                        && TextUtils.isEmpty(activityRegisterBinding.editTextMobile.getText().toString())
                        && TextUtils.isEmpty(activityRegisterBinding.editTextDOB.getText().toString())
                        && TextUtils.isEmpty(activityRegisterBinding.cpassword.getText().toString())
                ) {

                    activityRegisterBinding.editTextName.setError(null);
                    activityRegisterBinding.editTextEmail.setError(null);
                    activityRegisterBinding.editTextMobile.setError(null);
                    activityRegisterBinding.editTextPassword.setError(null);
                    activityRegisterBinding.editTextDOB.setError(null);
                    activityRegisterBinding.cpassword.setError(null);


                    Snackbar snackbar = Snackbar.make(view, "All the fields are mandatory", Snackbar.LENGTH_SHORT);

                    //to change the background color of snackbar
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.greendark));

                    //to change the text size of snackbar
                    TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    sbTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

                    //to change the text alignment of text
                    sbTextView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

                    //to set the height of snackbar
                    sbView.setMinimumHeight(150);

                    snackbar.show();

                } else if (file == null) {

                    activityRegisterBinding.editTextName.setError(null);
                    activityRegisterBinding.editTextEmail.setError(null);
                    activityRegisterBinding.editTextMobile.setError(null);
                    activityRegisterBinding.editTextPassword.setError(null);
                    activityRegisterBinding.editTextDOB.setError(null);
                    activityRegisterBinding.cpassword.setError(null);

                    Snackbar snackbar = Snackbar.make(view, "Please set the Profile image", Snackbar.LENGTH_SHORT);

                    //to change the background color of snackbar
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.greendark));

                    //to change the text size of snackbar
                    TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    sbTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

                    //to change the text alignment of text
                    sbTextView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

                    //to set the height of snackbar
                    sbView.setMinimumHeight(150);

                    snackbar.show();

                    //Toast.makeText(Register.this, "Please set the Profile image", Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                } else {

                    if (activityRegisterBinding.editTextEmail.getText().toString().matches(emailPattern) && activityRegisterBinding.editTextMobile.getText().toString().matches(mobilePattern)
                            && activityRegisterBinding.editTextPassword.getText().toString().matches(passwordPattern) && activityRegisterBinding.cpassword.getText().toString().matches(activityRegisterBinding.editTextPassword.getText().toString())) {
                        dialog.show();

                        //get the DatabaseReference
                        databaseReference = firebaseDatabase.getReference("data").child("users");
                        databaseReferenceForBanner = firebaseDatabase.getReference("data").child("Banner Images");
                        databaseReferenceForCartStatus = firebaseDatabase.getReference("data").child("users").child(activityRegisterBinding.editTextMobile.getText().toString().trim()).child("cartStatus").child("totalCount");


                        StorageReference storageReference2 = storageReference.child(s1);


                        storageReference2.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String downloadUrl = uri.toString();

                                                myPojo = new MyPojo();

                                                myPojo.setID(databaseReference.push().getKey());
                                                myPojo.setImage_URL(Objects.requireNonNull(downloadUrl));
                                                myPojo.setName(activityRegisterBinding.editTextName.getText().toString());
                                                myPojo.setDOB(activityRegisterBinding.editTextDOB.getText().toString());
                                                myPojo.setMobile(activityRegisterBinding.editTextMobile.getText().toString());
                                                myPojo.setEmail(activityRegisterBinding.editTextEmail.getText().toString());
                                                myPojo.setPassword(activityRegisterBinding.editTextPassword.getText().toString());
                                                myPojo.setName(activityRegisterBinding.editTextName.getText().toString());

                                                databaseReference.child(activityRegisterBinding.editTextMobile.getText().toString()).setValue(myPojo);
                                                databaseReferenceForCartStatus.setValue(0);

                                                insertHomePageData();
                                                insertCategoryWiseData();
                                                insertSearchData();

                                                dialog.cancel();

                                                Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Register.this, Login.class);
                                                //intent.putExtra("Profile_image", myPojo.getImage_URL());
                                                //intent.putExtra("username",activityRegisterBinding.editTextName.getText().toString());
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Database Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        helper.handleResult(requestCode, resultCode, result);  // call this helper class method
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        helper.handleGrantedPermisson(requestCode, grantResults);   // call this helper class method
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void insertHomePageData() {
        ArrayList<Category_Items_POJO> list = new Commons().getAtaAndOtherFlours();
        for (int j = 0; j < list.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("items").child(list.get(j).getCategoryName()).child(list.get(j).getItemName()).setValue(list.get(j));
        }

        ArrayList<Category_Items_POJO> dryFruitList = new Commons().getDryFruitsDetails();
        for (int j = 0; j < dryFruitList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("items").child(dryFruitList.get(j).getCategoryName()).child(dryFruitList.get(j).getItemName()).setValue(dryFruitList.get(j));
        }

        ArrayList<Category_Items_POJO> pulsesList = new Commons().getPulses();
        for (int j = 0; j < pulsesList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("items").child(pulsesList.get(j).getCategoryName()).child(pulsesList.get(j).getItemName()).setValue(pulsesList.get(j));
        }

        ArrayList<Category_Items_POJO> riceAndGrainsList = new Commons().getRiceAndGrains();
        for (int j = 0; j < riceAndGrainsList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("items").child(riceAndGrainsList.get(j).getCategoryName()).child(riceAndGrainsList.get(j).getItemName()).setValue(riceAndGrainsList.get(j));
        }

        ArrayList<Category_Items_POJO> spicesList = new Commons().getSpices();
        for (int j = 0; j < spicesList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("items").child(spicesList.get(j).getCategoryName()).child(spicesList.get(j).getItemName()).setValue(spicesList.get(j));
        }
    }

    private void insertSearchData() {
        ArrayList<Category_Items_POJO> list = new Commons().getSearchData();
        for (int j = 0; j < list.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("search").child("products").child(list.get(j).getItemName()).setValue(list.get(j));
        }
    }

    private void insertCategoryWiseData() {
        ArrayList<Category_Items_POJO> list = new Commons().getAtaAndOtherFlours();
        for (int j = 0; j < list.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("products").child(list.get(j).getCategoryName()).child(list.get(j).getItemCatName()).child(list.get(j).getItemName()).setValue(list.get(j));
        }

        ArrayList<Category_Items_POJO> dryFruitList = new Commons().getDryFruitsDetails();
        for (int j = 0; j < dryFruitList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("products").child(dryFruitList.get(j).getCategoryName()).child(dryFruitList.get(j).getItemCatName()).child(dryFruitList.get(j).getItemName()).setValue(dryFruitList.get(j));
        }

        ArrayList<Category_Items_POJO> pulsesList = new Commons().getPulses();
        for (int j = 0; j < pulsesList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("products").child(pulsesList.get(j).getCategoryName()).child(pulsesList.get(j).getItemCatName()).child(pulsesList.get(j).getItemName()).setValue(pulsesList.get(j));
        }

        ArrayList<Category_Items_POJO> riceAndGrainsList = new Commons().getRiceAndGrains();
        for (int j = 0; j < riceAndGrainsList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("products").child(riceAndGrainsList.get(j).getCategoryName()).child(riceAndGrainsList.get(j).getItemCatName()).child(riceAndGrainsList.get(j).getItemName()).setValue(riceAndGrainsList.get(j));
        }

        ArrayList<Category_Items_POJO> spicesList = new Commons().getSpices();
        for (int j = 0; j < spicesList.size(); j++) {
            databaseReference.child(myPojo.getMobile()).child("products").child(spicesList.get(j).getCategoryName()).child(spicesList.get(j).getItemCatName()).child(spicesList.get(j).getItemName()).setValue(spicesList.get(j));
        }
    }
}

