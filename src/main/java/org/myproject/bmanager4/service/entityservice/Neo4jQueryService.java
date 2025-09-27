package org.myproject.bmanager4.service.entityservice;

import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.database.Neo4jDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Neo4jQueryService {
    @Autowired
    private Neo4jDatabaseService neo4jDatabaseService;

    public CommonNodesStartAndEndDTO getAll(String queryStr) {
        return neo4jDatabaseService.getQueryNRM(queryStr);
    }

    public void execute(String queryStr) {
        neo4jDatabaseService.executeQuery(queryStr);
    }

    public CommonNodeDTO executeNQuery(String queryStr) {
        return neo4jDatabaseService.executeNQuery(queryStr);
    }
}
