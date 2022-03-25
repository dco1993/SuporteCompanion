package dco.uva.suportecompanion.data_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dco.uva.suportecompanion.model.ChamadoModel;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Chamado_DAO {

    private SQLiteDatabase db;
    DateTimeFormatter dtFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //colunas da tabela chamados
    private String[] colunas = {
            ChamadoSQLiteOpenHelper.COLUNA_ID,
            ChamadoSQLiteOpenHelper.COLUNA_SOLICITANTE,
            ChamadoSQLiteOpenHelper.COLUNA_INICIO,
            ChamadoSQLiteOpenHelper.COLUNA_DURACAO,
            ChamadoSQLiteOpenHelper.COLUNA_OBSERVACOES,
            ChamadoSQLiteOpenHelper.COLUNA_RESOLVIDO
    };

    private ChamadoSQLiteOpenHelper sqliteOpenHelper;

    public Chamado_DAO (Context context){
        sqliteOpenHelper = new ChamadoSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        db = sqliteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqliteOpenHelper.close();
    }

    public void inserir (String solicitante, LocalDateTime inicio, int duracao,
                         String observacoes, boolean resolvido){

        ContentValues values = new ContentValues();

        values.put(ChamadoSQLiteOpenHelper.COLUNA_SOLICITANTE, solicitante);
        values.put(ChamadoSQLiteOpenHelper.COLUNA_INICIO, inicio.toString());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_DURACAO, duracao);
        values.put(ChamadoSQLiteOpenHelper.COLUNA_OBSERVACOES, observacoes);
        values.put(ChamadoSQLiteOpenHelper.COLUNA_RESOLVIDO, resolvido);

        long insertId = db.insert(ChamadoSQLiteOpenHelper.TABELA, null, values);

    }

    public ChamadoModel buscar (long id){

        Cursor cursor = db.query(ChamadoSQLiteOpenHelper.TABELA, colunas,
                ChamadoSQLiteOpenHelper.COLUNA_ID + " = " + id,
                null, null, null, null);

        cursor.moveToFirst();

        ChamadoModel chamado = new ChamadoModel();

        chamado.setId(cursor.getLong(0));
        chamado.setSolicitante(cursor.getString(1));
        chamado.setInicio(LocalDateTime.parse(cursor.getString(2), dtFormato));
        chamado.setDuracao((cursor.getInt(3)));
        chamado.setObservacoes(cursor.getString(4));

        if(cursor.getInt(5) == 1){
            chamado.setResolvido(true);
        } else {
            chamado.setResolvido(false);
        }

        return chamado;
    }

    public List<ChamadoModel> getAll(){

        List<ChamadoModel> chamados = new ArrayList<ChamadoModel>();

        Cursor cursor = db.query(ChamadoSQLiteOpenHelper.TABELA, colunas, null,
                null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            ChamadoModel chamado = new ChamadoModel();

            chamado.setSolicitante(cursor.getString(1));
            chamado.setInicio(LocalDateTime.parse(cursor.getString(2), dtFormato));

            if(cursor.getInt(5) == 1){
                chamado.setResolvido(true);
            } else {
                chamado.setResolvido(false);
            }

            chamados.add(chamado);

            cursor.moveToNext();

        }

        cursor.close();

        return chamados;
    }

}
