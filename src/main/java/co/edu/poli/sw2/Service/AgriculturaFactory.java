package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Agricultura;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Piloto;
import co.edu.poli.sw2.model.Sensores;

/**
 * Fábrica concreta encargada de instanciar objetos de la clase {@link Agricultura}.
 * Implementa la interfaz {@link DronFactory}.
 * 
 * @author Jeisson Romero
 * @author Camilo
 * @version 1.0
 */
public class AgriculturaFactory implements DronFactory {

    private int id;
    private String serial;
    private String modelo;
    private String fabricante;
    private int peso;
    private Piloto piloto;
    private Sensores sensores;
    private double capacidadTanque;

    /**
     * Constructor por defecto.
     */
    public AgriculturaFactory() {
    }

    /**
     * Constructor con todos los parámetros necesarios para crear un dron agrícola.
     * 
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param piloto piloto asignado al dron
     * @param sensores sensores equipados en el dron
     * @param capacidadTanque capacidad del tanque del dron
     */
    public AgriculturaFactory(int id, String serial, String modelo, String fabricante, 
                              int peso, double capacidadTanque) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
        this.piloto = piloto;
        this.sensores = sensores;
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Instancia y devuelve un nuevo objeto de tipo {@link Agricultura}.
     * 
     * @return Instancia de {@link Agricultura} como un {@link Dron}.
     */
    @Override
    public Dron crearDron() {
    	Agricultura dron = new Agricultura(id, serial, modelo, fabricante, peso, capacidadTanque);
        return dron;
    }
}