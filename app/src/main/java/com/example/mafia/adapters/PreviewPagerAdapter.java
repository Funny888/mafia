package com.example.mafia.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.interfaces.ISend;
import com.example.mafia.utils.SplashCheckUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.mafia.utils.Logger.PTAG;


public class PreviewPagerAdapter extends RecyclerView.Adapter<PreviewPagerAdapter.MyHolder> {
    private static final String TAG = "PreviewPagerAdapter";

    private ISend<MaterialButton> mSend;
    private Context mContext;
    private static final String STORAGE_PATH = "gs://mafia-7b2ee.appspot.com/";
    private FirebaseStorage storage = FirebaseStorage.getInstance(STORAGE_PATH);
    private StorageReference reference = storage.getReference();
    private Bitmap mBitmap;


    public PreviewPagerAdapter(Context context) {
        mContext = context;
    }

    private File getImageAt(int position) {
        switch (position) {
            case 0:
                return new File(SplashCheckUtils.DIRECTORY + "/preview1.jpeg");
            case 1:
                return new File(SplashCheckUtils.DIRECTORY + "/preview2.jpg");
            case 2:
                return new File(SplashCheckUtils.DIRECTORY + "/preview3.jpg");
            case 3:
                return new File(SplashCheckUtils.DIRECTORY + "/preview4.jpg");
            default:
                return null;
        }
    }

    private String getStringAt(int position) {
        String[] list = mContext.getResources().getStringArray(R.array.naputstvie);
        return list[position];
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.preview_image, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Log.i(PTAG,TAG + "@onBindViewHolder: " + getImageAt(position).getAbsolutePath());
        mBitmap = BitmapFactory.decodeFile(getImageAt(position).getAbsolutePath());
        holder.mPagerImage.setImageBitmap(mBitmap);
        holder.mPagerText.setText(getStringAt(position));
        if (position == 3){
            holder.mBtn_ok.setVisibility(View.VISIBLE);
            mSend.send(holder.mBtn_ok);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        private TextView mPagerText;
        private ImageView mPagerImage;
        private MaterialButton mBtn_ok;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mPagerText = itemView.findViewById(R.id.pagerText);
            mPagerImage = itemView.findViewById(R.id.pagerImage);
            mBtn_ok = itemView.findViewById(R.id.btn_ok);
        }
    }

    public ISend<MaterialButton> setClick(ISend<MaterialButton> send){
        mSend = send;
        return mSend;
    }
}
