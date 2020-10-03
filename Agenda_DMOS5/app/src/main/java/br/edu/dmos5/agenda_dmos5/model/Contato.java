package br.edu.dmos5.agenda_dmos5.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Contato {

    private Long id;
    private String nome;
    private List<Telefone> telefones;
    private List<Telefone> celulares;
    private Usuario usuario;
    private List<Email> emails;

    public Contato(String nome, List<Telefone> telefones, List<Telefone> celulares, List<Email> emails) {
        this.nome = nome;
        this.telefones = telefones;
        this.celulares = celulares;
        this.emails = emails;
    }

    public Contato(String nome, List<Telefone> telefones, List<Telefone> celulares, Usuario usuario) {
        this.nome = nome;
        this.telefones = telefones;
        this.celulares = celulares;
        this.usuario = usuario;
    }

    public Contato(Long id, String nome, long idUsuario) {
        this.id = id;
        this.nome = nome;
        if(this.usuario == null){
            usuario = new Usuario();
        }
        usuario.setId(idUsuario);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Telefone> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<Telefone> celulares) {
        this.celulares = celulares;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
