package com.example.hellocarwash.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.hellocarwash.R;
import com.example.hellocarwash.model.Company;
import com.example.hellocarwash.model.Product;
import com.example.hellocarwash.model.ProductAdapter;

import java.util.ArrayList;

public class PricesForm extends AppCompatActivity {
    //private Button servicesButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices_form);
        getSupportActionBar().setTitle(getString(R.string.servicesActivityTitle));
        //servicesButton = (Button) findViewById(R.id.servicesButton);
        //servicesButton.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
               // startActivity(new Intent(PricesForm.this,SignInForm.class));
              //  finish();

        RecyclerView mRecycleView = findViewById(R.id.recyclerview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Company> companies = new ArrayList<>();

        ArrayList<Product> googleProduct = new ArrayList<>();
        googleProduct.add(new Product("Google AdSense"));
        googleProduct.add(new Product("Google Drive"));
        googleProduct.add(new Product("Google Mail"));
        googleProduct.add(new Product("Google Doc"));
        googleProduct.add(new Product("Android"));

        Company google = new Company("Google", googleProduct);
        companies.add(google);

        ArrayList<Product> microsoftProduct = new ArrayList<>();
        microsoftProduct.add(new Product("Windows"));
        microsoftProduct.add(new Product("SkyDrive"));
        microsoftProduct.add(new Product("Microsoft Store"));
        microsoftProduct.add(new Product("Microsoft Office"));

        Company microsoft = new Company("Microsoft", microsoftProduct);
        companies.add(microsoft);

        ProductAdapter adapter = new ProductAdapter(companies);
        mRecycleView.setAdapter(adapter);

            }

        }


