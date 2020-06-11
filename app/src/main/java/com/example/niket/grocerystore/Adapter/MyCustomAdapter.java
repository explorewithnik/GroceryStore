package com.example.niket.grocerystore.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.niket.grocerystore.Activities.More;
import com.example.niket.grocerystore.ItemsPOJO.Categories_POJO;
import com.example.niket.grocerystore.R;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ContactViewHolder> {

    private ArrayList<Categories_POJO> categoriesPOJOArralist;
    private Context context;
    private Categories_POJO categoriesPOJO;

    public MyCustomAdapter(Context context, ArrayList<Categories_POJO> categoriesPOJOArralist) {
        this.context = context;
        this.categoriesPOJOArralist = categoriesPOJOArralist;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_design, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        categoriesPOJO = categoriesPOJOArralist.get(position);

        String CategoryName = categoriesPOJO.getCategoryName();
        ArrayList allItemInSectionArrayList = categoriesPOJO.getAllItemInSectionArralist();

        holder.CategoryName.setText(CategoryName);
        MyCustomAdapterForItems myCustomAdapterForItems = new MyCustomAdapterForItems(context, allItemInSectionArrayList, CategoryName);
        holder.recycler_view_Category_items.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recycler_view_Category_items.setLayoutManager(layoutManager);
        holder.recycler_view_Category_items.setNestedScrollingEnabled(false);
        holder.recycler_view_Category_items.setAdapter(myCustomAdapterForItems);
        myCustomAdapterForItems.notifyDataSetChanged();

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesPOJO = categoriesPOJOArralist.get(holder.getAdapterPosition());
                String CategoryName = categoriesPOJO.getCategoryName();

                Intent intent = new Intent(context, More.class);
                intent.putExtra("CategoryName", CategoryName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesPOJOArralist.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        CardView cardView, cardView2;
        TextView CategoryName;
        RecyclerView recycler_view_Category_items;
        Button btnMore;

        ContactViewHolder(View itemView) {
            super(itemView);
            cardView2 = itemView.findViewById(R.id.childcard);
            cardView = itemView.findViewById(R.id.parentcard);
            CategoryName = itemView.findViewById(R.id.category_Name);
            recycler_view_Category_items = itemView.findViewById(R.id.recycler_category_itesms_horizontal);
            btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}

