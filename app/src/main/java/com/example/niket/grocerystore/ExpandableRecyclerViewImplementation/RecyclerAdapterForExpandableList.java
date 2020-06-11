package com.example.niket.grocerystore.ExpandableRecyclerViewImplementation;

import android.content.Context;
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

public class RecyclerAdapterForExpandableList extends RecyclerView.Adapter<RecyclerAdapterForExpandableList.MyViewHolder> {

    private ArrayList<MorePojo> morePojoArrayList;
    private Context mContext;
    private DatabaseReference databaseReference, databaseReferenceForCategory, databaseReferenceForSearch;
    private MorePojo morePojo1;
    private String catName, itemCatName;

    RecyclerAdapterForExpandableList(Context mContext, ArrayList<MorePojo> morePojoArrayList, String catName, String itemCatName) {
        this.mContext = mContext;
        this.morePojoArrayList = morePojoArrayList;
        this.catName = catName;
        this.itemCatName = itemCatName;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particular_item_listing, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterForExpandableList.MyViewHolder holder, final int position) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data").child("Cart").child("products");
        databaseReferenceForCategory = firebaseDatabase.getReference("data").child("items");
        databaseReferenceForSearch = firebaseDatabase.getReference("data").child("search").child("products");

        final MorePojo morePojo = morePojoArrayList.get(position);
        holder.productName.setText(morePojo.getItemName());
        String mrp = "₹" + morePojo.getItemPrice();
        holder.mrp.setText(mrp);

        String strike_MRP = "₹" + morePojo.getItemMRPStrikePrice();
        holder.strike_MRP.setText(strike_MRP);

        holder.product_weight.setText(morePojo.getItemWeight());

        if (morePojo.getButtonItemCount() == 0) {
            holder.addMinusButton.setVisibility(View.INVISIBLE);
            holder.addButton.setText(R.string.add_btn_text);
            holder.addPlusButton.setText("+");

            databaseReference.child(morePojo.getItemName()).setValue(null);
        } else {
            holder.addButton.setTextColor(mContext.getResources().getColor(R.color.black_light));
            holder.addButton.setBackgroundColor(Color.WHITE);

            String addBtn = morePojo.getButtonItemCount() + "";
            holder.addButton.setText(addBtn);

            holder.addMinusButton.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(morePojo.getItemImage()).into(holder.productImage);
        holder.strike_MRP.setPaintFlags(holder.strike_MRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.addPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                morePojo1 = morePojoArrayList.get(holder.getAdapterPosition());
                morePojoArrayList.get(holder.getAdapterPosition()).setAddedTocart(true);
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("products").child(catName).child(itemCatName).child(morePojo1.getItemName()).child("buttonItemCount");

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
                                MorePojo morePojo2 = new MorePojo();
                                morePojo2.setButtonItemCount(count + 1);
                                morePojo2.setCategoryName(catName);
                                morePojo2.setItemWeight(morePojo1.getItemWeight());
                                morePojo2.setItemMRPStrikePrice(morePojo1.getItemMRPStrikePrice());
                                morePojo2.setItemPrice(morePojo1.getItemPrice());
                                morePojo2.setItemName(morePojo1.getItemName());
                                morePojo2.setItemImage(morePojo1.getItemImage());
                                morePojo2.setTotalItemAmount((count + 1) * (morePojo1.getItemPrice()));
                                morePojo2.setItemCatName(itemCatName);
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
                // databaseReferenceForCategory.child(CategoryName).child(category_items_pojo1.getItemName()).child("plusMinusButton").setValue("-");

                if (mContext instanceof CategoryChildListing) {
                    ((AddorRemoveCallbacks) mContext).onAddProduct();
                }

            }
        });

        holder.addMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                morePojo1 = morePojoArrayList.get(holder.getAdapterPosition());
                morePojoArrayList.get(holder.getAdapterPosition()).setAddedTocart(false);


                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("products").child(catName).child(itemCatName).child(morePojo1.getItemName()).child("buttonItemCount");

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
                                morePojo2.setItemCatName(itemCatName);
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

                if (mContext instanceof CategoryChildListing) {
                    ((AddorRemoveCallbacks) mContext).onRemoveProduct();
                }

            }
        });


        holder.moreCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePojo morePojo = morePojoArrayList.get(position);
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

            productImage = itemView.findViewById(R.id.itemImage_expand);
            productName = itemView.findViewById(R.id.itemName_expand);
            mrp = itemView.findViewById(R.id.itemPrice_expand);
            strike_MRP = itemView.findViewById(R.id.itemMRPStrikePrice_expand);
            product_weight = itemView.findViewById(R.id.itemWeight_expand);
            moreCardview = itemView.findViewById(R.id.cv_expand);
            addButton = itemView.findViewById(R.id.addItemexpand);
            addPlusButton = itemView.findViewById(R.id.addItemPlusexpand);
            addMinusButton = itemView.findViewById(R.id.addItemMinusexpand);

            addButton.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
