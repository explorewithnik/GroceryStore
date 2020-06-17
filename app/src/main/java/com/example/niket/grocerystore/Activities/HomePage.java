package com.example.niket.grocerystore.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.niket.grocerystore.Adapter.BannerAdapter;
import com.example.niket.grocerystore.Adapter.MyCustomAdapter;
import com.example.niket.grocerystore.Adress.NavigationAddress;
import com.example.niket.grocerystore.BannerPOJO.BannerPojo;
import com.example.niket.grocerystore.CartActivity.AddorRemoveCallbacks;
import com.example.niket.grocerystore.CartActivity.Cart;
import com.example.niket.grocerystore.CartActivity.LayoutToDrawableConverter;
import com.example.niket.grocerystore.ExpandableRecyclerViewImplementation.ExpandableListing;
import com.example.niket.grocerystore.ItemsPOJO.Categories_POJO;
import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;
import com.example.niket.grocerystore.R;
import com.example.niket.grocerystore.Search.SearchViewActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.santalu.autoviewpager.AutoViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddorRemoveCallbacks {
    MenuItem menuItem;
    TextView textViewMobileNav, textViewNameNav;
    static int cart_count = 0;
    //int count;
    CircleImageView imageViewProfileNav;
    ArrayList<BannerPojo> bannerArraylist = new ArrayList<>();
    //viewpager implementation
    BannerAdapter bannerAdapter;
    AutoViewPager viewPager;

    //indicator for viewpager
    CircleIndicator indicator;

    // vertical recyclerview
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyCustomAdapter customAdapter;

    //user profile
    String userMobile, googleUserId;

    //cardview categories & search for products click events
    CardView cardViewCategories, cardviewSearchForProducts;

    //firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceForBanner;
    DatabaseReference databaseReferenceForCategory;
    DatabaseReference databaseReferenceForUserProfile;
    DatabaseReference databaseReferenceToatalCount;
    DatabaseReference databaseReference2;

    SharedPreferences sharedPreferences, sharedPreferencesForAdapter;

    String categoryName;

    String uName, uMobile, uImage;

    NavigationView navigationView;

    //pojo
    Categories_POJO categories_pojo;
    BannerPojo bannerPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (isConnectingToInternet(this)) {
            sharedPreferences = getSharedPreferences("save", 0);
            userMobile = sharedPreferences.getString("userMobile", null);
            googleUserId = sharedPreferences.getString("senderID", null);
            sharedPreferencesForAdapter = getSharedPreferences("adapterSharedpreferences", 0);

            //viewpager implementation
            viewPager = findViewById(R.id.viewpager);
            indicator = findViewById(R.id.indicator);

            cardViewCategories = findViewById(R.id.cardview_categories);
            cardviewSearchForProducts = findViewById(R.id.cardview_serachforproducts);

            cardviewClickEventsForSearch();
            cardviewClickEventsForCategory();

            //vertical recyclerview initialisation
            recyclerView = findViewById(R.id.recyclerview_category_vertical);
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            //layoutManager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            //firebase initilisation
            firebaseDatabase = FirebaseDatabase.getInstance();

            databaseReferenceForCategory = firebaseDatabase.getReference("data").child("users").child(userMobile).child("items");
            databaseReferenceForBanner = firebaseDatabase.getReference("data").child("Banner Images");
            databaseReferenceToatalCount = firebaseDatabase.getReference("data").child("users").child(userMobile).child("cartStatus");
            databaseReference2 = firebaseDatabase.getReference("data").child("users").child(userMobile).child("products");

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //navigation Drawer
            navigationView = findViewById(R.id.nav_view);
            //getting the navigation headerview
            View headerView = navigationView.getHeaderView(0);
            textViewMobileNav = headerView.findViewById(R.id.mobileeNavigationDrawer);
            textViewNameNav = headerView.findViewById(R.id.displayNameNavigationDrawer);
            imageViewProfileNav = headerView.findViewById(R.id.displayImageNavigationdrawer);
            navigationView.setNavigationItemSelectedListener(this);


            createAllDataForRecyclerview();
            getUserDetails();
            showBannerOnViewPager();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomePage.this, NoInternetConnection.class);
            startActivity(intent);
            finish();
        }

    }


    private void cardviewClickEventsForCategory() {

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ExpandableListing.class);
                startActivity(intent);
            }
        });

    }

    private void cardviewClickEventsForSearch() {

        cardviewSearchForProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SearchViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getUserDetails() {

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

                    textViewNameNav.setText(uName);
                    textViewMobileNav.setText(uMobile);
                    Glide.with(getApplicationContext()).load(uImage).into(imageViewProfileNav);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showBannerOnViewPager() {
        databaseReferenceForBanner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    bannerPojo = new BannerPojo();
                    if (data.child("bannerImage").getValue(String.class) != null) {
                        bannerPojo.setBannerImage(data.child("bannerImage").getValue(String.class));
                        bannerArraylist.add(bannerPojo);
                    }
                }
                bannerAdapter = new BannerAdapter(HomePage.this, bannerArraylist);

                viewPager.setAdapter(bannerAdapter);
                indicator.setViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void createAllDataForRecyclerview() {


        databaseReferenceForCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Categories_POJO> allDataArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    categoryName = dataSnapshot1.getKey();

                    //creating new arraylist each time inner loop exits
                    ArrayList<Category_Items_POJO> singleItemArraylist = new ArrayList<>();

                    for (DataSnapshot data2 : dataSnapshot1.getChildren()) {
                        Category_Items_POJO category_items_pojo = new Category_Items_POJO();
                        category_items_pojo.setItemName(data2.child("itemName").getValue(String.class));
                        category_items_pojo.setItemWeight(data2.child("itemWeight").getValue(String.class));
                        category_items_pojo.setItemPrice(data2.child("itemPrice").getValue(Integer.class));
                        category_items_pojo.setItemMRPStrikePrice(data2.child("itemMRPStrikePrice").getValue(String.class));
                        category_items_pojo.setItemImage(data2.child("itemImage").getValue(String.class));
                        category_items_pojo.setButtonItemCount(data2.child("buttonItemCount").getValue(Integer.class));
                        category_items_pojo.setPlusMinusButton(data2.child("plusMinusButton").getValue(String.class));
                        category_items_pojo.setItemCatName(data2.child("itemCatName").getValue(String.class));
                        singleItemArraylist.add(category_items_pojo);
                    }

                    categories_pojo = new Categories_POJO();
                    categories_pojo.setCategoryName(categoryName);
                    categories_pojo.setAllItemInSectionArralist(singleItemArraylist);
                    allDataArrayList.add(categories_pojo);

                    customAdapter = new MyCustomAdapter(HomePage.this, allDataArrayList);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            menuItem.setIcon(LayoutToDrawableConverter.convertLayoutToImage(HomePage.this, cart_count, R.drawable.ic_shopping_cartmain));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_action) {
            Intent intent = new Intent(HomePage.this, Cart.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.aboutUsNav:
                Intent about = new Intent(HomePage.this, AboutUs.class);
                startActivity(about);
                break;

            case R.id.shopByCatNav:

                Intent category = new Intent(HomePage.this, ExpandableListing.class);
                startActivity(category);
                break;

            case R.id.addressesNav:
                Intent address = new Intent(HomePage.this, NavigationAddress.class);
                startActivity(address);
                break;

            case R.id.cartNavigationDrawer:
                if (cart_count == 0) {
                    Toast.makeText(this, "Add Products to Cart", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    Intent cart = new Intent(HomePage.this, Cart.class);
                    startActivity(cart);
                    break;
                }


            case R.id.ordeHistorysNav:
                Intent orders = new Intent(HomePage.this, OrderHistory.class);
                startActivity(orders);
                break;

            case R.id.logoutNav:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(HomePage.this, Login.class);
                startActivity(intent);
                finish();
                break;

            case R.id.shareNav:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "coming soon";
                String shareSub = "Share Application";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;

            case R.id.editNav:

                Intent editProfile = new Intent(HomePage.this, Profile.class);
                startActivity(editProfile);
                break;


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot
                    dataSnapshot) {
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

