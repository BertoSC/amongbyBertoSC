public class Tarea implements Comparable<Tarea> {
    String descripcion;
    String habitacion;

    public Tarea(String descripcion, String habitacion) {
        this.descripcion = descripcion;
        this.habitacion = habitacion;
    }

    @Override
    public String toString() {
        return descripcion + " en " + habitacion;
    }

    @Override
    public int compareTo(Tarea otraTarea) {
        
        int comparacionHabitacion = this.habitacion.compareTo(otraTarea.habitacion);
        if (comparacionHabitacion != 0) {
            return comparacionHabitacion;
        }
        
        return this.descripcion.compareTo(otraTarea.descripcion);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((habitacion == null) ? 0 : habitacion.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (habitacion == null) {
            if (other.habitacion != null)
                return false;
        } else if (!habitacion.equals(other.habitacion))
            return false;
        return true;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

}
