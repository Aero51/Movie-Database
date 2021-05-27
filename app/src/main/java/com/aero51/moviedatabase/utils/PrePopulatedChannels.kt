package com.aero51.moviedatabase.utils

import com.aero51.moviedatabase.repository.model.epg.EpgChannel
import java.util.*

class PrePopulatedChannels {
    private val croatianList: MutableList<EpgChannel>
    private val croatianExpandedList: MutableList<EpgChannel>
    private val entertainmentList: MutableList<EpgChannel>
    private val movieList: MutableList<EpgChannel>
    private val documentaryList: MutableList<EpgChannel>
    private val newsList: MutableList<EpgChannel>
    private val musicList: MutableList<EpgChannel>
    private val childrenList: MutableList<EpgChannel>
    private val bosnianList: MutableList<EpgChannel>
    private val serbianList: MutableList<EpgChannel>
    private val montenegroList: MutableList<EpgChannel>
    private val sportsList: MutableList<EpgChannel>

    init {
        croatianList = ArrayList()
        croatianExpandedList = ArrayList()
        entertainmentList = ArrayList()
        movieList = ArrayList()
        documentaryList = ArrayList()
        newsList = ArrayList()
        musicList = ArrayList()
        childrenList = ArrayList()
        bosnianList = ArrayList()
        serbianList = ArrayList()
        montenegroList = ArrayList()
        sportsList = ArrayList()
        prepopulateAll()
    }
    private fun prepopulateAll() {
        prepopulateCroatianList()
        prepopulateCroatianExpandedList()
        prepopulateEntertainmentList()
        prepopulateMovieList()
        prepopulateDocumentaryList()
        prepopulateNewsList()
        prepopulateMusicList()
        prepopulateChildrenList()
        prepopulateBosnianList()
        prepopulateSerbianList()
        prepopulateMontenegroList()
        prepopulateSportsList()
    }

    private fun prepopulateCroatianList() {
        var channel = EpgChannel()
        channel.name = "hrt1"
        channel.display_name = "HRT 1"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "hrt2"
        channel.display_name = "HRT 2"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtl"
        channel.display_name = "RTL Televizija"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "novatv"
        channel.display_name = "NOVA TV"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtl2"
        channel.display_name = "RTL II"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "domatv"
        channel.display_name = "Doma TV"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "hrt3"
        channel.display_name = "HRT 3"
        croatianList.add(channel)
        channel = EpgChannel()
        channel.name = "hrt4"
        channel.display_name = "HRT 4"
        croatianList.add(channel)
    }

    private fun prepopulateCroatianExpandedList() {
        var channel = EpgChannel()
        channel.name = "rtlkockica"
        channel.display_name = "RTL Kockica"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "z1"
        channel.display_name = "Z1"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "osjeckatv"
        channel.display_name = "Osjecka TV"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "vtvvarazdin"
        channel.display_name = "VTV Varazdin"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "tvjadran"
        channel.display_name = "TV Jadran"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "slavonskatv"
        channel.display_name = "Slavonska TV"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "vinkovackatv"
        channel.display_name = "Vinkovacka TV"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "kanalri"
        channel.display_name = "Kanal RI"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "sbtv"
        channel.display_name = "SB TV"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "laudatotv"
        channel.display_name = "Laudato TV"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "mrezazg"
        channel.display_name = "MrezaZG"
        croatianExpandedList.add(channel)
        channel = EpgChannel()
        channel.name = "mrezast"
        channel.display_name = "MrezaST"
        croatianExpandedList.add(channel)
    }

    private fun prepopulateEntertainmentList() {
        var channel = EpgChannel()
        channel.name = "rtlliving"
        channel.display_name = "RTL Living"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "travel"
        channel.display_name = "Travel Channel"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "eenterntainment"
        channel.display_name = "EEntertainment"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkzabava"
        channel.display_name = "Pink Zabava"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkreality"
        channel.display_name = "Pink Reality"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "fineliving"
        channel.display_name = "Fine Living"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "woman"
        channel.display_name = "Woman"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkfashion"
        channel.display_name = "Pink Fashion"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkstyle"
        channel.display_name = "Pink Style"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkshow"
        channel.display_name = "Pink Show"
        entertainmentList.add(channel)
    }

