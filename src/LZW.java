
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LZW {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        byte current;
        byte[] dictionary = new byte[256];

        for (int i = 0; i < isLength; i++) {
            current = (byte) is.read();
            if (!contains(current, dictionary)){
                dictionary[i] = current;
                os.write(0);
                os.write(current);
            } else {
                System.out.println("aaa");
            }
        }

        /*//Limit 256
            if (dictionary.size() == 256)
                dictionary = new ArrayList<>();
        //os.write(dictionary);*/
    }

    private static boolean contains(byte current, byte[] dictionary) {
        for (byte b : dictionary) {
            if (b == current)
                return true;
        }
        return false;
    }

    public static void decompress(InputStream is, OutputStream os) {

    }
}
