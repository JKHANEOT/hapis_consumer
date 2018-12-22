/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.hapis.customer.ui.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import com.hapis.customer.exception.HapisException;
import com.hapis.customer.logger.LOG;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//import org.bouncycastle.util.encoders.Base64;


/**
 * The Class FileUtil.
 */
public class FileUtil {

    /**
     * The Constant logger.
     */
    private static final String TAG = FileUtil.class.getName();

    /**
     * Encode file to base64 binary string.
     *
     * @param fileName the file name
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String encodeFileToBase64BinaryString(File fileName) throws IOException {
        return new String(Base64.encode(readFile(fileName), Base64.DEFAULT));
    }

    /**
     * Encode file to base64 binary string.
     *
     * @param data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String encodeFileToBase64BinaryString(byte[] data) throws IOException {
        return data != null ? new String(Base64.encode(data, Base64.DEFAULT)) : null;
    }

    /**
     * Decode base64 binary string to bytes.
     *
     * @param encodedString the encoded string
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] decodeBase64BinaryStringToBytes(String encodedString) throws IOException {
        return Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
    }

    public static void writeToFile(byte[] fileData, String filePath) {

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(filePath);
            fos.write(fileData);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void savefile(InputStream inputStream, String destinationFilename) {
//		http://stackoverflow.com/questions/36171791/not-able-to-copy-image-using-action-get-content
        try {
            File f = new File(destinationFilename);
            f.setWritable(true, false);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            System.out.println("error in creating a file");
            e.printStackTrace();
        }
        /*String sourceFilename= sourceuri.getPath();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(sourceFilename));
			bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
			byte[] buf = new byte[1024];
			bis.read(buf);
			do_antarctida {
				bos.write(buf);
			} while(bis.read(buf) != -1);
		} catch (IOException e) {

		} finally {
			try {
				if (bis != null) bis.close();
				if (bos != null) bos.close();
			} catch (IOException e) {

			}
		}*/
    }


    /**
     * Read file.
     *
     * @param file the file
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] readFile(File file) throws IOException {
        if (file.length() > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] buffer = new byte[(int) file.length()];//new byte[1024];//
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }

    /**
     * Extract zip file from byte array.
     *
     * @param bytes   the bytes
     * @param outpath the outpath
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void extractZipFileFromByteArray(byte[] bytes, String outpath) throws IOException {
        ZipInputStream stream = new ZipInputStream(new ByteArrayInputStream(bytes));
        try {
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                String filepath = outpath + entry.getName();
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(filepath);
                    for (int c = stream.read(); c != -1; c = stream.read()) {
                        fout.write(c);
                    }
                    stream.closeEntry();
                    fout.close();
                } finally {
                    if (fout != null)
                        fout.close();
                }
            }
        } finally {
            stream.close();
        }
    }

    /**
     * Creates the zip.
     *
     * @param zipFile the zip file
     * @return the zip output stream
     */
    public static ZipOutputStream createZip(File zipFile) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return zipOut;
    }

    /**
     * Adds the file to zip.
     *
     * @param zos  the zos
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void addFileToZip(ZipOutputStream zos, File file) throws IOException {
        if (!file.canRead()) {
            LOG.d(TAG, "Cannot read file >>>>> " + file.getCanonicalPath());
            return;
        }

        zos.putNextEntry(new ZipEntry(file.getName()));

        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[(int) file.length()];
        int byteCount = 0;
        while ((byteCount = fis.read(buffer)) != -1) {
            zos.write(buffer, 0, byteCount);
        }

        fis.close();
        zos.closeEntry();
    }

    /**
     * Adds the dir to zip.
     *
     * @param zos the zos
     * @param dir the dir
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void addDirToZip(ZipOutputStream zos, File dir) throws IOException {
        if (!dir.canRead()) {
            LOG.d(TAG, "Cannot read file >>>>>> " + dir.getCanonicalPath());
            return;
        }

        File[] files = dir.listFiles();

        for (File source : files) {
            if (source.isDirectory()) {
                addDirToZip(zos, source);
            } else {
                addFileToZip(zos, source);
            }
        }
    }


    public static boolean isFileExists(String filePath) {
        File actualFile = new File(filePath);
        return actualFile != null && actualFile.exists();

    }

    /**
     * Delete file.
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (filePath != null && filePath.length() > 0) {
            File file = new File(filePath);
            if (file != null)
                deleteFile(file);
        }
    }

    /**
     * Delete file.
     *
     * @param file the file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (!file.delete()) {
                LOG.d(TAG, "Problem deleting file >>>>> " + file.getName());
            }
        } else {
            LOG.d(TAG, "File does not exist >>>>> " + file.getName());
        }
    }

    /**
     * Make directory.
     *
     * @param path the path
     * @return the file
     * @throws Exception
     */
    public static File makeDirectory(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                LOG.d(TAG, "Directory created >>>>> " + dir.getPath());
            } else {
                throw new IOException("Problem creating directory");
            }
        } else {
            LOG.d(TAG, "Directory already exist. Duplicate DKCode...");
        }
        return dir;
    }

    /**
     * Write file from encoded base64 string.
     *
     * @param encodedString the encoded string
     * @param outpath       the outpath
     */
    public static void writeFileFromEncodedBase64String(String encodedString, String outpath) {
        try {
            byte[] filedata = FileUtil.decodeBase64BinaryStringToBytes(encodedString);
            FileOutputStream fos = new FileOutputStream(outpath);
            fos.write(filedata);
            fos.close();
        } catch (IOException e) {
            LOG.d(TAG, "Error writing to file >>>>> " + outpath);
            e.printStackTrace();
        }
    }

    public static Bitmap getScaledBitmap(String filePath, int outWidth, int outHeight) throws HapisException {

        Bitmap resizedBitmap = null;

        try {

			/*InputStream in = new FileInputStream(filePath);

			// decode image size (decode metadata only, not the whole image)
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();
			in = null;*/

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            if (bitmap != null) {
                resizedBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HapisException(e.getMessage());
        }

        return resizedBitmap;
    }

    public static Bitmap getScaledBitmap(Bitmap originalBitmap, int outWidth, int outHeight) throws HapisException {

        Bitmap resizedBitmap = null;
        try {
            if (originalBitmap != null) {
                resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 150, 150, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HapisException(e.getMessage());
        }

        return resizedBitmap;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     * http://stackoverflow.com/questions/20067508/get-real-path-from-uri-android-kitkat-new-storage-access-framework/20559175#20559175
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
            // Return the remote address
            else if (isGoogleDriveDocument(uri)) {
                String mimeType = context.getContentResolver().getType(uri);
                if (null != mimeType && mimeType.length() > 0) {
                    int endIndex = mimeType.lastIndexOf("/");
                    if (endIndex != -1) {
                        mimeType = mimeType.substring(endIndex + 1, mimeType.length()); // not forgot to put check if(endIndex != -1)
                    }
                }
                String fileName = uri.getLastPathSegment();
                if (null != fileName && fileName.length() > 0) {
                    int endIndex = fileName.lastIndexOf("=");
                    if (endIndex != -1) {
                        fileName = fileName.substring(endIndex + 1, fileName.length()); // not forgot to put check if(endIndex != -1)
                    }
                }
                String filePathWithMimeType = fileName + "." + mimeType;
                return filePathWithMimeType;
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is GoogleDriveDocument.
     */
    public static boolean isGoogleDriveDocument(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getMimeType(Activity activity, Uri sourceUri) {
        String mimeType = null;

        mimeType = activity.getContentResolver().getType(sourceUri);

        return mimeType;
    }

    public static String getFileExtension(String mimeType) {

        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

        return extension;
    }

    public static boolean renameOrMoveFile(String actualFilePath, String newFilePath) {

        boolean success = false;

        File actualFile = new File(actualFilePath);
        if (actualFile != null && actualFile.exists()) {
            File newFile = new File(newFilePath);

            success = actualFile.renameTo(newFile);

            if (!success) {
                try {
                    FileUtils.copyDirectory(actualFile, newFile);
                    actualFile.delete();
                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    success = false;
                }
            }
        }

        return success;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap ImageViaAssets(Context context, String fileName) {

        AssetManager assetmanager = context.getAssets();
        InputStream is = null;
        try {

            is = assetmanager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    public static String getFileName(String trim) {
        return null;
    }

    /**
     * Method to get file size in Kb, Mb, Gb
     **/
    public static String getFileSizeInByteUnits(long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        if (size < sizeMb)
            return df.format(size / sizeKb) + " KB";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " MB";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " GB";

        return "";
    }

    public static boolean isFileSizeAcceptable(File file) {
        boolean isAcceptable = true;

        double fileLength = ((double) file.length() / (double) ApplicationConstants.FILE_SIZE_IN_MB);
        if (fileLength > ((double) ApplicationConstants.MAX_UPLOAD_FILE_SIZE / (double) ApplicationConstants.FILE_SIZE_IN_MB))
            isAcceptable = false;

        return isAcceptable;
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {

        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public static void deleteRecursive(final File fileOrDirectory, final boolean deleteFolder) {

        if (fileOrDirectory.isDirectory() && fileOrDirectory.listFiles() != null) {
            if (fileOrDirectory.listFiles().length > 0)
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child, false);

            if (!deleteFolder)
                fileOrDirectory.delete();
        }

    }
}
