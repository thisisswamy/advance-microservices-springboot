package com.swamy.learning.notesservice.docs;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.annotation.Generated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
	@Id
	private String id;
	private String title;
	private String freeTextNotes;
	private Long createdAt;
	private Long updatedAt;
	private String userId;

}
