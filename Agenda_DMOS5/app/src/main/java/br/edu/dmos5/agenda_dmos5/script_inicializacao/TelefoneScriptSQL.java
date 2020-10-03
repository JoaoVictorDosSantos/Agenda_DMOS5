package br.edu.dmos5.agenda_dmos5.script_inicializacao;

public class TelefoneScriptSQL {

    //Constantes da tabela telefone
    public static final String TABLE_TELEFONE = "telefone";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_CONTATO = "id_contato";
    public static final String COLUMN_NUMERO = "numero";
    public static final String COLUMN_IS_CELULAR = "celular";

    //Criando a tabela de telefone
    public static final String  CREATE_TABLE = "CREATE TABLE " + TABLE_TELEFONE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ID_CONTATO + " INTEGER, "
            + COLUMN_NUMERO + " TEXT NOT NULL, "
            + COLUMN_IS_CELULAR + " INTEGER NOT NULL, "
            + " FOREIGN KEY(" + COLUMN_ID_CONTATO + ") REFERENCES "
            + ContatoScriptSQL.TABLE_CONTATO + "(" + ContatoScriptSQL.COLUMN_ID + "));";
}
