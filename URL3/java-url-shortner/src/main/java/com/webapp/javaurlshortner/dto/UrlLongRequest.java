package com.webapp.javaurlshortner.dto;
// Data Transfer Object(dto) is an object that carries data between processes.
// DTOs are often used in conjunction with data access objects to retrieve data from a database.

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

// https://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/ApiModel.html
// The @ApiModel allows us to manipulate the meta data of a model from a simple description
// or name change to a definition of polymorphism
// References: https://www.javainuse.com/spring/boot_swagger_annotations

// The POST method has a UrlLongRequest as its request body. It is just a class with longUrl and expiresDate attributes.
// A model contains the data of the application. A data can be a single object or a collection of objects
// Here API Model contains data shared between end application and backend database

@ApiModel(description = "Request object for POST method")
public class UrlLongRequest {

    /*
    The @ApiModelProperty annotation allows us to control Swagger-specific definitions such as
    description (value), name, data type, example values, and allowed values for the model properties
     */

    @ApiModelProperty(required = true, notes = "Url to convert to short")
    private String longUrl;

    @ApiModelProperty(required = false, notes = "Optional expiration datetime of url")
    private Date expiresDate;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }
}