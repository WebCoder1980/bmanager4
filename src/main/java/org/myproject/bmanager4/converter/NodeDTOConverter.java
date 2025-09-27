package org.myproject.bmanager4.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.node.CommonNode;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class NodeDTOConverter {
    @Autowired
    private ObjectMapper objectMapper;

    public CommonNodeDTO toDTO(CommonNode source) {
        return new CommonNodeDTO(
                source.getElementId(),
                source.getLabels(),
                source.getProperties()
        );
    }

    public CommonNodeDTO toDTO(CommonNode source, Map<String, Map<String, Set<String>>> adjacencyMap) {
        String fromElementId = source.getElementId();
        for (Map.Entry<String, Set<CommonNode>> relation : source.getRelationNodes().entrySet()) {
            String relationType = relation.getKey();
            for (CommonNode toNode : relation.getValue()) {
                adjacencyMap.computeIfAbsent(fromElementId, (key) -> new TreeMap<>());
                adjacencyMap.get(fromElementId).computeIfAbsent(relationType, (key) -> new TreeSet<>());
                adjacencyMap.get(fromElementId)
                        .get(relationType)
                        .add(toNode.getElementId());
            }
        }

        return toDTO(source);
    }

    public CommonNodesStartAndEndDTO toDTO(Map<String, CommonNode> source) {
        Map<String, Map<String, Set<String>>> adjacencyMap = new TreeMap<>();

        Map<String, CommonNodeDTO> startNodes = source.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> toDTO(entry.getValue(), adjacencyMap)
                        )
                );

        Map<String, CommonNodeDTO> endNodes = new TreeMap<>();

        for (var entry : source.entrySet()) {
            for (var setNodes : entry.getValue().getRelationNodes().entrySet()) {
                for (var node : setNodes.getValue()) {
                    endNodes.computeIfAbsent(node.getElementId(), key -> toDTO(node));
                }
            }
        }

        return new CommonNodesStartAndEndDTO(startNodes, endNodes, adjacencyMap);
    }

    public CommonNode appliedNodeToCommonNode(Map<String, CommonNode> nodes, Node oldNode) {
            return nodes.computeIfAbsent(oldNode.elementId(), elementId -> {
                CommonNode newNode = new CommonNode();
                newNode.setElementId(elementId);

                newNode.setLabels(new TreeSet<>());
                oldNode.labels().forEach(label -> newNode.getLabels().add(label));

                newNode.setProperties(oldNode.asMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> e.getValue().toString()))
                );

                newNode.setRelationNodes(new TreeMap<>());

                return newNode;
            });
    }
}
