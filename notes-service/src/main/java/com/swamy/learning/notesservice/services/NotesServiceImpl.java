package com.swamy.learning.notesservice.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swamy.learning.notesservice.docs.Note;
import com.swamy.learning.notesservice.dto.NotesReq;
import com.swamy.learning.notesservice.exceptions.GenericNotesException;
import com.swamy.learning.notesservice.repos.NoteRepo;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NoteRepo noteRepo;
	
	Calendar calendar = Calendar.getInstance();
	
	
	public boolean areAllFieldsFilled(NotesReq req) {
		if(req.getTitle() == null || req.getTitle() == "" || req.getFreeTextNotes() == null || req.getFreeTextNotes()=="") {
			return false;
		}
		return true;
	}

	@Override
	public ResponseEntity<Object> saveNote(NotesReq req) {
		if(areAllFieldsFilled(req)) {
			Note note = new Note();
			note.setTitle(req.getTitle());
			note.setFreeTextNotes(req.getFreeTextNotes());
			note.setCreatedAt(calendar.getTimeInMillis());
			note.setUpdatedAt(calendar.getTimeInMillis());
			note.setUserId("niroop123");
			noteRepo.save(note);
		}else {
			throw new GenericNotesException("All fields are mandatory !", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Note Saved Successfully");
	}

	@Override
	public ResponseEntity<Object> getAllNotes() {
		List<Note> allNotes = noteRepo.findAll();
		return ResponseEntity.ok(allNotes);
	}

	@Override
	public ResponseEntity<Object> editNote(String id, NotesReq req) {
		Optional<Note> foundNote = noteRepo.findById(id);
		if(foundNote.isPresent()) {
			Note editableNote = foundNote.get();
			editableNote.setTitle(req.getTitle());
			editableNote.setFreeTextNotes(req.getFreeTextNotes());
			editableNote.setUpdatedAt(calendar.getTimeInMillis());
			try {
				noteRepo.save(editableNote);
			}catch(RuntimeException e) {
				throw new GenericNotesException("something went wrong !",HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok("note updated successfully !!");
				
		}
		throw new GenericNotesException("invalid id",HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Object> deleteNoteById(String id) {
		Optional<Note> findById = noteRepo.findById(id);
		if(findById.isPresent()) {
			noteRepo.deleteById(id);
			return ResponseEntity.ok("Note deleted successfully..!");
		}
		throw new GenericNotesException("Notes allready deleted/not existed.", HttpStatus.BAD_REQUEST);
	}

}
