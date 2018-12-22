package com.hapis.customer;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.hapis.customer.crashlog.ExceptionHandler;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.ApplicationLifecycleHandler;
import com.hapis.customer.ui.utils.MobileDeviceInfo;
import com.hapis.customer.utils.Util;

import java.io.File;
import java.io.IOException;

public class HapisApplication extends Application implements ApplicationConstants {

    public static String TAG = HapisApplication.class.getName();

    public static Resources resources;
    private static int versionCode;
    private static String versionName;
    public static HapisApplication mContext;
    private int applicationType;
    private String sourceType;

    private ExceptionHandler exceptionHandler;

    @Override
//http://stackoverflow.com/questions/27497553/build-tools-21-1-2-unexpected-top-level-exception
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);
        LOG.d(TAG, "Oncreate Called.");

        // register observer
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleListener());

        int logLevel = LOG.DEBUG;
        LOG.setLogLevel(logLevel);
//        PACKAGE_NAME = getApplicationContext().getPackageName();

        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mContext = this;
        JSONAdaptor.register();

//        applicationFolderPath = mContext.getExternalFilesDir(null).getAbsolutePath();
//        appDataBackUpFolderPath = mContext.getExternalFilesDir(null).getAbsolutePath();

        exceptionHandler = new ExceptionHandler();

//        initNhanceDb();
//        createFileSystemToUse();

        // Font Helper initialization
//        TypefaceHelper.initialize(this);
        try {
            LOG.d(TAG, "Starting App");
            resources = getResources();
//            modelToTakeAction = new Hashtable<String, BaseModel>();
//            modelToTakeActionNewApis = new Hashtable<String, MessageModel>();

//            initializeBackendURL();//backendUrl = resources.getString(R.string.backend_url);

            applicationType = resources.getInteger(R.integer.application_type);
            sourceType = resources.getString(R.string.source_type);

//            createAppDataBackUpFolder();

            com.hapis.customer.utils.Application applicationObj = com.hapis.customer.utils.Application.getInstance();
            applicationObj.setVersionNumber(versionName);
            applicationObj.setApplicationType(applicationType);
            applicationObj.setSourceType(sourceType);

            MobileDeviceInfo.MobileDeviceInfoInit(this);
//            exceptionHandler.startServiceToUpload();
        } catch (Exception exception) {
            LOG.d(TAG, "================== Exception ======================= :" + exception.getLocalizedMessage());
            exception.printStackTrace();
        }
    }

    public static HapisApplication getApplication() {
        return mContext;
    }

    public static Context getContext() {
        return mContext;
    }

    private String applicationFolderPath;

    public String getAppCrashLogFolderPath(String folderName) throws IOException {
        String fileAbsolutePath = null;
        try {
            if(applicationFolderPath == null)
                applicationFolderPath = defaultLocationToStore();

            File contentFile = new File(applicationFolderPath, folderName);
            if (contentFile != null && !contentFile.exists())
                contentFile.mkdirs();

            fileAbsolutePath = contentFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "Application Crash Log Folder Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    private boolean isInternalStorageRequired = true;

    public String defaultLocationToStore() {
        String defaultLocationToStore = mContext.getExternalFilesDir(null).getAbsolutePath();

        if (isInternalStorageRequired) {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            defaultLocationToStore = directory.getAbsolutePath();
            LOG.d(TAG, "Internal Folder Path : " + defaultLocationToStore);
        } else {
            try {
                File file = null;
                file = new File(defaultLocationToStore, FOLDER_NAME);
                if (file == null || (file != null && !file.exists())) {

                    Util.createPathIfNecessary(defaultLocationToStore, FOLDER_NAME);
                    defaultLocationToStore = defaultLocationToStore + "/" + FOLDER_NAME;
                    LOG.d(TAG, "External Folder Path : " + defaultLocationToStore);
                } else {
                    defaultLocationToStore = file.getAbsolutePath();
                }

                file = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return defaultLocationToStore;
    }

    public boolean createFileSystemToUse() {
        try {
//            getStoragePath(DB_FILE);
            getStoragePath(LOG_FILE);
//            createDBFile();
//            createLogFile();
            LOG.d(TAG, "Files got created");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public String getStoragePath(String dbFile) throws IOException {
        /*if (dbFile.equals(DB_FILE))
            return getInternalStoragePath(dbFile);
        else*/
        return isInternalStorageRequired ? getInternalStoragePath(dbFile) : getExternalStoragePath(dbFile);
    }

    public String getInternalStoragePath(String filename) throws IOException {

        if (filename.equals(DB_FILE)) {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            LOG.d(TAG, "Internal Folder Path : " + directory.getAbsolutePath());
            File contentFile = new File(directory, filename);
            LOG.d(TAG, "Internal File Path : " + contentFile.getAbsolutePath());
            return contentFile.getAbsolutePath();
        } else {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            applicationFolderPath = directory.getAbsolutePath();
            LOG.d(TAG, "Internal Folder Path : " + applicationFolderPath);
            File contentFile = new File(directory, filename);
            LOG.d(TAG, "Internal File Path : " + contentFile.getAbsolutePath());
            return contentFile.getAbsolutePath();
        }
    }

    private String getExternalStoragePath(String filename) throws IOException {
        String fileAbsolutePath = null;
        try {
            File file = null;
            file = new File(mContext.getExternalFilesDir(null).getAbsolutePath(), FOLDER_NAME);
            if (file == null || (file != null && !file.exists())) {

                Util.createPathIfNecessary(applicationFolderPath, FOLDER_NAME);
                applicationFolderPath = applicationFolderPath + "/" + FOLDER_NAME;
                LOG.d(TAG, "External Folder Path : " + applicationFolderPath);

                fileAbsolutePath = createFile(filename);
                LOG.d(TAG, "Files got created");
            } else {
                applicationFolderPath = file.getAbsolutePath();
                fileAbsolutePath = createFile(filename);
            }

            file = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "External File Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    private String createFile(String filename) throws IOException {

        File contentFile = new File(applicationFolderPath + "/" + filename);
        if (!contentFile.exists()) {
            contentFile.createNewFile();
        }
        return contentFile.getAbsolutePath();
    }

    public String getBackendUrl() {
        return getResources().getString(R.string.backend_url);
    }

    /*public void initializeCrashReportsDirectory() {
        File crashReporterPath = new File(applicationFolderPath, getResources().getString(R.string.crash_reports_folder));
        if (!crashReporterPath.exists()) {
            crashReporterPath.mkdirs();
            crashReporterPath.setExecutable(true);
            crashReporterPath.setReadable(true);
            crashReporterPath.setWritable(true);
        }

        if (BuildConfig.DEBUG) {
            CrashReporter.initialize(this, crashReporterPath.getAbsolutePath());
        }
    }*/
}
