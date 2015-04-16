package exampledao.dao.common;

import java.sql.*;

/**
 * Representa un objeto conexión cokpletamente abstracto. Se han incluído los campos
 * necesarios para conexiones con bases de datos típicas como Oracle, MySql, SqlServer,
 * etc.
 * Esta clase no se puede utilizar directamente, se la utiliza a través de instancias
 * de subclases de esta clase. Las instancias implementan los accesos y la forma de
 * los queries en cada caso.
 * 
 * @author Vladimir Calderón
 *
 */
public abstract class Conexion implements IConexion {

    /**
     * El host/servidor de la base de datos
     */
    protected String host;

    /**
     * El nombre de la base de datos.
     */
    protected String database;

    /**
     * El nombre de la instancia de la base de datos (caso MIcrosoft)
     */
    protected String instance;

    /**
     * El puerto de acceso a la base de datos.
     */
    protected int port;

    /**
     * El usuario con el cual se accede a la base de datos.
     */
    protected String username;

    /**
     * La contraseña utilizada
     */
    protected String password;

    /**
     * El objeto de tipo conexión que es de tipo de la interfase JDBC. Entonces,
     * cualquier implementación de Driver de Java de acceso a datos debe respetar
     * esta interfase.
     */
    protected Connection con;

    /**
     * Se maneja la conexión con el patrón de diseño Singleton. Esto para que haya 
     * solo una instancia de la conexión y no se abran innecesariamente otras.
     */
    protected static Conexion singleton;

    /**
     * Implementación del patrón singleton. Este método permit obtener o crear
     * la única instancia de tipo Conexion que está permitida existir.
     * 
     * @return Ul único objeto de tipo conexión que existe en la aplicación.
     */
    public static Conexion getOrCreate() {
        if (singleton == null) {
            singleton = ConexionMySql.getOrCreate();
        }
        singleton.conectar();
        return singleton;
    }

    /**
     * @return Returns the database.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @return Returns the instance.
     */
    public String getInstance() {
        return instance;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }
}
