package com.example.viralyapplication.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapToFile {
    private static BitmapToFile sBitmapToFile;

    public static BitmapToFile getInstance(){
        if (sBitmapToFile == null){
            sBitmapToFile = new BitmapToFile();
        }
        return sBitmapToFile;
    }

    public File bitmapToFile(Context context, Bitmap bitmap) {
        File cache = context.getExternalCacheDir();
        File shareFile = new File(cache, "myImage.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(shareFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return shareFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Uri getImageUri(Context context, Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Title", null);
        return Uri.parse(path);
    }
}
