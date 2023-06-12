package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.entity.Anime;
import com.example.androidapp.entity.Comentario;
import com.example.androidapp.services.AnimeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComentarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
/*
        EditText edtComentario = findViewById(R.id.edtComentario);
        Button btnSaveC = findViewById(R.id.btnSaveC);

        List<Comentario> cometarioList = new ArrayList<>();
        Comentario anime = new Comentario();

        btnSaveC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";

                anime.coment = edtComentario.getText().toString();
                //anime.name = edtNombre.getText().toString();
                //anime.descripcion = edtDescription.getText().toString();




                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://647aba4dd2e5b6101db0815f.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AnimeService service = retrofit.create(AnimeService.class);
                service.create(anime).enqueue(new Callback<Void>(){

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ComentarioActivity.this, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();// Cerrar la actividad y regresar a la actividad anterior
                        Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    */}
}
