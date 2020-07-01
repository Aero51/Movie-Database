package com.aero51.moviedatabase.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;

import java.util.ArrayList;
import java.util.List;

public  class GetCroChannelsLogoResource {
    private static List<Drawable> drawableList=new ArrayList<>();

    public static Drawable getDrawableForChannel(Context context,Integer position)
    {

        drawableList.add(context.getResources().getDrawable(R.drawable.htv1_logo));
        drawableList.add(context.getResources().getDrawable(R.drawable.htv2_logo));
        drawableList.add(context.getResources().getDrawable(R.drawable.htv3_logo));
        drawableList.add(context.getResources().getDrawable(R.drawable.novatv_logo_2019));
        drawableList.add(context.getResources().getDrawable(R.drawable.rtl));
        drawableList.add(context.getResources().getDrawable(R.drawable.rtl2_logo));
        drawableList.add(context.getResources().getDrawable(R.drawable.doma));
        drawableList.add(context.getResources().getDrawable(R.drawable.rtl_kockica_logo));
        drawableList.add(context.getResources().getDrawable(R.drawable.htv4_logo));

        return drawableList.get(position);
    }

}
