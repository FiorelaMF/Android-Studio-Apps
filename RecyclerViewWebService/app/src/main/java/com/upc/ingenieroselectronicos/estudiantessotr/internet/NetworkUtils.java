package com.upc.ingenieroselectronicos.estudiantessotr.internet;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//TODO 24: Crear la clase para la conexión asíncrona al servidor
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildURL(String tipo){
        String miURL = tipo;
        Uri builtUri = Uri.parse(miURL).buildUpon().build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        Log.e(TAG,"URL creada "+url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else
                return null;
        }finally
        {
            urlConnection.disconnect();
        }
    }
}
