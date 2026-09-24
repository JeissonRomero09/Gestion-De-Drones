
package co.edu.poli.sw2.Service.Facade;

import co.edu.poli.sw2.Service.Adapter.MisionAdapter;
import co.edu.poli.sw2.Service.Protorype.DronPrototype;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Mision;
import co.edu.poli.sw2.Service.Decorator.DronComponent;

public class DronFacade {

    private final DronPrototype prototypeService;

    public DronFacade(DronPrototype prototypeService) {
        this.prototypeService = prototypeService;
    }

    public String ejecutarPatrones(
            MisionAdapter adapter,
            Mision mision,
            Dron prototipoOriginal,
            DronComponent componente) {

        StringBuilder salida = new StringBuilder();

        // ADAPTER
        if (adapter != null && mision != null) {
            String json = adapter.convertir(mision);
            salida.append("[ADAPTER] ")
                  .append(json)
                  .append("\n");
        } else {
            salida.append("[ADAPTER] No ejecutado\n");
        }

        // PROTOTYPE
        if (prototipoOriginal != null && prototypeService != null) {

            Dron dronClonado =
                    prototypeService.clonarDron(prototipoOriginal);

            salida.append("[PROTOTYPE]\n");

            salida.append("Dron original: ")
                  .append(prototipoOriginal.getClass().getSimpleName())
                  .append("@")
                  .append(Integer.toHexString(
                          System.identityHashCode(prototipoOriginal))
                          .toUpperCase())
                  .append("\n");

            salida.append("Dron clonado: ")
                  .append(dronClonado.getClass().getSimpleName())
                  .append("@")
                  .append(Integer.toHexString(
                          System.identityHashCode(dronClonado))
                          .toUpperCase())
                  .append("\n");

            salida.append("¿Son el mismo objeto?: ")
                  .append(prototipoOriginal == dronClonado)
                  .append("\n");

        } else {
            salida.append("[PROTOTYPE] No ejecutado\n");
        }

        // DECORATOR
        if (componente != null) {
            salida.append("[DECORATOR] ")
                  .append(componente.descripcion())
                  .append("\n");
        } else {
            salida.append("[DECORATOR] No ejecutado\n");
        }

        return salida.toString();
    }
}

 