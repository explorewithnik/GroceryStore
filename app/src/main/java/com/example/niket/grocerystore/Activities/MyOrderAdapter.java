package com.example.niket.grocerystore.Activities;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.niket.grocerystore.PaymentGateway.OrderPojo;
import com.example.niket.grocerystore.R;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ContactViewHolder> {

    private ArrayList<OrderPojo> orderPojoArrayList;

    MyOrderAdapter(Context context, ArrayList<OrderPojo> orderPojoArrayList) {
        this.orderPojoArrayList = orderPojoArrayList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderlayout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderAdapter.ContactViewHolder holder, int position) {

        OrderPojo orderPojo = orderPojoArrayList.get(position);
        String orderName = orderPojo.getFlatNo()+", "+orderPojo.getLandMark();
        holder.orderName.setText(orderName);
        holder.orderNo.setText(orderPojo.getOrderId());
        holder.orderDate.setText(orderPojo.getCurrentDate());
        String orderAmount = "₹"+orderPojo.getFinalAmount();
        holder.orderAmount.setText(orderAmount);
    }

    @Override
    public int getItemCount() {
        return orderPojoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderDate, orderNo, orderAmount;
        ContactViewHolder(View itemView) {
            super(itemView);
            orderAmount = itemView.findViewById(R.id.orderfinalAmount);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderNo = itemView.findViewById(R.id.OrderNo);
            orderName = itemView.findViewById(R.id.orderName);
        }
    }
}
