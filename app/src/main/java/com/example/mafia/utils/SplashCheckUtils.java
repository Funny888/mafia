package com.example.mafia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.mafia.utils.Logger.PTAG;

public class SplashCheckUtils {
    private static final String TAG = "SplashCheckUtils";
    public static final String CHECK_WIFI = "WIFI";
    public static final String CHECK_RESOURCES = "RESOURCES";

    public static final String DIRECTORY = Environment.getExternalStorageDirectory() + File.separator + "mafia_sourse";
    private Context mContext;
    private static final String STORAGE_PATH = "gs://mafia-7b2ee.appspot.com/";
    private FirebaseStorage storage = FirebaseStorage.getInstance(STORAGE_PATH);
    private StorageReference reference = storage.getReference();

    public SplashCheckUtils(Context context) {
        mContext = context;
    }

    private LinkedHashMap<String, Callable<Boolean>> mCallables = new LinkedHashMap<>();

    public Callable<Boolean> checkWIFI = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info.isConnected();
        }
    };


    public Callable<Boolean> downloadResource = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            AtomicBoolean flag = new AtomicBoolean(false);
            AtomicBoolean finalResult = new AtomicBoolean(false);
            Task<ListResult> task = reference.listAll().addOnSuccessListener(Executors.newSingleThreadExecutor(),listResult -> {
                finalResult.set(saveImage(listResult.getItems()));
                flag.set(true);
                Log.d(PTAG,TAG + "@downloadResource: resources downloaded is " + finalResult.get());
            });
            while (!flag.get());
            return finalResult.get();
        }
    };

    private boolean saveImage(List<StorageReference> list) {
        Log.d(PTAG,TAG + "@saveImage: resources is " + list.size());
        Iterator<StorageReference> iterator = list.listIterator();
        File directory = new File(DIRECTORY);
        AtomicInteger downloaded = new AtomicInteger(0);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        while (list.size() != downloaded.get()) {
            if (iterator.hasNext()) {
                StorageReference image = iterator.next();
                Log.i(PTAG,TAG +"@saveImage: path resource" + DIRECTORY + File.separator + image.getName());
                FileDownloadTask download = image.getFile(new File(DIRECTORY, image.getName()));
                download.addOnSuccessListener(Executors.newSingleThreadExecutor(), new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        downloaded.incrementAndGet();
                        Log.i(PTAG, TAG + "@onSuccess: file downloaded");
                    }
                });
            }
        }

        return list.size() == downloaded.get();
    }

    public LinkedHashMap<String, Callable<Boolean>> startGetResultFunctions() throws InterruptedException {
        mCallables.clear();
        mCallables.put(CHECK_WIFI,checkWIFI);
        mCallables.put(CHECK_RESOURCES, downloadResource);
        return mCallables;
    }
}
