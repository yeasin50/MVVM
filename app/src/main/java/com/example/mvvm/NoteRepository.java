package com.example.mvvm;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allnotes = noteDao.getAllNote();
    }

    public void insert(Note note){
        new InsertNoteAsyncroTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncrTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteAsyncroTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotes(noteDao).execute();
    }
    public LiveData<List<Note>> getAllnotes(){
        return allnotes;
    }

    private static class InsertNoteAsyncroTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;
        private InsertNoteAsyncroTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static  class UpdateAsyncrTask extends AsyncTask<Note, Void,Void>{

        private NoteDao noteDao;
        private  UpdateAsyncrTask(NoteDao dao){
            this.noteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncroTask extends AsyncTask<Note, Void,Void>{

        private NoteDao noteDao;
        private DeleteAsyncroTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotes extends AsyncTask<Void,Void, Void>{

       private NoteDao noteDao;

       private DeleteAllNotes(NoteDao noteDao) {
           this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
           noteDao.deleteAllNote();
            return null;
        }
    }
}
