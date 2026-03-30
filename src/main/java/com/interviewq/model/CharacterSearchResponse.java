package com.interviewq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterSearchResponse {
    private Page page;
    private List<Character> characters;
}

