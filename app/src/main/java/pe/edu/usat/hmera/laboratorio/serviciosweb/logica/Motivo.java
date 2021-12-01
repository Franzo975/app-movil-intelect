package pe.edu.usat.hmera.laboratorio.serviciosweb.logica;

import java.util.ArrayList;

public class Motivo {
    private int id;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static ArrayList<Motivo> listaMotivo = new ArrayList<>();
}
