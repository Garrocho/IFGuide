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

public class AtividadeInicio extends AppCompatActivity {
    public ArrayList<String> a = new ArrayList<String>();
    //a.Add("teste");
    ListView lv;
    String[] s = new String[] {"prova \n 31/08/2017","trabalho \n 01/09/2017"};
    //String[] s2 = new String[] {"teste\n 31/08/2017","trabalho \n 01/09/2017"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_inicio);

        lv=(ListView)findViewById(R.id.lista);
        ArrayAdapter ad=new ArrayAdapter(AtividadeInicio.this,android.R.layout.simple_expandable_list_item_1,s);
        lv.setAdapter(ad);
        //ad.add("");
        //ArrayAdapter ad2=new ArrayAdapter(AtividadeInicio.this,android.R.layout.simple_expandable_list_item_1,s2);
        //lv.setAdapter(ad2);
        //get
        Fuel.get("http://10.125.228.167:8080/periodos").responseString(new Handler<String>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                //do something when it is failure
                Log.e("erro",error.toString());
            }

            @Override
            public void success(Request request, Response response, String data) {
                Log.e("sucesso",data);
                //do something when it is successful
                //Toast.makeText(AtividadeInicio.this, data, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void addItems(String titulo ,String data) {
        String aux=titulo+"\n"+data;
        //a.Add(aux);
        ArrayAdapter ad2=new ArrayAdapter(AtividadeInicio.this,android.R.layout.simple_expandable_list_item_1,a);
        lv.setAdapter(ad2);
    }
}

