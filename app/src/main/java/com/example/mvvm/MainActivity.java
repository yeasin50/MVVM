package com.example.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int REQ_CODE= 101;
    public static final int REQED_CODE= 11;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton button= findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent,REQ_CODE);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllmotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update recycler
//                Toast.makeText(getApplicationContext(),"OnChabeged",Toast.LENGTH_SHORT).show();

                recyclerView.setAdapter(adapter);
                adapter.submitList(notes);
            }
        });

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
           }
       }).attachToRecyclerView(recyclerView);

       adapter.OnitemClickListner(new NoteAdapter.OnItemClickListner() {
           @Override
           public void OnItemClick(Note note) {

               Intent intent = new Intent(MainActivity.this,AddNote.class);
               intent.putExtra(AddNote.EXTRA_TITLE,note.getTitle() );
               intent.putExtra(AddNote.EXTRA_DES,note.getDescription() );
               intent.putExtra(AddNote.EXTRA_PRI,note.getPriority() );
               intent.putExtra(AddNote.EXTRA_ID,note.getId() );
               startActivityForResult(intent,REQED_CODE);
           }
       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.del_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_bar_dellAll){
            noteViewModel.deleteallNotes();
            return  true;
        }
        else
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE && resultCode == RESULT_OK){

                String t = data.getStringExtra(AddNote.EXTRA_TITLE);
                String d = data.getStringExtra(AddNote.EXTRA_DES);
                int p = data.getIntExtra(AddNote.EXTRA_PRI,1);

                Note note = new Note(t,d,p);
                noteViewModel.insert(note);
                Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT).show();

            }

        else  if (requestCode == REQED_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddNote.EXTRA_ID, -1);
            if(id==-1){
                Toast.makeText(MainActivity.this, "Unablke to Update", Toast.LENGTH_SHORT).show();
            }
            String title  = data.getStringExtra(AddNote.EXTRA_TITLE);
            String des =data.getStringExtra(AddNote.EXTRA_DES);
            int pr = data.getIntExtra(AddNote.EXTRA_PRI, 1);
            Note note = new Note(title,des,pr);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Updated",Toast.LENGTH_SHORT).show();

        }
        else {
                Log.i(TAG, "onActivityResult: ");
            }
    }


}
