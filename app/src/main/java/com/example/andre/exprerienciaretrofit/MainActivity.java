package com.example.andre.exprerienciaretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.andre.exprerienciaretrofit.models.Course;
import com.example.andre.exprerienciaretrofit.models.Instructor;
import com.example.andre.exprerienciaretrofit.models.UdacityCatalog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private static final String TAG = "testRetrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UdacityService service = retrofit.create(UdacityService.class);
        Call<UdacityCatalog> requestCatalog = service.ListCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if(response.isSuccessful()){
                    Log.i("TAG", "Erro: " + response.code());
                }
                else {
                    // Requisição retornou com sucesso.

                    UdacityCatalog catalog = response.body();

                    for ( Course c : catalog.courses){
                        Log.i(TAG, String.format("%s: %s",c.title, c.subtitle));

                        for(Instructor i : c.instructors) {
                            Log.i(TAG, i.name);
                        }

                        Log.i(TAG, "----------");
                    }
                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });
    }
}
