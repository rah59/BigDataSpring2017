package tutorial.cs5551.com.translateapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassifyActivity extends AppCompatActivity {

    String sourceImg;
    TextView outputTextView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_classify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        outputTextView = (TextView) findViewById(R.id.txt_Result);
    }
    public void logout(View v) {
        Intent redirect = new Intent(ClassifyActivity.this, LoginActivity.class);
        startActivity(redirect);
    }


    public void testImage(View v) {

        ImageView imageView = (ImageView) findViewById(R.id.ImageView1);
        imageView.setImageResource(R.drawable.sore);

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();

        String retval = "";

        final ClarifaiClient client = new ClarifaiBuilder("xSj3UtS7J9397wku4XwCjfJVb2dYUaF7U5rIkacQ", "ujp7e5O7llwf_PHpcE-KMcyhQrABU_cMsl24Ubp8")
                .client(new OkHttpClient())
                .buildSync();
        client.getToken();

        //Use the custom model Mouth-Sore to predict the image
        ClarifaiResponse response = client.predict("Mouth-Sore")
                .withInputs(
                        ClarifaiInput.forImage(ClarifaiImage.of(image))
                )
                .executeSync();
        List<ClarifaiOutput<Concept>> predictions = (List<ClarifaiOutput<Concept>>) response.get();


        if (predictions.isEmpty()) {
            retval = "You are pulling my leg. I don't see any sores. You are good!";
        }
        else{
            List<Concept> data = predictions.get(0).data();

            String Sore_name = "";
            float Sore_value = 0;

            for (int i = 0; i < data.size(); i++) {
                System.out.println(data.get(i).name() + " - " + data.get(i).value());
                if (data.get(i).value() > Sore_value) {
                    Sore_value = data.get(i).value();
                    Sore_name = data.get(i).name();
                }
            }

            retval = "There is a " + String.format("%.2f", Sore_value*100) + "% chance that this is a " + Sore_name + ".";

        }

        outputTextView.setText(retval);
    }
}
