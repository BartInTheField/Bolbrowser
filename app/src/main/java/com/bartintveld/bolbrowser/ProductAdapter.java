package com.bartintveld.bolbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by barti on 14-3-2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> products){
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Declaration of product
        Product product = getItem(position);

        //Make convertView
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_layout, parent, false);
        }

        //Declaration of TextViews
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView specsTag = (TextView) convertView.findViewById(R.id.specsTag);
        TextView summary = (TextView) convertView.findViewById(R.id.summary);

        //Declaration of ImageView
        ImageView imageView = (ImageView) convertView.findViewById(R.id.smallImage);

        //Filling TextViews with product info
        title.setText(product.getTitle());
        specsTag.setText(product.getSpecsTag());
        summary.setText(product.getSummary());

        //Filling ImageView with image from product
        //Using Picasso
        Picasso.with(getContext()).load(product.getSmallImageUrl()).into(imageView);

        //Returning view for display
        return convertView;
    }
}
