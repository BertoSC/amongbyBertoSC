import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import java.util.Iterator;;

public class App {
    List<Tarea> generalTar; // lista de tareas por defecto
    List<String> generalPer;// lista de personajes por defecto
    List<Estudiante> pnjPart; //lista de estudiantes creados en cada partida
    List<Impostor> impPart; //lista de impostores creados en cada partida
    List<Personaje> expulsados; //almacenamos los estudiantes expulsados
    List<Personaje> asesinados; //almacenamos los estudiantes asesinados además de la lista que tiene cada impostor
    Scanner teclado;
    Random aleatorio;
    long tiempoMax; // almacena el tiempo de respuesta por defecto y que se modifica en menú
    long tiempoPar; // variable del tiempo que se usa para cada partida individual para aplicar las penalizaciones

    public App(Scanner sc) {
        this.teclado = sc;
        aleatorio = new Random();
        llenarListaTareas();
        llenarListaNombres();
        tiempoMax = 15000; // se reinicia la variable por defecto cada vez que arrancamos la App

    }

    private void llenarListaTareas() { // creamos las tareas por defecto cada vez que arranca la App
        generalTar = new ArrayList<>();
        generalTar.add(new Tarea("Arreglar las mesas", "Comedor"));
        generalTar.add(new Tarea("Limpiar las estanterías", "Comedor"));
        generalTar.add(new Tarea("Comprobar los pc", "Aula de Informática"));
        generalTar.add(new Tarea("Cambiar los cables", "Aula de Informática"));
        generalTar.add(new Tarea("Hacer inventario", "Almacén"));
        generalTar.add(new Tarea("Revisar provisiones", "Almacén"));
        generalTar.add(new Tarea("Levantar barricadas", "Entrada"));
        generalTar.add(new Tarea("Vigilar la puerta", "Entrada"));
        generalTar.add(new Tarea("Revisar el cuadro de contadores", "Sala de Mantenimiento"));
        generalTar.add(new Tarea("Arreglar los enchufes", "Sala de Mantenimiento"));
    }

    private void llenarListaNombres() { // creamos los nombres por defecto cada vez que arranca la App
        generalPer = new ArrayList<>();
        generalPer.add("@alf");
        generalPer.add("@ber");
        generalPer.add("@car");
        generalPer.add("@fer");
        generalPer.add("@dan");
        generalPer.add("@eli");
        generalPer.add("@mar");

    }

    public void crearPersonajes(List<String> generalPer, int impostores) {
        List<String> nombresTemp = new ArrayList<>();
        nombresTemp.addAll(generalPer);
        // creamos impostores(elegimos de la lista de nombres y los eliminamos de la
        // lista para que no se repitan)
        while (impostores > 0) {
            String temp = nombresTemp.get(aleatorio.nextInt(nombresTemp.size()));
            impPart.add(new Impostor(temp));
            nombresTemp.remove(temp);
            impostores--;
        }

        // creamos estudiantes
        for (String alias : nombresTemp) {
            pnjPart.add(new Estudiante(alias));

        }

    }

    private void prepararTareas(int totalTareas) {
        // asignamos las tareas de forma aleatoria a los personajes e impostores en
        // función de un
        // parámetro
        for (Personaje personaje : pnjPart) {
            int tareasPNJ = totalTareas;
            List<Tarea> tareasTemp = new ArrayList<>();
            tareasTemp.addAll(generalTar);
            while (tareasPNJ > 0) {
                int temp = aleatorio.nextInt(tareasTemp.size());
                personaje.tareas.add(tareasTemp.get(temp));
                tareasTemp.remove(temp);
                tareasPNJ--;
            }
        }

        for (Personaje personaje : impPart) {
            int tareasPNJ = totalTareas;
            List<Tarea> tareasTemp = new ArrayList<>();
            tareasTemp.addAll(generalTar);
            while (tareasPNJ > 0) {
                int temp = aleatorio.nextInt(tareasTemp.size());
                personaje.tareas.add(tareasTemp.get(temp));
                tareasTemp.remove(temp);
                tareasPNJ--;
            }
        }

    }

    // desarrollo y estructura de la partida
    public void partida() {
        pnjPart = new ArrayList<>();
        impPart = new ArrayList<>();
        expulsados = new ArrayList<>();
        asesinados = new ArrayList<>();
        tiempoPar = tiempoMax;  //inicializamos la variable temporal con el valor que configuramos en menú o por defecto
        crearPersonajes(generalPer, 2);
        prepararTareas(5);
        int rondas = 5;
        int rondaNum = 1;
        System.out.println();
        while (rondas > 0) {
            System.out.println("RONDA " + rondaNum);
            System.out.println("----------------------------");
            System.out.println();
            comprobarAsesinato();

            if (recuento()) {
                break;
            }
            tareasRealizadas();
            System.out.println();
            votacion();
            System.out.println();
            if (recuento()) {
                break;

            }
            rondas--;
            rondaNum++;

        }
        if (pnjPart.size() > impPart.size()) {
            System.out.println("LOS ESTUDIANTES GANAN");
        } else {
            System.out.println("LOS IMPOSTORES GANAN");
        }
        imprimirResultado();
    }

