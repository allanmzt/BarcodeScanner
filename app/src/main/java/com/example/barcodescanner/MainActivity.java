package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private IntentIntegrator qrScan;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrScan = new IntentIntegrator(this);
        tv=findViewById(R.id.txt_barcode);
        iv=findViewById(R.id.imageView);

    }

    public void clickScan(View view) {
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    tv.setText(result.getContents());
                    MyTask myTask=new MyTask(this);
                    try {
                        myTask.execute(new URL("https://us.openfoodfacts.org/api/v0/product/"+result.getContents()));
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void AsynkTaskResult(Product product){
        tv.setText(product.getName());
        Picasso.get().load(product.getImgURL()).into(iv);
    }
}