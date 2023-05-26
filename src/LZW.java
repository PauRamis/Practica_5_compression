
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
        int  indexAnterior = 0;
        List<TableEntry> dictionary = new ArrayList<>();

        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();
            index = search(dictionary, current, indexAnterior);
            if (index == 0) {
                //Nova entrada al diccionari i ho escrivim
                dictionary.add(new TableEntry(0, current));
                os.write(indexAnterior);
                os.write(current);
                indexAnterior = 0;

            } 
            else indexAnterior = index;
                /*//Ja tenim aquest índex apuntant allà on jo vull, però hi ha un altre igual ja existent?
                //Si és així, tornem a repetir.
                int tempindex = index;
                while (tempindex != 0 && isLength > i+1) {
                    isLength--;
                    tempindex = search(dictionary, current, index - 1);
                    if (tempindex != 0) index = tempindex;
                }
                dictionary.add(new TableEntry(index, current));

               

                //Escrivim la posició de la entrada corresponent antes del seguent
                os.write(index);
                os.write(current);*/
        }

        //Si index no es 0, es que ens hem quedat de per la meitat
        if (indexAnterior != 0){
            index = findRoot(dictionary, current, index);
            os.write(index);
            os.write(current);
        }
        /*//Limit 256
            if (dictionary.size() == 256)
                dictionary = new ArrayList<>();*/
    }

    private static int findRoot(List<TableEntry> dictionary, byte current, int index) {
        for (TableEntry tableEntry : dictionary) {
            if (tableEntry.symbol == (current))
                return tableEntry.index;
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

    public static void decompress(InputStream is, OutputStream os) {
    }
}