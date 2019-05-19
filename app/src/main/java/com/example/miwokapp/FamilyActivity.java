package com.example.miwokapp;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

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

                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
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
    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            releaseMediaPlayer();
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // String ArrayList of Numbers
        final ArrayList<Word> words = new ArrayList<Word>();
        String[] item = new String[10];
        //words.add("One");
       /* Word w = new Word("Lutti" , "One");
        words.add(w);
        */
        // ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,item);

        words.add(new Word("әpә" , "father" , R.drawable.family_father , R.raw.family_father));
        words.add(new Word("әṭa" , "mother" , R.drawable.family_mother , R.raw.family_mother));
        words.add(new Word("angsi", "son" , R.drawable.family_son , R.raw.family_son));
        words.add(new Word("tune","daughter" ,R.drawable.family_daughter  ,R.raw.family_daughter));
        words.add( new Word("taachi","older brother" , R.drawable.family_older_brother ,
                R.raw.family_older_brother));
        words.add(new Word("chalitti" ,"younger brother" , R.drawable.family_younger_brother ,
                R.raw.family_younger_brother));
        words.add(new Word("teṭe", "older sister" , R.drawable.family_older_sister  ,
                R.raw.family_older_sister));
        words.add(new Word("kolliti","younger sister" , R.drawable.family_younger_sister ,
                R.raw.family_younger_sister));
        words.add(new Word("ama","grandmother" , R.drawable.family_grandmother ,
                R.raw.family_grandmother));
        words.add(new Word("paapa","grandfather" , R.drawable.family_grandfather ,
                R.raw.family_grandfather));



        WordAdapter adapter = new WordAdapter(this, words , R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioID());
                    mediaPlayer.start();


                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
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
