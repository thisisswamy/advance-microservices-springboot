package com.swamy.learning.notesservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.learning.notesservice.dto.NotesReq;
import com.swamy.learning.notesservice.services.NotesService;

@RestController
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/save") //CREATE
	public ResponseEntity<Object> saveNote(@RequestBody NotesReq req){	
		return notesService.saveNote(req);
	}
	
	@GetMapping("/allNotes") //READ
	public  ResponseEntity<Object> allNotes(){
		return notesService.getAllNotes();
	}
	@PutMapping("/edit/{id}") //UPDATE
	public ResponseEntity<Object> editNote(@PathVariable String id,@RequestBody NotesReq req){	
		return notesService.editNote(id,req);
	}
	
	@DeleteMapping("/delete/{id}") //DELETE
	public ResponseEntity<Object> deleteNote(@PathVariable String id){
		return notesService.deleteNoteById(id);
	}
	

}
