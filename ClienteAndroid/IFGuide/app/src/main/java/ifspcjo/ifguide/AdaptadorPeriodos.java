package ifspcjo.ifguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPeriodos extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> cursos, series;

    public AdaptadorPeriodos(Activity context, ArrayList<String> cursos, ArrayList<String> series) {
        super(context, R.layout.item_lista_periodos, cursos);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.cursos=cursos;
        this.series=series;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View itemLista = inflater.inflate(R.layout.item_lista_periodos, null,true);

        TextView rotuloCurso = (TextView) itemLista.findViewById(R.id.rotulo_curso);
        TextView rotuloSerie = (TextView) itemLista.findViewById(R.id.rotulo_serie);

        rotuloCurso.setText("Curso: " + cursos.get(position));
        rotuloSerie.setText("Serie: " + series.get(position));

        return itemLista;

    };
}