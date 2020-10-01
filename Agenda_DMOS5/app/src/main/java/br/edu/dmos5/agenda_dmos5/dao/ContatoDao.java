package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.ContatoScriptSQL;
import br.edu.dmos5.agenda_dmos5.util.UsuarioUtil;

public class ContatoDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;

    public ContatoDao(Context context) {
        sqlLiteHelper = new SQLiteHelper(context);
    }

    public void add(Contato contato) {
        if (contato == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(ContatoScriptSQL.COLUMN_NOME, contato.getNome());
        valores.put(ContatoScriptSQL.COLUMN_TELEFONE, contato.getTelefone());
        valores.put(ContatoScriptSQL.COLUMN_CELULAR, contato.getCelular());
        valores.put(ContatoScriptSQL.COLUMN_ID_USUARIO, UsuarioUtil.getInstance().getIdUsuarioLogado());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        if(sqLiteDatabase.insert(ContatoScriptSQL.TABLE_CONTATO, null, valores) == -1){
            throw new SQLException("Erro ao adicionar contato");
        }
        sqLiteDatabase.close();
    }

    public List<Contato> recuperateAll(){

        List<Contato> contatos = new ArrayList<>();
        Contato contato;
        Cursor cursor;

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();


        String colunas[] = new String[]{
                ContatoScriptSQL.COLUMN_NOME,
                ContatoScriptSQL.COLUMN_TELEFONE,
                ContatoScriptSQL.COLUMN_CELULAR,
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
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
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
                ContatoScriptSQL.COLUMN_NOME,
                ContatoScriptSQL.COLUMN_TELEFONE,
                ContatoScriptSQL.COLUMN_CELULAR,
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
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
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
}
