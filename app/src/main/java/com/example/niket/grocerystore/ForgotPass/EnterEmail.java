package com.example.niket.grocerystore.ForgotPass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niket.grocerystore.Activities.Login;
import com.example.niket.grocerystore.POJO_Class.MyPojo;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;

import mehdi.sakout.fancybuttons.FancyButton;

public class EnterEmail extends AppCompatActivity {

    EditText email, vCode, updatepass, mobile;
    FancyButton submitButton;
    static int count = 0;
    int randomNumber;
    String emailPattern, passwordPattern, mobilePattern;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog dialog;
    TextInputLayout textInputEditTextEmail, textInputEditUpdatePass, textInputEditverifyCode, textInputEditmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);
        emailPattern = "^([_A-Za-z0-9-.]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
        passwordPattern = "^([a-zA-Z0-9@#$%^&+=]{6,15})$";
        mobilePattern = "^([0-9]{10,10})$";
        email = findViewById(R.id.email_f);
        vCode = findViewById(R.id.randomCode);
        submitButton = findViewById(R.id.submit);
        textInputEditTextEmail = findViewById(R.id.textinputemail_f);
        textInputEditUpdatePass = findViewById(R.id.textInput_updatepass);
        textInputEditverifyCode = findViewById(R.id.textInput_randomCode);
        textInputEditmobile = findViewById(R.id.textInput_mobile);
        updatepass = findViewById(R.id.update_pass);
        mobile = findViewById(R.id.mobile);


        textInputEditverifyCode.setVisibility(View.GONE);
        textInputEditUpdatePass.setVisibility(View.GONE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("users");

        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait....");
        dialog.setCanceledOnTouchOutside(false);
        randomNumber = getRandomNumber();
        onSubmitClick();
    }


    private int getRandomNumber() {
        Random random = new Random();
        int a = random.nextInt(5000);
        Log.d("123", "getRandomNumber: " + a);
        return a;
    }

    private void onSubmitClick() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;

                if (count == 1) {

                    if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(mobile.getText().toString())) {
                        email.setError("field cannot be empty");
                        mobile.setError("field cannot be empty");
                        count = 0;
                    } else if (TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(mobile.getText().toString())) {
                        email.setError("field cannot be empty");
                        count = 0;
                    } else if (!email.getText().toString().matches(emailPattern)) {
                        email.setError("invalid email");
                        count = 0;
                    } else if (!isConnectingToInternet(EnterEmail.this)) {
                        Toast.makeText(EnterEmail.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        count = 0;
                    } else if (TextUtils.isEmpty(mobile.getText().toString())) {
                        mobile.setError("field cannot be empty");
                        count = 0;
                    } else if (!mobile.getText().toString().matches(mobilePattern)) {
                        mobile.setError("mobile must be of 10 digits");
                        count = 0;
                    } else if (TextUtils.isEmpty(email.getText().toString())) {
                        email.setError("field cannot be empty");
                        count = 0;
                    } else {
                        dialog.show();
//                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                //checking if email exist or not from firebase
//                                if (checkIfEmailExist(email.getText().toString(), dataSnapshot)) {
//                                    dialog.cancel();
//                                    //used library for sendind code to gmail
//                                    BackgroundMail.newBuilder(EnterEmail.this)
//                                            .withUsername("explorewithnick@gmail.com")
//                                            .withPassword("Niket@9193")
//                                            .withMailto(email.getText().toString())
//                                            .withType(BackgroundMail.TYPE_PLAIN)
//                                            .withSubject("Verification code")
//                                            .withBody(String.valueOf(randomNumber))
//                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    textInputEditmobile.setEnabled(false);
//                                                    textInputEditTextEmail.setEnabled(false);
//                                                    textInputEditverifyCode.setVisibility(View.VISIBLE);
//                                                    submitButton.setText("Verify");
//                                                    //do some magic
//                                                    // Toast.makeText(EnterEmail.this, "verification code sent to your mail", Toast.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                                                @Override
//                                                public void onFail() {
//                                                    count = 0;
//                                                    //Toast.makeText(EnterEmail.this, "Network error ", Toast.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .send();
//                                } else {
//                                    dialog.cancel();
//                                    email.setError("email not yet registered");
//                                    count = 0;
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                count = 0;
//                            }
//                        });

                    }


                } else if (count == 2) {

                    if (TextUtils.isEmpty(vCode.getText().toString())) {
                        vCode.setError("field cannot be empty");
                        count = 1;
                    } else {
                        if (randomNumber == Integer.valueOf(vCode.getText().toString())) {
                            textInputEditmobile.setEnabled(false);
                            textInputEditTextEmail.setEnabled(false);
                            textInputEditverifyCode.setEnabled(false);
                            textInputEditUpdatePass.setVisibility(View.VISIBLE);
                            submitButton.setText("update password");
                            Toast.makeText(EnterEmail.this, "Verification done", Toast.LENGTH_SHORT).show();
                           /* Intent intent = new Intent(EnterEmail.this, UpdatePass.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            Toast.makeText(EnterEmail.this, "code doesn't match", Toast.LENGTH_SHORT).show();
                            count = 1;
                        }
                    }
                } else if (count == 3) {
                    if (TextUtils.isEmpty(updatepass.getText().toString())) {
                        updatepass.setError("field cannot be empty");
                        count = 2;
                    } else if ((updatepass.length() < 5 || updatepass.length() > 15)) {
                        updatepass.setError("min 5-max 15");
                        count = 2;
                    } else if (!updatepass.getText().toString().matches(passwordPattern)) {
                        updatepass.setError(" only allowed  a-zA-Z0-9@#$%^&+=  ");
                        count = 2;
                    } else {
                        dialog.show();
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (checkIfMobileandEmailExist(mobile.getText().toString(), email.getText().toString(), dataSnapshot)) {

                                    dialog.cancel();
                                    databaseReference.child(mobile.getText().toString()).child("password").setValue(updatepass.getText().toString());
                                    Toast.makeText(EnterEmail.this, "password recovered successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(EnterEmail.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    dialog.cancel();
                                    Toast.makeText(EnterEmail.this, "mobile doesn't belongs to you", Toast.LENGTH_SHORT).show();
                                    count = 2;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                count = 2;
                            }
                        });

                    }
                }

            }
        });


    }

    public boolean checkIfMobileandEmailExist(String mobile, String email, DataSnapshot dataSnapshot) {

        MyPojo myPojo = new MyPojo();

        for (DataSnapshot data : dataSnapshot.getChildren()) {

            myPojo.setMobile(Objects.requireNonNull(data.getValue(MyPojo.class)).getMobile());
            myPojo.setEmail(Objects.requireNonNull(data.getValue(MyPojo.class)).getEmail());

            if (myPojo.getMobile().equals(mobile) && myPojo.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }


    private boolean checkIfEmailExist(String email, DataSnapshot dataSnapshot) {
        MyPojo myPojo = new MyPojo();
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            myPojo.setEmail(Objects.requireNonNull(data.getValue(MyPojo.class)).getEmail());

            if (myPojo.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info)
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        return false;
    }

}
