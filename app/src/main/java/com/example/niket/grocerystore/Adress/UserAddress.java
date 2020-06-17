package com.example.niket.grocerystore.Adress;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAddress extends AppCompatActivity {
    ActionBar actionBar;
    LinearLayout linearLayout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userMobile, totalAmount;
    SharedPreferences sharedPreferences;
    AddressClass addressPojo;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AddressAdapter addressAdapter;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar = getSupportActionBar();
            actionBar.setTitle("Delivery Address");
        }

        Intent intent=getIntent();
        totalAmount=intent.getStringExtra("totalPrice");

        recyclerView = findViewById(R.id.address_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        linearLayout = findViewById(R.id.addAddress);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAddress.this, AddAddress.class);
                intent.putExtra("title", "Add Address");
                intent.putExtra("title2","userAdd");
                intent.putExtra("totalPrice", String.valueOf(totalAmount));
                startActivity(intent);

            }
        });

        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Address");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<AddressClass> arrayListAddressPojos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    addressPojo = new AddressClass();
                    addressPojo.setName(data.child("name").getValue(String.class));
                    addressPojo.setUserCity(data.child("userCity").getValue(String.class));
                    addressPojo.setUserState(data.child("userState").getValue(String.class));
                    addressPojo.setUserPinCode(data.child("userPinCode").getValue(String.class));
                    addressPojo.setUserMobile(data.child("userMobile").getValue(String.class));
                    addressPojo.setUserLandmark(data.child("userLandmark").getValue(String.class));
                    addressPojo.setUserFlatNumber(data.child("userFlatNumber").getValue(String.class));
                    addressPojo.setUserEmail(data.child("userEmail").getValue(String.class));
                    addressPojo.setAddressDelteId(data.getKey());

                    arrayListAddressPojos.add(addressPojo);

                }
                Intent intent=getIntent();
                totalAmount=intent.getStringExtra("totalPrice");
                addressAdapter = new AddressAdapter(arrayListAddressPojos, userMobile,totalAmount,UserAddress.this);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
