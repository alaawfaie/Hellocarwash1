package com.example.hellocarwash.model;

import android.view.View;
import android.widget.TextView;

import com.example.hellocarwash.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class CompanyViewHolder extends GroupViewHolder {
    private TextView groupTextView ;

    public CompanyViewHolder(View itemView) {

        super(itemView);
        groupTextView = itemView.findViewById(R.id.groupTextView);
    }

    public void bind(Company company){
        groupTextView.setText(company.getTitle());
    }
}
