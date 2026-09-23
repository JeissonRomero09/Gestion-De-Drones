package co.edu.poli.sw2.Service.Builder;

import co.edu.poli.sw2.model.Vigilancia;

/**
 * Builder concreto para crear instancias de {@link Vigilancia}.
 *
 * <p>Este constructor configura la información base del dron y la propiedad
 * específica de detección térmica del modelo de vigilancia.</p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class VigilanciaBuilder implements DronBuilder {
    /**
     * Resultado actual en proceso de construcción.
     */
    private Vigilancia result;

    /**
     * Crea un builder de vigilancia e inicializa el objeto base.
     */
    public VigilanciaBuilder() {
        this.reset();
    }

    /**
     * Reinicia la construcción del producto.
     */
    @Override
    public void reset() {
        this.result = new Vigilancia();
    }

    /**
     * Asigna los datos básicos del dron de vigilancia.
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
     * Asigna el atributo especializado del dron de vigilancia.
     */
    @Override
    public void buildAtributoEspecializado() {
        this.result.setDeteccionTermica(true);
    }

    /**
     * Devuelve el dron de vigilancia construido.
     *
     * @return instancia final de {@link Vigilancia}.
     */
    public Vigilancia getResult() {
        Vigilancia product = this.result;
        this.reset();
        return product;
    }
}