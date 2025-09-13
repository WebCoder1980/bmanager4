package org.myproject.bmanager4.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonNode {
    private String elementId;

    private Set<String> labels;

    private Map<String, String> properties;

    private Map<String, Set<CommonNode>> relationNodes;
}
