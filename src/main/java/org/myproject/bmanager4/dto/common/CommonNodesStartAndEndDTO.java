package org.myproject.bmanager4.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonNodesStartAndEndDTO {
    private Map<String, CommonNodeDTO> startNodes;

    private Map<String, CommonNodeDTO> endNodes;

    private Map<String, Map<String, Set<String>>> adjacencyMap;
}
