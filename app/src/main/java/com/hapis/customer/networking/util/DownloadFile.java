package com.hapis.customer.networking.util;


import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.URLFileInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;

import static com.hapis.customer.networking.util.RestConstants.TRUST_STORE_PASSWORD;

//

/**
 * Created by JKHAN
 */

public class DownloadFile {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    private static final String TAG = DownloadFile.class.getName();

    public static void downloadFileSync(String downloadUrl, final String destinationPath) throws Exception {
        try {
            // SSL Certificate creation
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream store = HapisApplication.getContext().getResources().openRawResource(R.raw.nhancenow);
            trusted.load(store, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            sslContext.init(null, new TrustManager[]{trustManager}, null);

            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.startsWith("https://") && downloadUrl.contains("nhancenow")) {
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }

            if (URLFileInfo.isValidURLFormat(downloadUrl)) {
                String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");


                Request request = new Request.Builder().url(downloadUrl).addHeader(RestConstants.JWT_HEADER, jwtHeader).build();
                client.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            response.body().close();
                            if (response != null)
                                throw new IOException("Failed to download file: " + response);
                        }
                        FileOutputStream fos = new FileOutputStream(destinationPath);
                        fos.write(response.body().bytes());
                        fos.flush();
                        fos.close();
                        response.body().close();

                    }
                });
            } else {
                LOG.d(TAG, "Invalid URL Format - " + downloadUrl);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    public static void downloadFileAsync(final String downloadUrl, final String destinationPath) throws IOException {
        if (URLFileInfo.isValidURLFormat(downloadUrl)) {
            final Callback call = new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isRedirect()) {
                        String redirectUrl = response.header("Location");
                        if(redirectUrl != null && redirectUrl.length() > 0){
                            LOG.d(TAG, "Redirect URL: " + response.code() + " - " + redirectUrl);
                            downloadFileAsync(redirectUrl, destinationPath);
                        }else{
                            if (!response.isSuccessful()) {
                                response.body().close();
                                if (response != null)
                                    throw new IOException("Failed to download file: " + response);
                            }
                            if (response.isSuccessful()) {
                                FileOutputStream fos = new FileOutputStream(destinationPath);
                                fos.write(response.body().bytes());
                                fos.flush();
                                fos.close();
                                response.body().close();
                            }
                        }
                    }else {
                        if (!response.isSuccessful()) {
                            response.body().close();
                            if (response != null)
                                throw new IOException("Failed to download file: " + response);
                        }
                        if (response.isSuccessful()) {
                            FileOutputStream fos = new FileOutputStream(destinationPath);
                            fos.write(response.body().bytes());
                            fos.flush();
                            fos.close();
                            response.body().close();
                        }
                    }
                }
            };
            post(downloadUrl, call);
        } else {
            LOG.d(TAG, "Invalid URL Format - " + downloadUrl);
        }
    }

    private static DownloadFileListener mDownloadFileListener;

    public interface DownloadFileListener {
        void onDownloadCompleted(Response response, String downloadedFilePath);

        void onDownloadFailed(IOException exception, String downloadedFilePath);

    }

    public void setOnDownloadCompleteListener(final DownloadFileListener mDownloadFileListener) {
        DownloadFile.mDownloadFileListener = mDownloadFileListener;
    }

    public void DownloadAsync(final String downloadUrl, final String destinationPath, final DownloadFileListener mDownloadFileListener) {
        String downloadURLExtension = URLFileInfo.getFileExtensionFromURL(downloadUrl);
        URLFileInfo mURLFileTypeDetector = new URLFileInfo(downloadUrl);
//        String urlFileExtensionFromSERVER = "wrongExtension";
        /*try {
            urlFileExtensionFromSERVER = mURLFileTypeDetector.detectTypeOfFile();
            LOG.d(TAG, "URL_TAG_EXTENSION :" + urlFileExtensionFromSERVER);
        } catch (DefaultExceptionHandler deh) {

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // if (downloadURLExtension.equalsIgnoreCase(urlFileExtensionFromSERVER)) {
        String downloadFileTAG = destinationPath;

        final Callback call = new Callback() {
            public void onFailure(Call call, IOException e) {
                mDownloadFileListener.onDownloadFailed(e, destinationPath);
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {

                if (response.isRedirect()) {
                    String redirectUrl = response.header("Location");
                    if(redirectUrl != null && redirectUrl.length() > 0){
                        LOG.d(TAG, "Redirect URL: " + response.code() + " - " + redirectUrl);
                        DownloadAsync(redirectUrl, destinationPath, mDownloadFileListener);
                    }else{
                        LOG.d(TAG, "" + response.code());
                        if (!response.isSuccessful()) {
                            mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                            response.body().close();
                            if (response != null)
                                throw new IOException("Failed to download file: " + response);
                        }

                        long startTime = System.currentTimeMillis();
                        ResponseBody body = response.body();
                        long contentLength = body.contentLength();
                        FileOutputStream fos = new FileOutputStream(destinationPath);
                        fos.write(response.body().bytes());
                        fos.flush();
                        fos.close();
                        response.body().close();
                        long endTime = System.currentTimeMillis();
                        LOG.d("FileLength - " + contentLength + "Took - ", (endTime - startTime) + " ms");
                        mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                    }
                }else{
                    LOG.d(TAG, "" + response.code());
                    if (!response.isSuccessful()) {
                        mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                        response.body().close();
                        if (response != null)
                            throw new IOException("Failed to download file: " + response);
                    }

                    long startTime = System.currentTimeMillis();
                    ResponseBody body = response.body();
                    long contentLength = body.contentLength();
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();
                    long endTime = System.currentTimeMillis();
                    LOG.d("FileLength - " + contentLength + "Took - ", (endTime - startTime) + " ms");
                    mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                }
            }
        };
        postAysncWithListeners(downloadUrl, call, downloadFileTAG);
       /* } else {
            Toast.makeText(HapisApplication.getContext(), "URL redirected", Toast.LENGTH_SHORT).show();
        }*/
    }

    private static BufferedSink sink = null;
    private static BufferedSource source = null;

    public static void postAysncWithListeners(String downloadUrl, Callback call, String downloadFileTAG) {
        try {
            // SSL Certificate creation
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream store = HapisApplication.getContext().getResources().openRawResource(R.raw.nhancenow);
            trusted.load(store, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.startsWith("https://") && downloadUrl.contains("nhancenow")) {
                client = new OkHttpClient().newBuilder()
                        .followRedirects(false)
                        .followSslRedirects(false)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient().newBuilder()
                        .followRedirects(false)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");

            if (URLFileInfo.isValidURLFormat(downloadUrl)) {
                Request request = new Request.Builder().url(downloadUrl).addHeader(RestConstants.JWT_HEADER, jwtHeader).tag(downloadFileTAG).build();
//                DocumentsFullViewActivity.DOWNLOAD_TAG = downloadFileTAG;
                client.newCall(request).enqueue(call);
            } else {
                LOG.d(TAG, "Invalid URL Format - " + downloadUrl);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }/* finally {
            Util.closeQuietly(sink);
            Util.closeQuietly(source);
        }*/

    }

    public static void post(String downloadUrl, Callback call) throws IOException {
        String userAgentHeader = System.getProperty("http.agent");
        try {
            // SSL Certificate creation
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream store = HapisApplication.getContext().getResources().openRawResource(R.raw.nhancenow);
            trusted.load(store, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.startsWith("https://") && downloadUrl.contains("nhancenow")) {
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");


            if (URLFileInfo.isValidURLFormat(downloadUrl)) {
                Request request = new Request.Builder().url(downloadUrl).addHeader(RestConstants.JWT_HEADER, jwtHeader).build();
                client.newCall(request).enqueue(call);
            } else {
                LOG.d(TAG, "Invalid URL Format - " + downloadUrl);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
