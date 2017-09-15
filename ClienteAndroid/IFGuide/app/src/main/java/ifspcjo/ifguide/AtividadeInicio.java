package ifspcjo.ifguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class AtividadeInicio extends AppCompatActivity {
    ListView lv;
    public ArrayList<String> titulos;
    public ArrayList<String> descricoes;
    public ArrayList<String> datas;
    public ArrayList<String> horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_inicio);

        Fuel.get("http://127.0.0.1:8080/eventos").responseString(new Handler<String>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                //do something when it is failure
                Log.e("erro", error.toString());
            }

            @Override
            public void success(Request request, Response response, String data) {
                Log.e("sucesso", data);
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

                lv=(ListView)findViewById(R.id.lista);
                Adaptador adaptador = new Adaptador(AtividadeInicio.this, titulos, descricoes, datas, horas);
                lv.setAdapter(adaptador);
            }
        });

    }
}

