package exampledao.dao;

import exampledao.dao.common.ObjectDTO;

import java.sql.Date;

import java.util.Calendar;

public class PersonaDTO extends ObjectDTO {

    private int id;
    private String nombre;
    private Date fechaNacimiento;
    private float salario;

    public PersonaDTO() {
    }

    public PersonaDTO(int _id, String _nombre, Date _fecha, float _salario) {
        this.id = _id;
        this.nombre = _nombre;
        this.fechaNacimiento = _fecha;
        this.salario = _salario;
    }

    public Object obtenerColumna(int col) {
        switch (col) {
        case 0:
            return new Integer(this.id);
        case 1:
            return this.nombre;
        case 2:
            return this.fechaNacimiento;
        case 3:
            return new Float(this.salario);
        default:
            return null;
        }
    }

    public void setId(int newid) {
        this.id = newid;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String newnombre) {
        this.nombre = newnombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFechaNacimiento(Date newfechaNacimiento) {
        this.fechaNacimiento = newfechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public String getFechaNacimientoForTextField() {
        java.util.Calendar objCalendar = Calendar.getInstance();
        objCalendar.setTimeInMillis(fechaNacimiento.getTime());
        return 
            objCalendar.get(Calendar.YEAR) + "-" +
            objCalendar.get(Calendar.MONTH) + "-" + 
            objCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setSalario(float newsalario) {
        this.salario = newsalario;
    }

    public float getSalario() {
        return salario;
    }
}
