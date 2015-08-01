package com.vinjoe.isolapp.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinjoe.isolapp.R;

import java.io.ByteArrayInputStream;

/**
 * Created by BrGi on 07/06/2015.
 */
public class CustomAdapter extends CursorAdapter {
    private static LayoutInflater inflater;
    private ImageLoader imageLoader;
    public CustomAdapter(Context context, Cursor cursor, boolean b) {
        super(context, cursor, b);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(getViewType(cursor), viewGroup, false);
    }

    public int getViewType(Cursor cursor) {
        return cursor.getBlob(cursor.getColumnIndex("Foto")) == null ? R.layout.list_item : R.layout.list_item;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView t = (TextView) view.findViewById(R.id.text);
        t.setText(cursor.getString(cursor.getColumnIndex("Nome")));
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        imageLoader.DisplayImage("Foto"+cursor.getPosition(), image,cursor);
        byte[] blob = cursor.getBlob(cursor.getColumnIndex("Foto"));
        if (blob != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            Bitmap b = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(b);
        } else {
            image.setImageBitmap(null);
        }
    }
}
