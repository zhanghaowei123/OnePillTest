package com.onepilltest.URL;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ConUtil {
    public static String getInputStream(HttpURLConnection connection) {
        String returnString = "";
        try {
            InputStream is = connection.getInputStream();
            byte[] temp = new byte[255];
            int len;
            StringBuffer sb = new StringBuffer();
            while ((len = is.read(temp)) != -1) {
                sb.append(new String(temp, 0, len));
            }
            returnString = new String(sb);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static void setOutputStream(HttpURLConnection connection,String writer){
        try {
            OutputStream os = connection.getOutputStream();
            os.write(writer.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

