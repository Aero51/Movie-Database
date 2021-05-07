package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;

import java.util.List;

@Entity(tableName = "tv_show_details")
public class TvShowDetailsResponse {

    //TODO change values according to json below
    @PrimaryKey(autoGenerate = true)
    private int db_id;
    private List<TvShowGenresResponse.TvShowGenre> genres;
    private Integer id;
    private String imdb_id;
    private String original_title;
    private String overview;
    private Double popularity;
    private List<TvShowDetailsResponse.ProductionCompany> production_companies;
    private String release_date;
    private Integer revenue;
    private Integer runtime;
    private String tagline;
    private String title;
    private Double vote_average;
    private Integer vote_count;
    private String backdrop_path;
    private String poster_path;


    /*
 {
    "backdrop_path": "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
    "created_by": [
        {
            "id": 9813,
            "credit_id": "5256c8c219c2956ff604858a",
            "name": "David Benioff",
            "gender": 2,
            "profile_path": "/xvNN5huL0X8yJ7h3IZfGG4O2zBD.jpg"
        },
        {
            "id": 228068,
            "credit_id": "552e611e9251413fea000901",
            "name": "D. B. Weiss",
            "gender": 2,
            "profile_path": "/2RMejaT793U9KRk2IEbFfteQntE.jpg"
        }
    ],
    "episode_run_time": [
        60
    ],
    "first_air_date": "2011-04-17",
    "genres": [
        {
            "id": 10765,
            "name": "Sci-Fi & Fantasy"
        },
        {
            "id": 18,
            "name": "Drama"
        },
        {
            "id": 10759,
            "name": "Action & Adventure"
        }
    ],
    "homepage": "http://www.hbo.com/game-of-thrones",
    "id": 1399,
    "in_production": false,
    "languages": [
        "en"
    ],
    "last_air_date": "2019-05-19",
    "last_episode_to_air": {
        "air_date": "2019-05-19",
        "episode_number": 6,
        "id": 1551830,
        "name": "The Iron Throne",
        "overview": "In the aftermath of the devastating attack on King's Landing, Daenerys must face the survivors.",
        "production_code": "806",
        "season_number": 8,
        "still_path": "/3x8tJon5jXFa1ziAM93hPKNyW7i.jpg",
        "vote_average": 4.814,
        "vote_count": 129
    },
    "name": "Game of Thrones",
    "next_episode_to_air": null,
    "networks": [
        {
            "name": "HBO",
            "id": 49,
            "logo_path": "/tuomPhY2UtuPTqqFnKMVHvSb724.png",
            "origin_country": "US"
        }
    ],
    "number_of_episodes": 73,
    "number_of_seasons": 8,
    "origin_country": [
        "US"
    ],
    "original_language": "en",
    "original_name": "Game of Thrones",
    "overview": "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
    "popularity": 577.112,
    "poster_path": "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
    "production_companies": [
        {
            "id": 76043,
            "logo_path": "/9RO2vbQ67otPrBLXCaC8UMp3Qat.png",
            "name": "Revolution Sun Studios",
            "origin_country": "US"
        },
        {
            "id": 12525,
            "logo_path": null,
            "name": "Television 360",
            "origin_country": ""
        },
        {
            "id": 5820,
            "logo_path": null,
            "name": "Generator Entertainment",
            "origin_country": ""
        },
        {
            "id": 12526,
            "logo_path": null,
            "name": "Bighead Littlehead",
            "origin_country": ""
        }
    ],
    "production_countries": [
        {
            "iso_3166_1": "GB",
            "name": "United Kingdom"
        },
        {
            "iso_3166_1": "US",
            "name": "United States of America"
        }
    ],
    "seasons": [
        {
            "air_date": "2010-12-05",
            "episode_count": 176,
            "id": 3627,
            "name": "Specials",
            "overview": "",
            "poster_path": "/kMTcwNRfFKCZ0O2OaBZS0nZ2AIe.jpg",
            "season_number": 0
        },
        {
            "air_date": "2011-04-17",
            "episode_count": 10,
            "id": 3624,
            "name": "Season 1",
            "overview": "Trouble is brewing in the Seven Kingdoms of Westeros. For the driven inhabitants of this visionary world, control of Westeros' Iron Throne holds the lure of great power. But in a land where the seasons can last a lifetime, winter is coming...and beyond the Great Wall that protects them, an ancient evil has returned. In Season One, the story centers on three primary areas: the Stark and the Lannister families, whose designs on controlling the throne threaten a tenuous peace; the dragon princess Daenerys, heir to the former dynasty, who waits just over the Narrow Sea with her malevolent brother Viserys; and the Great Wall--a massive barrier of ice where a forgotten danger is stirring.",
            "poster_path": "/zwaj4egrhnXOBIit1tyb4Sbt3KP.jpg",
            "season_number": 1
        },
        {
            "air_date": "2012-04-01",
            "episode_count": 10,
            "id": 3625,
            "name": "Season 2",
            "overview": "The cold winds of winter are rising in Westeros...war is coming...and five kings continue their savage quest for control of the all-powerful Iron Throne. With winter fast approaching, the coveted Iron Throne is occupied by the cruel Joffrey, counseled by his conniving mother Cersei and uncle Tyrion. But the Lannister hold on the Throne is under assault on many fronts. Meanwhile, a new leader is rising among the wildings outside the Great Wall, adding new perils for Jon Snow and the order of the Night's Watch.",
            "poster_path": "/5tuhCkqPOT20XPwwi9NhFnC1g9R.jpg",
            "season_number": 2
        },
        {
            "air_date": "2013-03-31",
            "episode_count": 10,
            "id": 3626,
            "name": "Season 3",
            "overview": "Duplicity and treachery...nobility and honor...conquest and triumph...and, of course, dragons. In Season 3, family and loyalty are the overarching themes as many critical storylines from the first two seasons come to a brutal head. Meanwhile, the Lannisters maintain their hold on King's Landing, though stirrings in the North threaten to alter the balance of power; Robb Stark, King of the North, faces a major calamity as he tries to build on his victories; a massive army of wildlings led by Mance Rayder march for the Wall; and Daenerys Targaryen--reunited with her dragons--attempts to raise an army in her quest for the Iron Throne.",
            "poster_path": "/7d3vRgbmnrRQ39Qmzd66bQyY7Is.jpg",
            "season_number": 3
        },
        {
            "air_date": "2014-04-06",
            "episode_count": 10,
            "id": 3628,
            "name": "Season 4",
            "overview": "The War of the Five Kings is drawing to a close, but new intrigues and plots are in motion, and the surviving factions must contend with enemies not only outside their ranks, but within.",
            "poster_path": "/dniQ7zw3mbLJkd1U0gdFEh4b24O.jpg",
            "season_number": 4
        },
        {
            "air_date": "2015-04-12",
            "episode_count": 10,
            "id": 62090,
            "name": "Season 5",
            "overview": "The War of the Five Kings, once thought to be drawing to a close, is instead entering a new and more chaotic phase. Westeros is on the brink of collapse, and many are seizing what they can while the realm implodes, like a corpse making a feast for crows.",
            "poster_path": "/527sR9hNDcgVDKNUE3QYra95vP5.jpg",
            "season_number": 5
        },
        {
            "air_date": "2016-04-24",
            "episode_count": 10,
            "id": 71881,
            "name": "Season 6",
            "overview": "Following the shocking developments at the conclusion of season five, survivors from all parts of Westeros and Essos regroup to press forward, inexorably, towards their uncertain individual fates. Familiar faces will forge new alliances to bolster their strategic chances at survival, while new characters will emerge to challenge the balance of power in the east, west, north and south.",
            "poster_path": "/zvYrzLMfPIenxoq2jFY4eExbRv8.jpg",
            "season_number": 6
        },
        {
            "air_date": "2017-07-16",
            "episode_count": 7,
            "id": 81266,
            "name": "Season 7",
            "overview": "The long winter is here. And with it comes a convergence of armies and attitudes that have been brewing for years.",
            "poster_path": "/3dqzU3F3dZpAripEx9kRnijXbOj.jpg",
            "season_number": 7
        },
        {
            "air_date": "2019-04-14",
            "episode_count": 6,
            "id": 107971,
            "name": "Season 8",
            "overview": "The Great War has come, the Wall has fallen and the Night King's army of the dead marches towards Westeros. The end is here, but who will take the Iron Throne?",
            "poster_path": "/39FHkTLnNMjMVXdIDwZN8SxYqD6.jpg",
            "season_number": 8
        }
    ],
    "spoken_languages": [
        {
            "english_name": "English",
            "iso_639_1": "en",
            "name": "English"
        }
    ],
    "status": "Ended",
    "tagline": "Winter Is Coming",
    "type": "Scripted",
    "vote_average": 8.4,
    "vote_count": 14227
}

    */


    public TvShowDetailsResponse() {
    }



    public List<TvShowGenresResponse.TvShowGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<TvShowGenresResponse.TvShowGenre> genres) {
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public List<TvShowDetailsResponse.ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<TvShowDetailsResponse.ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public static class ProductionCompany {

        private Integer id;
        private String logo_path;
        private String name;
        private String origin_country;


        public ProductionCompany() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

}
