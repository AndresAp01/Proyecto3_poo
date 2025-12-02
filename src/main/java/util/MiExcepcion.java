package util;

public class MiExcepcion extends Exception {

    private int codigoError;

    public MiExcepcion(String mensaje) {
        super(mensaje);
    }

    public MiExcepcion(String mensaje, int codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    public int getCodigoError() {
        return codigoError;
    }
}
