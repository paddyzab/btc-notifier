package com.paddy.btc.notifier.btc_notifier.storage;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class StorageHelper {

    private final Context context;

    public StorageHelper(final Context context) {
        this.context = context;
    }

    public void storageToInternalMemory(final String fileName, final Object object) {
        try {
            final String objectAsString = getGsonInstance().toJson(object);
            final FileOutputStream fileOutputStream = context.openFileOutput(fileName, 0);
            fileOutputStream.write(objectAsString.getBytes("UTF-8"));
            fileOutputStream.close();
        } catch (Throwable t) {

        }
    }

    public Object loadFromInternalMemory(final String fileName, Type returnType) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = context.openFileInput(fileName);

            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStreamReader.close();

            final String json = stringBuilder.toString();
            final Gson gson = new Gson();
            return gson.fromJson(json, returnType);

        } catch (FileNotFoundException e) {

        } catch (Throwable t) {

        } finally {
            close(fileInputStream);
        }

        return null;
    }

    private void close(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            //noop
        }
    }

    public Gson getGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();

        return gsonBuilder.create();
    }

}
