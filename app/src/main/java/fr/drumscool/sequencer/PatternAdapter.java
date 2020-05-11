package fr.drumscool.sequencer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

import fr.drumscool.sequencer.R;

public class PatternAdapter extends BaseAdapter {
    Context context;
    ArrayList<ArrayList<Boolean>> combinaisons;


    int patternsView[];
    LayoutInflater inflter;

    public PatternAdapter(Context applicationContext, ArrayList<ArrayList<Boolean>> combinaisons) {
        this.context = applicationContext;
        this.combinaisons = combinaisons;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return combinaisons.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        int Width = viewGroup.getWidth();

        ArrayList<Boolean> combinaison = combinaisons.get(i);
        Bitmap bg = Bitmap.createBitmap( 75 , 42, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);
        Paint color = new Paint();

        int x = 10;

        int xIncrement = (75 - x) / combinaison.size();

        for(int j = 0; j < combinaison.size(); j++)
        {
            boolean combinaisonElement = combinaison.get(j);
            if(combinaisonElement)
                color.setColor(Color.parseColor("#00ff00"));
            else
                color.setColor(Color.parseColor("#7f7f7f"));

            canvas.drawRect(x,5, x + xIncrement - 10,37, color);
            x += xIncrement;
        }

        view = inflter.inflate(R.layout.custom_spinner_items, null);
        FrameLayout icon = (FrameLayout) view.findViewById(R.id.imageView);
        icon.setMinimumWidth(Width);
        icon.setMinimumHeight(42);
        //icon.setImageResource(new BitmapDrawable(bg));
        icon.setBackground(new BitmapDrawable(bg));
        return view;
    }
}