    // comprobamos si hay coincidencia en la habitación de impostor/estudiante y si
    // hay asesinato
    public void comprobarAsesinato() {
        Iterator<Impostor> iteratorImpostores = impPart.iterator();
        while (iteratorImpostores.hasNext()) {
            Impostor impostor = iteratorImpostores.next();
            Tarea tareaImpostor = impostor.tareaActual();
            String habitacionImpostor = tareaImpostor.getHabitacion();

            Iterator<Estudiante> iteratorEstudiantes = pnjPart.iterator();
            while (iteratorEstudiantes.hasNext()) {
                Estudiante estudiante = iteratorEstudiantes.next();
                Tarea tareaEstudiante = estudiante.tareaActual();
                String habitacionEstudiante = tareaEstudiante.getHabitacion();

                if (habitacionImpostor.equals(habitacionEstudiante)) {
                    if (impostor.asesinar(estudiante)) {
                        System.out.println("¡OJO! ASESINATOS EN EL EDIFICIO");
                        System.out.println(
                                "El estudiante " + estudiante.alias + " ha sido asesinado en " + habitacionEstudiante);
                        asesinados.add(estudiante);
                        iteratorEstudiantes.remove();
                        System.out.println();
                    }
                }
            }
        }
    }

    // método para mostrar las tareas realizadas por cada personaje en cada ronda
    public void tareasRealizadas() {
        for (Personaje personaje : pnjPart) {
            Tarea tarea = personaje.tareas.poll();
            System.out.println(
                    personaje.alias + " ha realizado la tarea " + tarea.descripcion + " en " + tarea.habitacion);
            personaje.tareas.offer(tarea);
        }
        for (Personaje personaje : impPart) {
            Tarea tarea = personaje.tareas.poll();
            System.out.println(
                    personaje.alias + " ha realizado la tarea " + tarea.descripcion + " en " + tarea.habitacion);
            personaje.tareas.offer(tarea);
        }
    }

    // método para expulsar a un personaje del juego
    public void expulsar(String expulsado) {
        if (expulsado.equals("skip")) {
            return;
        }
        Iterator<Estudiante> iterator = pnjPart.iterator();
        while (iterator.hasNext()) {
            Personaje personaje = iterator.next();
            if (personaje.getAlias().equals(expulsado)) {
                expulsados.add(personaje);
                iterator.remove();
                return;

            }
        }

        Iterator<Impostor> iteratorImpostor = impPart.iterator();
        while (iteratorImpostor.hasNext()) {
            Personaje personaje = iteratorImpostor.next();
            if (personaje.getAlias().equals(expulsado)) {
                expulsados.add(personaje);
                iteratorImpostor.remove();
                return;
            }
        }

        System.out.println("El personaje no existe");
        return;
    }

    // método para las deliberaciones: se controla el tiempo, se penaliza (o no) y
    // se llama a expulsar de darse el caso

    public void votacion() {
        System.out.println("Escribe el nombre del jugador que quieres expulsar (o skip para seguir)");
        System.out.println("Tienes " + tiempoPar + " milisegundos para votar");
        long tiempoInicio = System.currentTimeMillis();
        String respuesta = teclado.nextLine();
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        if (tiempoTotal > tiempoPar) {
            tiempoPar = tiempoPar - (tiempoPar / 5);
            System.out.println("Te has pasado de tiempo");
        } else {
            expulsar(respuesta);

        }
    }

    // método para controlar el final de partida en función de los estudiantes e
    // impostores que estén vivos
    public Boolean recuento() {
        if (pnjPart.size() <= impPart.size()) {

            return true;
        } else if (impPart.size() == 0) {

            return true;
        }
        return false;
    }

    // método para imprimir el resultado de la partida: primero estudiantes vivos,
    // asesinados y expulsados, y luego impostores vivos, expulsados
    public void imprimirResultado() {
        if (!pnjPart.isEmpty()) {
            Collections.sort(pnjPart);
            System.out.println();
            System.out.println("ESTUDIANTES SUPERVIVIENTES");
            for (Personaje personaje : pnjPart) {
                System.out.println(personaje.alias + " es estudiante y ha sobrevivido");
            }

        }

        if (!asesinados.isEmpty()) {
            Collections.sort(asesinados);
            System.out.println();
            System.out.println("ESTUDIANTES ASESINADOS");
            for (Personaje personaje : asesinados) {
                System.out.println(personaje.alias + " era estudiante y ha muerto asesinado");
            }
        }

        if (!expulsados.isEmpty()) {
            Collections.sort(expulsados);
            System.out.println();
            System.out.println("ESTUDIANTES EXPULSADOS");
            for (Personaje personaje : expulsados) {
                if (personaje instanceof Estudiante) {
                    System.out.println(personaje.alias + " era estudiante y ha muerto por expulsión");
                }
            }
        }

        if (!impPart.isEmpty()) {
            Collections.sort(impPart);
            System.out.println();
            System.out.println("IMPOSTORES SUPERVIVIENTES");
            for (Personaje personaje : impPart) {
                System.out.println(personaje.alias + " es impostor y ha sobrevivido");
            }
        }

        if (!expulsados.isEmpty()) {
            Collections.sort(expulsados);
            System.out.println();
            System.out.println("IMPOSTORES EXPULSADOS");
            for (Personaje personaje : expulsados) {
                if (personaje instanceof Impostor) {
                    System.out.println(personaje.alias + " era impostor y ha muerto por expulsión");
                }
            }
            System.out.println();
        }

    }

