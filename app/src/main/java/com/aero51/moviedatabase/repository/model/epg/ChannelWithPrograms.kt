package com.aero51.moviedatabase.repository.model.epg

class ChannelWithPrograms {
    var nearestTimePosition: Int? = null
    var nowPlayingPercentage: Int? = null
    var programsList: List<EpgProgram>? = null
    var channel: EpgChannel? = null
}