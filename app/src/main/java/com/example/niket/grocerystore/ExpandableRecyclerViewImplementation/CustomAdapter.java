package com.example.niket.grocerystore.ExpandableRecyclerViewImplementation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.niket.grocerystore.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CustomAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, List<ChildItemPojo>> _listDataChild;


    private ChildItemPojo childItemPojo;

    CustomAdapter(Context context, List<String> listDataHeader, HashMap<String, List<ChildItemPojo>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<ChildItemPojo> list = this._listDataChild.get(this._listDataHeader.get(groupPosition));
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        List<ChildItemPojo> list = this._listDataChild.get(this._listDataHeader.get(groupPosition));
        ChildItemPojo childItemPojo = new ChildItemPojo();
        if (list != null && list.size() > 0) {
            childItemPojo = list.get(childPosititon);
        }
        return childItemPojo;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean expanded, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(infalInflater).inflate(R.layout.parent_category_layout, new LinearLayout(_context),false);
        }

        TextView lblListHeader = view.findViewById(R.id.parentName);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        ImageView imageView = view.findViewById(R.id.categoryImage);
        ImageView arrow = view.findViewById(R.id.arrowup);

        if (expanded) {
            arrow.setBackgroundResource(R.drawable.ic_down_arrow);
        } else {
            arrow.setBackgroundResource(R.drawable.ic_up_arrow);
        }

        switch (headerTitle) {


            case "Dry Fruits & Nuts":
                Glide.with(_context).load(R.mipmap.dry).into(imageView);

                break;
            case "Edible Oils":
                Glide.with(_context).load(R.mipmap.oil).into(imageView);
                break;

            case "Ghee":
                Glide.with(_context).load(R.mipmap.ghee).into(imageView);
                break;

            case "Pulses":
                Glide.with(_context).load(R.mipmap.pulses).into(imageView);
                break;
            case "Rice & Other Grains":
                Glide.with(_context).load(R.mipmap.rice).into(imageView);
                break;
            case "Spices":
                Glide.with(_context).load(R.mipmap.spices).into(imageView);
                break;

            case "Atta and Other Flours":
                Glide.with(_context).load(R.mipmap.atta).into(imageView);
                break;


        }

        return view;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        childItemPojo = (ChildItemPojo) getChild(groupPosition, childPosition);


        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(infalInflater).inflate(R.layout.child_category_layout, new LinearLayout(_context),false);
        }

        TextView txtListChild = view.findViewById(R.id.childName);

        txtListChild.setText(childItemPojo.getItemName());

        CardView cardView = view.findViewById(R.id.product);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String headerTitle = (String) getGroup(groupPosition);
                childItemPojo = (ChildItemPojo) getChild(groupPosition, childPosition);
                //Toast.makeText(_context, headerTitle, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(_context, CategoryChildListing.class);
                intent.putExtra("CatName", headerTitle);
                intent.putExtra("ItemCatName", childItemPojo.getItemName());
                _context.startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
