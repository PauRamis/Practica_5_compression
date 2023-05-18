import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        //is = numero a codificar (1,1,1,1,1,1)
        //os = new ByteArrayOutputStream() numero comprimit a tornar?
        int isLenght = is.available();
        int current;
        int lastNumber = 0;
        int stack = 0;

        for (int i = 0; i < isLenght; i++) {
            current = is.read();
            if (i == 0) {
                //Si és el primer, s'afegeix a l'output i s'identifica com el lastNumber.
                lastNumber = current;
                os.write(current);
                continue;
            }

            //Si és igual a l'anterior numero, s'acumula.
            if (lastNumber == current)
                stack++;

                //Si no és igual a l'anterior
            else {
                // Si ja n'hi ha d'acumulats, s'afegeixen (amb RLE)
                if (stack != 0) {
                    os.write(lastNumber);
                    os.write(stack - 1);
                    stack = 0;
                }

                //S'afegeix el que ha romput la cadena
                os.write(current);
                lastNumber = current;
            }
        }
        if (stack != 0) {
            os.write(lastNumber);
            os.write(stack - 1);
        }
    }

    public static void decompress(InputStream is, OutputStream os) {

    }
}
