package com.example.androidapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.entity.Anime;
import com.example.androidapp.services.AnimeService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormAnime extends AppCompatActivity {
    private EditText edtNombre, edtDescription, edtEmail, edtUrl;
    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private ImageView ivAvatar;
    private String fotoEnBase64;
    private Bitmap photo;
    private String img;
    Button btnCamara, btnGaleria;

    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtUrl = findViewById(R.id.edtNumberP);
        edtNombre = findViewById(R.id.edtNameP);
        //edtDescription = findViewById(R.id.edtTipoP);
        btnSave = findViewById(R.id.btnDelete);
        btnCamara = findViewById(R.id.btnCamara);
        btnGaleria = findViewById(R.id.btnGaleria);
        ivAvatar = findViewById(R.id.imageFoto);
        List<Anime> animeList = new ArrayList<>();
        Anime anime = new Anime();

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOpenCamera();
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                else {
                    String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 2000);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";

                anime.urlImagen = img;
                anime.name = edtNombre.getText().toString();
                //anime.descripcion = edtDescription.getText().toString();




                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://64789771362560649a2e13af.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AnimeService service = retrofit.create(AnimeService.class);
                service.create(anime).enqueue(new Callback<Void>(){

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(FormAnime.this, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();// Cerrar la actividad y regresar a la actividad anterior
                        Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(photo);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit imgRetro = new Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AnimeService.ImageToSave imageToSave = new AnimeService.ImageToSave(fotoEnBase64);

            AnimeService imageService = imgRetro.create(AnimeService.class);

            Call<AnimeService.ImageResponse> imgC = imageService.saveImage(imageToSave);

            imgC.enqueue(new Callback<AnimeService.ImageResponse>() {
                @Override
                public void onResponse(Call<AnimeService.ImageResponse> call, Response<AnimeService.ImageResponse> response) {
                    if(response.isSuccessful()){
                        System.out.println(response.body().getUrl());
                        img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                        edtUrl.setText(img);
                    }
                    else
                        Log.i("MAIN_APP", "No se subiÃ³");
                }

                @Override
                public void onFailure(Call<AnimeService.ImageResponse> call, Throwable t) {

                }
            });



        }

        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivAvatar.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Retrofit imgRetro = new Retrofit.Builder()
                        .baseUrl("https://demo-upn.bit2bittest.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AnimeService.ImageToSave imageToSave = new AnimeService.ImageToSave(fotoEnBase64);

                AnimeService imageService = imgRetro.create(AnimeService.class);

                Call<AnimeService.ImageResponse> imgC = imageService.saveImage(imageToSave);

                imgC.enqueue(new Callback<AnimeService.ImageResponse>() {
                    @Override
                    public void onResponse(Call<AnimeService.ImageResponse> call, Response<AnimeService.ImageResponse> response) {
                        if(response.isSuccessful()){
                            System.out.println(response.body().getUrl());
                            img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                        }
                        else
                            System.out.println("No subio");
                    }

                    @Override
                    public void onFailure(Call<AnimeService.ImageResponse> call, Throwable t) {

                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }


    private void handleOpenCamera() {
        if(checkSelfPermission(Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED)
        {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara");
            abrirCamara();
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando");
            String[] permissions = new String[] {Manifest.permission.CAMERA};
            requestPermissions(permissions, 1000);
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }


}
