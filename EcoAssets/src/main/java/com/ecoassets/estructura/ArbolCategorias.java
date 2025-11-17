package com.ecoassets.estructura;

import java.util.ArrayList;
import java.util.List;

class ArbolCategorias {
    private NodoCategoria raiz;

    public ArbolCategorias() {
        // Inicializar con nodo raíz virtual
        this.raiz = new NodoCategoria(0, "Todas las Categorías", -1);
    }

    public void agregarCategoria(NodoCategoria categoria, Integer idPadre) {
        if (idPadre == null) {
            // Es una categoría de nivel 0
            raiz.agregarHijo(categoria);
        } else {
            // Buscar el padre y agregar
            NodoCategoria padre = buscarPorId(idPadre);
            if (padre != null) {
                padre.agregarHijo(categoria);
            }
        }
    }

    public NodoCategoria buscarPorId(Integer id) {
        return raiz.buscarPorId(id);
    }

    public List<NodoCategoria> buscarPorNombre(String nombre) {
        return raiz.buscarPorNombre(nombre);
    }

    // Obtener todas las categorías de nivel específico
    public List<NodoCategoria> obtenerPorNivel(int nivel) {
        List<NodoCategoria> resultado = new ArrayList<>();
        obtenerPorNivelRecursivo(raiz, nivel, resultado);
        return resultado;
    }

    private void obtenerPorNivelRecursivo(NodoCategoria nodo, int nivelBuscado,
            List<NodoCategoria> resultado) {
        if (nodo.getNivel() == nivelBuscado) {
            resultado.add(nodo);
        }

        for (NodoCategoria hijo : nodo.getHijos()) {
            obtenerPorNivelRecursivo(hijo, nivelBuscado, resultado);
        }
    }

    // Imprimir árbol completo (para debugging)
    public void imprimirArbol() {
        imprimirArbolRecursivo(raiz, "");
    }

    private void imprimirArbolRecursivo(NodoCategoria nodo, String prefijo) {
        System.out.println(prefijo + nodo.getNombre());
        for (NodoCategoria hijo : nodo.getHijos()) {
            imprimirArbolRecursivo(hijo, prefijo + "  ");
        }
    }

    // Obtener categorías raíz (nivel 0)
    public List<NodoCategoria> obtenerCategoriasRaiz() {
        return raiz.getHijos();
    }

    public NodoCategoria getRaiz() {
        return raiz;
    }
}