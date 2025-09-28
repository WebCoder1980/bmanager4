package org.myproject.bmanager4.service.entityservice;

import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.database.Neo4jDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonEntityService {
    @Autowired
    private Neo4jDatabaseService neo4jDatabaseService;

    public CommonNodesStartAndEndDTO getAll(String entity) {
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

    public CommonNodeDTO create(String entity, Map<String, Object> properties) {
        final String labelsFilter = formatLabelsFilter(entity);
        final String queryStr = String.format("""
                        CREATE (n%s)
                        SET n += $properties
                        RETURN n
                        """,
                        labelsFilter
                );

        return neo4jDatabaseService.commonCreate(queryStr, properties);
    }

    private String formatLabelsFilter(String entity) {
        if (entity == null) {
            return "";
        }

        return String.format(":%s", entity);
    }
}
