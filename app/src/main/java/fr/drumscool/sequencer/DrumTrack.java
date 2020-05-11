package fr.drumscool.sequencer;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class DrumTrack {
    LinearLayout trackLayout;
    ArrayList<Pattern>  trackPatterns;
    int ressourceSoundId;
    MultiTrackSequencer sequencer;
    int trackNumber;

    DrumTrack(MultiTrackSequencer sequencer, int trackNumber, int ressourceSoundId, int[] patternSubdivs){
        this.sequencer = sequencer;
        this.trackNumber = trackNumber;
        this.ressourceSoundId = ressourceSoundId;

        for(int subdiv : patternSubdivs)
            trackPatterns.add(new Pattern(subdiv));
    }

    public void createView(Context context){
        this.trackLayout = new LinearLayout(context);
    }
}
