package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ContatoDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;

    public ContatoDao(Context context) {
        sqlLiteHelper = new SQLiteHelper(context);
    }

    public void add(Contato contato) {
        if (contato == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_NOME, contato.getNome());
        valores.put(SQLiteHelper.COLUMN_TELEFONE, contato.getTelefone());
        valores.put(SQLiteHelper.COLUMN_CELULAR, contato.getCelular());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        if(sqLiteDatabase.insert(SQLiteHelper.TABLE_CONTATO, null, valores) == -1){
            throw new SQLException("Erro ao adicionar aluno");
        }
        sqLiteDatabase.close();
    }

    public List<Contato> recuperateAll(){

        List<Contato> contatos = new ArrayList<>();
        Contato contato;
        Cursor cursor;

        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();


        String columns[] = new String[]{
                SQLiteHelper.COLUMN_NOME,
                SQLiteHelper.COLUMN_TELEFONE,
                SQLiteHelper.COLUMN_CELULAR
        };

        String sortOrder = SQLiteHelper.COLUMN_NOME + " ASC";

        cursor = sqLiteDatabase.query(
                SQLiteHelper.TABLE_CONTATO,
                columns,
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
}
