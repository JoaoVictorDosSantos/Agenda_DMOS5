package br.edu.dmos5.agenda_dmos5.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    //Constantes do Banco de Dados
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "agenda.db";

    //Constantes da tabela Contato
    public static final String TABLE_CONTATO = "contato";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_CELULAR = "celular";

    //Contexto
    private Context context;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_CONTATO + " (";
        sql += COLUMN_NOME + " TEXT NOT NULL, ";
        sql += COLUMN_TELEFONE + " TEXT NOT NULL, ";
        sql += COLUMN_CELULAR + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
