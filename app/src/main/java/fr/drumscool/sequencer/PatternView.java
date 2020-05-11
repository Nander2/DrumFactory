package fr.drumscool.sequencer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static fr.drumscool.sequencer.R.color.toBePlayed;

public class PatternView implements AdapterView.OnItemSelectedListener {
    Pattern shownPattern;
    Spinner view;
    ArrayList<ArrayList<Boolean>> patterns;

    PatternView(ViewGroup insertPoint, int subdiv){
        patterns = generatePatternCombinaison(subdiv);
        shownPattern = new Pattern(subdiv);
        createSpinner(insertPoint);
        setSelection(0);
    }

    private ArrayList<ArrayList<Boolean>> generatePatternCombinaison(int nbDiv)
    {
        class patternTree {
            patternTree left = null;
            patternTree right = null;
            boolean value = false;

            patternTree(boolean value, int child){
                this.value = value;
                if(child > 0){
                    this.left = new patternTree(true, child - 1);
                    this.right = new patternTree(false, child - 1);
                }
            }

            void getChildPossibleValue(ArrayList<Boolean> ancestor, ArrayList<ArrayList<Boolean>> toAddTo){
                ancestor.add(value);
                if(left != null && right != null) {
                    left.getChildPossibleValue((ArrayList<Boolean>)ancestor.clone(), toAddTo);
                    right.getChildPossibleValue((ArrayList<Boolean>)ancestor.clone(), toAddTo);
                }
                else {
                    toAddTo.add(ancestor);
                }

            }
        }

        patternTree leftGenerator = new patternTree(true, nbDiv - 1);
        patternTree rightGenerator = new patternTree(false, nbDiv - 1);
        ArrayList<ArrayList<Boolean>> toAddTo = new ArrayList<ArrayList<Boolean>>();
        leftGenerator.getChildPossibleValue(new ArrayList<Boolean>(), toAddTo);
        rightGenerator.getChildPossibleValue(new ArrayList<Boolean>(), toAddTo);

        return toAddTo;
    }

    private void createSpinner(ViewGroup insertPoint){

        view = new Spinner(insertPoint.getContext());
        view.setBackground(null);
        view.setPadding (0,0,0,0);
        PatternAdapter customAdapter = new PatternAdapter(insertPoint.getContext(), patterns);
        view.setAdapter(customAdapter);

        view.setOnItemSelectedListener(this);
        view.setSelection(15);
        //view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        insertPoint.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
    }

    public void setSelection(int position) {
        shownPattern.notes = patterns.get(position);
        view.setSelection(position);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        int Width = arg0.getWidth();
        System.out.println("arg0 width = " +  Width);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        shownPattern.notes = patterns.get(position);
    }
}
