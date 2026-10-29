import java.io.File;
import java.io.IOException;

public class Actividad1 {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Error, hay que insertar 3 argumentos");
            System.exit(1);
        }

        String texto = args[0];
        File ficheroEntrada = new File(args[1]);
        File ficheroSalida = new File(args[2]);

        if (!ficheroEntrada.exists()) {
            System.err.println("Error, el fichero no existe: " + ficheroEntrada.getAbsolutePath());
            System.exit(2);
        }

        ProcessBuilder pb = new ProcessBuilder("grep", texto);

        pb.redirectInput(ficheroEntrada);

        pb.redirectOutput(ficheroSalida);

        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();

            if (exitCode == 0 || exitCode == 1) {
                System.out.println("Proceso completado. Resultado guardado en: " + ficheroSalida.getAbsolutePath());
            } else {
                System.err.println("El comando grep finalizó con errores. Código de salida: " + exitCode);
            }
        } catch (IOException e) {
            System.err.println("Error, de E/S al ejecutar el proceso: " + e.getMessage());

        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}