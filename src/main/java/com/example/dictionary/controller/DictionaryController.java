package com.example.dictionary.controller;

import com.example.dictionary.dto.CreateDictionaryDto;
import com.example.dictionary.dto.DictionaryDto;
import com.example.dictionary.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dictionaries")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping
    @Operation(summary = "Create a new dictionary",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Dictionary created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DictionaryDto.class)))
            })
    public DictionaryDto createDictionary(@RequestBody CreateDictionaryDto dictionaryDto) {
        return dictionaryService.createDictionary(dictionaryDto);
    }

    @GetMapping
    @Operation(summary = "Get all dictionaries",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of dictionaries",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DictionaryDto.class))
                            )
                    )
            })
    public List<DictionaryDto> getAllDictionaries() {
        return dictionaryService.getAllDictionaries();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dictionary by ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Dictionary found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DictionaryDto.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public DictionaryDto getDictionaryById(@PathVariable UUID id) {
        try {
            return dictionaryService.getDictionaryById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete dictionary by ID",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Dictionary deleted successfully"),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found")
            })
    public void deleteDictionary(@PathVariable UUID id) {
        try {
            dictionaryService.deleteDictionary(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", id));
        }
    }
}