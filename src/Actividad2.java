import java.io.File;
import java.io.IOException;

public class Actividad2 {
    public static void main(String[] args) {

        ProcessBuilder pb = new ProcessBuilder("ls", "-l");

        File directorioPorDefecto = pb.directory();
        System.out.println("--- Directorio por defecto del ProcessBuilder ---");
        System.out.println(directorioPorDefecto == null ?
                "null (Utiliza el directorio de trabajo actual: " + System.getProperty("user.dir") + ")" :
                directorioPorDefecto.getAbsolutePath());
        System.out.println();

        File[] directoriosAProcesar = {
                new File(System.getProperty("user.dir")),
                new File("/tmp"),
                new File(System.getProperty("user.home"))
        };
        for (File dir : directoriosAProcesar) {
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("El directorio no existe o no es válido: " + dir.getAbsolutePath());
                continue;
            }

            System.out.println("Ejecutando comando en: " + dir.getAbsolutePath());

            pb.directory(dir);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            try {
                Process proceso = pb.start();
                proceso.waitFor();
            } catch (IOException e) {
                System.err.println("Error al iniciar el proceso en " + dir.getName() + ": " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Proceso interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.println();
        }
    }
}