package com.example.niket.grocerystore.Search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niket.grocerystore.CartActivity.AddorRemoveCallbacks;
import com.example.niket.grocerystore.CartActivity.Cart;
import com.example.niket.grocerystore.CartActivity.LayoutToDrawableConverter;
import com.example.niket.grocerystore.POJO_Class.MorePojo;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity implements AddorRemoveCallbacks {

    LinearLayout linearLayout;
    MenuItem menuItem;
    ActionBar actionBar;
    static int cart_count = 0;
    EditText editTextSearch;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SearchViewAdapter searchViewAdapter;
    private String userMobile;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    ArrayList<MorePojo> morePojoArrayList;
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            actionBar = getSupportActionBar();
            actionBar.setTitle(R.string.search);
        }
        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        editTextSearch = findViewById(R.id.searchEdittext);
        linearLayout = findViewById(R.id.linearSearch);

        animation();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("search").child("products");

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerview_search);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Log.e("onCreate", "is empty value is : " + editTextSearch.getText().toString().isEmpty());

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    String startWith = charSequence.toString().toLowerCase().trim();
                    String endWith = charSequence.toString().toLowerCase().trim() + "\uf8ff";
                    query = firebaseDatabase.getReference("data").child("users").child(userMobile).child("search").child("products").orderByChild("searchItemName").startAt(startWith).endAt(endWith);

                    query.addValueEventListener(valueEventListener);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            morePojoArrayList = new ArrayList<>();
            for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                MorePojo morePojo = new MorePojo();
                morePojo.setCategoryName(data2.child("categoryName").getValue(String.class));
                morePojo.setItemName(data2.child("itemName").getValue(String.class));
                morePojo.setItemWeight(data2.child("itemWeight").getValue(String.class));
                morePojo.setItemPrice(data2.child("itemPrice").getValue(Integer.class));
                morePojo.setItemMRPStrikePrice(data2.child("itemMRPStrikePrice").getValue(String.class));
                morePojo.setItemImage(data2.child("itemImage").getValue(String.class));
                morePojo.setButtonItemCount(data2.child("buttonItemCount").getValue(Integer.class));
                morePojo.setItemCatName(data2.child("itemCatName").getValue(String.class));
                morePojoArrayList.add(morePojo);

            }

            searchViewAdapter = new SearchViewAdapter(SearchViewActivity.this, morePojoArrayList);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(searchViewAdapter);
            searchViewAdapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void animation() {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animadd);
        linearLayout.startAnimation(animation1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.cart_action);

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
            menuItem.setIcon(LayoutToDrawableConverter.convertLayoutToImage(SearchViewActivity.this, cart_count, R.drawable.ic_shopping_cartmain));
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_action) {
            Intent intent = new Intent(SearchViewActivity.this, Cart.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onAddProduct() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("cartStatus").child("totalCount");
        rootRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Integer count = mutableData.getValue(Integer.class);
                if (count != null) {
                    mutableData.setValue(count + 1);
                    invalidateOptionsMenu();
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
}
