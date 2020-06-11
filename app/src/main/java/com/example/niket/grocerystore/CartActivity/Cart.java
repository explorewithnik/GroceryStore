package com.example.niket.grocerystore.CartActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niket.grocerystore.Adapter.CartAdapter;
import com.example.niket.grocerystore.Adress.UserAddress;
import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;
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

public class Cart extends AppCompatActivity implements AddorRemoveCallbacks {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReferenceForTotalPrice;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter cartAdapter;
    static int cart_count = 0;
    MenuItem menuItem;
    TextView textViewTotal, textViewcheckout;
    LinearLayout checkout;
    CardView cardView;
    ActionBar actionBar;
    int totalAmount;
    ImageView arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            actionBar = getSupportActionBar();
            actionBar.setTitle("Cart");
        }

        cardView = findViewById(R.id.chk);
        textViewTotal = findViewById(R.id.total);
        checkout = findViewById(R.id.checkout);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("data").child("Cart").child("products");
        databaseReferenceForTotalPrice = firebaseDatabase.getReference("data").child("totalPrice").child("total");

        textViewcheckout = findViewById(R.id.tvchk);
        arrow = findViewById(R.id.arrow);
        recyclerView = findViewById(R.id.cart_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        createAllDataForRecyclerview();
        onClickCheckout();
    }

    private void onClickCheckout() {


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Cart.this, UserAddress.class);
                intent.putExtra("totalPrice", String.valueOf(totalAmount));
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.cart_action);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("cartStatus").child("totalCount");
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

        menuItem.setIcon(LayoutToDrawableConverter.convertLayoutToImage(Cart.this, cart_count, R.drawable.ic_shopping_cartmain));
        return true;
    }
    public void createAllDataForRecyclerview() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Category_Items_POJO> singleItemArraylist = new ArrayList<>();
                ArrayList<Integer> arrayList = new ArrayList<>();

                for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                    Category_Items_POJO category_items_pojo = new Category_Items_POJO();
                    category_items_pojo.setItemName(data2.child("itemName").getValue(String.class));
                    category_items_pojo.setItemWeight(data2.child("itemWeight").getValue(String.class));
                    category_items_pojo.setItemPrice(data2.child("itemPrice").getValue(Integer.class));
                    category_items_pojo.setItemMRPStrikePrice(data2.child("itemMRPStrikePrice").getValue(String.class));
                    category_items_pojo.setItemImage(data2.child("itemImage").getValue(String.class));
                    category_items_pojo.setButtonItemCount(data2.child("buttonItemCount").getValue(Integer.class));
                    category_items_pojo.setPlusMinusButton(data2.child("plusMinusButton").getValue(String.class));
                    category_items_pojo.setCategoryName(data2.child("categoryName").getValue(String.class));
                    category_items_pojo.setTotalItemAmount(data2.child("totalItemAmount").getValue(Integer.class));
                    category_items_pojo.setItemCatName(data2.child("itemCatName").getValue(String.class));
                    arrayList.add(data2.child("totalItemAmount").getValue(Integer.class));
                    singleItemArraylist.add(category_items_pojo);
                }

                //calculating total cart amount
                totalAmount = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    totalAmount = totalAmount + arrayList.get(i);
                    String amount = "₹" + totalAmount;
                    textViewTotal.setText(amount);
                    if (cart_count == 0) {
                        finish();
                    }
                }
                cartAdapter = new CartAdapter(Cart.this, singleItemArraylist);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
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

    @Override
    public void onAddProduct() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("cartStatus").child("totalCount");
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
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("cartStatus").child("totalCount");
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
}
