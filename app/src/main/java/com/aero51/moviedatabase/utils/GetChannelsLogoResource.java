package com.aero51.moviedatabase.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;

import java.util.ArrayList;
import java.util.List;

public class GetChannelsLogoResource {
    private static List<Drawable> drawableList = new ArrayList<>();

    public static Drawable getDrawableForChannel(Context context, Integer position) {
        if (drawableList.size() == 0) {
            drawableList.add(context.getResources().getDrawable(R.drawable.htv1_logo));
            drawableList.add(context.getResources().getDrawable(R.drawable.htv2_logo));
            drawableList.add(context.getResources().getDrawable(R.drawable.htv3_logo));
            drawableList.add(context.getResources().getDrawable(R.drawable.novatv_logo_2019));
            drawableList.add(context.getResources().getDrawable(R.drawable.rtl));
            drawableList.add(context.getResources().getDrawable(R.drawable.rtl2_logo));
            drawableList.add(context.getResources().getDrawable(R.drawable.doma));
            drawableList.add(context.getResources().getDrawable(R.drawable.rtl_kockica_logo));
            drawableList.add(context.getResources().getDrawable(R.drawable.htv4_logo));
        }
        return drawableList.get(position);
    }

    public static Integer getResIdForChannelLogo(Integer position) {
        List<Integer> resourceIdsList = new ArrayList<>();
        resourceIdsList.add(R.drawable.htv1_logo);
        resourceIdsList.add(R.drawable.htv2_logo);
        resourceIdsList.add(R.drawable.htv3_logo);
        resourceIdsList.add(R.drawable.novatv_logo_2019);
        resourceIdsList.add(R.drawable.rtl);
        resourceIdsList.add(R.drawable.rtl2_logo);
        resourceIdsList.add(R.drawable.doma);
        resourceIdsList.add(R.drawable.rtl_kockica_logo);
        resourceIdsList.add(R.drawable.htv4_logo);


        return resourceIdsList.get(position);
    }

    public static List<EpgOtherChannel> getOtherChannelsList() {
        List<EpgOtherChannel> otherChannelList = new ArrayList<>();

        //hr
        EpgOtherChannel epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("SPORTSKATV");
        epgOtherChannel.setDrawableInteger(R.drawable.sptv_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("Z1");
        epgOtherChannel.setDrawableInteger(R.drawable.z1_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("KANALRI");
        epgOtherChannel.setDrawableInteger(R.drawable.kanalr_logo);
        otherChannelList.add(epgOtherChannel);





        //srb
        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTS1");
        epgOtherChannel.setDrawableInteger(R.drawable.rts1_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTS2");
        epgOtherChannel.setDrawableInteger(R.drawable.rts2_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTS3");
        epgOtherChannel.setDrawableInteger(R.drawable.rts_3_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("PINK1");
        epgOtherChannel.setDrawableInteger(R.drawable.pink_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("PINK2");
        epgOtherChannel.setDrawableInteger(R.drawable.pink_2);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("PRVA");
        epgOtherChannel.setDrawableInteger(R.drawable.prva_srpska_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("STARTVTR");
        epgOtherChannel.setDrawableInteger(R.drawable.startv);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("HAPPY");
        epgOtherChannel.setDrawableInteger(R.drawable.happy_new_logo);
        otherChannelList.add(epgOtherChannel);



        //bih
        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("BHT1");
        epgOtherChannel.setDrawableInteger(R.drawable.bht1_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("OBN");
        epgOtherChannel.setDrawableInteger(R.drawable.obn_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("HAYATPLUS");
        epgOtherChannel.setDrawableInteger(R.drawable.hayatplus_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("PINKBH");
        epgOtherChannel.setDrawableInteger(R.drawable.pink_bh_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTRS");
        epgOtherChannel.setDrawableInteger(R.drawable.rtrs_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTVZENICA");
        epgOtherChannel.setDrawableInteger(R.drawable.rtv_ze_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("TVSARAJEVO");
        epgOtherChannel.setDrawableInteger(R.drawable.tvsa_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("FTV");
        epgOtherChannel.setDrawableInteger(R.drawable.federalnatv_logo);
        otherChannelList.add(epgOtherChannel);




        //cg
        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTCG1");
        epgOtherChannel.setDrawableInteger(R.drawable.rtcg1_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("RTCG2");
        epgOtherChannel.setDrawableInteger(R.drawable.rtcg2_logo);
        otherChannelList.add(epgOtherChannel);





        //slo
        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("SLO1");
        epgOtherChannel.setDrawableInteger(R.drawable.rtv_slo1_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("SLO2");
        epgOtherChannel.setDrawableInteger(R.drawable.rtv_slo2_logo);
        otherChannelList.add(epgOtherChannel);

        epgOtherChannel = new EpgOtherChannel();
        epgOtherChannel.setChannelName("SLO3");
        epgOtherChannel.setDrawableInteger(R.drawable.rtv_slo3_logo);
        otherChannelList.add(epgOtherChannel);



        return otherChannelList;
    }

}
