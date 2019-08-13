package vcy.nur.ed.tp1;

import java.net.Socket;

import vcy.nur.ed.Server;

public abstract class Funcionalidad {
    protected Server elServidor;

    protected String nombre;

    /**
     * Atiende una consulta sin ningun parametro y no hace nada.
     */
    public abstract String atenderConsulta(Socket clt) ;
}