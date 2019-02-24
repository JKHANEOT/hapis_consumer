package com.hapis.customer.networking;

import android.content.Context;
import android.util.Log;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.exception.HapisException;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.networking.util.RestConstants;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.models.CustomResponseModel;
import com.hapis.customer.ui.models.ErrorMessage;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.MobileDeviceInfo;
import com.hapis.customer.utils.Application;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by RAHUL on 19-09-2016.
 */
public class RestCall implements RestConstants {

    private static final String TAG = RestCall.class.getName();
    public static final MediaType JSON = MediaType.parse("application/json");

    public void post(Context mContext, boolean showProgress, String loadingMsg, String url, String json) throws IOException {
        Log.d(TAG, "URL: " + url);
        Log.d(TAG, "json: " + json);

        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();

            RequestBody body = RequestBody.create(JSON, json.getBytes());
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Integer userStatus = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
            if (!(url.contains(RestConstants.LOGIN_URL) || url.contains(RestConstants.REGISTER_USER_REQUEST_URL)) && userStatus == ApplicationConstants.USER_LOGGED_OUT) {
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .post(body)
                    .build();
            if (showProgress)
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            client.newCall(request).enqueue(callBack(mContext, showProgress));
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

    public void put(Context mContext, boolean showProgress, String loadingMsg, String url, String json) throws IOException {
        Log.d(RestCall.class.getName(), "URL: " + url);
        Log.d(RestCall.class.getName(), "json: " + json);

        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();

            RequestBody body = RequestBody.create(JSON, json.getBytes());
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Integer userStatus = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
            if (userStatus == ApplicationConstants.USER_LOGGED_OUT) {
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .put(body)
                    .build();
            if (showProgress)
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            client.newCall(request).enqueue(callBack(mContext, showProgress));
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

    public void get2(Context mContext, boolean showProgress, String loadingMsg, String url, Callback callback) throws IOException {
        Log.d(RestCall.class.getName(), "url: " + url);
        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Integer userStatus = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
            if (userStatus == ApplicationConstants.USER_LOGGED_OUT) {
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .get()
                    .build();
            if (showProgress) {
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            }
            client.newCall(request).enqueue(callback);
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

    public void get(Context mContext, boolean showProgress, String loadingMsg, String url) throws IOException {
        Log.d(RestCall.class.getName(), "url: " + url);
        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Integer userStatus = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
            if (userStatus == ApplicationConstants.USER_LOGGED_OUT) {
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .get()
                    .build();
            if (showProgress)
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            client.newCall(request).enqueue(callBack(mContext, showProgress));
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

    public void delete(Context mContext, boolean showProgress, String loadingMsg, String url) throws IOException {
        Log.d(RestCall.class.getName(), "url: " + url);
        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .delete()
                    .build();
            if (showProgress)
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            client.newCall(request).enqueue(callBack(mContext, showProgress));
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

    private Callback callBack(final Context mActivity, final boolean showProgress) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (showProgress)
                    ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                if (e != null && e instanceof ConnectException) {
                    restCallListener.onFailure(call, new IOException(HapisApplication.getApplication().getResources().getString(R.string.no_network_message)));
                } else if (e != null && e instanceof SocketException) {
                    restCallListener.onFailure(call, new IOException(HapisApplication.getApplication().getResources().getString(R.string.connection_socket_ssl_exception)));
                } else if (e != null && e instanceof SocketTimeoutException) {
                    restCallListener.onFailure(call, new IOException(HapisApplication.getApplication().getResources().getString(R.string.connection_time_out)));
                } else if (e != null && e instanceof SSLException) {
                    restCallListener.onFailure(call, new IOException(HapisApplication.getApplication().getResources().getString(R.string.connection_socket_ssl_exception)));
                } else
                    restCallListener.onFailure(call, new IOException(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                int status = -1;
                Exception exception = null;
                List<ErrorMessage> errorMessages = null;
                if (!response.isSuccessful()) {
                    String bodyErrorMsg = response.body().string();
                    if (bodyErrorMsg != null && (response.code() == 401 || response.code() == 400)) {
                        restCallListener.onResponse(Result.FAILED, "",
                                null, HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                    } else {
                        restCallListener.onResponse(Result.EXCEPTION, "", null, response.toString());
                        if (showProgress)
                            ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                        throw new IOException("Unexpected code " + response);
                    }
                } else {
                    if (response != null && response.body() != null) {
                        Integer userStatus = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
                        if (!(response.networkResponse().request().url().toString().contains(RestConstants.LOGIN_URL) || response.networkResponse().request().url().toString().contains(RestConstants.REGISTER_USER_REQUEST_URL))
                                && userStatus == ApplicationConstants.USER_LOGGED_OUT) {
                            return;
                        }
                        if (response.headers() != null && response.headers().get(RestConstants.JWT_HEADER) != null) {
                            String jwtHeader = response.headers().get(RestConstants.JWT_HEADER);
                            if (jwtHeader != null) {
                                LOG.d("RestConstants.JWT_HEADER", jwtHeader);
                                AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, jwtHeader);
                            }
                        }

                        String resStr = response.body().string();
                        Log.d(TAG, resStr);
                        try {
                            if (resStr != null && resStr.endsWith(".00") || resStr.startsWith("0")) {
                                restCallListener.onResponse(Result.SUCCESS, resStr, errorMessages, "");
                            } else {
                                CustomResponseModel responseModel = JSONAdaptor.fromJSON(resStr, CustomResponseModel.class);
                                ResponseStatus responseStatus = responseModel.getStatus();
                                if (responseStatus != null && responseStatus.getStatusCode() != null) {
                                    status = responseStatus.getStatusCode();
                                }
                                if (status != 1) {
                                    if (showProgress)
                                        ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                                    if (status == 2) {
                                        ((BaseFragmentActivity) mActivity).dismissProgressDialog();
//                                    Util.showAppForceUpdateDialog(mActivity);
                                    } else if (status == 1) {

                                        String errorMessage = responseStatus.getMessageDescription();
                                        String errorCode = responseStatus.getErrorCode();

                                        if (errorCode != null && errorMessage != null) {

                                            ErrorMessage msg = new ErrorMessage();
                                            msg.setMessageDescription(errorMessage);
                                            msg.setErrorCode(errorCode);

                                            errorMessages = new ArrayList<>();
                                            errorMessages.add(msg);

                                            exception = new HapisException(HapisException.SERVER_EXCEPTION, errorMessage);
                                            exception.printStackTrace();
                                            restCallListener.onResponse(Result.FAILED, "", errorMessages, errorMessage);
                                        } else {
                                            ErrorMessage msg = new ErrorMessage();
                                            msg.setMessageDescription(mActivity != null ? HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request) : "Something seems to have gone wrong. We appreciate your patience while we put it back together.");
                                            errorMessages = new ArrayList<>();

                                            errorMessages.add(msg);
                                            restCallListener.onResponse(Result.EXCEPTION, "", errorMessages, response.toString());
                                            if (showProgress)
                                                ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                                            throw new IOException("Unexpected code " + response);
                                        }
                                    }
                                } else if (status == 1) {
                                    if (showProgress)
                                        ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                                    restCallListener.onResponse(Result.SUCCESS, resStr, errorMessages, "");
                                } else {
                                    restCallListener.onResponse(Result.FAILED, "",
                                            errorMessages, HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                                }
                            }
                        } catch (HapisException ne) {
                            if (showProgress)
                                ((BaseFragmentActivity) mActivity).dismissProgressDialog();
                            restCallListener.onResponse(Result.EXCEPTION, "", errorMessages, ne.toString());
                        }
                    } else {
                        restCallListener.onResponse(Result.FAILED, "",
                                errorMessages, HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                    }
                }
            }
        };
    }

    /* =================================================================================================================================== */
    public static void post(String url, String json, Callback call) throws IOException {
        Log.d(TAG, "URL: " + url);
        Log.d(TAG, "json: " + json);

        String userAgentHeader = System.getProperty("http.agent");
        try {

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
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
                    new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                            .build();

            RequestBody body = RequestBody.create(JSON, json.getBytes());
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(call);
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

    public static void put(String url, String json, Callback call) throws IOException {
        Log.d(RestCall.class.getName(), "URL: " + url);
        Log.d(RestCall.class.getName(), "json: " + json);

        String userAgentHeader = System.getProperty("http.agent");
        try {

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
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
                    new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                            .build();

            RequestBody body = RequestBody.create(JSON, json.getBytes());
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .put(body)
                    .build();
            client.newCall(request).enqueue(call);
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

    public static void get(String url, Callback call) throws IOException {
        Log.d(RestCall.class.getName(), "url: " + url);
        String userAgentHeader = System.getProperty("http.agent");
        try {

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
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
                    new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                            .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .get()
                    .build();
            client.newCall(request).enqueue(call);
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

    public static void delete(String url, Callback call) throws IOException {
        Log.d(RestCall.class.getName(), "url: " + url);
        String userAgentHeader = System.getProperty("http.agent");
        try {

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
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
                    new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                            .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .delete()
                    .build();
            client.newCall(request).enqueue(call);
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

    public static void postNew(String url, String json, Callback call) throws IOException {
        String userAgentHeader = System.getProperty("http.agent");
        try {
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
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
                    new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                            .build();
            String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                    .build();
            client.newCall(request).enqueue(call);
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

    private static SSLSocketFactory getPinnedCertSslSocketFactory(Context context) {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.nhancenow);
            trusted.load(in, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            Log.e("MyApp", e.getMessage(), e);
        }
        return null;
    }

    public enum Result {
        SUCCESS, FAILED, EXCEPTION
    }

    public RestCallListener restCallListener;

    public void setOnRestCallListener(RestCallListener listener) {
        this.restCallListener = listener;
    }

    public interface RestCallListener {
        void onFailure(Call call, IOException e);

        void onResponse(Result result, String response, List<ErrorMessage> errorMessages, String msg);
    }


    public void postToOutSideWorld(Context mContext, boolean showProgress, String loadingMsg, String url, Callback callback, FormBody formBody) throws IOException {
        Log.d(TAG, "URL: " + url);
        try {


            OkHttpClient formClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            Request formRequest = new Request.Builder()
                    .url(url) // The URL to send the data to
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();
            if (showProgress)
                ((BaseFragmentActivity) mContext).showProgressDialog(mContext, loadingMsg);
            formClient.newCall(formRequest).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
