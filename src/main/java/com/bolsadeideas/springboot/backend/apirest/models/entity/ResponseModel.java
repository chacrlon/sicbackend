package com.bolsadeideas.springboot.backend.apirest.models.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {

    public Integer code;
    public String message;
    public Object data;
    public Integer status;
    public Long  id;

}
