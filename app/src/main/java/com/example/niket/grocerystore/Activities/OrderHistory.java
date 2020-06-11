package com.example.niket.grocerystore.Activities;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niket.grocerystore.PaymentGateway.OrderPojo;
import com.example.niket.grocerystore.PaymentGateway.SortOrdersByDate;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class OrderHistory extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userMobile;

    // vertical recyclerview
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyOrderAdapter myOrderAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setTitle("My Orders");
        }
        //vertical recyclerview initialisation
        recyclerView = findViewById(R.id.recorder);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<OrderPojo> arrayList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    OrderPojo orderPojo = data.getValue(OrderPojo.class);
                    arrayList.add(orderPojo);
                    Collections.sort(arrayList, new SortOrdersByDate());
                }
                myOrderAdapter = new MyOrderAdapter(OrderHistory.this, arrayList);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(myOrderAdapter);
                myOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