    private fun prepopulateMovieList() {
        var channel = EpgChannel()
        channel.name = "rtlpassion"
        channel.display_name = "RTL Passion"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "rtlcrime"
        channel.display_name = "RTL Crime"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "hbo1"
        channel.display_name = "HBO"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "hbo2"
        channel.display_name = "HBO 2"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "hbo3"
        channel.display_name = "HBO 3"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "cinemax1"
        channel.display_name = "Cinemax 1"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinemax2"
        channel.display_name = "Cinemax 2"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestartv"
        channel.display_name = "Cinestar TV"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestaraction"
        channel.display_name = "Cinestar Action"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestarpremiere1"
        channel.display_name = "Cinestar Premiere 1"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestarpremiere2"
        channel.display_name = "Cinestar Premiere 2"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestarfantasy"
        channel.display_name = "Cinestar Fantasy"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "cinestarcomedy"
        channel.display_name = "Cinestar Comedy"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "foxtv"
        channel.display_name = "FOX TV"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "foxcrime"
        channel.display_name = "FOX Crime"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "foxlife"
        channel.display_name = "FOX Life"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "foxmovies"
        channel.display_name = "FOX Movies"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "axn"
        channel.display_name = "AXN"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "epicdrama"
        channel.display_name = "Epic Drama"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "axnspin"
        channel.display_name = "AXN Spin"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "klasiktv"
        channel.display_name = "KLASIK TV"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "scifi"
        channel.display_name = "SciFi Channel"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "tv1000"
        channel.display_name = "TV1000"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "m1film"
        channel.display_name = "M1 Film"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "amc"
        channel.display_name = "AMC"
        entertainmentList.add(channel)
        channel = EpgChannel()
        channel.name = "kinotv"
        channel.display_name = "KinoTV"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "filmboxplus"
        channel.display_name = "FilmBox Plus"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "filmboxextra"
        channel.display_name = "FilmBox Extra"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "diva"
        channel.display_name = "DIVA"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkaction"
        channel.display_name = "Pink Action"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkcrime"
        channel.display_name = "Pink Crime and Mystery"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkhorror"
        channel.display_name = "Pink Horror"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkfilm"
        channel.display_name = "Pink Film"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkmovies"
        channel.display_name = "Pink Movies"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkpremium"
        channel.display_name = "Pink Premium"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkcomedy"
        channel.display_name = "Pink Comedy"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkserije"
        channel.display_name = "Pink Serije"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinksoap"
        channel.display_name = "Pink Soap"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkthriller"
        channel.display_name = "Pink Thriller"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkwestern"
        channel.display_name = "Pink Western"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkclassic"
        channel.display_name = "Pink Classic"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkworld"
        channel.display_name = "Pink World Cinema"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkfamily"
        channel.display_name = "Pink Family"
        movieList.add(channel)
        channel = EpgChannel()
        channel.name = "pickbox"
        channel.display_name = "PickBox"
        movieList.add(channel)
    }

    private fun prepopulateDocumentaryList() {
        var channel = EpgChannel()
        channel.name = "cbsreality"
        channel.display_name = "CBS Reality"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "tlc"
        channel.display_name = "TLC"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "history"
        channel.display_name = "History Channel"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "history2"
        channel.display_name = "History Channel 2"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "discovery"
        channel.display_name = "Discovery Channel"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "discoverysci"
        channel.display_name = "Discovery Science"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "animalplanet"
        channel.display_name = "Animal Planet"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "natgeo"
        channel.display_name = "National Geographic"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "natgeowild"
        channel.display_name = "National Geographic Wild"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "viasatexp"
        channel.display_name = "Viasat Explorer"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "viasathis"
        channel.display_name = "Viasat History"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "viasatnat"
        channel.display_name = "Viasat Nature"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "crimeinvestigation"
        channel.display_name = "Crime & Investigations"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "dokutv"
        channel.display_name = "Doku TV"
        documentaryList.add(channel)
        channel = EpgChannel()
        channel.name = "planetearth"
        channel.display_name = "Planet Earth"
        documentaryList.add(channel)
    }

    private fun prepopulateNewsList() {
        var channel = EpgChannel()
        channel.name = "jazeera"
        channel.display_name = "Al Jazeera Balkans"
        newsList.add(channel)
        channel = EpgChannel()
        channel.name = "cnn"
        channel.display_name = "CNN"
        newsList.add(channel)
        channel = EpgChannel()
        channel.name = "bbcworldnews"
        channel.display_name = "BBC World News"
        newsList.add(channel)
        channel = EpgChannel()
        channel.name = "bloomberg"
        channel.display_name = "Bloomberg"
        newsList.add(channel)
        channel = EpgChannel()
        channel.name = "cnbc"
        channel.display_name = "CNBC"
        newsList.add(channel)
    }

    private fun prepopulateMusicList() {
        var channel = EpgChannel()
        channel.name = "cmc"
        channel.display_name = "CMC"
        musicList.add(channel)
        channel = EpgChannel()
        channel.name = "jugoton"
        channel.display_name = "Jugoton"
        musicList.add(channel)
        channel = EpgChannel()
        channel.name = "dmc"
        channel.display_name = "DMC"
        musicList.add(channel)
        channel = EpgChannel()
        channel.name = "mtvlive"
        channel.display_name = "MTV Live HD"
        musicList.add(channel)
        channel = EpgChannel()
        channel.name = "mtvbase"
        channel.display_name = "MTV Base"
        musicList.add(channel)
        channel = EpgChannel()
        channel.name = "mtvmusic"
        channel.display_name = "MTV Music"
        musicList.add(channel)
    }

