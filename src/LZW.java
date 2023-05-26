
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
        byte current;
        List<TableEntry> dictionary = new ArrayList<>();

        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();
            int index = contains(dictionary, current, 0);
            if (index == 0) {
                //Nova entrada al diccionari
                dictionary.add(new TableEntry(0, current));

                //Ho escribim
                os.write(0);
                os.write(current);
            } else {
                //Si es l'ultim, es processa com a unic
                if (isLength == i + 1) {
                    os.write(0);
                    os.write(current);
                } else {
                    //Escribim la posició de la entrada corresponent antes del seguent
                    // Todo: repeat till no found?
                    os.write(index);
                    current = (byte) is.read();
                    os.write(current);
                    // Afegir al diccionari el que hem trobat
                    dictionary.add(new TableEntry(index, current));
                    isLength--;
                }
            }
        }

        /*//Limit 256
            if (dictionary.size() == 256)
                dictionary = new ArrayList<>();*/
    }

    //Cerca una entrada del diccionari amb el symbol corresponent apuntant a un index
    private static int contains(List<TableEntry> dictionary, byte current, int index) {
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