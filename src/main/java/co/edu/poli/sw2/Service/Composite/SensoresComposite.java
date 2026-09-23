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

    /**
     * Nombre del grupo de sensores.
     */
    private String nombreGrupo;

    /**
     * Lista de componentes hijos que conforman el grupo.
     */
    private List<SensoresComponent> children;

    /**
     * Crea un composite con un nombre por defecto.
     */
    public SensoresComposite() {
        this.children = new ArrayList<>();
    }

    /**
     * Crea un composite con un nombre de grupo específico.
     *
     * @param nombreGrupo nombre que identificará al grupo de sensores.
     */
    public SensoresComposite(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.children = new ArrayList<>();
    }

    /**
     * Agrega un componente hijo al grupo.
     *
     * @param component componente que se agregará al grupo.
     */
    public void add(SensoresComponent component) {
        children.add(component);
    }

    /**
     * Elimina un componente hijo del grupo.
     *
     * @param component componente que se removerá del grupo.
     */
    public void remove(SensoresComponent component) {
        children.remove(component);
    }

    /**
     * Obtiene los subcomponentes del grupo.
     *
     * @return lista de componentes hijos.
     */
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

    /**
     * Obtiene el nombre del grupo.
     *
     * @return nombre del grupo de sensores.
     */
    @Override
    public String getNombre() {
        return nombreGrupo != null ? nombreGrupo : "Grupo de Sensores";
    }

    /**
     * Asigna el nombre del grupo.
     *
     * @param nombreGrupo nuevo nombre para el grupo de sensores.
     */
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

}