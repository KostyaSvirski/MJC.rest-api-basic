package com.epam.esm.service.command;

import com.epam.esm.service.command.impl.SearchByDescriptionCommand;
import com.epam.esm.service.command.impl.SearchByNameCommand;
import com.epam.esm.service.command.impl.SearchByTagCommand;
import com.epam.esm.service.command.impl.SortByParamCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TypesOfCommands {

    private SearchByNameCommand searchByName;
    private SearchByDescriptionCommand searchByDescription;
    private SearchByTagCommand searchByTag;
    private SortByParamCommand sortByParam;
    private Map<String, ActionCommand> mapOfCommands = new HashMap<>();

    @Autowired
    public void setSearchByName(SearchByNameCommand searchByName) {
        this.searchByName = searchByName;
        mapOfCommands.put("name", searchByName);
    }

    @Autowired
    public void setSearchByDescription(SearchByDescriptionCommand searchByDescription) {
        this.searchByDescription = searchByDescription;
        mapOfCommands.put("description", searchByDescription);
    }

    @Autowired
    public void setSearchByTag(SearchByTagCommand searchByTag) {
        this.searchByTag = searchByTag;
        mapOfCommands.put("tag", searchByTag);

    }

    @Autowired
    public void setSortByParam(SortByParamCommand sortByParam) {
        this.sortByParam = sortByParam;
        mapOfCommands.put("sort", sortByParam);
    }

    public Map<String, ActionCommand> getMapOfCommands() {
        return mapOfCommands;
    }
}
