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
    public Usuario retornaUsu(){
        Usuario Usu ;
        Cursor cursor=gw.getDatabase().rawQuery("SELECT * FROM Usuario", null);
        int idUsu = cursor.getInt(cursor.getColumnIndex("idUsu"));
        String idPer = cursor.getString(cursor.getColumnIndex("idPers"));
        Usu=new Usuario(idUsu, idPer);
        cursor.close();
        return Usu;
    }
    public String retornaIdPer(){
        Cursor cursor=gw.getDatabase().rawQuery("SELECT * FROM Usuario", null);
        String idPer = cursor.getString(cursor.getColumnIndex("idPers"));
        return idPer;
    }
}
