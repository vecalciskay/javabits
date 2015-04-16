package exampledao.dao;

import exampledao.dao.common.IDAO;

import exampledao.estructura.Cadena;

public abstract class PersonaDAO implements IDAO {
    public abstract Cadena<PersonaDTO> seleccionarTodos() throws Exception;
}
