package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;

import java.util.ArrayList;
import java.util.List;

public class PrePopulatedChannels {

    private List<EpgChannel> croatianList;
    private List<EpgChannel> croatianExpandedList;
    private List<EpgChannel> entertainmentList;
    private List<EpgChannel> movieList;
    private List<EpgChannel> documentaryList;
    private List<EpgChannel> newsList;
    private List<EpgChannel> musicList;
    private List<EpgChannel> childrenList;
    private List<EpgChannel> bosnianList;
    private List<EpgChannel> serbianList;
    private List<EpgChannel> montenegroList;
    private List<EpgChannel> sportsList;


    public PrePopulatedChannels() {

        croatianList = new ArrayList<>();
        croatianExpandedList = new ArrayList<>();
        entertainmentList = new ArrayList<>();
        movieList = new ArrayList<>();
        documentaryList = new ArrayList<>();
        newsList = new ArrayList<>();
        musicList = new ArrayList<>();
        childrenList = new ArrayList<>();
        bosnianList = new ArrayList<>();
        serbianList = new ArrayList<>();
        montenegroList = new ArrayList<>();
        sportsList = new ArrayList<>();

        prepopulateAll();
    }

    private void prepopulateAll() {

        prepopulateCroatianList();
        prepopulateCroatianExpandedList();
        prepopulateEntertainmentList();
        prepopulateMovieList();
        prepopulateDocumentaryList();
        prepopulateNewsList();
        prepopulateMusicList();
        prepopulateChildrenList();
        prepopulateBosnianList();
        prepopulateSerbianList();
        prepopulateMontenegroList();
        prepopulateSportsList();
    }

    private void prepopulateCroatianList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("hrt1");
        channel.setDisplay_name("HRT 1");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("hrt2");
        channel.setDisplay_name("HRT 2");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtl");
        channel.setDisplay_name("RTL Televizija");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("novatv");
        channel.setDisplay_name("NOVA TV");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtl2");
        channel.setDisplay_name("RTL II");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("domatv");
        channel.setDisplay_name("Doma TV");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("hrt3");
        channel.setDisplay_name("HRT 3");
        croatianList.add(channel);

