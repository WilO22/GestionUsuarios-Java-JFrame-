package com.mycompany.gestionusuarios;

/**
 * Servicio para gestionar usuarios de la farmacia.
 * Implementa un arreglo simple de 5 posiciones máximo.
 * 
 * @author Wil
 */
public class UsuarioService {

    private static final int CAPACIDAD_MAXIMA = 5;
    private String[] usuarios;
    private int contador;

    public UsuarioService() {
        this.usuarios = new String[CAPACIDAD_MAXIMA];
        this.contador = 0;
    }

    /**
     * Lista todos los usuarios registrados en el arreglo.
     * 
     * @return Arreglo con los usuarios actuales (sin posiciones vacías)
     */
    public String[] listarUsuarios() {
        String[] lista = new String[contador];
        for (int i = 0; i < contador; i++) {
            lista[i] = usuarios[i];
        }
        return lista;
    }

    /**
     * Agrega un nuevo usuario al arreglo si hay espacio disponible.
     * 
     * @param usuario Nombre del usuario a agregar
     * @return true si se agregó exitosamente, false si no hay espacio
     */
    public boolean agregarUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }
        if (contador >= CAPACIDAD_MAXIMA) {
            return false;
        }
        usuarios[contador] = usuario.trim();
        contador++;
        return true;
    }

    /**
     * Busca si un usuario existe en el arreglo.
     * 
     * @param usuario Nombre del usuario a buscar
     * @return true si existe, false si no existe
     */
    public boolean buscarUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < contador; i++) {
            if (usuarios[i].equalsIgnoreCase(usuario.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la cantidad de usuarios registrados.
     * 
     * @return Número de usuarios actuales
     */
    public int getCantidadUsuarios() {
        return contador;
    }

    /**
     * Verifica si hay espacio disponible para más usuarios.
     * 
     * @return true si hay espacio, false si está lleno
     */
    public boolean hayEspacio() {
        return contador < CAPACIDAD_MAXIMA;
    }

    /**
     * Obtiene la capacidad máxima del arreglo.
     * 
     * @return Capacidad máxima (5)
     */
    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }
}
