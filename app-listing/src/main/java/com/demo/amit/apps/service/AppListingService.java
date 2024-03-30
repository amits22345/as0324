package com.demo.amit.apps.service;

import java.util.List;

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
			toolsRepository.save(requestToEntity(tool));
		} catch (Exception e) {
			log.warn("Duplicate key {} is not allowed ", tool.getToolCode());
			throw new DuplicateToolException("Duplicate tool details with code ::" + tool.getToolCode());
		}

		log.info("Tool details is saved");
	}

	private Tools requestToEntity(Tool tool) {
		// TODO Auto-generated method stub
		return Tools.builder().toolCode(tool.getToolCode()).toolName(tool.getToolName())
				.toolType(tool.getToolType()).brand(tool.getBrand()).freeOnWeekendsOrHolidays(tool.isFreeOnWeekendsOrHolidays())
				.rentperDay(tool.getRentperDay())
				.build();
	}

	public List<Tool> findAll() throws Exception {
		log.info("Inside the AppListingService.fetchAll");
		List<Tools> tools;
		try {
			tools = toolsRepository.findAll();
			return tools.stream().map(this::responseToDtoList).toList();

		} catch (Exception e) {
			log.error("Error while fetching the app list ");
			throw new Exception(e);
		}
	}

	private Tool responseToDtoList(Tools tools1) {
		return Tool.builder().brand(tools1.getBrand()).toolCode(tools1.getToolCode()).toolId(tools1.getToolId())
				.toolName(tools1.getToolName()).toolType(tools1.getToolType()).freeOnWeekendsOrHolidays(tools1.isFreeOnWeekendsOrHolidays())
				.rentperDay(tools1.getRentperDay())
				.build();

	}

}
