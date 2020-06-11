package com.example.niket.grocerystore.Adress;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.niket.grocerystore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NavigationAddressAdapter extends RecyclerView.Adapter<NavigationAddressAdapter.ContactViewHolder> {
    private ArrayList<AddressPojo> arrayListAddressPojos;
    private Context mContext;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userMobile;


    NavigationAddressAdapter(Context context, ArrayList<AddressPojo> arrayListAddressPojos, String userMobile) {
        this.mContext = context;
        this.arrayListAddressPojos = arrayListAddressPojos;
        this.userMobile = userMobile;
    }

    @NonNull
    @Override
    public NavigationAddressAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_addresses, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NavigationAddressAdapter.ContactViewHolder holder, int position) {

        holder.radioButton.setVisibility(View.INVISIBLE);

        AddressPojo addressPojo = arrayListAddressPojos.get(position);
        holder.tvname.setText(addressPojo.getName());
        holder.tvmobile.setText(addressPojo.getMobile());
        String tvStateCityPINText = addressPojo.getState() + ", " + addressPojo.getCity() + "- " + addressPojo.getPin();
        holder.tvStateCityPIN.setText(tvStateCityPINText);
        holder.tvlandmark.setText(addressPojo.getLandmark());
        holder.tvflatNo.setText(addressPojo.getAddress());

        holder.tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressPojo addressPojo = arrayListAddressPojos.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, AddAddress.class);
                intent.putExtra("name2", addressPojo.getName());
                intent.putExtra("mobile2", addressPojo.getMobile());
                intent.putExtra("state2", addressPojo.getState());
                intent.putExtra("city2", addressPojo.getCity());
                intent.putExtra("pin2", addressPojo.getPin());
                intent.putExtra("email2", addressPojo.getEmail());
                intent.putExtra("landmark2", addressPojo.getLandmark());
                intent.putExtra("flatno2", addressPojo.getAddress());
                intent.putExtra("deleteKey", addressPojo.getDeletekey());
                intent.putExtra("title", "Update Address");
                intent.putExtra("title2", "NavigationAdd");

                mContext.startActivity(intent);
            }
        });

        holder.tvremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressPojo addressPojo = arrayListAddressPojos.get(holder.getAdapterPosition());
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("data").child("users").child(userMobile).child("Address").child(addressPojo.getDeletekey());
                databaseReference.setValue(null);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayListAddressPojos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        RadioButton radioButton;
        TextView tvname, tvflatNo, tvlandmark, tvStateCityPIN, tvmobile, tvedit, tvremove;

        ContactViewHolder(View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.addressRadio);
            tvname = itemView.findViewById(R.id.addressName);
            tvflatNo = itemView.findViewById(R.id.addressFlatNo);
            tvlandmark = itemView.findViewById(R.id.addressArea);
            tvStateCityPIN = itemView.findViewById(R.id.addressSCP);
            tvmobile = itemView.findViewById(R.id.addressMobile);
            tvedit = itemView.findViewById(R.id.addressEdit);
            tvremove = itemView.findViewById(R.id.addressRemove);

        }
    }
}
