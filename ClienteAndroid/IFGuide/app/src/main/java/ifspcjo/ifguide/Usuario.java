package ifspcjo.ifguide;

/**
 * Created by mashim on 03/10/17.
 */

public class Usuario {

    private int idUsu;
    private String idPer;

    public Usuario(){

    }

    public Usuario(int idUsu,String idPer){
        this.idUsu=idUsu;
        this.idPer=idPer;
    }

    public int getIdUsu() {
        return idUsu;
    }
    public String getIdPer(){
        return idPer;
    }

    @Override
    public boolean equals(Object o){
        return this.idUsu == ((Usuario)o).idUsu;
    }
    @Override
    public int hashCode(){
        return this.idUsu;
    }


}
