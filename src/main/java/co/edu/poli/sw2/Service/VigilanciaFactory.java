package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Piloto;
import co.edu.poli.sw2.model.Sensores;
import co.edu.poli.sw2.model.Vigilancia;

/**
 * Fábrica concreta encargada de instanciar objetos de la clase {@link Vigilancia}.
 * Implementa la interfaz {@link DronFactory}.
 * 
 * @author Jeisson
 * @author Camilo
 * @version 1.0
 */
public class VigilanciaFactory implements DronFactory {

    private int id;
    private String serial;
    private String modelo;
    private String fabricante;
    private int peso;
    private Piloto piloto;
    private Sensores sensores;
    private boolean deteccionTermica;

    /**
     * Constructor por defecto.
     */
    public VigilanciaFactory() {
    }

    /**
     * Constructor con todos los parámetros necesarios para la posterior instanciación
     * de un dron de vigilancia.
     * 
     * @param id Identificador único del dron.
     * @param serial Número de serie del dron.
     * @param modelo Modelo del dron.
     * @param fabricante Empresa fabricante.
     * @param peso Peso total del dron.
     * @param piloto Objeto {@link Piloto} asignado al dron.
     * @param sensores Objeto {@link Sensores} equipado en el dron.
     * @param deteccionTermica Estado del sensor de detección térmica.
     */
    public VigilanciaFactory(int id, String serial, String modelo, String fabricante, 
                             int peso, Piloto piloto, Sensores sensores, boolean deteccionTermica) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
        this.piloto = piloto;
        this.sensores = sensores;
        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Instancia y devuelve un nuevo objeto de tipo {@link Vigilancia}.
     * 
     * @return Instancia concreta de {@link Vigilancia} configurada como un {@link Dron}.
     */
    @Override
    public Dron crearDron() {
        return new Vigilancia(id, serial, modelo, fabricante, peso,piloto, sensores, deteccionTermica);
    }
}