    private fun prepopulateChildrenList() {
        var channel = EpgChannel()
        channel.name = "boomerang"
        channel.display_name = "Boomerang"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "cartoonnetwork"
        channel.display_name = "Cartoon Network"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "nickelodeon"
        channel.display_name = "Nickelodeon"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "nickjr"
        channel.display_name = "NickJr."
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "minitv"
        channel.display_name = "Mini TV"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "disney"
        channel.display_name = "Disney Channel"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "disneyxd"
        channel.display_name = "Disney XD"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "pikaboo"
        channel.display_name = "Pikaboo"
        childrenList.add(channel)
        channel = EpgChannel()
        channel.name = "vavoom"
        channel.display_name = "Vavoom"
        childrenList.add(channel)
    }

    private fun prepopulateBosnianList() {
        var channel = EpgChannel()
        channel.name = "bht1"
        channel.display_name = "BHT 1"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "ftv"
        channel.display_name = "Federalna TV"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "hayat"
        channel.display_name = "Hayat TV"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "obn"
        channel.display_name = "OBN"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkbh"
        channel.display_name = "Pink BH"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "bntv"
        channel.display_name = "BN Televizija"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "tktuzla"
        channel.display_name = "TV TK Tuzla"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtrs"
        channel.display_name = "RTRS"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkm"
        channel.display_name = "Pink M"
        bosnianList.add(channel)
        channel = EpgChannel()
        channel.name = "cinematv"
        channel.display_name = "Cinema TV"
        bosnianList.add(channel)
    }

    private fun prepopulateSerbianList() {
        var channel = EpgChannel()
        channel.name = "rts1"
        channel.display_name = "RTS 1"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "rts2"
        channel.display_name = "RTS 2"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtssat"
        channel.display_name = "RTS Satelit"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "prva"
        channel.display_name = "Prva"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "happy"
        channel.display_name = "Happy TV"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtv1"
        channel.display_name = "RTV 1"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "rtv2"
        channel.display_name = "RTV 2"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "pink1"
        channel.display_name = "Pink 1"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "pinkplus"
        channel.display_name = "Pink Plus"
        serbianList.add(channel)
        channel = EpgChannel()
        channel.name = "cinemania"
        channel.display_name = "Cinemania"
        serbianList.add(channel)
    }

    private fun prepopulateMontenegroList() {
        var channel = EpgChannel()
        channel.name = "rtcg1"
        channel.display_name = "RTCG 1"
        montenegroList.add(channel)
        channel = EpgChannel()
        channel.name = "rtcg2"
        channel.display_name = "RTCG 2"
        montenegroList.add(channel)
    }

    private fun prepopulateSportsList() {
        var channel = EpgChannel()
        channel.name = "sportskatv"
        channel.display_name = "Sportska TV"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena1"
        channel.display_name = "Arena Sport 1"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena2"
        channel.display_name = "Arena Sport 2"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena3"
        channel.display_name = "Arena Sport 3"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena4"
        channel.display_name = "Arena Sport 4"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena5"
        channel.display_name = "Arena Sport 5"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "arena6"
        channel.display_name = "Arena Sport 6"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "skhd"
        channel.display_name = "SportKlub HD"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "sk1"
        channel.display_name = "SportKlub 1"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "sk2"
        channel.display_name = "SportKlub 2"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "sk3"
        channel.display_name = "SportKlub 3"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "sk4"
        channel.display_name = "SportKlub 4"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "eurosport1"
        channel.display_name = "EuroSport 1"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "eurosport2"
        channel.display_name = "EuroSport 2"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "fightbox"
        channel.display_name = "FightBox"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "fightchannel"
        channel.display_name = "Fight Channel"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "fightnetwork"
        channel.display_name = "Fight Network"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "extremesports"
        channel.display_name = "Extreme Sports"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "nauticalchannel"
        channel.display_name = "Nautical Channel"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "kreatortv"
        channel.display_name = "Kreator TV"
        sportsList.add(channel)
        channel = EpgChannel()
        channel.name = "loviribolov"
        channel.display_name = "Lov i Ribolov"
        sportsList.add(channel)
    }

    fun getCroatianList(): List<EpgChannel> {
        return croatianList
    }

    fun getCroatianExpandedList(): List<EpgChannel> {
        return croatianExpandedList
    }

    fun getEntertainmentList(): List<EpgChannel> {
        return entertainmentList
    }

    fun getMovieList(): List<EpgChannel> {
        return movieList
    }

    fun getDocumentaryList(): List<EpgChannel> {
        return documentaryList
    }

    fun getNewsList(): List<EpgChannel> {
        return newsList
    }

    fun getMusicList(): List<EpgChannel> {
        return musicList
    }

    fun getChildrenList(): List<EpgChannel> {
        return childrenList
    }

    fun getBosnianList(): List<EpgChannel> {
        return bosnianList
    }

    fun getSerbianList(): List<EpgChannel> {
        return serbianList
    }

    fun getMontenegroList(): List<EpgChannel> {
        return montenegroList
    }

    fun getSportsList(): List<EpgChannel> {
        return sportsList
    }


}