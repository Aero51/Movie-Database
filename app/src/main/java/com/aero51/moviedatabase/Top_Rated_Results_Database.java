package com.aero51.moviedatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Top_Rated_Result.class, version = 1)
@TypeConverters({Converters.class})
public abstract class Top_Rated_Results_Database extends RoomDatabase {

    private static Top_Rated_Results_Database instance;

    public abstract Top_Rated_Result_Dao top_rated_results_dao();

    public static synchronized Top_Rated_Results_Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Top_Rated_Results_Database.class, "results_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private Top_Rated_Result_Dao top_rated_result_dao;
        private PopulateDbAsyncTask(Top_Rated_Results_Database db){
            top_rated_result_dao =db.top_rated_results_dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
           // top_rated_results_dao.insert(new Top_Rated_Results("Title 1", "Description 1",1));
          // top_rated_results_dao.insert(new Note("Title 2", "Description 2",2));
          //  top_rated_results_dao.insert(new Note("Title 3", "Description 3",3));

            return null;
        }
    }



}