    // métodos para el menú

    public void construirMenu(Scanner sc, App game) {
        MenuItem añadirTarea = new MenuItem("Añadir tarea", sc, game);
        MenuItem borrarTarea = new MenuItem("Borrar tarea", sc, game);
        MenuItem verTareas = new MenuItem("Ver tareas", sc, game);
        Menu Tareas = new Menu("Tareas", sc, game);
        Tareas.addMenu(añadirTarea);
        Tareas.addMenu(borrarTarea);
        Tareas.addMenu(verTareas);

        MenuItem añadirJug = new MenuItem("Añadir jugador", sc, game);
        MenuItem borrarJug = new MenuItem("Borrar jugador", sc, game);
        MenuItem verJug = new MenuItem("Ver jugadores", sc, game);
        Menu jugadores = new Menu("Jugadores", sc, game);
        jugadores.addMenu(añadirJug);
        jugadores.addMenu(borrarJug);
        jugadores.addMenu(verJug);
        MenuItem tiempo = new MenuItem("Configurar tiempo de respuesta", sc, game);
        MenuItem jugar = new MenuItem("Jugar", sc, game);

        Menu config = new Menu("Configuración", sc, game);
        config.addMenu(Tareas);
        config.addMenu(jugadores);
        config.addMenu(tiempo);
        Menu principal = new Menu("AmongSanCle", sc, game);
        principal.addMenu(config);
        principal.addMenu(jugar);
        principal.ejecutar();

    }

    public void añadirTarea() {
        System.out.println();
        verTareas();
        System.out.println("Introduce la descripción de la tarea");
        String descripcion = teclado.nextLine();
        System.out.println("Introduce la habitación de la tarea");
        String habitacion = teclado.nextLine();
        generalTar.add(new Tarea(descripcion, habitacion));
        System.out.println();
    }

    public void borrarTarea() {
        System.out.println();
        verTareas();
        System.out.println("Introduce la descripción de la tarea a borrar");
        String descripcion = teclado.nextLine();
        System.out.println("Introduce la habitación de la tarea a borrar");
        String habitacion = teclado.nextLine();
        generalTar.remove(new Tarea(descripcion, habitacion));
        System.out.println();
    }

    public void verTareas() {
        System.out.println();
        System.out.println("Lista de tareas disponibles");
        System.out.println("----------------------------");
        System.out.println();
        Collections.sort(generalTar);
        for (Tarea tarea : generalTar) {
            System.out.println(tarea);
        }
        System.out.println();
    }

    public void añadirJugador() {
        System.out.println();
        verJugadores();
        System.out.println("Introduce el nombre del jugador");
        String nombre = teclado.nextLine();
        if (nombre.startsWith("@") && nombre.length() == 4) {
            if (!verificarRep(nombre)) {
                generalPer.add(nombre);
            } else {
                System.out.println("El jugador ya existe");
            }
        } else {
            System.out.println("El nombre del jugador debe empezar por @ y tener una longitud de 4 caracteres");
        }
        System.out.println();
    }

    public boolean verificarRep(String nom) {
        for (String jugador : generalPer) {
            if (jugador.equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public void verJugadores() {
        System.out.println();
        System.out.println("Lista de jugadores disponibles");
        System.out.println("------------------------------");
        Collections.sort(generalPer);
        for (String jugador : generalPer) {
            System.out.println(jugador);
        }
        System.out.println();
    }

    public void borrarJugador() {
        System.out.println();
        verJugadores();
        System.out.println("Introduce el nombre del jugador a borrar");
        String nombre = teclado.nextLine();
        generalPer.remove(nombre);
        System.out.println();
    }

    public void cambiarTiempo() {
        System.out.println();
        System.out.println("Introduce el tiempo máximo de respuesta (en milisegundos): ");
        tiempoMax = teclado.nextLong();
        System.out.println();
    }

    // método main para arrancar la App
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        sc = Instancia.getInstancia();
        App game = new App(sc);

        game.construirMenu(sc, game);

    }
}
