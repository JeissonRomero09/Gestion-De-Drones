package co.edu.poli.sw2.Service.Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un contenedor de sensores en el patrón Composite (nodo compuesto).
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public class SensoresComposite implements SensoresComponent {

    private String nombreGrupo;
    private List<SensoresComponent> children;

    public SensoresComposite() {
        this.children = new ArrayList<>();
    }

    public SensoresComposite(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.children = new ArrayList<>();
    }

    public void add(SensoresComponent component) {
        children.add(component);
    }

    public void remove(SensoresComponent component) {
        children.remove(component);
    }

    public List<SensoresComponent> getChildren() {
        return children;
    }

    /**
     * Construye y concatena el resultado de la ejecución de todos los subcomponentes
     * para retornarlo a la vista de JavaFX.
     * 
     * @return Cadena multicapa con el informe completo del grupo de sensores.
     */
    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(getNombre()).append(" ===\n");
        for (SensoresComponent child : children) {
            sb.append("  ↳ ").append(child.execute()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getNombre() {
        return nombreGrupo != null ? nombreGrupo : "Grupo de Sensores";
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

}