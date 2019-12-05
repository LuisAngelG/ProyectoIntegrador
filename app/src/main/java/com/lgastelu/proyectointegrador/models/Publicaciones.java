package com.lgastelu.proyectointegrador.models;

public class Publicaciones {

    private Long pub_id;
    private String pub_contenido;
    private String user_id;

    public Long getPub_id() {
        return pub_id;
    }

    public void setPub_id(Long pub_id) {
        this.pub_id = pub_id;
    }

    public String getPub_contenido() {
        return pub_contenido;
    }

    public void setPub_contenido(String pub_contenido) {
        this.pub_contenido = pub_contenido;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Publicaciones{" +
                "pub_id=" + pub_id +
                ", pub_contenido='" + pub_contenido + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
