
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LZW {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        int current;
        int last;
        List<Integer> dictionary = new ArrayList<>();

        for (int i = 0; i < isLength; i++) {
            current = is.read();
            if (!dictionary.contains(current))
                dictionary.add(current);
        }
        //os.write(current);
    }

    public static void decompress(InputStream is, OutputStream os) {

    }
}
