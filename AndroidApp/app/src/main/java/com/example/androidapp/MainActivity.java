package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private List<Integer> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);


        Button btn1 = this.findViewById(R.id.btnMisPoke);
        Button btn2 = this.findViewById(R.id.btnRegistrarPoke);

        btn1.setOnClickListener(view -> {
            Intent intent =  new Intent(getApplicationContext(), ListaActivity.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(view -> {
            Intent intent =  new Intent(getApplicationContext(), FormAnime.class);
            startActivity(intent);
        });

    }
}