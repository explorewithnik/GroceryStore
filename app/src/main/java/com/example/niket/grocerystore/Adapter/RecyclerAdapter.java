package com.example.niket.grocerystore.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.niket.grocerystore.Activities.More;
import com.example.niket.grocerystore.CartActivity.AddorRemoveCallbacks;
import com.example.niket.grocerystore.POJO_Class.MorePojo;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    private ArrayList<MorePojo> morePojoArrayList;
    private Context mContext;
    private DatabaseReference databaseReference, databaseReferenceForCategory, databaseReferenceForSearch;
    private MorePojo morePojo1;
    private String catName;
    SharedPreferences sharedPreferences;
    String userMobile;

    public RecyclerAdapter(Context mContext, ArrayList<MorePojo> morePojoArrayList, String catName) {
        this.mContext = mContext;
        this.morePojoArrayList = morePojoArrayList;
        this.catName = catName;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_class_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        sharedPreferences = mContext.getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Cart").child("products");
        databaseReferenceForCategory = firebaseDatabase.getReference("data").child("users").child(userMobile).child("items");
        databaseReferenceForSearch = firebaseDatabase.getReference("data").child("users").child(userMobile).child("search").child("products");

        MorePojo morePojo = morePojoArrayList.get(holder.getAdapterPosition());
        holder.productName.setText(morePojo.getItemName());
        holder.mrp.setText(String.valueOf(morePojo.getItemPrice()));
        holder.strike_MRP.setText(morePojo.getItemMRPStrikePrice());
        holder.product_weight.setText(morePojo.getItemWeight());
        Log.e("adapter more", "item getButtonItemCount " + morePojo.getButtonItemCount());

        if (morePojo.getButtonItemCount() != null && morePojo.getButtonItemCount() == 0) {
            Log.e("adapter more if", "item getButtonItemCount " + morePojo.getButtonItemCount());
            holder.addMinusButton.setVisibility(View.INVISIBLE);
            holder.addButton.setText(R.string.add_button_text);
            holder.addPlusButton.setText("+");
        } else {
            Log.e("adapter more else", "item getButtonItemCount " + morePojo.getButtonItemCount());
            holder.addButton.setTextColor(mContext.getResources().getColor(R.color.black_light));
            holder.addButton.setBackgroundColor(Color.WHITE);
            holder.addButton.setText(String.valueOf(morePojo.getButtonItemCount()));
            holder.addMinusButton.setVisibility(View.VISIBLE);
        }

        Glide.with(mContext).load(morePojo.getItemImage()).into(holder.productImage);
        holder.strike_MRP.setPaintFlags(holder.strike_MRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.addPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                morePojo1 = morePojoArrayList.get(position);

                morePojoArrayList.get(holder.getAdapterPosition()).setAddedTocart(true);

                Log.e("Wish", "catName " + catName + " ItemCatName() " + morePojo1.getItemCatName());
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("products").child(catName).child(morePojo1.getItemCatName()).child(morePojo1.getItemName()).child("buttonItemCount");

                rootRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Object v = mutableData.getValue();
                        if (v != null && v.equals(0)) {
                            Log.e("Wish 2", "v " + v);
                        } else {
                            Integer count = mutableData.getValue(Integer.class);
                            if (count != null) {
                                mutableData.setValue(count + 1);
                                MorePojo morePojo2 = new MorePojo();
                                morePojo2.setButtonItemCount(count + 1);
                                morePojo2.setCategoryName(catName);
                                morePojo2.setItemWeight(morePojo1.getItemWeight());
                                morePojo2.setItemMRPStrikePrice(morePojo1.getItemMRPStrikePrice());
                                morePojo2.setItemPrice(morePojo1.getItemPrice());
                                morePojo2.setItemName(morePojo1.getItemName());
                                morePojo2.setItemImage(morePojo1.getItemImage());
                                morePojo2.setTotalItemAmount((count + 1) * (morePojo1.getItemPrice()));
                                morePojo2.setItemCatName(morePojo1.getItemCatName());
                                databaseReference.child(morePojo1.getItemName()).setValue(morePojo2);
                                databaseReferenceForCategory.child(catName).child(morePojo1.getItemName()).child("buttonItemCount").setValue(count + 1);
                                databaseReferenceForSearch.child(morePojo1.getItemName()).child("buttonItemCount").setValue(count + 1);
                            }
                        }

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            System.out.println("Firebase counter increment failed.");
                        } else {
                            System.out.println("Firebase counter increment succeeded.");

                        }
                    }
                });
                if (mContext instanceof More) {
                    ((AddorRemoveCallbacks) mContext).onAddProduct();
                }

            }
        });

        holder.addMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                morePojo1 = morePojoArrayList.get(holder.getAdapterPosition());
                morePojoArrayList.get(holder.getAdapterPosition()).setAddedTocart(false);


                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("users").child(userMobile).child("products").child(catName).child(morePojo1.getItemCatName()).child(morePojo1.getItemName()).child("buttonItemCount");

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
                                MorePojo morePojo2 = new MorePojo();

                                morePojo2.setButtonItemCount(count - 1);
                                morePojo2.setCategoryName(catName);
                                morePojo2.setItemWeight(morePojo1.getItemWeight());
                                morePojo2.setItemMRPStrikePrice(morePojo1.getItemMRPStrikePrice());
                                morePojo2.setItemPrice(morePojo1.getItemPrice());
                                morePojo2.setItemName(morePojo1.getItemName());
                                morePojo2.setItemImage(morePojo1.getItemImage());
                                morePojo2.setTotalItemAmount((count - 1) * (morePojo1.getItemPrice()));
                                morePojo2.setItemCatName(morePojo1.getItemCatName());
                                databaseReference.child(morePojo1.getItemName()).setValue(morePojo2);
                                databaseReferenceForCategory.child(catName).child(morePojo1.getItemName()).child("buttonItemCount").setValue(count - 1);
                                databaseReferenceForSearch.child(morePojo1.getItemName()).child("buttonItemCount").setValue(count - 1);
                            }
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            System.out.println("Firebase counter increment failed.");
                        } else {
                            System.out.println("Firebase counter increment succeeded.");

                        }
                    }
                });

                if (mContext instanceof More) {
                    ((AddorRemoveCallbacks) mContext).onRemoveProduct();
                }

            }
        });


        holder.moreCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePojo morePojo = morePojoArrayList.get(holder.getAdapterPosition());
                Toast.makeText(mContext, morePojo.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return morePojoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, mrp, strike_MRP, product_weight;
        CardView moreCardview;
        Button addButton, addPlusButton, addMinusButton;

        MyViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.itemImage_more);
            productName = itemView.findViewById(R.id.itemName_more);
            mrp = itemView.findViewById(R.id.itemPrice_more);
            strike_MRP = itemView.findViewById(R.id.itemMRPStrikePrice_more);
            product_weight = itemView.findViewById(R.id.itemWeight_more);
            moreCardview = itemView.findViewById(R.id.cv_more);
            addButton = itemView.findViewById(R.id.addItem_more);
            addPlusButton = itemView.findViewById(R.id.addItemPlus_more);
            addMinusButton = itemView.findViewById(R.id.addItemMinus_more);

            addButton.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
