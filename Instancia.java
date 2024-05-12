import java.util.Scanner;

public class Instancia {
    private static Scanner entrada;

    private Instancia() {
    }

    public static Scanner getInstancia() {
        if (entrada == null) {
            entrada = new Scanner(System.in);

        }
        return entrada;
    }

}
