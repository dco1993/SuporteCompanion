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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public void inserir (ChamadoModel chamado){

        ContentValues values = new ContentValues();

        values.put(ChamadoSQLiteOpenHelper.COLUNA_SOLICITANTE, chamado.getSolicitante());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_INICIO, chamado.getInicio().format(formatter));
        values.put(ChamadoSQLiteOpenHelper.COLUNA_DURACAO, chamado.getDuracao());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_OBSERVACOES, chamado.getObservacoes());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_RESOLVIDO, chamado.getResolvido());

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
        chamado.setInicio(LocalDateTime.parse(cursor.getString(2), formatter));
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

            chamado.setId(cursor.getLong(0));
            chamado.setSolicitante(cursor.getString(1));
            chamado.setInicio(LocalDateTime.parse(cursor.getString(2), formatter));
            chamado.setDuracao(cursor.getInt(3));

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

    public void update (ChamadoModel chamado){

        String id = Long.toString(chamado.getId());

        ContentValues values = new ContentValues();

        values.put(ChamadoSQLiteOpenHelper.COLUNA_SOLICITANTE, chamado.getSolicitante());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_INICIO, chamado.getInicio().format(formatter));
        values.put(ChamadoSQLiteOpenHelper.COLUNA_DURACAO, chamado.getDuracao());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_OBSERVACOES, chamado.getObservacoes());
        values.put(ChamadoSQLiteOpenHelper.COLUNA_RESOLVIDO, chamado.getResolvido());

        db.update(ChamadoSQLiteOpenHelper.TABELA, values,
            ChamadoSQLiteOpenHelper.COLUNA_ID + "=" + id, null);

    }

    public void delete (long idChamado){
        String id = Long.toString(idChamado);
        long deleted = db.delete(ChamadoSQLiteOpenHelper.TABELA, ChamadoSQLiteOpenHelper.COLUNA_ID + "="
                                + id, null);
    }

}
