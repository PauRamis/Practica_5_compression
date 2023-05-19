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
                if (stack == 256) {
                    //Límit, escriure i tornar a començar
                    os.write(lastNumber);
                    os.write(255);
                    stack = 0;

                    //Canviem el lastNumber perquè no volem que continui amb l'stack,
                    //sinó que comenci de nou
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

    public static void decompress(InputStream is, OutputStream os) throws IOException {
        int isLength = is.available();
        int current;
        int lastNumber = 0;
        boolean first = true;

        for (int i = 0; i < isLength; i++) {
            current = is.read();
            os.write(current);

            //Si l'actual és igual a l'anterior, el seguent serà el comptador.
            if (lastNumber == current && !first) {
                int comptador = is.read();
                for (int j = 0; j < comptador; j++) {
                    os.write(current);
                }
                isLength -= 1;
                if (lastNumber != 0)
                    lastNumber = 0;
                else lastNumber = 1;
            } else
                lastNumber = current;
            first = false;
        }
    }
}
//TODO Find what is missing
//superior@ordsuperior:~/Documents$ hexdump im.bmp | less
//superior@ordsuperior:~/Documents$ hexdump im.bmp.decomp.rle  | less