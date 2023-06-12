package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidapp.adapters.AnimeAdapter;
import com.example.androidapp.entity.Anime;
import com.example.androidapp.services.AnimeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {

    public RecyclerView rvListaSimple;
    //public Button btnRM,btnVM, btnS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://64789771362560649a2e13af.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimeService service = retrofit.create(AnimeService.class);
        service.getAll().enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                List<Anime> items = response.body();
                rvListaSimple = findViewById(R.id.rvListaSimple);
                rvListaSimple.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvListaSimple.setAdapter(new AnimeAdapter(items));
                //Log.i("Main", "Response" + response.body().size());
                //Log.i("Main", new Gson().toJson(data));
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {

            }
        });

    }
}
