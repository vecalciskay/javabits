package exampledao.dao;


public class FactoryDAOMysql extends FactoryDAO {
    
    private static FactoryDAOMysql instancia = null;
    
    public static FactoryDAOMysql getOrCreate() {
        if (instancia == null) {
            instancia = new FactoryDAOMysql();
        }
        
        return instancia;
    }
    
    private FactoryDAOMysql() {
    }
    
    public PersonaDAO newPersonaDao() {
        return new PersonaDAOMysql();
    }
}
