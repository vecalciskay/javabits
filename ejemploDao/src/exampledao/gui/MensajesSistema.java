package exampledao.gui;

import exampledao.estructura.Cadena;

import java.sql.Date;

import java.util.Iterator;
import java.util.Observable;

public class MensajesSistema extends Observable {

    private static MensajesSistema instancia = null;

    private Cadena<Mensaje> listaMensajes = null;

    public static MensajesSistema getOrCreate() {
        if (instancia == null)
            instancia = new MensajesSistema();
        return instancia;
    }

    private MensajesSistema() {
        listaMensajes = new Cadena<Mensaje>();
    }

    public static void nuevoMensaje(String msg) {
        MensajesSistema msjs = MensajesSistema.getOrCreate();
        msjs.nuevo(new Mensaje(msg));
    }

    public void nuevo(Mensaje objMsj) {
        this.listaMensajes.insertar(objMsj);
        
        this.setChanged();
        this.notifyObservers();
    }

    public static String getMensajes() {
        MensajesSistema msjs = MensajesSistema.getOrCreate();

        StringBuffer respuesta = new StringBuffer();

        Date hace3Min = new Date(System.currentTimeMillis() - 180000);

        Iterator<Mensaje> iter = msjs.getListaMensajes().iterator();

        while (iter.hasNext()) {
            Mensaje mensajeGuardado = iter.next();
            if (mensajeGuardado.getMomento().compareTo(hace3Min) > 0) {
                respuesta.append(mensajeGuardado.getTexto() + ".");
            }
        }

        return respuesta.toString();
    }

    public Cadena<Mensaje> getListaMensajes() {
        return listaMensajes;
    }
}
