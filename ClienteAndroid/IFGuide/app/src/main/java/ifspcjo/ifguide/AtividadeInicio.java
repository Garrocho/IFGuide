package ifspcjo.ifguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.*;
import android.view.*;
import android.app.*;

public class AtividadeInicio extends AppCompatActivity {
    ListView lv;
    public ArrayList<String> titulos;
    public ArrayList<String> descricoes;
    public ArrayList<String> datas;
    public ArrayList<String> horas;
    public EditText recebeCurso,recebeSerie;
    public Button btenviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_inicio);
        recebeSerie = (EditText) findViewById(R.id.campoSerie);
        recebeCurso = (EditText) findViewById(R.id.campoCurso);
        btenviar = (Button) findViewById(R.id.btenviar);

            Fuel.get("http://http://127.0.0.1:8080/eventos").responseString(new Handler<String>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                //do something when it is failure
                Log.e("erro", error.toString());
            }

            @Override
            public void success(Request request, Response response, String data) {
                Log.e("sucesso", data);
                filtro(data);
                lv=(ListView)findViewById(R.id.lista);
                Adaptador adaptador = new Adaptador(AtividadeInicio.this, titulos, descricoes, datas, horas);
                lv.setAdapter(adaptador);
            }
        });
        btenviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String serie = (recebeSerie.getText().toString());
                String curso = (recebeCurso.getText().toString());
                Fuel.get("http://192.168.43.62:8080/periodos?curso="+curso+"&serie="+serie).responseString(new Handler<String>() {
                    @Override
                    public void failure(Request request, Response response, FuelError error) {
                        //do something when it is failure
                        Log.e("erro", error.toString());
                    }

                    @Override
                    public void success(Request request, Response response, String data) {
                        Log.e("sucesso", data);

                        try {
                            JSONArray jsonPeriodos = new JSONArray(data);
                            JSONObject periodos = new JSONObject(jsonPeriodos.getString(1));
                            String id= periodos.getString("id");
                            Log.d("SUCESSO", String.valueOf(id));
                            Fuel.get("http://192.168.43.62:8080/eventos?id_periodo="+id).responseString(new Handler<String>() {
                                @Override
                                public void failure(Request request, Response response, FuelError error) {
                                    //do something when it is failure
                                    Log.e("erro", error.toString());
                                }

                                @Override
                                public void success(Request request, Response response, String data) {
                                    Log.e("sucesso", data);
                                    filtro(data);
                                    lv=(ListView)findViewById(R.id.lista);
                                    Adaptador adaptador = new Adaptador(AtividadeInicio.this, titulos, descricoes, datas, horas);
                                    lv.setAdapter(adaptador);
                                }
                            });
                        }catch (Exception error) {
                        }
                    }
                });
            }
        });
    }
    public void filtro(String data){
        try {
            JSONArray jsonEventos = new JSONArray(data);
            JSONObject evento;

            titulos = new ArrayList<String>();
            datas = new ArrayList<String>();
            descricoes = new ArrayList<String>();
            horas = new ArrayList<String>();

            for (int i=0; i<jsonEventos.length(); i++) {
                evento = new JSONObject(jsonEventos.getString(i));
                titulos.add(evento.getString("titulo"));
                datas.add(evento.getString("data"));
                descricoes.add(evento.getString("descricao"));
                horas.add(evento.getString("hora") + " as " + evento.getString("duracao"));

            }

        }catch (Exception erro) {
            Log.e("ERROR", erro.getMessage());
        }
    }

}

