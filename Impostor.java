import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Impostor extends Personaje {
    List<Estudiante> asesinados;

    Impostor(String aliasI) {
        super(aliasI);
        asesinados = new LinkedList<>();
    }

    public boolean asesinar(Estudiante estudiante) {
        Random random = new Random();
        int resultado = random.nextInt(2); // 0 para muerte, 1 para vida

        if (resultado == 0) { // Si el resultado es 0 (cara), asesina al estudiante
            asesinados.add(estudiante);
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return "Impostor [" + alias + "]";
    }
}
