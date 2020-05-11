package fr.drumscool.sequencer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MultiTrackSequencer sequencer;

    SeekBar bpmSeekBar;
    TextView bpmTextView;

    Button sequencerStartButton;
    Button sequencerStopButton;

    String msg = "Android : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "The onCreate() event " + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bpmSeekBar = (SeekBar) findViewById(R.id.bpmSeekBar );
        this.bpmSeekBar.setMax(208);
        this.bpmSeekBar.setMin(40);
        this.bpmSeekBar.setProgress(90);
        this.bpmTextView = (TextView) findViewById(R.id.bpmTextView);

        sequencerStartButton = (Button) findViewById(R.id.sequencerStartButton);
        sequencerStopButton = (Button) findViewById(R.id.sequencerStopButton);

        this.bpmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                bpmTextView.setText(String.valueOf(progressValue));
            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sequencer.setSequencerBPM(seekBar.getProgress());
            }
        });

        sequencerStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sequencer.play();
            }
        });

        sequencerStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sequencer.stop();
            }
        });

        sequencer = new MultiTrackSequencer(this,90);

        LinearLayout insertPoint = (LinearLayout) findViewById(R.id.patternViewLayout);

        LinearLayout track1Layout = new LinearLayout(this);
        LinearLayout track2Layout = new LinearLayout(this);



        //LinearLayout track3Layout = new LinearLayout(this);

        //track1Layout.
        PatternView track1Pattern1View = new PatternView(track1Layout, 4);
        PatternView track1Pattern2View = new PatternView(track1Layout, 4);
        /*PatternView track1Pattern3View = new PatternView(track1Layout, 4);
        PatternView track1Pattern4View = new PatternView(track1Layout, 4);*/

        PatternView track2Pattern1View = new PatternView(track2Layout, 3);
        PatternView track2Pattern2View = new PatternView(track2Layout, 3);
        /*PatternView track2Pattern3View = new PatternView(track2Layout, 3);
        PatternView track2Pattern4View = new PatternView(track2Layout, 3);

        PatternView track3Pattern1View = new PatternView(track3Layout, 5);
        PatternView track3Pattern2View = new PatternView(track3Layout,5);
        PatternView track3Pattern3View = new PatternView(track3Layout,5);
        PatternView track3Pattern4View = new PatternView(track3Layout,5);*/

        sequencer.addPatternToTrack(track1Pattern1View.shownPattern, 0);
        sequencer.addPatternToTrack(track1Pattern2View.shownPattern, 0);
        /*sequencer.addPatternToTrack(track1Pattern3View.shownPattern, 0);
        sequencer.addPatternToTrack(track1Pattern4View.shownPattern, 0);*/

        sequencer.addPatternToTrack(track2Pattern1View.shownPattern, 1);
        sequencer.addPatternToTrack(track2Pattern2View.shownPattern, 1);
        //sequencer.addPatternToTrack(track2Pattern3View.shownPattern, 1);
        //sequencer.addPatternToTrack(track2Pattern4View.shownPattern, 1);

        /*sequencer.addPatternToTrack(track3Pattern1View.shownPattern, 2);
        sequencer.addPatternToTrack(track3Pattern2View.shownPattern, 2);
        sequencer.addPatternToTrack(track3Pattern3View.shownPattern, 2);
        sequencer.addPatternToTrack(track3Pattern4View.shownPattern, 2);*/

        sequencer.setTrackSound(0,SoundManager.SOUND_KICK);
        sequencer.setTrackSound(1,SoundManager.SOUND_SNARE);
        //sequencer.setTrackSound(2,SoundManager.SOUND_HHC);

        //insertPoint.addView(track1Layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //insertPoint.addView(track2Layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //insertPoint.addView(track3Layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        insertPoint.addView(track1Layout);
        insertPoint.addView(track2Layout);

        //sequencer.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        sequencer.stop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(msg, "The onRestart() event");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString(GAME_STATE_KEY, gameState);
        //outState.putString(TEXT_VIEW_KEY, textView.getText());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
        Log.d(msg, "The onSaveInstanceState() event " + outState);
    }

}
