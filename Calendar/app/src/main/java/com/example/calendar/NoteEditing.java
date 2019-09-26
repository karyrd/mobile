package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteEditing extends AppCompatActivity {

    private Intent intent;
    EditText input;
    private String old_note;
    private String new_note;
    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);
        input = findViewById(R.id.textinput2);

        intent = getIntent();
        Bundle extras = intent.getExtras();
        old_note = extras.getString("selectedNote");
        date = extras.getLong("selectedDate");

        input.setText(old_note);
    }

    public void DeleteNote(View view)
    {
        intent.putExtra("mode", "delete");
        intent.putExtra("oldNote", old_note);
        intent.putExtra("date", date);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void ChangeNote(View view)
    {
        intent.putExtra("mode", "change");
        intent.putExtra("oldNote", old_note);
        intent.putExtra("date", date);
        new_note = input.getText().toString();
        intent.putExtra("newNote", new_note);
        setResult(RESULT_OK, intent);
        finish();
    }

}
