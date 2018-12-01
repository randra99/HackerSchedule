package com.example.wildcats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button  openCbt, analyzeBtn;
    TextView text;
    public static String storedfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openCbt = (Button) findViewById(R.id.camerabt);
        analyzeBtn = (Button) findViewById(R.id.button2);
        text = (TextView) findViewById(R.id.label1);

        openCbt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, CameraAcitivity.class));
            }
        });

        analyzeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ParseJSON a = new ParseJSON(storedfile);
                String s = a.test();
                text.setText(s);
            }
        });
    }
}
