package dco.uva.suportecompanion.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChamadoModel {

    private long id;
    private String solicitante;
    private LocalDateTime inicio;
    private int duracao;
    private String observacoes;
    private boolean resolvido;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChamadoModel(long _id, String _solicitante, LocalDateTime _inicio,
                        int _duracao, String _observacoes, boolean _resolvidos){
        this.id = _id;
        this.solicitante = _solicitante;
        this.inicio = _inicio;
        this.duracao = _duracao;
        this.observacoes = _observacoes;
        this.resolvido = _resolvidos;
    }

    public ChamadoModel(){

    }

    public void setId(long _id){
        this.id = _id;
    }

    public long getId(){
        return id;
    }

    public void setSolicitante(String _solicitante){
        this.solicitante = _solicitante;
    }

    public String getSolicitante(){
        return solicitante;
    }

    public void setInicio(LocalDateTime _inicio){
        this.inicio = _inicio;
    }

    public LocalDateTime getInicio(){
        return inicio;
    }

    public void setDuracao(int _duracao){
        this.duracao = _duracao;
    }

    public int getDuracao(){
        return duracao;
    }

    public void setObservacoes(String _observacoes){
        this.observacoes = _observacoes;
    }

    public String getObservacoes(){
        return observacoes;
    }

    public void setResolvido(boolean _resolvido){
        this.resolvido = _resolvido;
    }

    public boolean getResolvido(){
        return resolvido;
    }

    public String toString(){
        String obj;
        obj = "ID: " + Long.toString(id);
        obj += "\nSolicitante: " + solicitante;
        obj += "\nInicio: " + inicio.format(formatter);
        obj += "\nDuração: " + Integer.toString(duracao);
        obj += "\nObservações: " + observacoes;
        obj += "\nResolvido: " + resolvido;
        return obj;
    }
}
