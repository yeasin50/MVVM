package com.example.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddNote extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DES = "com.example.mvvm.EXTRA_DES";
    public static final String EXTRA_PRI = "com.example.mvvm.EXTRA_PRI";
    public static final String EXTRA_ID = "com.example.mvvm.EXTRA_ID";
    EditText editTexttitle;
    EditText editTextDescrp;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextDescrp = findViewById(R.id.editText2);
        editTexttitle = findViewById(R.id.editText);
        numberPicker = findViewById(R.id.numpcker);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTexttitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescrp.setText(intent.getStringExtra(EXTRA_DES));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRI, 0));
        }

        else {
            setTitle("Add Note");
        }

    }

    private void SAVENOTE() {
        String title = editTexttitle.getText().toString().trim();
        String des = editTextDescrp.getText().toString().trim();
        int p = numberPicker.getValue();

        if(title.isEmpty() || des.isEmpty()){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DES, des);
        intent.putExtra(EXTRA_PRI, p);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id !=-1){
            intent.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_bar:
                SAVENOTE();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
