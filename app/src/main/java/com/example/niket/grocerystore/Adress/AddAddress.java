package com.example.niket.grocerystore.Adress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niket.grocerystore.FancyButton.FancyButton;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAddress extends AppCompatActivity {

    ActionBar actionBar;

    ProgressDialog dialog;
    Intent intent;
    String title, name, mobile, state, city, pin, email, landmark, flatno, deleteKey;
    FancyButton fancyButton;
    SharedPreferences sharedPreferences;
    String emailPattern, namePattern, mobilePattern, PINCode, userMobile;
    EditText edName, edEmail, edAddress, edLandmark, edPIN, edState, edContact, edCity;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar = getSupportActionBar();
        }

        Intent intent = getIntent();
        totalAmount = intent.getStringExtra("totalPrice");

        emailPattern = "^([_A-Za-z0-9-.]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
        namePattern = "^([a-zA-Z_ ]{2,15})$";
        mobilePattern = "^([0-9]{10,10})$";
        PINCode = "^([0-5]{6,6})$";

        sharedPreferences = getSharedPreferences("save", 0);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // /dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait.....");
        dialog.setMessage("Saving Address");
        dialog.setCanceledOnTouchOutside(false);


        fancyButton = findViewById(R.id.saveAddressbtn);

        edName = findViewById(R.id.nameAddress);
        edEmail = findViewById(R.id.emailAddress);
        edAddress = findViewById(R.id.addressAddress);
        edLandmark = findViewById(R.id.landmarkAddress);
        edPIN = findViewById(R.id.pincodeAddress);
        edState = findViewById(R.id.stateAddress);
        edContact = findViewById(R.id.contactAddress);
        edCity = findViewById(R.id.cityAddress);

        intent = getIntent();
        name = intent.getStringExtra("name2");
        mobile = intent.getStringExtra("mobile2");
        state = intent.getStringExtra("state2");
        city = intent.getStringExtra("city2");
        pin = intent.getStringExtra("pin2");
        email = intent.getStringExtra("email2");
        landmark = intent.getStringExtra("landmark2");
        flatno = intent.getStringExtra("flatno2");
        deleteKey = intent.getStringExtra("deleteKey");

        Log.e("data", "name " + name + " mobile " + mobile + " state " + state + " city " + city + " pin " + pin + " email " + email + " landmark " + landmark + " flatno " + flatno + " deleteKey " + deleteKey);

        title = intent.getStringExtra("title");

        if (title != null) {
            if (title.equals("Update Address")) {
                actionBar.setTitle(title);
                updateAddress(name, mobile, state, city, pin, email, landmark, flatno, deleteKey);
            } else {
                actionBar.setTitle(title);
                saveAddress();
            }
        }
    }

    private void saveAddress() {
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
                if (TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                    edAddress.setError("address can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edLandmark.getText().toString().trim())) {
                    edLandmark.setError("landmark can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edState.getText().toString().trim())) {
                    edState.setError("state can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edCity.getText().toString().trim())) {
                    edCity.setError("city can't be empty");
                    return;
                }
//                if (!edName.getText().toString().trim().matches(namePattern)) {
//                    edName.setError("name must be minumum 2 and maximum 15 letters only");
//                    return;
//                }
                if (TextUtils.isEmpty(edName.getText().toString().trim())) {
                    edName.setError("name can't be empty");
                    return;
                }

                if (!edEmail.getText().toString().trim().matches(emailPattern)) {
                    edEmail.setError("invalid email");
                    return;
                }
                if (TextUtils.isEmpty(edEmail.getText().toString().trim())) {
                    edEmail.setError("email can't be empty");
                    return;
                }
                if (!edContact.getText().toString().trim().matches(mobilePattern)) {
                    edContact.setError("mobile must be of 10 digits");
                    return;
                }
                if (TextUtils.isEmpty(edContact.getText().toString().trim())) {
                    edContact.setError("mobile can't be empty");
                    return;
                }

                if (TextUtils.isEmpty(edPIN.getText().toString().trim())) {
                    edPIN.setError("PIN can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edName.getText().toString().trim())
                        && TextUtils.isEmpty(edCity.getText().toString().trim())
                        && TextUtils.isEmpty(edEmail.getText().toString().trim())
                        && TextUtils.isEmpty(edContact.getText().toString().trim())
                        && TextUtils.isEmpty(edAddress.getText().toString().trim())
                        && TextUtils.isEmpty(edLandmark.getText().toString().trim())
                        && TextUtils.isEmpty(edState.getText().toString().trim())
                        && TextUtils.isEmpty(edPIN.getText().toString().trim())
                ) {

                    edName.setError(null);
                    edCity.setError(null);
                    edEmail.setError(null);
                    edContact.setError(null);
                    edAddress.setError(null);
                    edLandmark.setError(null);
                    edState.setError(null);
                    edPIN.setError(null);
                    Toast.makeText(AddAddress.this, "All the fields are mandatory   ", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(edName.getText().toString().trim())
                        && !TextUtils.isEmpty(edCity.getText().toString().trim())
                        && !TextUtils.isEmpty(edEmail.getText().toString().trim())
                        && !TextUtils.isEmpty(edContact.getText().toString().trim())
                        && !TextUtils.isEmpty(edAddress.getText().toString().trim())
                        && !TextUtils.isEmpty(edLandmark.getText().toString().trim())
                        && !TextUtils.isEmpty(edState.getText().toString().trim())
                        && !TextUtils.isEmpty(edPIN.getText().toString().trim())
                ) {
                    if (/*edName.getText().toString().trim().matches(namePattern) &&*/ edContact.getText().toString().trim().matches(mobilePattern)
                            && edEmail.getText().toString().trim().matches(emailPattern)) {

                        dialog.show();
                        edName.setError(null);
                        edEmail.setError(null);
                        edContact.setError(null);
                        edAddress.setError(null);
                        edLandmark.setError(null);
                        edState.setError(null);
                        edPIN.setError(null);

                        AddressClass addressClass = new AddressClass(edName.getText().toString().trim(), edLandmark.getText().toString().trim(), edContact.getText().toString().trim(), edEmail.getText().toString().trim(), edPIN.getText().toString().trim(), edState.getText().toString().trim(), edCity.getText().toString().trim(), edAddress.getText().toString().trim().trim(),deleteKey);

                        userMobile = sharedPreferences.getString("userMobile", "");
                        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Address");
                        Log.e("navaddress", "landmark " + addressClass.getUserLandmark() + " pin " + addressClass.getUserPinCode() + " city " + addressClass.getUserCity());

                        databaseReference.push().setValue(addressClass);

                        dialog.cancel();
                        String title = intent.getStringExtra("title2");

                        if (title != null && title.equals("NavigationAdd")) {
                            Intent intent2 = new Intent(AddAddress.this, NavigationAddress.class);
                            startActivity(intent2);
                        } else {
                            Intent intent = new Intent(AddAddress.this, UserAddress.class);
                            intent.putExtra("totalPrice", String.valueOf(totalAmount));
                            startActivity(intent);
                        }

                    }

                }


            }
        });
    }


    private void updateAddress(String name, String mobile, String state, String city, String pin, String email, String landmark, String flatno, final String deleteKey) {
        edName.setText(name);
        edContact.setText(mobile);
        edState.setText(state);
        edCity.setText(city);
        edPIN.setText(pin);
        edEmail.setText(email);
        edLandmark.setText(landmark);
        edAddress.setText(flatno);

        fancyButton.setText("Update Address");

        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                    edAddress.setError("address can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edLandmark.getText().toString().trim())) {
                    edLandmark.setError("landmark can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edState.getText().toString().trim())) {
                    edState.setError("state can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edCity.getText().toString().trim())) {
                    edCity.setError("city can't be empty");
                    return;
                }
//                if (!edName.getText().toString().trim().matches(namePattern)) {
//                    edName.setError("name must be minumum 2 and maximum 15 letters only");
//                    return;
//                }
                if (TextUtils.isEmpty(edName.getText().toString().trim())) {
                    edName.setError("name can't be empty");
                    return;
                }

                if (!edEmail.getText().toString().trim().matches(emailPattern)) {
                    edEmail.setError("invalid email");
                    return;
                }
                if (TextUtils.isEmpty(edEmail.getText().toString().trim())) {
                    edEmail.setError("email can't be empty");
                    return;
                }
                if (!edContact.getText().toString().trim().matches(mobilePattern)) {
                    edContact.setError("mobile must be of 10 digits");
                    return;
                }
                if (TextUtils.isEmpty(edContact.getText().toString().trim())) {
                    edContact.setError("mobile can't be empty");
                    return;
                }

                if (TextUtils.isEmpty(edPIN.getText().toString().trim())) {
                    edPIN.setError("PIN can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(edName.getText().toString().trim())
                        && TextUtils.isEmpty(edCity.getText().toString().trim())
                        && TextUtils.isEmpty(edEmail.getText().toString().trim())
                        && TextUtils.isEmpty(edContact.getText().toString().trim())
                        && TextUtils.isEmpty(edAddress.getText().toString().trim())
                        && TextUtils.isEmpty(edLandmark.getText().toString().trim())
                        && TextUtils.isEmpty(edState.getText().toString().trim())
                        && TextUtils.isEmpty(edPIN.getText().toString().trim())
                ) {

                    edName.setError(null);
                    edEmail.setError(null);
                    edContact.setError(null);
                    edAddress.setError(null);
                    edLandmark.setError(null);
                    edState.setError(null);
                    edPIN.setError(null);
                    Toast.makeText(AddAddress.this, "All the fields are mandatory   ", Toast.LENGTH_SHORT).show();


                } else if (!TextUtils.isEmpty(edName.getText().toString().trim())
                        && !TextUtils.isEmpty(edCity.getText().toString().trim())
                        && !TextUtils.isEmpty(edEmail.getText().toString().trim())
                        && !TextUtils.isEmpty(edContact.getText().toString().trim())
                        && !TextUtils.isEmpty(edAddress.getText().toString().trim())
                        && !TextUtils.isEmpty(edLandmark.getText().toString().trim())
                        && !TextUtils.isEmpty(edState.getText().toString().trim())
                        && !TextUtils.isEmpty(edPIN.getText().toString().trim())
                ) {
                    if (/*edName.getText().toString().trim().matches(namePattern) &&*/ edContact.getText().toString().trim().matches(mobilePattern)
                            && edEmail.getText().toString().trim().matches(emailPattern)) {
                        intent = getIntent();
                        dialog.show();
                        edName.setError(null);
                        edEmail.setError(null);
                        edContact.setError(null);
                        edAddress.setError(null);
                        edLandmark.setError(null);
                        edState.setError(null);
                        edPIN.setError(null);

                        AddressClass addressClass = new AddressClass(edName.getText().toString().trim(), edLandmark.getText().toString().trim(), edContact.getText().toString().trim(), edEmail.getText().toString().trim(), edPIN.getText().toString().trim(), edState.getText().toString().trim(), edCity.getText().toString().trim(), edAddress.getText().toString().trim().trim(),deleteKey);

                        userMobile = sharedPreferences.getString("userMobile", "");
                        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Address");
                        databaseReference.child(deleteKey).setValue(addressClass);

                        dialog.cancel();
                        String title = intent.getStringExtra("title2");
                        if (title != null && title.equals("NavigationAdd")) {
                            Intent intent2 = new Intent(AddAddress.this, NavigationAddress.class);
                            startActivity(intent2);
                        } else {
                            Intent intent = new Intent(AddAddress.this, UserAddress.class);
                            intent.putExtra("totalPrice", String.valueOf(totalAmount));
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
