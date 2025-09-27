package org.myproject.bmanager4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonNodeDTO {
    private String elementId;

    private Set<String> labels;

    private Map<String, Object> properties;
}
