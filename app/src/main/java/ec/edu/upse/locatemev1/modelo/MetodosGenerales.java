package ec.edu.upse.locatemev1.modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Gitwym on 11/10/2017.
 */

public class MetodosGenerales {

    public static String cryptMD5(String texto)
    {
        try
        {
            final char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

            MessageDigest msgdgt = MessageDigest.getInstance("MD5");
            byte[] bytes = msgdgt.digest(texto.getBytes());
            StringBuilder strCryptMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++)
            {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                strCryptMD5.append(HEXADECIMALES[high]);
                strCryptMD5.append(HEXADECIMALES[low]);
            }
            return strCryptMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
