package ifspcjo.ifguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.*;
import android.widget.Toast;

public class AtividadeInicio extends AppCompatActivity {
    ListView lv;
    public String idPer = null;
    public ArrayList<String> titulos;
    public ArrayList<String> descricoes;
    public ArrayList<String> datas;
    public ArrayList<String> horas;
    public ArrayList<String> cursos;
    public ArrayList<String> series;
    public ArrayList<String> ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_inicio);

        verificaPeriodo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.botao_voltar:
                // User chose the "Settings" item, show the app settings UI...
                idPer = null;
                verificaPeriodo();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void verificaPeriodo() {

        // Busca o idPer na base de dados (criar Tabela)
        // ....
        //....

        if (idPer == null) {

            Fuel.get("http://192.168.43.62:8080/periodos").responseString(new Handler<String>() {
                @Override
                public void failure(Request request, Response response, FuelError error) {
                    //do something when it is failure
                    Log.e("ERROU", error.toString());
                }

                @Override
                public void success(Request request, Response response, String data) {
                    try {
                        JSONArray jsonPeriodos = new JSONArray(data);
                        JSONObject periodo;

                        cursos = new ArrayList<String>();
                        series = new ArrayList<String>();
                        ids = new ArrayList<String>();

                        for (int i = 0; i < jsonPeriodos.length(); i++) {
                            periodo = new JSONObject(jsonPeriodos.getString(i));
                            cursos.add(periodo.getString("curso"));
                            series.add(periodo.getString("serie"));
                            ids.add(periodo.getString("id"));
                        }

                    } catch (Exception erro) {
                        Log.e("ERROR", erro.getMessage());
                    }

                    lv = (ListView) findViewById(R.id.lista);
                    AdaptadorPeriodos adaptadorPeriodos = new AdaptadorPeriodos(AtividadeInicio.this, cursos, series);
                    lv.setAdapter(adaptadorPeriodos);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            idPer = ids.get(position);
                            // Gravar no banco de dados o Selecionado.
                            // ...
                            // ...

                            baixarEventos();
                        }
                    });
                }
            });
        }else {
            baixarEventos();
        }
    }

    public void baixarEventos() {

        Fuel.get("http://192.168.43.62:8080/eventos?id_periodo="+idPer).responseString(new Handler<String>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                //do something when it is failure
                Log.e("erro", error.toString());
            }

            @Override
            public void success(Request request, Response response, String data) {
                filtro(data);
                lv=(ListView)findViewById(R.id.lista);
                AdaptadorEventos adaptadorEventos = new AdaptadorEventos(AtividadeInicio.this, titulos, descricoes, datas, horas);
                lv.setAdapter(adaptadorEventos);
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

