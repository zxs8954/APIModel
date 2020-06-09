package com.autoTest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cases {
    private int id;
    private String url;
    private String uri;
    private String method;
    private String param;
    private String dependData;
    private String expect;
    private String actual;
}
