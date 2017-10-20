package ifspcjo.ifguide;

/**
 * Created by mashim on 03/10/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UsuarioDAO {

    private final String TABLE_CLIENTES = "Usuario";
    private DbGateway gw;

    public UsuarioDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }
    public boolean salvar(String idPer){
        ContentValues cv = new ContentValues();
        cv.put("idPer", idPer);
        return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }
    public boolean atualizar(String idPer) {
        ContentValues cv = new ContentValues();
        cv.put("idPer", idPer);
        return gw.getDatabase().update(TABLE_CLIENTES, cv, "idUsu=?", new String[]{"1"}) > 0;
    }
    public Usuario retornaUsu(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Usuario ORDER BY idUsu DESC", null);
        Usuario retorno;
        if(cursor.moveToFirst()){
            int idUsu = cursor.getInt(cursor.getColumnIndex("idUsu"));
            String idPer = cursor.getString(cursor.getColumnIndex("idPer"));
            cursor.close();
            retorno=new Usuario(idUsu,idPer);
            return retorno;
        }
        retorno=new Usuario(1,"-1");
        return retorno;

    }
    public String retornaIdPer(){
        Cursor cursor=gw.getDatabase().rawQuery("SELECT * FROM Usuario", null);
        String idPer = cursor.getString(cursor.getColumnIndex("idPers"));
        cursor.close();
        return idPer;
    }
}
