package com.demo.amit.apps.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.amit.apps.dto.Tool;
import com.demo.amit.apps.exception.DuplicateToolException;
import com.demo.amit.apps.service.AppListingService;

@RestController
@RequestMapping("/api/app-listing")
public class AppListingController {
	private final AppListingService appListingService;

	
    public AppListingController(AppListingService appListingService) {
        this.appListingService = appListingService;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Tool request) throws DuplicateToolException {
        appListingService.createToolEntry(request);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tool> findAll() throws Exception {
        return appListingService.findAll();
    }
    
}
