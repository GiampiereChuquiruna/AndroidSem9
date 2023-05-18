package com.example.appvideojuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> numbers = new ArrayList<>();
    int var ;
    Random r = new Random();
    int num1, num2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvPersonaje1 = findViewById(R.id.tvPersonaje);
        TextView tvPunta1 = findViewById(R.id.tvPunta1);
        TextView tvPersonaje2 = findViewById(R.id.tvPersonaje2);
        TextView tvPunta2 = findViewById(R.id.tvPunT2);
        TextView tvGanador = findViewById(R.id.tvGanador);
        //EditText edNum1 = findViewById(R.id.edNum1);
        //EditText edNum2 = findViewById(R.id.edNum2);
        //EditText edNum3 = findViewById(R.id.edNum3);

        Button btnPersonaje = findViewById(R.id.btnPersonaje);
        Button btnReset = findViewById(R.id.btnReset);
        //Button btnMultiplicacion = findViewById(R.id.btnReset);

        btnPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPersonaje.getText().equals("Personaje1")){
                    btnPersonaje.setText("Personaje2");
                    num1 = r.nextInt(11);
                    tvPersonaje1.setText("Personaje1");
                    tvPunta1.setText(num1 + "");
                }

                else if(btnPersonaje.getText().equals("Personaje2")){
                    num2 = r.nextInt(11);
                    tvPersonaje2.setText("Personaje2");
                    tvPunta2.setText(num2 + "");
                    if(num2 > num1){
                        tvGanador.setText("Ganador:Personaje2");
                    }
                    else if(num1 == num2){
                        tvGanador.setText("Empate");
                    }
                    else{
                        tvGanador.setText("Ganador:Personaje1");
                    }
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPersonaje.setText("Personaje1");
                tvPersonaje1.setText("-");
                tvPersonaje2.setText("-");
                tvPunta1.setText("-");
                tvPunta2.setText("-");
                tvGanador.setText("-");
            }
        });
    }
}