package com.example.niket.grocerystore.Activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.niket.grocerystore.Adapter.RecyclerAdapter;
import com.example.niket.grocerystore.CartActivity.AddorRemoveCallbacks;
import com.example.niket.grocerystore.CartActivity.Cart;
import com.example.niket.grocerystore.CartActivity.LayoutToDrawableConverter;
import com.example.niket.grocerystore.POJO_Class.MorePojo;
import com.example.niket.grocerystore.R;
import com.example.niket.grocerystore.Search.SearchViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class More extends AppCompatActivity implements AddorRemoveCallbacks {

    String categoryName, namePattern;
    MenuItem menuItem;

    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReferenceMore;

    String firebaseCategoryName, catIemName;
    String userMobile;
    SharedPreferences sharedPreferences;

    // vertical recyclerview
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    RecyclerAdapter customAdapter;
    static int cart_count = 0;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            actionBar = getSupportActionBar();
        }
        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        namePattern = "^([a-zA-Z])$";
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("items");

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerview_more);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CategoryName");
        Log.e("More", "CategoryName " + categoryName);

        if (categoryName != null) {
            actionBar.setTitle(categoryName);
        } else {
            actionBar.setTitle("Products");
        }


        databaseReferenceMore = firebaseDatabase.getReference("data").child("users").child(userMobile).child("products");


        databaseReferenceMore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {

                    firebaseCategoryName = dataSnapshot2.getKey();
                    Log.e("More", "firebaseCategoryName " + firebaseCategoryName);

                    ArrayList<MorePojo> morePojoArrayList = new ArrayList<>();
                    if (firebaseCategoryName.equals(categoryName)) {

                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                            catIemName = dataSnapshot3.getKey();


                            for (DataSnapshot data2 : dataSnapshot3.getChildren()) {
                                MorePojo morePojo = new MorePojo();
                                morePojo.setItemName(data2.child("itemName").getValue(String.class));
                                morePojo.setItemWeight(data2.child("itemWeight").getValue(String.class));
                                morePojo.setItemPrice(data2.child("itemPrice").getValue(Integer.class));
                                morePojo.setItemMRPStrikePrice(data2.child("itemMRPStrikePrice").getValue(String.class));
                                morePojo.setItemImage(data2.child("itemImage").getValue(String.class));
                                morePojo.setButtonItemCount(data2.child("buttonItemCount").getValue(Integer.class));
                                morePojo.setItemCatName(data2.child("itemCatName").getValue(String.class));
                                morePojoArrayList.add(morePojo);


                            }


                            customAdapter = new RecyclerAdapter(More.this, morePojoArrayList, firebaseCategoryName);
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menuItem = menu.findItem(R.id.cart_action2);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("cartStatus").child("totalCount");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer count = dataSnapshot.getValue(Integer.class);
                if (count != null) {
                    cart_count = count;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (cart_count == 0) {
            menuItem.setVisible(false);
        } else {
            menuItem.setIcon(LayoutToDrawableConverter.convertLayoutToImage(More.this, cart_count, R.drawable.ic_shopping_cartmain));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cart_action2:
                Intent intent = new Intent(More.this, Cart.class);
                startActivity(intent);
                break;
            case R.id.cart_search:
                Intent intent2 = new Intent(More.this, SearchViewActivity.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddProduct() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("cartStatus").child("totalCount");
        rootRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                Object v = mutableData.getValue();
                if (v != null && v.equals(0)) {
                } else {
                    Integer count = mutableData.getValue(Integer.class);
                    if (count != null) {
                        mutableData.setValue(count + 1);
                        invalidateOptionsMenu();
                    }
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    @Override
    public void onRemoveProduct() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("cartStatus").child("totalCount");

        rootRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Integer count = mutableData.getValue(Integer.class);
                if (count != null) {
                    if (count <= 0) {
                        mutableData.setValue(0);
                    } else {
                        mutableData.setValue(count - 1);
                        invalidateOptionsMenu();
                    }
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
