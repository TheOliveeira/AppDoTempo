package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Pegando os componentes
        Button mButton = (Button)findViewById(R.id.button);
        EditText mEdit   = (EditText)findViewById(R.id.edittext);
        //Retorno da busca
        final JSONArray[] retornoApi = new JSONArray[1];
        //Criando Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //"Ouvindo" o botão
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //Setando a rota da api a ser buscada de forma dinâmica
                        String content = mEdit.getText().toString();
                        String apiOptions = "&appid=2f631940e4df35fe1385c0d5d2521075&lang=pt&units=metric";
                        String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + content + apiOptions;

                        //Fazendo a requisição e o retorno vindo em um JSONOBJECTREQUEST
                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.GET,
                                API_URL,
                                null,
                                new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response){
                                        Log.e("Rest Response11", response.toString());
                                        try {
                                            // Retornando resultados dos valores para a aplicação
                                            JSONObject tempResults = response.getJSONObject("main");
                                            Log.e("Temp", tempResults.getString("temp"));
                                            TextView temperaturaContainer = (TextView)findViewById(R.id.temperatura);
                                            TextView cidadeContainer = (TextView)findViewById(R.id.cidade);
                                            temperaturaContainer.setText(tempResults.getString("temp") + " Cº");
                                            cidadeContainer.setText(response.getString("name"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Error", error.toString());
                                    }
                                }
                        );
                        requestQueue.add(objectRequest);
                    }
                });
    }

}