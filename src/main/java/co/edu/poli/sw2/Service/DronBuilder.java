package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Dron;

public interface DronBuilder {
    void reset();
    void buildDatosBasicos(int id, String serial, String modelo, String fabricante, int peso);
    void buildAtributoEspecializado(); // Aquí cada constructor concreto maneja su atributo único
}

