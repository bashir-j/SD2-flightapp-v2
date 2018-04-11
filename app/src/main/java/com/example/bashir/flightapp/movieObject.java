package com.example.bashir.flightapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.Serializable;

public class movieObject  {
    String _id;
    String title;
    int width;
    String category;
    Bitmap thumbnail;
    Bitmap bioImage;
    String artist;
    String ageLimit;
    ImageView imgV;
    ImageView imgV2;
    TextView txtV;
    String bio;
    movieObject(String _id, String title, String bio){
        this._id=_id;
        this.title=title;
        this.bio = bio;
    }

    public void getThumbnail(){
        new DownloadImageTask(this.thumbnail , true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.115:3000/thumbnail/" + this._id + "/150x150");
        //new DownloadImageTask(this.thumbnail , true).execute("http://192.168.1.115:3000/thumbnail/" + this._id + "/150x150");
    }

    public void getBioImage(){
        new DownloadImageTask(this.thumbnail, false).execute("http://192.168.1.115:3000/thumbnail/" + this._id + "/600x600");
    }

    public void setThumbnail(Bitmap result){
        this.thumbnail = Bitmap.createScaledBitmap(result,width/3,width/3,false);
        txtV.setVisibility(View.VISIBLE);
        this.imgV.setImageBitmap(this.thumbnail);
    }
    public void setBioImage(Bitmap result){
        this.bioImage = result;
        this.imgV2.setImageBitmap(this.bioImage);
    }

    public void setImageView(ImageView imgV){
        this.imgV = imgV;
    }

    public void setTextView(TextView txtV){
        this.txtV = txtV;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap thumb;
        Boolean getSmall;

        public DownloadImageTask(Bitmap thumbnail, Boolean getSmall) {
            this.thumb = thumbnail;
            this.getSmall = getSmall;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            Log.d("Prog","Start");
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Log.d("Prog","Finish");
            if(getSmall) {
                setThumbnail(result);
            }
            else{
                setBioImage(result);
            }
        }
    }
}
