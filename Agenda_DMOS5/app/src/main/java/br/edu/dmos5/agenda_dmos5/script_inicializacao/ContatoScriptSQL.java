package br.edu.dmos5.agenda_dmos5.script_inicializacao;

public class ContatoScriptSQL {

    //Constantes da tabela Contato
    public static final String TABLE_CONTATO = "contato";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_CELULAR = "celular";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_USUARIO = "id_usuario";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CONTATO + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NOME + " TEXT NOT NULL,"
                    + COLUMN_ID_USUARIO + " INTEGER, "
                    + " FOREIGN KEY(" + COLUMN_ID_USUARIO + ") REFERENCES "
                    + UsuarioScriptSQL.TABLE_USUARIO + "(" + UsuarioScriptSQL.COLUMN_ID + "));";

    //ATUALIZAÇÃO DA VERSÃO 2
    public static final String RENOMEANDO_TABELA =
            " ALTER TABLE " +  TABLE_CONTATO + " RENAME TO " + TABLE_CONTATO + "_velha";

    public static final String ATUALIZANDO_CONTATOS =
            " INSERT INTO " +  TABLE_CONTATO + "(" + COLUMN_NOME + "," + COLUMN_ID_USUARIO +") " +
            " SELECT " + COLUMN_NOME + "," + COLUMN_ID_USUARIO + " FROM " + TABLE_CONTATO + "_velha " +
            " ORDER BY " + COLUMN_ID +" ASC;";

    public static final String ATUALIZANDO_TELEFONE_FIXO =
            " INSERT INTO " +  TelefoneScriptSQL.TABLE_TELEFONE + "(" + TelefoneScriptSQL.COLUMN_ID_CONTATO + "," +
            TelefoneScriptSQL.COLUMN_NUMERO + "," + TelefoneScriptSQL.COLUMN_IS_CELULAR + ") " +
            " SELECT " + COLUMN_ID + "," + COLUMN_CELULAR + ", 1 FROM " + TABLE_CONTATO + "_velha;";

    public static final String ATUALIZANDO_TELEFONE_CELULAR =
            " INSERT INTO " +  TelefoneScriptSQL.TABLE_TELEFONE + "(" + TelefoneScriptSQL.COLUMN_ID_CONTATO + "," +
                    TelefoneScriptSQL.COLUMN_NUMERO + "," + TelefoneScriptSQL.COLUMN_IS_CELULAR + ") " +
                    " SELECT " + COLUMN_ID + "," + COLUMN_TELEFONE + ", 0 FROM " + TABLE_CONTATO + "_velha;";

    public static final String EXCLUIR_TABELA_RENOMEADA = "DROP TABLE IF EXISTS "+ TABLE_CONTATO +"_velha;";
}
