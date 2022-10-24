package com.baz.wizeline.javamaven.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiPublicaModel implements Serializable {

    /**
     * Constante serialVersionUID.
     */
    private static final long serialVersionUID = 4676905652686424400L;
    @JsonProperty("postId")
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

    public ApiPublicaModel() {
        super();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void ApiPublicaModel(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApiPublicaModel [postId=");
        builder.append(postId);
        builder.append(", id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", email=");
        builder.append(email);
        builder.append(", body=");
        builder.append(body);
        builder.append("]");
        return builder.toString();
    }

}
