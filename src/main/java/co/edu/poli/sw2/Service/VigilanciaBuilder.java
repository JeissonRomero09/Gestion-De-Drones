package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Vigilancia;

public class VigilanciaBuilder implements DronBuilder {
    private Vigilancia result;

    public VigilanciaBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.result = new Vigilancia();
    }

    @Override
    public void buildDatosBasicos(int id, String serial, String modelo, String fabricante, int peso) {
        this.result.setId(id);
        this.result.setSerial(serial);
        this.result.setModelo(modelo);
        this.result.setFabricante(fabricante);
        this.result.setPeso(peso);
    }

    @Override
    public void buildAtributoEspecializado() {
        // Atributo específico de Vigilancia asignado por defecto en true
        this.result.setDeteccionTermica(true);
    }

    // Método crucial del diagrama para recuperar el producto finalizado (getResult())
    public Vigilancia getResult() {
        Vigilancia product = this.result;
        this.reset(); // Deja listo el builder para otra construcción
        return product;
    }
}
