package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.ContatoScriptSQL;
import br.edu.dmos5.agenda_dmos5.util.UsuarioUtil;

public class ContatoDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;
    private Context context;

    public ContatoDao(Context context) {
        sqlLiteHelper = new SQLiteHelper(context);
        this.context = context;
    }

    public void add(Contato contato) {
        if (contato == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(ContatoScriptSQL.COLUMN_NOME, contato.getNome());
        valores.put(ContatoScriptSQL.COLUMN_ID_USUARIO, UsuarioUtil.getInstance().getIdUsuarioLogado());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try{
            if(sqLiteDatabase.insert(ContatoScriptSQL.TABLE_CONTATO, null, valores) == -1){
                throw new SQLException("Erro ao adicionar contato");
            }else{
                TelefoneDao telefoneDao = new TelefoneDao(context);
                telefoneDao.addList(contato.getTelefones(), sqLiteDatabase, contato.getId());
                telefoneDao.addList(contato.getCelulares(), sqLiteDatabase, contato.getId());

                EmailDao emailDao = new EmailDao(context);
                emailDao.addList(contato.getEmails(), sqLiteDatabase, contato.getId());
            }
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }finally {
            sqLiteDatabase.endTransaction();
        }
        sqLiteDatabase.close();
    }

    public List<Contato> recuperateAll(){

        List<Contato> contatos = new ArrayList<>();
        Contato contato;
        Cursor cursor;

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();


        String colunas[] = new String[]{
                ContatoScriptSQL.COLUMN_ID,
                ContatoScriptSQL.COLUMN_NOME,
                ContatoScriptSQL.COLUMN_ID_USUARIO
        };

        String sortOrder = ContatoScriptSQL.COLUMN_NOME + " ASC";

        cursor = sqLiteDatabase.query(
                ContatoScriptSQL.TABLE_CONTATO,
                colunas,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            contato = new Contato(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2)
            );
            contatos.add(contato);
        }
        cursor.close();
        sqLiteDatabase.close();
        return contatos;
    }

    public List<Contato> recuperatePorIdUsuario(Long id){

        List<Contato> contatos = new ArrayList<>();
        Contato contato;
        Cursor cursor;

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String colunas[] = new String[]{
                ContatoScriptSQL.COLUMN_ID,
                ContatoScriptSQL.COLUMN_NOME,
                ContatoScriptSQL.COLUMN_ID_USUARIO
        };

        String sortOrder = ContatoScriptSQL.COLUMN_NOME + " ASC";
        String where = ContatoScriptSQL.COLUMN_ID_USUARIO + " = ?";
        String[] argumentos = {String.valueOf(id)};

        cursor = sqLiteDatabase.query(
                ContatoScriptSQL.TABLE_CONTATO,
                colunas,
                where,
                argumentos,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            contato = new Contato(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2)
            );
            contatos.add(contato);
        }
        cursor.close();
        sqLiteDatabase.close();
        return contatos;
    }

    public void atualizaContatosExistente(){
        ContentValues valores = new ContentValues();
        valores.put(ContatoScriptSQL.COLUMN_ID_USUARIO, UsuarioUtil.getInstance().getIdUsuarioLogado());

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();
        String[] argumentos = {"1"};

        sqLiteDatabase.update(ContatoScriptSQL.TABLE_CONTATO, valores, "1 = ?", argumentos);
        sqLiteDatabase.close();
    }

    public Contato buscaPorId(Long id) {
        Contato contato = null;
        Cursor cursor;

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String colunas[] = new String[]{
                ContatoScriptSQL.COLUMN_ID,
                ContatoScriptSQL.COLUMN_NOME,
                ContatoScriptSQL.COLUMN_ID_USUARIO
        };

        String where = ContatoScriptSQL.COLUMN_ID + " = ?";
        String[] argumentos = {String.valueOf(id)};

        cursor = sqLiteDatabase.query(
                ContatoScriptSQL.TABLE_CONTATO,
                colunas,
                where,
                argumentos,
                null,
                null,
                null
        );

        while (cursor.moveToFirst()){
            contato = new Contato(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2)
            );
        }
        cursor.close();
        sqLiteDatabase.close();
        return contato;
    }

    public void atualizar(Contato contato) {
        SQLiteHelper dbHelper = new SQLiteHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (contato == null) throw new NullPointerException();
        ContentValues valores = new ContentValues();
        valores.put(ContatoScriptSQL.COLUMN_NOME, contato.getNome());

//        if (!contato.getFavorito()) {
//            valores.put(ContatoContratoDao.ContatoEntry.COLUNA_FAVORITO, 0);
//        } else {
//            valores.put(ContatoContratoDao.ContatoEntry.COLUNA_FAVORITO, 1);
//        }

        String[] argumentos = {String.valueOf(contato.getId())};
        String where = ContatoScriptSQL.COLUMN_ID + "= ?";
        db.update(ContatoScriptSQL.TABLE_CONTATO, valores, where, argumentos);
        db.close();
    }

    public void remover(Long id) {
        if (id == null) throw new NullPointerException();

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        try{
            String[] argumento = {String.valueOf(id)};
            String where = ContatoScriptSQL.COLUMN_ID + "= ?";
            sqLiteDatabase.delete(ContatoScriptSQL.TABLE_CONTATO, where, argumento);

            TelefoneDao telefoneDao = new TelefoneDao(context);
            telefoneDao.deletePorIdContato(id, sqLiteDatabase);

            EmailDao emailDao = new EmailDao(context);
            emailDao.removerPorIdContato(id,sqLiteDatabase);

            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }
}
