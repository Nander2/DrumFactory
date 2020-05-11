package fr.drumscool.sequencer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

public class Pattern {
    int subDiv = 1;
    ArrayList<Boolean> notes;

    Pattern(int subdiv) {
        subDiv = subdiv;
        notes= new ArrayList<Boolean>(subDiv);
        for(int i=0; i< subDiv ; i++) {
            notes.add(false);
        }
    }

    Pattern(ArrayList<Boolean> notes) {
        subDiv = notes.size();
        this.notes= notes;
    }

    public boolean shallPlay(int currentPlayCursorPos, int patternStartPos, int divPerBlack){
        if(divPerBlack%subDiv == 0) {
            int Interval = divPerBlack / subDiv;
            if((currentPlayCursorPos - patternStartPos) % Interval == 0){
                int currentNoteIndex = (currentPlayCursorPos - patternStartPos) / Interval;
                return notes.get(currentNoteIndex);
            }
        }
        return false;
    }

    public boolean isFinished(int currentPlayCursorPos, int divPerBlack){
        return (currentPlayCursorPos+1)%divPerBlack == 0;
    }

    public void randomise(){
        for (int i =0;i<subDiv;i++){
            notes.set(i, random());
        }
    }

    public void printPattern(){
        System.out.print("|");
        for(int i = 0; i < subDiv; i++){
            System.out.print(String.valueOf(notes.get(i)) + " ");
        }
        System.out.print("|");
    }

    private boolean random()
    {
        int nombre= (int)(Math.random()*100);
        return nombre>=50;
    }
}
