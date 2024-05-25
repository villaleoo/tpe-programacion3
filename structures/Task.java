package tpe.structures;

public class Task implements Comparable<Task>{
    private String id_tarea;
    private String nombre_tarea;
    private float tiempo_ejecucion;
    private boolean esCritica;
    private int nivel_prioridad;


    public Task(String id_tarea, String nombre_tarea, float tiempo_ejecucion, boolean esCritica, int nivel_prioridad){
        this.id_tarea=id_tarea;
        this.nombre_tarea=nombre_tarea;
        this.tiempo_ejecucion=tiempo_ejecucion;
        this.esCritica=esCritica;
        this.nivel_prioridad=nivel_prioridad;

    }

    public String getId_tarea() {
        return id_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public boolean isEsCritica() {
        return esCritica;
    }

    public float getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public int getNivel_prioridad() {
        return nivel_prioridad;
    }

    @Override
    public int compareTo(Task t) {                 //valor positivo, la instancia es mayor ; valor negativo, el parametro es mayor, valor 0 son iguales
        char delim = 'T';

        // Crea un array que en la posicion 0 estara el delimitador y en la posicion 1 la parte numerica del ID
        String[] partsOfThis = this.getId_tarea().split(String.valueOf(delim));
        String[] partsOfEntry = t.getId_tarea().split(String.valueOf(delim));

        int idThis= Integer.parseInt(partsOfThis[1]);
        int idEntry= Integer.parseInt(partsOfEntry[1]);

        return idThis - idEntry;
    }

    @Override
    public String toString() {
        return "\nid_tarea: "+id_tarea+";\nnombre_tarea: "+nombre_tarea+";\ntiempo_ejecucion: "+tiempo_ejecucion+";\nes_critica: "+esCritica+";\nnivel_prioridad: "+nivel_prioridad+";\n";
    }



}
