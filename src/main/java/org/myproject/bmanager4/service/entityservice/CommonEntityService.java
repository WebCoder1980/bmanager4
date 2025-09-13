package org.myproject.bmanager4.service.entityservice;

import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.service.database.Neo4jDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonEntityService {
    @Autowired
    private Neo4jDatabaseService neo4jDatabaseService;

    public Map<String, CommonNodeDTO> getAll(String entity) {
        final String labelsFilter = formatLabelsFilter(entity);
        final String queryStr = String.format("""
                        MATCH (n%s)
                        OPTIONAL MATCH (n%s)-[r]->(m)
                        RETURN n, r, m
                        """,
                labelsFilter,
                labelsFilter
        );

        return neo4jDatabaseService.getQueryNRM(queryStr);
    }

    private String formatLabelsFilter(String entity) {
        if (entity == null) {
            return "";
        }

        return String.format(":%s", entity);
    }
}
