package dco.uva.suportecompanion.data_management;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class ChamadoSQLiteOpenHelper extends SQLiteOpenHelper {

    //definir nome do banco
    private static final String DATABASE_NAME = "chamados.db";

    //definir nome da tabela
    public static final String TABELA = " chamados ";

    //definir as colunas da tabela "chamados"
    public static final String COLUNA_ID = " id ";
    public static final String COLUNA_SOLICITANTE = " solicitante ";
    public static final String COLUNA_INICIO = " inicio ";
    public static final String COLUNA_DURACAO = " duracao ";
    public static final String COLUNA_OBSERVACOES = " observacoes ";
    public static final String COLUNA_RESOLVIDO = " resolvido ";

    //versão do banco de dados
    private static final int DATABASE_VERSION = 1;

    //query para criar tabela
    private static final String CREATE_TABLE = " create table"
            + TABELA + "("
            + COLUNA_ID + " integer primary key autoincrement , "
            + COLUNA_SOLICITANTE + " text not null , "
            + COLUNA_INICIO + " text not null , "
            + COLUNA_DURACAO + " integer not null , "
            + COLUNA_OBSERVACOES + " text not null , "
            + COLUNA_RESOLVIDO + " integer not null ) ;";

    public ChamadoSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(" drop table if exists " + TABELA);
        onCreate(db);
    }

}
