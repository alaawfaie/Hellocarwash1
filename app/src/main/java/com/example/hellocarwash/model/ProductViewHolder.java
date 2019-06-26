package com.example.hellocarwash.model;

import android.view.View;
import android.widget.TextView;

import com.example.hellocarwash.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ProductViewHolder extends ChildViewHolder {
    private TextView itemTextView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        itemTextView = itemView.findViewById(R.id.itemTextView);
    }

    public void bind(Product product){itemTextView.setText(product.name);}
}
