package com.example.niket.grocerystore.ExpandableRecyclerViewImplementation;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.niket.grocerystore.CartActivity.Cart;
import com.example.niket.grocerystore.CartActivity.LayoutToDrawableConverter;
import com.example.niket.grocerystore.R;
import com.example.niket.grocerystore.Search.SearchViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListing extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ChildItemPojo>> listDataChild;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceForExpandableRec;

    String categoryName;
    static int cart_count = 0;
    ChildItemPojo childItemPojo;
    MenuItem menuItem;
    ActionBar actionBar;

    SharedPreferences sharedPreferences;
    String userMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_listing);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            actionBar = getSupportActionBar();
            actionBar.setTitle("Categories");
        }

        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");

        expListView = findViewById(R.id.expandlv);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceForExpandableRec = firebaseDatabase.getReference("data").child("users").child(userMobile).child("products");

        prepareListData();


    }


    private void prepareListData() {
        databaseReferenceForExpandableRec.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDataHeader = new ArrayList<>();
                listDataChild = new HashMap<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    categoryName = data.getKey();
                    listDataHeader.add(categoryName);

                    List<ChildItemPojo> items = new ArrayList<>();
                    for (DataSnapshot data2 : data.getChildren()) {
                        childItemPojo = new ChildItemPojo();
                        childItemPojo.setItemName(data2.getKey());
                        items.add(childItemPojo);

                    }

                    listDataChild.put(categoryName, items);
                    listAdapter = new CustomAdapter(ExpandableListing.this, listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cart_action2:
                Intent intent = new Intent(ExpandableListing.this, Cart.class);
                startActivity(intent);
                break;
            case R.id.cart_search:
                Intent intent2 = new Intent(ExpandableListing.this, SearchViewActivity.class);
                startActivity(intent2);
                break;


        }
        return super.onOptionsItemSelected(item);
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
            menuItem.setIcon(LayoutToDrawableConverter.convertLayoutToImage(ExpandableListing.this, cart_count, R.drawable.ic_shopping_cartmain));
        }
        return true;
    }
}