        channel = new EpgChannel();
        channel.setName("hrt4");
        channel.setDisplay_name("HRT 4");
        croatianList.add(channel);
    }

    private void prepopulateCroatianExpandedList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("rtlkockica");
        channel.setDisplay_name("RTL Kockica");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("z1");
        channel.setDisplay_name("Z1");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("osjeckatv");
        channel.setDisplay_name("Osjecka TV");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("vtvvarazdin");
        channel.setDisplay_name("VTV Varazdin");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("tvjadran");
        channel.setDisplay_name("TV Jadran");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("slavonskatv");
        channel.setDisplay_name("Slavonska TV");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("vinkovackatv");
        channel.setDisplay_name("Vinkovacka TV");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("kanalri");
        channel.setDisplay_name("Kanal RI");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("sbtv");
        channel.setDisplay_name("SB TV");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("laudatotv");
        channel.setDisplay_name("Laudato TV");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("mrezazg");
        channel.setDisplay_name("MrezaZG");
        croatianExpandedList.add(channel);

        channel = new EpgChannel();
        channel.setName("mrezast");
        channel.setDisplay_name("MrezaST");
        croatianExpandedList.add(channel);
    }

    private void prepopulateEntertainmentList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("rtlliving");
        channel.setDisplay_name("RTL Living");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("travel");
        channel.setDisplay_name("Travel Channel");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("eenterntainment");
        channel.setDisplay_name("EEntertainment");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkzabava");
        channel.setDisplay_name("Pink Zabava");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkreality");
        channel.setDisplay_name("Pink Reality");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("fineliving");
        channel.setDisplay_name("Fine Living");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("woman");
        channel.setDisplay_name("Woman");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkfashion");
        channel.setDisplay_name("Pink Fashion");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkstyle");
        channel.setDisplay_name("Pink Style");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkshow");
        channel.setDisplay_name("Pink Show");
        entertainmentList.add(channel);
    }

    private void prepopulateMovieList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("rtlpassion");
        channel.setDisplay_name("RTL Passion");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtlcrime");
        channel.setDisplay_name("RTL Crime");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("hbo1");
        channel.setDisplay_name("HBO");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("hbo2");
        channel.setDisplay_name("HBO 2");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("hbo3");
        channel.setDisplay_name("HBO 3");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinemax1");
        channel.setDisplay_name("Cinemax 1");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinemax2");
        channel.setDisplay_name("Cinemax 2");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestartv");
        channel.setDisplay_name("Cinestar TV");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestaraction");
        channel.setDisplay_name("Cinestar Action");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestarpremiere1");
        channel.setDisplay_name("Cinestar Premiere 1");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestarpremiere2");
        channel.setDisplay_name("Cinestar Premiere 2");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestarfantasy");
        channel.setDisplay_name("Cinestar Fantasy");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinestarcomedy");
        channel.setDisplay_name("Cinestar Comedy");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("foxtv");
        channel.setDisplay_name("FOX TV");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("foxcrime");
        channel.setDisplay_name("FOX Crime");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("foxlife");
        channel.setDisplay_name("FOX Life");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("foxmovies");
        channel.setDisplay_name("FOX Movies");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("axn");
        channel.setDisplay_name("AXN");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("epicdrama");
        channel.setDisplay_name("Epic Drama");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("axnspin");
        channel.setDisplay_name("AXN Spin");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("klasiktv");
        channel.setDisplay_name("KLASIK TV");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("scifi");
        channel.setDisplay_name("SciFi Channel");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("tv1000");
        channel.setDisplay_name("TV1000");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("m1film");
        channel.setDisplay_name("M1 Film");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("amc");
        channel.setDisplay_name("AMC");
        entertainmentList.add(channel);

        channel = new EpgChannel();
        channel.setName("kinotv");
        channel.setDisplay_name("KinoTV");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("filmboxplus");
        channel.setDisplay_name("FilmBox Plus");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("filmboxextra");
        channel.setDisplay_name("FilmBox Extra");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("diva");
        channel.setDisplay_name("DIVA");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkaction");
        channel.setDisplay_name("Pink Action");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkcrime");
        channel.setDisplay_name("Pink Crime and Mystery");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkhorror");
        channel.setDisplay_name("Pink Horror");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkfilm");
        channel.setDisplay_name("Pink Film");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkmovies");
        channel.setDisplay_name("Pink Movies");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkpremium");
        channel.setDisplay_name("Pink Premium");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkcomedy");
        channel.setDisplay_name("Pink Comedy");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkserije");
        channel.setDisplay_name("Pink Serije");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinksoap");
        channel.setDisplay_name("Pink Soap");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkthriller");
        channel.setDisplay_name("Pink Thriller");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkwestern");
        channel.setDisplay_name("Pink Western");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkclassic");
        channel.setDisplay_name("Pink Classic");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkworld");
        channel.setDisplay_name("Pink World Cinema");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkfamily");
        channel.setDisplay_name("Pink Family");
        movieList.add(channel);

        channel = new EpgChannel();
        channel.setName("pickbox");
        channel.setDisplay_name("PickBox");
        movieList.add(channel);
    }

    private void prepopulateDocumentaryList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("cbsreality");
        channel.setDisplay_name("CBS Reality");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("tlc");
        channel.setDisplay_name("TLC");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("history");
        channel.setDisplay_name("History Channel");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("history2");
        channel.setDisplay_name("History Channel 2");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("discovery");
        channel.setDisplay_name("Discovery Channel");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("discoverysci");
        channel.setDisplay_name("Discovery Science");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("animalplanet");
        channel.setDisplay_name("Animal Planet");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("natgeo");
        channel.setDisplay_name("National Geographic");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("natgeowild");
        channel.setDisplay_name("National Geographic Wild");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("viasatexp");
        channel.setDisplay_name("Viasat Explorer");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("viasathis");
        channel.setDisplay_name("Viasat History");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("viasatnat");
        channel.setDisplay_name("Viasat Nature");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("crimeinvestigation");
        channel.setDisplay_name("Crime & Investigations");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("dokutv");
        channel.setDisplay_name("Doku TV");
        documentaryList.add(channel);

        channel = new EpgChannel();
        channel.setName("planetearth");
        channel.setDisplay_name("Planet Earth");
        documentaryList.add(channel);
    }

    private void prepopulateNewsList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("jazeera");
        channel.setDisplay_name("Al Jazeera Balkans");
        newsList.add(channel);

        channel = new EpgChannel();
        channel.setName("cnn");
        channel.setDisplay_name("CNN");
        newsList.add(channel);

        channel = new EpgChannel();
        channel.setName("bbcworldnews");
        channel.setDisplay_name("BBC World News");
        newsList.add(channel);

        channel = new EpgChannel();
        channel.setName("bloomberg");
        channel.setDisplay_name("Bloomberg");
        newsList.add(channel);

        channel = new EpgChannel();
        channel.setName("cnbc");
        channel.setDisplay_name("CNBC");
        newsList.add(channel);
    }

    private void prepopulateMusicList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("cmc");
        channel.setDisplay_name("CMC");
        musicList.add(channel);

        channel = new EpgChannel();
        channel.setName("jugoton");
        channel.setDisplay_name("Jugoton");
        musicList.add(channel);

        channel = new EpgChannel();
        channel.setName("dmc");
        channel.setDisplay_name("DMC");
        musicList.add(channel);

        channel = new EpgChannel();
        channel.setName("mtvlive");
        channel.setDisplay_name("MTV Live HD");
        musicList.add(channel);

        channel = new EpgChannel();
        channel.setName("mtvbase");
        channel.setDisplay_name("MTV Base");
        musicList.add(channel);

        channel = new EpgChannel();
        channel.setName("mtvmusic");
        channel.setDisplay_name("MTV Music");
        musicList.add(channel);
    }

    private void prepopulateChildrenList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("boomerang");
        channel.setDisplay_name("Boomerang");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("cartoonnetwork");
        channel.setDisplay_name("Cartoon Network");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("nickelodeon");
        channel.setDisplay_name("Nickelodeon");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("nickjr");
        channel.setDisplay_name("NickJr.");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("minitv");
        channel.setDisplay_name("Mini TV");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("disney");
        channel.setDisplay_name("Disney Channel");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("disneyxd");
        channel.setDisplay_name("Disney XD");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("pikaboo");
        channel.setDisplay_name("Pikaboo");
        childrenList.add(channel);

        channel = new EpgChannel();
        channel.setName("vavoom");
        channel.setDisplay_name("Vavoom");
        childrenList.add(channel);
    }

    private void prepopulateBosnianList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("bht1");
        channel.setDisplay_name("BHT 1");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("ftv");
        channel.setDisplay_name("Federalna TV");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("hayat");
        channel.setDisplay_name("Hayat TV");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("obn");
        channel.setDisplay_name("OBN");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkbh");
        channel.setDisplay_name("Pink BH");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("bntv");
        channel.setDisplay_name("BN Televizija");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("tktuzla");
        channel.setDisplay_name("TV TK Tuzla");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtrs");
        channel.setDisplay_name("RTRS");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkm");
        channel.setDisplay_name("Pink M");
        bosnianList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinematv");
        channel.setDisplay_name("Cinema TV");
        bosnianList.add(channel);
    }

    private void prepopulateSerbianList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("rts1");
        channel.setDisplay_name("RTS 1");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rts2");
        channel.setDisplay_name("RTS 2");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtssat");
        channel.setDisplay_name("RTS Satelit");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("prva");
        channel.setDisplay_name("Prva");
        serbianList.add(channel);


        channel = new EpgChannel();
        channel.setName("happy");
        channel.setDisplay_name("Happy TV");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtv1");
        channel.setDisplay_name("RTV 1");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtv2");
        channel.setDisplay_name("RTV 2");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("pink1");
        channel.setDisplay_name("Pink 1");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("pinkplus");
        channel.setDisplay_name("Pink Plus");
        serbianList.add(channel);

        channel = new EpgChannel();
        channel.setName("cinemania");
        channel.setDisplay_name("Cinemania");
        serbianList.add(channel);
    }

    private void prepopulateMontenegroList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("rtcg1");
        channel.setDisplay_name("RTCG 1");
        montenegroList.add(channel);

        channel = new EpgChannel();
        channel.setName("rtcg2");
        channel.setDisplay_name("RTCG 2");
        montenegroList.add(channel);
    }

    private void prepopulateSportsList() {
        EpgChannel channel = new EpgChannel();

        channel.setName("sportskatv");
        channel.setDisplay_name("Sportska TV");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena1");
        channel.setDisplay_name("Arena Sport 1");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena2");
        channel.setDisplay_name("Arena Sport 2");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena3");
        channel.setDisplay_name("Arena Sport 3");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena4");
        channel.setDisplay_name("Arena Sport 4");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena5");
        channel.setDisplay_name("Arena Sport 5");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("arena6");
        channel.setDisplay_name("Arena Sport 6");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("skhd");
        channel.setDisplay_name("SportKlub HD");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("sk1");
        channel.setDisplay_name("SportKlub 1");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("sk2");
        channel.setDisplay_name("SportKlub 2");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("sk3");
        channel.setDisplay_name("SportKlub 3");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("sk4");
        channel.setDisplay_name("SportKlub 4");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("eurosport1");
        channel.setDisplay_name("EuroSport 1");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("eurosport2");
        channel.setDisplay_name("EuroSport 2");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("fightbox");
        channel.setDisplay_name("FightBox");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("fightchannel");
        channel.setDisplay_name("Fight Channel");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("fightnetwork");
        channel.setDisplay_name("Fight Network");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("extremesports");
        channel.setDisplay_name("Extreme Sports");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("nauticalchannel");
        channel.setDisplay_name("Nautical Channel");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("kreatortv");
        channel.setDisplay_name("Kreator TV");
        sportsList.add(channel);

        channel = new EpgChannel();
        channel.setName("loviribolov");
        channel.setDisplay_name("Lov i Ribolov");
        sportsList.add(channel);
    }

    public List<EpgChannel> getCroatianList() {
        return croatianList;
    }

    public List<EpgChannel> getCroatianExpandedList() {
        return croatianExpandedList;
    }

    public List<EpgChannel> getEntertainmentList() {
        return entertainmentList;
    }

    public List<EpgChannel> getMovieList() {
        return movieList;
    }

    public List<EpgChannel> getDocumentaryList() {
        return documentaryList;
    }

    public List<EpgChannel> getNewsList() {
        return newsList;
    }

    public List<EpgChannel> getMusicList() {
        return musicList;
    }

    public List<EpgChannel> getChildrenList() {
        return childrenList;
    }

    public List<EpgChannel> getBosnianList() {
        return bosnianList;
    }

    public List<EpgChannel> getSerbianList() {
        return serbianList;
    }

    public List<EpgChannel> getMontenegroList() {
        return montenegroList;
    }

    public List<EpgChannel> getSportsList() {
        return sportsList;
    }
}
