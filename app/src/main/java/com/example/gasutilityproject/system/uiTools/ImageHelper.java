package com.example.gasutilityproject.system.uiTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {

    public static String convertUriToBase64(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }
    }

    public static void loadBase64ToImageView(ImageView imageView, String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        imageView.setImageBitmap(bitmap);
    }
    public static File convertBitmapToFile(Context context, Bitmap bitmap) {
        // Create a temporary file in the cache directory
        File cacheDir = context.getCacheDir();
        File tempFile = new File(cacheDir, "temp_image.jpg");

        try {
            // Write the Bitmap data to the file
            FileOutputStream fos = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }

}
