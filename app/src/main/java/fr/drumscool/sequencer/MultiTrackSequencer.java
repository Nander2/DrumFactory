package fr.drumscool.sequencer;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;

public class MultiTrackSequencer {

    boolean started = false;
    int sequencerBPM = 90;
    int currentPlayCursorPos;
    int currentPatternsStartPlayPos;
    int divPerBlack;

    int stepDurationInMs;

    ArrayList<ArrayList<Pattern>> tracks;
    ArrayList<Integer> tracksSound;

    ArrayList<DrumTrack> drumTracks;

    SoundManager soundManager;

    private Handler myHandler;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {

            int currentPatternIndex = currentPlayCursorPos/divPerBlack;
            int maxTrackSize = 0;

            for (int trackIndex = 0; trackIndex < tracks.size(); trackIndex++) {
                ArrayList<Pattern> track = tracks.get(trackIndex);
                if(track.size() > maxTrackSize){
                    maxTrackSize = track.size();
                }
                if(track.size() > currentPatternIndex) {
                    if (track.get(currentPatternIndex).shallPlay(currentPlayCursorPos, currentPatternsStartPlayPos, divPerBlack)) {
                        soundManager.playSound(tracksSound.get(trackIndex));
                    }
                }
            }

            currentPlayCursorPos++;

            if(currentPlayCursorPos % divPerBlack == 0)
            {
                if(currentPlayCursorPos/divPerBlack >= maxTrackSize){
                    currentPlayCursorPos = 0;
                }
                currentPatternsStartPlayPos = currentPlayCursorPos;
            }

            myHandler.postDelayed(myRunnable, stepDurationInMs);
        }
    };

    MultiTrackSequencer(Context context, int bpm) {
        sequencerBPM = bpm;
        tracks = new ArrayList<ArrayList<Pattern>>();
        tracksSound = new ArrayList<Integer>();
        soundManager = new SoundManager(context);
        divPerBlack = 1;
        myHandler = new Handler();
    }

    public int createDrumTrack(int ressourceSoundId, int[] patternSubdivs){
        int insertinIndex = drumTracks.size();
        drumTracks.add(new DrumTrack(this, insertinIndex, ressourceSoundId, patternSubdivs));
        return insertinIndex;
    }

    private int addTrackIfNecessary(int trackNumber){
        if(trackNumber >= tracks.size()) {
            trackNumber = tracks.size();
            tracks.add(new ArrayList<Pattern>());
            tracksSound.add(SoundManager.SOUND_DEFAULT);
        }
        return trackNumber;
    }

    public int addPatternToTrack(Pattern pat, int trackNumber){

        trackNumber = addTrackIfNecessary(trackNumber);

        tracks.get(trackNumber).add(pat);

        return trackNumber;
    }

    public int computeDivPerBlack(){

        ArrayList<Integer> toClean = new ArrayList<Integer>();

        for (int i =0;i<tracks.size();i++){
            ArrayList<Pattern> track = tracks.get(i);
            for(int j =0;j<track.size();j++) {
                toClean.add(track.get(j).subDiv);
            }
        }

        for(int i = 0 ; i < toClean.size(); i++){
            int left = toClean.get(i);
            for(int j = i+1; j < toClean.size(); j++){
                int right = toClean.get(j);
                if(left == right || (left > right && left % right == 0)){
                    toClean.remove(j);
                    j--;
                }
                if(right > left && right % left == 0){
                    toClean.remove(i);
                    i--;
                    break;
                }
            }
        }

        int subdiv = 1;
        Iterator<Integer> it = toClean.iterator();
        while(it.hasNext()){
            subdiv *=  it.next();
        }

        return subdiv;
    }

    public int computeSequenceStepDuration() {
        divPerBlack = computeDivPerBlack();
        int oneBeatDurationInMs = 60000/sequencerBPM;
        return oneBeatDurationInMs/divPerBlack;
    }

    public int setTrackSound(int trackNumber, int soundNumber){
        trackNumber = addTrackIfNecessary(trackNumber);
        tracksSound.set(trackNumber,soundNumber);
        return trackNumber;
    }

    public void play() {
        if (!started) {
            currentPlayCursorPos = 0;
            currentPatternsStartPlayPos = 0;
            stepDurationInMs = computeSequenceStepDuration();
            started = true;
            myHandler.postDelayed(myRunnable, stepDurationInMs);
        }
    }

    public void stop(){
        if(myHandler != null && started) {
            myHandler.removeCallbacks(myRunnable); // On arrete le callback
            started = false;
        }
    }

    public void setSequencerBPM(int newBpm){
        stop();
        sequencerBPM = newBpm;
        play();
    }

}
