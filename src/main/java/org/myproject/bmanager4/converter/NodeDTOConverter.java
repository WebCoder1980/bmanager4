package org.myproject.bmanager4.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.node.CommonNode;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
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
                source.getProperties(),
                source.getRelationNodes()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                        entry2 -> entry2.getValue()
                                                .stream()
                                                .map(CommonNode::getElementId)
                                                .collect(Collectors.toSet())
                                )
                        )

        );
    }

    public Map<String, CommonNodeDTO> toDTO(Map<String, CommonNode> source) {
        return source.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> toDTO(entry.getValue())
                        )
                );
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
