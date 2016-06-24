package itesm.mx.reciclamas.Values;

/**
 * Created by Cesar on 6/23/2016.
 */
public class Database {
    private Database() {} // constructor que evita la instanciacion de la clase
    // nombres de las tablas
    public static final String USUARIO = "User";
    public static final String REGISTRO = "Registro";

    /* Columnas de cada tabla */
    public static class User {
        public static final String CANTIDAD = "cantidadReciclada";
    }
    public static class Registro{
        public static final String USUARIO = "usuario";
        public static final String CANTIDAD = "cantidadRegistrada";
        public static final String EVIDENCIA = "evidencia";
        public static final String TIPO = "tipo";
    }
}
