package com.swamy.learning.notesservice.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class NotesReq {
	private String title;
	private String freeTextNotes;
}
