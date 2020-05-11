package fr.drumscool.sequencer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fr.drumscool.sequencer.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int patternsView[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] patternsView) {
        this.context = applicationContext;
        this.patternsView = patternsView;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return patternsView.length;
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
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        icon.setImageResource(patternsView[i]);
        return view;
    }
}