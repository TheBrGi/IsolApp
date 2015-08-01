package com.vinjoe.isolapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by BrGi on 07/06/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private List<HashMap> items;
    private static LayoutInflater inflater;

    public CustomListAdapter(List<HashMap> items, Context context) {
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).containsKey("immagine") ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HashMap item = (HashMap) getItem(position);
        View mView = convertView;
        int type = getItemViewType(position);
        int layout = type == 0 ? R.layout.list_item : R.layout.list_item;
        if (convertView == null)
            mView = inflater.inflate(layout, parent, false);
        String testo = (String) item.get("testo");
        if (testo != null)
            ((TextView) mView.findViewById(R.id.text)).setText(testo);
        if (type == 0) {
            Bitmap imm = (Bitmap) item.get("immagine");
            ImageView i = (ImageView) mView.findViewById(R.id.imageView);
            if (imm != null) {
                i.setImageBitmap(imm);
            }
        }
        ImageButton imageButton = (ImageButton) mView.findViewById(R.id.imageButton);
        final View v = mView;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                i.putExtra("coordinate", (double[]) item.get("coordinate"));
                v.getContext().startActivity(i);
            }
        });
        return mView;
    }
}
