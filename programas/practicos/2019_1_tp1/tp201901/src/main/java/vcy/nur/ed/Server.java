package vcy.nur.ed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class Server 
{
    private static final Logger logger = LogManager.getLogger(Server.class);
    /**
     *
     */

    private static final String VCY_NUR_ED_TP1_ORDENAR_SIMPLE = "vcy.nur.ed.tp1.OrdenarSimple";

    public static void main(String[] args) {
        Server srv = new Server();
        logger.info("Server creado");
        srv.adicionarOperacion(VCY_NUR_ED_TP1_ORDENAR_SIMPLE);

        srv.ejecutar();
    }

    private void ejecutar() {
    }

    private void adicionarOperacion(String metodo) {
        
    }
}
