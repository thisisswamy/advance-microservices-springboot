package com.swamy.learning.notesservice.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDTO {
	private String title;
	private String freeTextNotes;
	private Date createdAt;
	private Date updatedAt;

}
