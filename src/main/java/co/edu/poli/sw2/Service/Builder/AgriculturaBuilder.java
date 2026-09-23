package co.edu.poli.sw2.Service.Builder;

import co.edu.poli.sw2.model.Agricultura;

/**
 * Builder concreto para crear instancias de {@link Agricultura}.
 *
 * <p>Este constructor asigna los datos comunes del dron y el atributo específico
 * de capacidad de tanque del modelo agrícola.</p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class AgriculturaBuilder implements DronBuilder {
    /**
     * Resultado actual que está siendo construido.
     */
    private Agricultura result;

    /**
     * Crea un builder de agricultura e inicializa el objeto base.
     */
    public AgriculturaBuilder() {
        this.reset();
    }

    /**
     * Reinicia la construcción del producto.
     */
    @Override
    public void reset() {
        this.result = new Agricultura();
    }

    /**
     * Asigna los datos básicos del dron agrícola.
     *
     * @param id identificador del dron.
     * @param serial número de serie del dron.
     * @param modelo modelo del dron.
     * @param fabricante fabricante del dron.
     * @param peso peso del dron.
     */
    @Override
    public void buildDatosBasicos(int id, String serial, String modelo, String fabricante, int peso) {
        this.result.setId(id);
        this.result.setSerial(serial);
        this.result.setModelo(modelo);
        this.result.setFabricante(fabricante);
        this.result.setPeso(peso);
    }

    /**
     * Asigna el atributo especializado del dron agrícola.
     */
    @Override
    public void buildAtributoEspecializado() {
        this.result.setCapacidadTanque(25.0);
    }

    /**
     * Devuelve el dron agrícola construido.
     *
     * @return instancia final de {@link Agricultura}.
     */
    public Agricultura getResult() {
        Agricultura product = this.result;
        this.reset();
        return product;
    }
}