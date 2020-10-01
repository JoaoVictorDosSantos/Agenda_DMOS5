package br.edu.dmos5.agenda_dmos5.model;

import androidx.annotation.NonNull;

public class Contato {

    private String nome;
    private String telefone;
    private String celular;
    private Usuario usuario;

    public Contato(String nome, String telefone, String celular) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
    }

    public Contato(String nome, String telefone, String celular, Usuario usuario) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
