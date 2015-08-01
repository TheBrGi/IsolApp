package com.vinjoe.isolapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vinjoe.isolapp.database.assetsdb.MyDatabase;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BrGi on 07/06/2015.
 */
public class Util {
    private Util() {
    }

    static void prepareListData(List<HashMap> list, Context context, String table) {

        MyDatabase db = new MyDatabase(context);
        Cursor categoria = db.getCategoria(table);
        if (categoria == null) return;
        while (!categoria.isAfterLast()) {
            HashMap hm = new HashMap();
            String s = categoria.getString(categoria.getColumnIndex("Nome"));
            hm.put("testo", s);
            byte[] blob = categoria.getBlob(categoria.getColumnIndex("Foto"));
            if (blob != null) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                Bitmap b = BitmapFactory.decodeStream(inputStream);
                hm.put("immagine", b);
            }
            list.add(hm);
            categoria.moveToNext();
        }
        HashMap hm1 = new HashMap();
        hm1.put("testo", "Element");
        list.add(hm1);

    }
}


