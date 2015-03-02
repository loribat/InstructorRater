package com.mycompany.instructorrater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by loribatherson on 2/28/15.
 */
public class InstructorDataFetcher {

    // method getUrlBytes
    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte [] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            } // end while there are more bytes to read from the connection
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        } // end try block
    } // end method getUrlBytes

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    } // end method getUrl

    // Add method(s) to parse the JSON objects and put them in
    // an Array of Instructor Objects

} // end class com.mycompany.instructorrater.InstructorDataFetcher