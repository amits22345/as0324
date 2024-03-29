package com.demo.amit.apps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.amit.apps.dto.Tool;
import com.demo.amit.apps.exception.DuplicateToolException;
import com.demo.amit.apps.model.Tools;
import com.demo.amit.apps.repository.ToolsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppListingService {

	private ToolsRepository toolsRepository;

	@Autowired
	public AppListingService(ToolsRepository toolsRepository) {
		this.toolsRepository = toolsRepository;
	}

	public void createToolEntry(Tool tool) throws DuplicateToolException {
		log.info("Inside the AppListingService.createToolEntry --- tool entry with code {}", tool.getToolCode());
		log.debug("InputDate {}", tool);

		try {
			toolsRepository.save(Tools.builder().toolCode(tool.getToolCode()).toolName(tool.getToolName())
					.toolType(tool.getToolType()).brand(tool.getBrand()).freeOnWeekends(tool.isFreeOnWeekends())
					.build());
		} catch (Exception e) {
			log.warn("Duplicate key {} is not allowed ", tool.getToolCode());
			throw new DuplicateToolException("Duplicate tool details with code ::"+tool.getToolCode());
		}

		log.info("Tool details is saved");
	}

}
