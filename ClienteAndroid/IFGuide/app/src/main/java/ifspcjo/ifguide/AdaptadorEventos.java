package ifspcjo.ifguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorEventos extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titulos, descricoes, datas, horas;

    public AdaptadorEventos(Activity context, ArrayList<String> titulos, ArrayList<String> descricoes, ArrayList<String> datas, ArrayList<String> horas) {
        super(context, R.layout.item_lista_eventos, titulos);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.titulos=titulos;
        this.descricoes=descricoes;
        this.datas=datas;
        this.horas=horas;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View itemLista = inflater.inflate(R.layout.item_lista_eventos, null,true);

        TextView rotuloTitulo = (TextView) itemLista.findViewById(R.id.rotulo_titulo);
        TextView rotuloData = (TextView) itemLista.findViewById(R.id.rotulo_data);
        TextView rotuloDescricao = (TextView) itemLista.findViewById(R.id.rotulo_descricao);
        TextView rotuloHora = (TextView) itemLista.findViewById(R.id.rotulo_hora);

        rotuloTitulo.setText(titulos.get(position));
        rotuloData.setText(datas.get(position));
        rotuloDescricao.setText(descricoes.get(position));
        rotuloHora.setText(horas.get(position));

        return itemLista;

    };
}