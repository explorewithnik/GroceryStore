package com.example.niket.grocerystore.PaymentGateway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niket.grocerystore.Activities.HomePage;
import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class PaymentOptions extends AppCompatActivity {

    String finalAmount, name, mobile, state, city, pin, email, landmark, flatno, orderId, userMobile;
    CardView bhimcv, phonePecv, cashcv;
    TextView totaltv;
    Intent intent;
    private String TAG = "PaymentOptions";
    String payeeAddress = "7877641965@upi";
    String payeeName = "KISHAN CHOUDHARY";
    String transactionNote = "UPI Payment";
    String amount;
    String currencyUnit = "INR";
    String appName = "The Grocery Store";
    String transactionReference;
    String merchantCode = "1000200300";
    DatabaseReference databaseReference, databaseReferenceSearch, databaseReferenceCart, databaseReferenceCartStatus, databaseReferenceItems, databaseReferenceProducts;
    ProgressDialog dialog;
    String mode;


    String CurrentDate, currentTime;

    FirebaseDatabase firebaseDatabase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        bhimcv = findViewById(R.id.Bhim);
        phonePecv = findViewById(R.id.PhonePe);
        cashcv = findViewById(R.id.cash);
        totaltv = findViewById(R.id.totalamount);

        firebaseDatabase = FirebaseDatabase.getInstance();
        intent = getIntent();
        finalAmount = intent.getStringExtra("finalAmount");
        String finalAmountTxt = "₹" + finalAmount;
        totaltv.setText(finalAmountTxt);

        name = intent.getStringExtra("name2");
        mobile = intent.getStringExtra("mobile2");
        email = intent.getStringExtra("email2");
        state = intent.getStringExtra("state2");
        city = intent.getStringExtra("city2");
        pin = intent.getStringExtra("pin2");
        flatno = intent.getStringExtra("flatno2");
        landmark = intent.getStringExtra("landmark2");

        //dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait.....");
        dialog.setTitle("Placing your order");
        dialog.setCanceledOnTouchOutside(false);


        amount = finalAmount;
        transactionReference = String.valueOf(getRandomNumber());
        orderId = String.valueOf(getRandomNumber() + getRandomNumber() + getRandomNumber());

        sharedPreferences = getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");

        databaseReferenceProducts = firebaseDatabase.getReference("data").child("products");
        databaseReferenceSearch = firebaseDatabase.getReference("data").child("search").child("products");
        databaseReferenceItems = firebaseDatabase.getReference("data").child("items");
        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Orders");
        databaseReferenceCart = firebaseDatabase.getReference("data").child("Cart").child("products");
        databaseReferenceCartStatus = firebaseDatabase.getReference("data").child("cartStatus").child("totalCount");

        bhimcv.setEnabled(false);
        //Bhim
        bhimcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CurrentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                currentTime = CurrentDate.substring(CurrentDate.lastIndexOf(" ") + 1);

                Uri uri = Uri.parse("upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&mc=" + merchantCode + "&tr=" + transactionReference + "&tn=" + transactionNote +
                        "&am=" + Integer.valueOf(amount) + "&cu=" + currencyUnit + "&appname=" + appName);
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("in.org.npci.upiapp");

                if (null != intent) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    mode = "BHIM UPI";
                    dialog.show();
                    startActivityForResult(intent, 1);


                } else {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "in.org.npci.upiapp")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "in.org.npci.upiapp")));
                    }
                    Toast.makeText(PaymentOptions.this, "App Not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bhimm close

        phonePecv.setEnabled(false);
        phonePecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                currentTime = CurrentDate.substring(CurrentDate.lastIndexOf(" ") + 1);
                Uri uri = Uri.parse("upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&mc=" + merchantCode + "&tr=" + transactionReference + "&tn=" + transactionNote +
                        "&am=" + amount + "&cu=" + currencyUnit + "&appname=" + appName);
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("com.phonepe.app");

                if (null != intent) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    mode = "PhonePe UPI";
                    dialog.show();
                    startActivityForResult(intent, 1);

                } else {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "in.org.npci.upiapp")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "in.org.npci.upiapp")));
                    }
                    Toast.makeText(PaymentOptions.this, "App Not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cashcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());
                currentTime = CurrentDate.substring(CurrentDate.lastIndexOf(" ") + 1);
                Log.d("0667", "onClick: " + currentTime);
                mode = "Cash";

                dialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OrderPojo orderPojo = new OrderPojo();
                        orderPojo.setName(name);
                        orderPojo.setCity(city);
                        orderPojo.setCurrentDate(CurrentDate);
                        orderPojo.setCurrentTime(currentTime);
                        orderPojo.setEmail(email);
                        orderPojo.setFinalAmount(amount);
                        orderPojo.setFlatNo(flatno);
                        orderPojo.setLandMark(landmark);
                        orderPojo.setMobile(mobile);
                        orderPojo.setState(state);
                        orderPojo.setTransactionReference(transactionReference);
                        orderPojo.setOrderId(orderId);
                        orderPojo.setLandMark(landmark);
                        orderPojo.setMode(mode);
                        databaseReference.push().setValue(orderPojo);
                        databaseReferenceCart.setValue(null);
                        databaseReferenceCartStatus.setValue(0);

                        databaseReferenceSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String name = data.getKey();
                                    if (name != null)
                                        databaseReferenceSearch.child(name).child("buttonItemCount").setValue(0);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReferenceProducts.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String categoryName = data.getKey();
                                    for (DataSnapshot data2 : data.getChildren()) {
                                        String itemCatName = data2.getKey();
                                        for (DataSnapshot data3 : data2.getChildren()) {
                                            String itemName = data3.getKey();
                                            Log.e("databaseReferenceItems", "categoryName is : " + categoryName + " itemName is : " + itemName);
                                            if (categoryName != null && itemCatName != null && itemName != null)
                                                databaseReferenceProducts.child(categoryName).child(itemCatName).child(itemName).child("buttonItemCount").setValue(0);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReferenceItems.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String categoryName = data.getKey();
                                    for (DataSnapshot data2 : data.getChildren()) {
                                        String itemName = data2.getKey();
                                        Log.e("databaseReferenceItems", "categoryName is : " + categoryName + " itemName is : " + itemName);
                                        if (categoryName != null && itemName != null)
                                            databaseReferenceItems.child(categoryName).child(itemName).child("buttonItemCount").setValue(0);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.cancel();

                        Toast.makeText(PaymentOptions.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(PaymentOptions.this, HomePage.class);
                        startActivity(intent1);
                        finish();
                    }
                }, 2000);


            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res != null)
                if (res.toLowerCase().contains(search.toLowerCase())) {

                    OrderPojo orderPojo = new OrderPojo();
                    orderPojo.setName(name);
                    orderPojo.setCity(city);
                    orderPojo.setCurrentDate(CurrentDate);
                    orderPojo.setCurrentTime(currentTime);
                    orderPojo.setEmail(email);
                    orderPojo.setFinalAmount(amount);
                    orderPojo.setFlatNo(flatno);
                    orderPojo.setLandMark(landmark);
                    orderPojo.setMobile(mobile);
                    orderPojo.setState(state);
                    orderPojo.setTransactionReference(transactionReference);
                    orderPojo.setOrderId(orderId);
                    orderPojo.setLandMark(landmark);
                    orderPojo.setMode(mode);
                    databaseReference.push().setValue(orderPojo);


                    dialog.cancel();
                    Toast.makeText(PaymentOptions.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                    /*String senderEmail = email;
                    Intent email = new Intent(android.content.Intent.ACTION_SENDTO);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{senderEmail});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Grocery Store Order Status");
                    email.putExtra(Intent.EXTRA_TEXT, message);
                    //need this to prompts email client only
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));*/

                    Intent intent1 = new Intent(PaymentOptions.this, HomePage.class);
                    startActivity(intent1);
                    finish();

                } else {
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private int getRandomNumber() {
        Random random = new Random();
        int a = random.nextInt(50000000);
        int b = random.nextInt(50000000);
        int c = random.nextInt(50000000);
        return a + b + c;
    }
}
