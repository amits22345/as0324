package com.demo.amit.apps.rental.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:tools.properties")
public class ToolConfig {

   

	@Value("${tool.count}")
    private int toolCount;

    @Value("#{'${tool.app}'.split('##')}")
    private List<String> tools;

    public List<ToolDTO> loadTools() {
        List<ToolDTO> toolList = new ArrayList<>();

        for (int i = 0; i < toolCount; i++) {
            String[] toolProperties = tools.get(i).split(",");
            ToolDTO tool = new ToolDTO();
            tool.setToolName(toolProperties[0]);
            tool.setToolType(toolProperties[1]);
            tool.setToolCode(toolProperties[2]);
            tool.setBrand(toolProperties[3]);
            tool.setFreeOnWeekends(Boolean.parseBoolean(toolProperties[4]));
            tool.setFreeOnHolidays(Boolean.parseBoolean(toolProperties[5]));
            tool.setRentPerDay(new BigDecimal(toolProperties[6]));
            toolList.add(tool);
        }

        return toolList;
    }
}