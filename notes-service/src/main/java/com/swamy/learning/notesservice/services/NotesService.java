package com.swamy.learning.notesservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swamy.learning.notesservice.dto.NotesReq;

@Service
public interface NotesService {

	ResponseEntity<Object> saveNote(NotesReq req);

	ResponseEntity<Object> getAllNotes();

	ResponseEntity<Object> editNote(String id, NotesReq req);

	ResponseEntity<Object> deleteNoteById(String id);

}
