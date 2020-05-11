package org.qr.ajaxServlet;

import org.codehaus.jackson.map.ObjectMapper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

@AllArgsConstructor
@Getter

public class JsonResponce {

    private final boolean isSuccess;
    private final String error;
    private final Object data;

    @SneakyThrows
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
