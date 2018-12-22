package com.hapis.customer.ui.utils;

import android.os.StrictMode;
import android.util.Patterns;
import android.webkit.MimeTypeMap;

import com.google.common.io.ByteStreams;
import com.hapis.customer.crashlog.DefaultExceptionHandler;
import com.hapis.customer.logger.LOG;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Rahul on 6/30/2017.
 */

public class URLFileInfo {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String mFileURL;

    public interface URLFileType {
        String MP4 = "00";
        String JPEG = "FF";
        String PNG = "89";
        String GIF = "47";
        String TIFF = "4D";
        String PDF = "25";
    }

    public static boolean isValidURLFormat(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public URLFileInfo(String fileURL) {
        this.mFileURL = fileURL;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String detectTypeOfFile(/*String fileURL*/) throws IOException, DefaultExceptionHandler {

        String fileExtn = "";

        URL url = new URL(mFileURL);
        HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
        connection.setRequestProperty("Range", "bytes=" + 0 + "-" + 0);
        connection.connect();
        byte[] bytes = ByteStreams.toByteArray(connection.getInputStream());
        System.out.println("URL: " + url.toString() + "  is of type: " + bytesToHex(bytes));
        switch (bytesToHex(bytes)) {
            case "00":
                System.out.println("URL: " + url.toString() + "  is of type: mp4");
                break;
            case "FF":
                System.out.println("URL: " + url.toString() + "  is of type: image/jpeg");
                fileExtn = "jpeg";
                break;
            case "89":
                System.out.println("URL: " + url.toString() + "  is of type: image/png");
                fileExtn = "png";
                break;
            case "47":
                System.out.println("URL: " + url.toString() + "  is of type: image/gif");
                fileExtn = "gif";
                break;
            case "25":
                System.out.println("URL: " + url.toString() + "  is of type: pdf");
                fileExtn = "pdf";
                break;
            default:
                fileExtn = "" + bytesToHex(bytes);
                break;

        }
        connection.disconnect();
        return fileExtn;
    }

    public static String getFileExtensionFromURL(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return extension;
    }

    public static String getFileTypeFromURL(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private String getSize(String url1) {
        URL url = null;
        try {
            url = new URL(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int length = connection.getContentLength();
        String size = "" + String.valueOf(length);
        return size;
    }

    private boolean doesFileExistsAtURL(String fileURL) {
        boolean fileExists;
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con = (HttpURLConnection) new URL(fileURL).openConnection();
            con.setRequestMethod("HEAD");
            if ((con.getResponseCode() == HttpURLConnection.HTTP_OK)) {
                LOG.d("FILE_EXISTS", "true");
                fileExists = true;
            } else {
                LOG.d("FILE_EXISTS", "false");
                fileExists = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.d("FILE_EXISTS", "false");
            fileExists = false;
        }
        return fileExists;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
