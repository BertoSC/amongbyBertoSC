import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class ComponenteMenu {
    protected ComponenteMenu padre;
    protected String nombre;
    Scanner sc;
    App app;

    protected ComponenteMenu(String nombre, Scanner sc, App app) {
        padre = null;
        this.nombre = nombre;
        this.sc = sc;
        this.app = app;
    }

    abstract void ejecutar();
}

class Menu extends ComponenteMenu {
    List<ComponenteMenu> hijos = new ArrayList<>();

    public Menu(String nombre, Scanner sc, App app) {
        super(nombre, sc, app);
    }

    void addMenu(ComponenteMenu cm) {
        this.hijos.add(cm);
        cm.padre = this;
    }

    @Override
    void ejecutar() {
        int numMenu = -1;
        while (numMenu == -1) {
            System.out.println();
            System.out.println("Menú " + this.nombre);
            System.out.println("--------------------");
            for (int i = 0; i < hijos.size(); i++) {
                System.out.println(i + ". " + hijos.get(i).nombre);
            }
            System.out.println(hijos.size() + ". Salir");
            System.out.println("Teclea un número de opción");
            String opcion = sc.nextLine();
            if (opcion.matches("\\d+")) {
                int numOpcion = Integer.parseInt(opcion);
                if (numOpcion >= 0 && numOpcion <= hijos.size()) {
                    numMenu = numOpcion;
                }
            }
        }
        if (numMenu == this.hijos.size()) {
            if (this.padre == null) {
                System.out.println();
                System.out.println("-----------------");
                System.out.println("Gracias por jugar");
                System.out.println("-----------------");
                System.exit(0);
            } else {
                this.padre.ejecutar();
            }
        } else {
            this.hijos.get(numMenu).ejecutar();
        }
    }
}

class MenuItem extends ComponenteMenu {
    public MenuItem(String nombre, Scanner sc, App app) {
        super(nombre, sc, app);
    }

    @Override
    void ejecutar() {
        switch (this.nombre) {

            case "Añadir tarea":
                app.añadirTarea();
                break;
            case "Borrar tarea":
                app.borrarTarea();
                break;
            case "Ver tareas":
                app.verTareas();
                break;
            case "Añadir jugador":
                app.añadirJugador();
                break;
            case "Borrar jugador":
                app.borrarJugador();
                break;
            case "Ver jugadores":
                app.verJugadores();
                break;
            case "Configurar tiempo de respuesta":
                app.cambiarTiempo();
                break;
            case "Jugar":
                app.partida();
                break;
        }

        System.out.println("pulsa tecla para regresar a menú anterior ");
        this.sc.nextLine();
        System.out.println("-------------------------------");
        this.padre.ejecutar();
    }
}
