/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.vibecampo.android.storage.SimpleStorage;
import com.vibecampo.android.storage.Storage;
import com.vibecampo.android.storage.util.FileCreate;
import com.vibecampo.android.storage.util.SizeUnit;
import com.vibecampo.android.utils.FileLog;
import com.vibecampo.android.utils.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import ke.co.toshngure.basecode.images.compression.FileUtil;

/**
 * Created by Anthony Ngure on 7/25/2016.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */
public class ImageCompressor {

    //max width and height values of the compressed image is taken as 612x816
    private static final float MAX_WIDTH = 612.0f;
    private static final float MAX_HEIGHT = 816.0f;
    public static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    public static final int QUALITY = 80;
    private static final double COMPRESS_THRESHOLD = 35.0;
    private String mDestinationDirectory;
    private String mFileName;
    private Storage mStorage;
    private Context mContext;
    private CompressCallBack mCompressCallBack;

    public interface CompressCallBack {
        void onFail();

        void onSuccess(Uri uri);
    }

    private static final String TAG = ImageCompressor.class.getSimpleName();

    private ImageCompressor(Context context, CompressCallBack compressCallBack) {
        this.mContext = context;
        this.mCompressCallBack = compressCallBack;
        this.mDestinationDirectory = StorageUtils.getPendingUploadDirectory();
        this.mFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        this.mStorage = SimpleStorage.getExternalStorage();
    }

    public static ImageCompressor getDefault(Context context, CompressCallBack compressCallBack) {
        return new ImageCompressor(context, compressCallBack);
    }

    public static class Builder {

        private ImageCompressor compressor;

        public Builder(Context context, CompressCallBack compressCallBack) {
            compressor = new ImageCompressor(context, compressCallBack);
        }

        public Builder setDestinationDirectory(String destinationDirectory) {
            compressor.mDestinationDirectory = destinationDirectory;
            return this;
        }

        public Builder setFileName(String fileName) {
            compressor.mFileName = fileName;
            return this;
        }

        public ImageCompressor build() {
            return compressor;
        }
    }

    public void compress(File file) {

        double fileSize = mStorage.getSize(file, SizeUnit.KB);
        FileLog.toFile(TAG, "compress, File Size = " + (fileSize));
        if (mStorage.getSize(file, SizeUnit.KB) <= COMPRESS_THRESHOLD) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(COMPRESS_FORMAT, QUALITY, out);
            FileCreate fileCreate = mStorage.createFile(mDestinationDirectory, mFileName, out.toByteArray());
            if (fileCreate.isCreated) {
                mCompressCallBack.onSuccess(Uri.fromFile(fileCreate.file));
            } else {
                mCompressCallBack.onFail();
            }
            FileLog.toFile(TAG, "compress, File will not be compressed = " + (fileSize));
            mCompressCallBack.onSuccess(Uri.fromFile(file));
            return;
        }

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(file.getPath(), options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualWidth < 0 || actualHeight < 0) {
            Bitmap bitmap2 = BitmapFactory.decodeFile(file.getPath());
            actualWidth = bitmap2.getWidth();
            actualHeight = bitmap2.getHeight();
        }

        float imgRatio = actualWidth / actualHeight;
        float maxRatio = MAX_WIDTH / MAX_HEIGHT;

        if (actualHeight > MAX_HEIGHT || actualWidth > MAX_WIDTH) {
            if (imgRatio < maxRatio) {
                imgRatio = MAX_HEIGHT / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) MAX_HEIGHT;
            } else if (imgRatio > maxRatio) {
                imgRatio = MAX_WIDTH / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) MAX_WIDTH;
            } else {
                actualHeight = (int) MAX_HEIGHT;
                actualWidth = (int) MAX_WIDTH;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(file.getPath(), options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            mCompressCallBack.onFail();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, BITMAP_CONFIG);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            mCompressCallBack.onFail();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
                new Paint(Paint.FILTER_BITMAP_FLAG));
        ExifInterface exif;
        try {
            exif = new ExifInterface(file.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                    scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            mCompressCallBack.onFail();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(COMPRESS_FORMAT, QUALITY, out);
        FileCreate fileCreate = mStorage.createFile(mDestinationDirectory, mFileName, out.toByteArray());
        if (fileCreate.isCreated) {
            mCompressCallBack.onSuccess(Uri.fromFile(fileCreate.file));
        } else {
            mCompressCallBack.onFail();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            mCompressCallBack.onFail();
        }


        //imagePath = storage.getFile(StorageUtils.getPendingUploadDirectory(), imageName).getPath();

        // Mediascanner need to scan for the image saved
        //Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //Uri fileContentUri = Uri.fromFile(imageFile);
        //mediaScannerIntent.setData(fileContentUri);
        //mContext.sendBroadcast(mediaScannerIntent);

        //BeeLog.d(TAG, "compressImage ImageName " + imagePath);

    }

    public void compress(Bitmap bitmap) {
        mStorage.deleteFile(mDestinationDirectory, StorageUtils.CACHED + ".jpg");
        FileCreate fileCreate = mStorage.createFile(mDestinationDirectory,
                StorageUtils.CACHED + ".jpg", bitmap, COMPRESS_FORMAT, 100);
        if (fileCreate.isCreated) {
            compress(fileCreate.file);
        } else {
            mCompressCallBack.onFail();
        }
    }

    public void compress(Uri imageUri) {
        try {
            compress(FileUtil.from(mContext, imageUri));
        } catch (IOException e) {
            e.printStackTrace();
            mCompressCallBack.onFail();
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }


        return inSampleSize;
    }
}
