package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Agricultura;

public class AgriculturaBuilder implements DronBuilder {
    private Agricultura result;

    public AgriculturaBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.result = new Agricultura();
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
        // Atributo específico de Agricultura asignado por defecto
        this.result.setCapacidadTanque(25.0);
    }

    // Método crucial del diagrama para recuperar el producto finalizado (getResult())
    public Agricultura getResult() {
        Agricultura product = this.result;
        this.reset(); // Deja listo el builder para otra construcción
        return product;
    }
}
