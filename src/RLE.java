import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        int current;
        int lastNumber = 0;
        int stack = 0;

        for (int i = 0; i < isLength; i++) {
            current = is.read();
            if (i == 0) {
                //Si és el primer, s'afegeix a l'output i s'identifica com el lastNumber.
                lastNumber = current;
                os.write(current);
                continue;
            }

            //Si és igual a l'anterior numero, s'acumula.
            if (lastNumber == current) {
                stack++;
                if (stack == 256){
                    //Límit, escriure i tornar a començar
                    os.write(lastNumber);
                    os.write(255);
                    stack = 0;

                    //Cambiam el lastNumber perque no volem que continui amb l'stack,
                    //Sinó que començi de nou
                    if (lastNumber != 0)
                        lastNumber = 0;
                    else lastNumber = 1;
                }
            }

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
