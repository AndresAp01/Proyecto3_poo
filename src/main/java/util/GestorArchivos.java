package util;

import java.io.*;

public class GestorArchivos {

    public static <T extends Serializable> void guardarObjeto(String ruta, T objeto) throws MiExcepcion {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            throw new MiExcepcion("Error al guardar el archivo: " + ruta);
        }
    }

    public static <T> T cargarObjeto(String ruta, Class<T> tipo) throws MiExcepcion {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            throw new MiExcepcion("El archivo no existe: " + ruta);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            return tipo.cast(obj);
        } catch (IOException | ClassNotFoundException e) {
            throw new MiExcepcion("Error al cargar el archivo: " + ruta);
        }
    }
}
