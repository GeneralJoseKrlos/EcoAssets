package com.ecoassets.estructura;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo del arbol de categorias
 */
public class NodoCategoria {
    private Integer id;
    private String nombre;
    private int nivel;
    private NodoCategoria padre;
    private List<NodoCategoria> hijos;

    public NodoCategoria(Integer id, String nombre, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(NodoCategoria hijo) {
        hijo.setPadre(this);
        this.hijos.add(hijo);
    }

    public boolean esHoja() {
        return this.hijos.isEmpty();
    }

    public boolean esRaiz() {
        return this.padre == null;
    }

    // Búsqueda en profundidad
    public NodoCategoria buscarPorId(Integer id) {
        if (this.id.equals(id)) {
            return this;
        }

        for (NodoCategoria hijo : hijos) {
            NodoCategoria resultado = hijo.buscarPorId(id);
            if (resultado != null) {
                return resultado;
            }
        }

        return null;
    }

    // Búsqueda por nombre
    public List<NodoCategoria> buscarPorNombre(String nombre) {
        List<NodoCategoria> resultados = new ArrayList<>();

        if (this.nombre.toLowerCase().contains(nombre.toLowerCase())) {
            resultados.add(this);
        }

        for (NodoCategoria hijo : hijos) {
            resultados.addAll(hijo.buscarPorNombre(nombre));
        }

        return resultados;
    }

    // Obtener ruta completa de categoría
    public String getRutaCompleta() {
        if (esRaiz()) {
            return nombre;
        }
        return padre.getRutaCompleta() + " > " + nombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public NodoCategoria getPadre() {
        return padre;
    }

    public void setPadre(NodoCategoria padre) {
        this.padre = padre;
    }

    public List<NodoCategoria> getHijos() {
        return hijos;
    }

    public void setHijos(List<NodoCategoria> hijos) {
        this.hijos = hijos;
    }

    @Override
    public String toString() {
        return "NodoCategoria{id=" + id + ", nombre='" + nombre + "', nivel=" + nivel + "}";
    }
}