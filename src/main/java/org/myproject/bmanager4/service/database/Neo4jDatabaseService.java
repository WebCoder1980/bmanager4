package org.myproject.bmanager4.service.database;

import org.myproject.bmanager4.converter.NodeDTOConverter;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.node.CommonNode;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Neo4jDatabaseService {
    private Driver driver;

    @Autowired
    private NodeDTOConverter nodeDTOConverter;

    @Autowired
    public Neo4jDatabaseService(Driver driver) {
        this.driver = driver;

        driver.verifyConnectivity();
    }

    public CommonNodesStartAndEndDTO getQueryNRM(String queryStr) {
        Map<String, CommonNode> nodes = new HashMap<>();

        ExecutableQuery query = driver.executableQuery(queryStr);
        EagerResult eagerResult = query.execute();

        for (Record record : eagerResult.records()) {
            List<Pair<String, Value>> fields = record.fields();

            if (fields.size() != 3 || !fields.get(0).key().equals("n") || !fields.get(1).key().equals("r") || !fields.get(2).key().equals("m")) {
                throw new IllegalArgumentException();
            }

            Node startAppliedNode = fields.get(0).value().asNode();
            Relationship relationship = null;
            Node endAppliedNode = null;

            CommonNode startNode = nodeDTOConverter.appliedNodeToCommonNode(nodes, startAppliedNode);
            CommonNode endNode = null;

            boolean hasRelationship = true;

            try {
                relationship = fields.get(1).value().asRelationship();
                endAppliedNode = fields.get(2).value().asNode();

                endNode = nodeDTOConverter.appliedNodeToCommonNode(nodes, endAppliedNode);
            } catch (Exception e) {
                hasRelationship = false;
            }

            if (hasRelationship) {
                startNode.getRelationNodes()
                        .computeIfAbsent(relationship.type(), i -> new HashSet<>())
                        .add(endNode);
            }
        }

        return nodeDTOConverter.toDTO(nodes);
    }

    public void executeQuery(String queryStr) {
        driver.executableQuery(queryStr).execute();
    }

    public CommonNodeDTO executeNQuery(String queryStr) {
        ExecutableQuery query = driver.executableQuery(queryStr);
        EagerResult eagerResult = query.execute();

        Record record = eagerResult.records().getFirst();
        List<Pair<String, Value>> fields = record.fields();

        if (fields.size() != 1 || !fields.get(0).key().equals("n")) {
            throw new IllegalArgumentException();
        }

        Node appliedNode = fields.getFirst().value().asNode();
        CommonNode commonNode = nodeDTOConverter.appliedNodeToCommonNode(new TreeMap<>(), appliedNode);

        return nodeDTOConverter.toDTO(commonNode);
    }
}
