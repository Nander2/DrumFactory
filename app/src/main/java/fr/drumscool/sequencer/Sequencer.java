package fr.drumscool.sequencer;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sequencer {

    boolean started = false;
    int sequencerBPM = 90;
    int currentPlayCursorPos;
    int divPerBlack;
    int loopDurationInMs;
    Timer timer;
    Vector patterns;
    Pattern currentPattern;
    int currentPatternStartPlayPos;

    SoundManager soundManager;
    private Handler myHandler;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            //find with pattern contains note at this interval and play it
            //System.out.println(currentPattern.notes);
            if (currentPattern.shallPlay(currentPlayCursorPos, currentPatternStartPlayPos, divPerBlack)){
                soundManager.playSound(SoundManager.SOUND_KICK);
            }
            currentPlayCursorPos++;
            if(currentPlayCursorPos % divPerBlack == 0)
            {
                selectNextPattern();
            }

            myHandler.postDelayed(myRunnable, loopDurationInMs);
        }
    };

    Sequencer(Context context, int bpm) {
        sequencerBPM = bpm;
        soundManager = new SoundManager(context);
        divPerBlack = 1;
        patterns =new Vector();
        myHandler = new Handler();
    }

    public void addPattern(Pattern pat){
        patterns.addElement(pat);
        System.out.println(pat);
    }

    public void insertPattern(Pattern pat, int pos){
        patterns.insertElementAt(pat, pos);
    }

    public int computeDivPerBlack(){
        TreeSet<Integer> set = new TreeSet<Integer>();
        //int LastPatternSize =
        for (int i =0;i<patterns.size();i++){
            set.add(((Pattern)patterns.elementAt(i)).subDiv);
        }

        //a optimisé
        int subdiv = 1;
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()){
            subdiv *=  it.next();
        }

        return subdiv;
    }

    public void printPattern(){
        for (int i =0;i<patterns.size();i++){
            ((Pattern)patterns.elementAt(i)).printPattern();
        }
        System.out.println("");
    }

    public int computeSequenceStepDuration() {
        divPerBlack = computeDivPerBlack();
        int oneBeatDurationInMs = 60000/sequencerBPM;
        return oneBeatDurationInMs/divPerBlack;
    }

    public void addRamdomPattern(){
        int subdiv = (int)(2+Math.random()*6);
        Pattern newPattern = new Pattern(subdiv);
        newPattern.randomise();
        addPattern(newPattern);
    }

    public void play() {
        if (patterns.size() > 0 && !started) {
            currentPlayCursorPos = 0;
            currentPattern = (Pattern) patterns.elementAt(0);
            currentPatternStartPlayPos = 0;
            loopDurationInMs = computeSequenceStepDuration();
            started = true;
            myHandler.postDelayed(myRunnable, loopDurationInMs);
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

    public void selectNextPattern(){
        int newIndex = patterns.indexOf(currentPattern) + 1;
        if(newIndex >= patterns.size()){
            newIndex = 0;
            currentPlayCursorPos = 0;
        }
        currentPattern = (Pattern) patterns.elementAt(newIndex);
        currentPatternStartPlayPos = currentPlayCursorPos;
    }
}
