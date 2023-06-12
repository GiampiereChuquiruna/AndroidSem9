package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.entity.Anime;
import com.example.androidapp.services.AnimeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FuncionActivity extends AppCompatActivity {
    private EditText edtUrl, edtName, edtDescription, edtEmail;
    private Button btnDelete;
    private Button btnActualizar;
    private Button btnAtras;

    private AnimeService animeService;
    private Anime currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcion);

        edtUrl = findViewById(R.id.edtUrlImagen);
        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescripcion);
        //btnDelete = findViewById(R.id.btnDelete);
        btnActualizar = findViewById(R.id.btnActualizar);

        int id = getIntent().getIntExtra("id", 0);
        String urlImagen = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");
        String descripcion = getIntent().getStringExtra("descripcion");

        if (urlImagen != null && name != null && descripcion != null) {
            currentItem = new Anime();
            currentItem.setId(id);
            currentItem.setUrlImagen(urlImagen);
            currentItem.setName(name);
            currentItem.setDescripcion(descripcion);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://64789771362560649a2e13af.mockapi.io/") // Reemplaza con tu URL base
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        animeService = retrofit.create(AnimeService.class);

        // Mostrar los datos del elemento seleccionado en los campos de edición
        if (currentItem != null) {
            edtUrl.setText(currentItem.getUrlImagen());
            edtName.setText(currentItem.getName());
            edtDescription.setText(currentItem.getDescripcion());
        }

        // Configurar el botón de guardado
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAnime(currentItem.getId());
            }
        });
       // btnDelete.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //           deleteAnime(currentItem.getId());
            //}
        //});


    }
    private void saveChanges() {
        // Obtener los nuevos valores ingresados por el usuario
        String newUrl = edtUrl.getText().toString();
        String newName = edtName.getText().toString();
        String newDescription = edtDescription.getText().toString();

        // Actualizar los datos del elemento seleccionado
        if (currentItem != null) {
            currentItem.setUrlImagen(newUrl);
            currentItem.setName(newName);
            currentItem.setDescripcion(newDescription);

            // Llamar a la API para actualizar el elemento
            Call<Void> call = animeService.update(currentItem.getId(), currentItem);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(FuncionActivity.this, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();// Cerrar la actividad y regresar a la actividad anterior
                    } else {
                        Toast.makeText(FuncionActivity.this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(FuncionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    private void deleteAnime(int id) {
        Call<Void> call = animeService.delete(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FuncionActivity.this, "Anime eliminado exitosamente", Toast.LENGTH_SHORT).show();// Cerrar la actividad y regresar a la actividad anterior
                    Intent intent =  new Intent(getApplicationContext(), ListaActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(FuncionActivity.this, "Error al eliminar el anime", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FuncionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
