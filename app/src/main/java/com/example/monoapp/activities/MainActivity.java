package com.example.monoapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.monoapp.R;
import com.example.monoapp.adapters.NotesAdapter;
import com.example.monoapp.database.NotesDatabase;
import com.example.monoapp.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener((v) ->{
            startActivityForResult(
                    new Intent(getApplicationContext(), CreateNoteActivity.class),
                    REQUEST_CODE_ADD_NOTE
            );
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes();
    }

    private void getNotes() {
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getNotesDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

            }
        }
        new GetNoteTask().execute();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes();
        }
    }
}
