package com.example.miwokapp;



import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer ;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager ;

    /**
     +     * This listener gets triggered whenever the audio focus changes
     +     * (i.e., we gain or lose audio focus because of another app or device).
     +     */
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                            || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.
                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }

                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN )
                    {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mediaPlayer.start();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                    {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }

                }
            }  ;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    } ;

    /// OnCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        // String ArrayList of Numbers
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Wetetti" , "Red", R.drawable.color_red , R.raw.color_red));
        words.add(new Word("Chokokki" , "Green", R.drawable.color_green , R.raw.color_green));
        words.add(new Word("Takaakki","Brown", R.drawable.color_brown , R.raw.color_brown));
        words.add( new Word("Topoppi","Gray", R.drawable.color_gray , R.raw.color_gray));
        words.add(new Word("Kululli" ,"Black", R.drawable.color_black , R.raw.color_black));
        words.add(new Word("Kelelli","White" , R.drawable.color_white  , R.raw.color_white));
        words.add(new Word("Topiisә","Dusty Yellow"  , R.drawable.color_dusty_yellow , R.raw.color_dusty_yellow));
        words.add(new Word("Chiwiiṭә", "mustard yellow" , R.drawable.color_mustard_yellow , R.raw.color_mustard_yellow));


        WordAdapter adapter = new WordAdapter(this, words , R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Word word = words.get(position);// returns a Word object then store it Word object

                Toast.makeText(view.getContext() , "clicked" , Toast.LENGTH_SHORT).show();
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // We have audio focus now.

                    mediaPlayer = MediaPlayer.create( ColorsActivity.this , word.getmAudioID());
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
