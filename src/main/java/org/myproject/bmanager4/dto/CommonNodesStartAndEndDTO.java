package org.myproject.bmanager4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonNodesStartAndEndDTO {
    private Map<String, CommonNodeDTO> startNodes;

    private Map<String, CommonNodeDTO> endNodes;
}
