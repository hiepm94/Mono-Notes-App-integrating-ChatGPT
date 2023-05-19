package com.example.monoapp.listener;

import com.example.monoapp.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
