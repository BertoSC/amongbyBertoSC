import java.util.LinkedList;
import java.util.Queue;

class Personaje implements Comparable<Personaje> {
    String alias;
    Queue<Tarea> tareas;

    public Personaje(String aliasP) {
        this.alias = aliasP;
        tareas = new LinkedList<>();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Queue<Tarea> getTareas() {
        return new LinkedList<>(tareas);
    }

    public void setTareas(Queue<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Tarea tareaActual() {
        return tareas.peek();
    }

    @Override
    public int compareTo(Personaje otroPersonaje) {
        
        return this.alias.compareTo(otroPersonaje.alias);
    }

}
