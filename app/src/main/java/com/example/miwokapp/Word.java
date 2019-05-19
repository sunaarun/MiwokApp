package com.example.miwokapp;


import android.media.MediaPlayer;

import static android.R.attr.id;

/**
 * Created by RASH ARUN on 2/19/2018.
 */

public class Word {

    private String mDefaultTranslation ;
    private String mMiwokTranslation ;
    private int mImageID = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1 ;
    private int  mAudioID;

    public Word(String Miwok, String english , int songID )
    {
        this.mMiwokTranslation = Miwok;
        this.mDefaultTranslation = english;
        this.mAudioID = songID ;
    }

    public Word(String miwok , String english , int ImageId ,int songID)
    {
        this.mMiwokTranslation = miwok;
        this.mDefaultTranslation = english ;
        this.mImageID = ImageId;
        this.mAudioID = songID;
    }

    public int getmAudioID() {return  mAudioID ; }
    public int getImageID(){ return  mImageID ;}
    public String getDefaultTranslation()
    {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation()
    {
        return  mMiwokTranslation;
    }



    /*
     * to check out if the word object has an image
     * returns a false or true
     */
    public boolean hasImage()
    {return mImageID != NO_IMAGE_PROVIDED ; }



    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageID=" + mImageID +
                ", mAudioID=" + mAudioID +
                '}';
    }
}
