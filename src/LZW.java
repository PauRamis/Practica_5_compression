
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class TableEntry {
    int index;
    byte symbol;

    public TableEntry(int index, byte symbol) {
        this.index = index;
        this.symbol = symbol;
    }
}

public class LZW {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        byte current = 0;
        int index = 0;
        int indexAnterior = 0;
        List<TableEntry> dictionary = new ArrayList<>();

        //Bucle principal
        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();

            //index = cercar una entrada igual a current en el diccionari
            index = search(dictionary, current, indexAnterior);

            //Si no la hem trobat, nova entrada al diccionari i la escrivim
            if (index == 0) {
                dictionary.add(new TableEntry(indexAnterior, current));
                os.write(indexAnterior);
                os.write(current);
                indexAnterior = 0;
            }
            //Si la trobem, la guardem i continuem
            else indexAnterior = index;

            //Limit 256
            if (dictionary.size() == 256)
                dictionary = new ArrayList<>();
        }

        //Si índex no és 0 en acabar, és que ens hem quedat de per la meitat
        if (indexAnterior != 0) {
            index = findRoot(dictionary, current, index);
            os.write(index);
            os.write(current);
        }
    }

    //Cerca quina entrada es referencia a l'index
    private static int findRoot(List<TableEntry> dictionary, byte current, int index) {
        int position = 0;
        for (TableEntry tableEntry : dictionary) {
            position++;
            if (tableEntry.symbol == (current) && tableEntry.index == index)
                //TODO No estic segur de que aquesta sigui la manera correcta de fer-ho, encara que funciona.
                return position-2;
        }
        return 0;
    }

    //Cerca una entrada del diccionari amb el symbol corresponent apuntant a un índex
    private static int search(List<TableEntry> dictionary, byte current, int index) {
        int position = 0;
        for (TableEntry tableEntry : dictionary) {
            position++;
            if (tableEntry.symbol == (current) && tableEntry.index == index)
                return position;
        }
        return 0;
    }

    public static void decompress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        byte current = 0;
        int index = 0;
        int indexAnterior = 0;

        //El primer sempre serà un index
        boolean isNumber = false;

        List<TableEntry> dictionary = new ArrayList<>();

        //Bucle principal
        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();

            //És un index o un numero?
            if (isNumber){
                index = search(dictionary, current, indexAnterior);

                //Primera vegada que surt el caracter
                if (index == 0) {
                    dictionary.add(new TableEntry(indexAnterior, current));
                    os.write(current);
                    indexAnterior = 0;
                } else {

                }
            }
            else {
                //Guardam el index que ve antes del numero
                indexAnterior = current;
            }

            //Alternam entre index y byte
            isNumber = !isNumber;
        }

    }
}