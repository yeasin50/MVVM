package com.example.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized  NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)// onCreate Insertion
                    .build();

        }
        return instance;
    }
    public static RoomDatabase.Callback  callback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedDbAsyncTask(instance).execute();
        }
    };

    public static class PopulatedDbAsyncTask extends AsyncTask<Void, Void, Void>{
        NoteDao noteDao;

        private PopulatedDbAsyncTask(NoteDatabase database){
            noteDao = database.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","description 1", 1));
            noteDao.insert(new Note("Title 2","description 2", 2));
            noteDao.insert(new Note("Title 3","description 3", 3));
            return null;
        }
    }
}
