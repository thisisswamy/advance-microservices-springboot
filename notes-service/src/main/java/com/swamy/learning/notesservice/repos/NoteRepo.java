package com.swamy.learning.notesservice.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.swamy.learning.notesservice.docs.Note;

public interface NoteRepo extends MongoRepository<Note, String> {
	
}


