package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Note> adapter;
    private ArrayAdapter<String> dayadapter;
    private EditText inputText;
    private List<Note> notes;
    private List<String> daynotes;
    private CalendarView calendarView;
    private long selected_date;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        calendarView = findViewById(R.id.calendar);
        inputText = findViewById(R.id.textinput);

        notes = JSONHelper.importFromJSON(this);
        if(notes == null)
            notes = new ArrayList<>();
        daynotes = new ArrayList<>();
        dayadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daynotes);
        selected_date = calendarView.getDate();

        ImportSelectedDateNotes();
        listView.setAdapter(dayadapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year,
                                            int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                selected_date = c.getTimeInMillis();

                ImportSelectedDateNotes();
            }
        });

        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent event)
            {
                InputMethodManager inputManager =
                        (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(actionID == EditorInfo.IME_ACTION_DONE)
                {
                    inputManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                else
                {
                    inputManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String note = daynotes.get(position);

                Intent intent = new Intent(MainActivity.this, NoteEditing.class);

                intent.putExtra("selectedNote", note);
                intent.putExtra("selectedDate", selected_date);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String old_note = data.getStringExtra("oldNote");
                long date = data.getLongExtra("date", 0);

                switch(data.getStringExtra("mode"))
                {
                    case "delete" :
                    {
                        for(Note note : notes)
                        {
                            if(note.getNote().equals(old_note) && LongToDate(note.getDate()).equals(LongToDate(date)))
                            {
                                notes.remove(note);
                                ImportSelectedDateNotes();

                                Toast.makeText(this, "Note deleted.",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        break;
                    }
                    case "change" :
                    {
                        String new_note = data.getStringExtra("newNote");
                        for(Note note : notes)
                        {
                            if(note.getNote().equals(old_note) && LongToDate(note.getDate()).equals(LongToDate(date)))
                            {
                                notes.remove(note);
                                notes.add(new Note(new_note, date));
                                ImportSelectedDateNotes();

                                Toast.makeText(this, "Note changed.",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        break;
                    }
                }

            }
        }
        Toast.makeText(this, "OnActivityResult finished", Toast.LENGTH_LONG).show();
    }

    public void AddNote(View view)
    {
        if("".equals(inputText.getText().toString())) {
            Toast.makeText(this, "Write a note first!",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            try {
                String note = inputText.getText().toString();
                long date = selected_date;
                Note new_note = new Note(note, date);
                notes.add(new_note);
                daynotes.add(new_note.getNote());
                dayadapter.notifyDataSetChanged();
                inputText.setText("");

            } catch (Exception e) {
                Toast.makeText(this, "An error occurred while adding a note",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void SaveNotes(View view)
    {
        boolean result = JSONHelper.exportToJSON(this, notes);
        if(result){
            Toast.makeText(this, "Notes saved", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "An error occurred while saving notes", Toast.LENGTH_LONG).show();
        }
    }

    public void ImportSelectedDateNotes()
    {
        daynotes.clear();

        for (Note note : notes) {
            if(LongToDate(note.getDate()).equals(LongToDate(selected_date))){
                daynotes.add(note.getNote());
            }
        }

        dayadapter.notifyDataSetChanged();
    }

    public String LongToDate(long date_long)
    {
        String str = new SimpleDateFormat
                ("dd/MM/yyyy").format(new Date(date_long));
        return str;
    }
}
