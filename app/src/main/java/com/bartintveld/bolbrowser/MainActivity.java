package com.bartintveld.bolbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BolApiConnector.ProductsAvailable {

    private ArrayList<Product> products = new ArrayList<>();

    private ListView listView;
    private TextView textView;

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting adapter
        productAdapter = new ProductAdapter(this, products);

        //Making reference to ListView
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(productAdapter);
    }

    public void searchProduct(View v){
        //Test
        String[] urls = new String[]{"https://api.bol.com/catalog/v4/search/?apikey=25C4742A92BF468EB2BD888FC8FBFF40&format=json&q=potter"};
        BolApiConnector getProducts = new BolApiConnector(this);
        getProducts.execute(urls);
}


    @Override
    public void productAvailable(ArrayList<Product> result) {
        products = result;
        productAdapter = new ProductAdapter(this, products);
        listView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }
}
