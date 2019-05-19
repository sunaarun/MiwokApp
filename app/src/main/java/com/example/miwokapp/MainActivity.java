package com.example.miwokapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.onClick;
import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Find the View that shows the Numbers Category
        TextView numbers = (TextView) findViewById(R.id.numbers);

        //Set the onclickListener on that View
        numbers.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext() ,"Open the List of Numbers" , Toast.LENGTH_LONG).show();
                Intent numberIntent = new Intent(MainActivity.this , NumbersActivity.class);
                startActivity(numberIntent);

            }
        });

        // Set the onClickListener  on the Family Category
        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Open The  Family List .." , Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this , FamilyActivity.class);
                startActivity(i);
            }
        });


        // Set The onClickListener on The Colors Category
        TextView colors = (TextView) findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(),"Open The Colors Category .. " ,Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this , ColorsActivity.class);
                startActivity(i);
            }
        });


        // Set The onClickListener on The Phrases Category
        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(),"Open The Phrases Category .. " ,Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this , PhrasesActivity.class);
                startActivity(i);
            }
        });
    }


}

