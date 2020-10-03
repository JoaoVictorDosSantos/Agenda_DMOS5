package br.edu.dmos5.agenda_dmos5.script_inicializacao;

public class EmailScriptSQL {

    //Constantes da tabela email
    public static final String TABLE_EMAIL = "email";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_CONTATO = "id_contato";
    public static final String COLUMN_EMAIL = "email";

    //Criando a tabela de email
    public static final String  CREATE_TABLE = "CREATE TABLE " + TABLE_EMAIL + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ID_CONTATO + " INTEGER, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + " FOREIGN KEY(" + COLUMN_ID_CONTATO + ") REFERENCES "
            + ContatoScriptSQL.TABLE_CONTATO + "(" + ContatoScriptSQL.COLUMN_ID + "));";
}
