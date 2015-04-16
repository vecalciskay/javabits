package exampledao.dao;


public abstract class FactoryDAO {
    
    private static FactoryDAO instancia = null;
    
    public static FactoryDAO getOrCreate() {
        if (instancia == null) {
            instancia = FactoryDAOMysql.getOrCreate();
        }
        
        return instancia;
    }
    
    
    public abstract PersonaDAO newPersonaDao();
}
