
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class LZW {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        byte current;
        byte[] dictionary = new byte[256];

        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();
            int found = contains(current, dictionary);
            if (found == 0){
                //Nova entrada al diccionari
                dictionary[i] = current;
                os.write(0);
                os.write(current);
            } else {
                //Si es l'ultim, es processa com a unic
                if (isLength == i+1){
                    os.write(0);
                    os.write(current);
                } else {
                //Escribim la posició de la entrada corresponent antes del seguent
                os.write(found);
                current = (byte) is.read();
                os.write(current);
                dictionary[i] = (byte) (dictionary[found-1]+current);
                isLength--;
                }
            }
        }

        /*//Limit 256
            if (dictionary.size() == 256)
                dictionary = new ArrayList<>();*/
    }

    private static int contains(byte current, byte[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i] == current)
                return i+1;
        }
        return 0;
    }

    public static void decompress(InputStream is, OutputStream os) {

    }
}
 /*class arIndex {
     public arIndex() {
     }

 }*/