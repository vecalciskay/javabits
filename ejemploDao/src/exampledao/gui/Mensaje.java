package exampledao.gui;

import exampledao.estructura.Cadena;

import java.sql.Date;

import java.util.Iterator;

public class Mensaje {
    
    
    private String texto;
    private Date momento;
       
    public Mensaje(String msg) {
        this.texto = msg;
        momento = new Date(System.currentTimeMillis());
    }

    public void setTexto(String newtexto) {
        this.texto = newtexto;
    }

    public String getTexto() {
        return texto;
    }

    public void setMomento(Date newmomento) {
        this.momento = newmomento;
    }

    public Date getMomento() {
        return momento;
    }
}
