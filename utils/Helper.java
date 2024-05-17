package tpe.utils;

public class Helper {

    public int intTaskId(String id){
        char delim = 'T';

        // Crea un array que en la posicion 0 estara el delimitador y en la posicion 1 la parte numerica del ID
        String[] partsOfId = id.split(String.valueOf(delim));

        return Integer.parseInt(partsOfId[1]);
    }
}
