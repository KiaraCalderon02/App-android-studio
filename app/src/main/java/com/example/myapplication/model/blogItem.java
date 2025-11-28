package com.example.myapplication.model;

public class blogItem {
    public int id;
    public String titulo;
    public String imagen_name;
    public int autor_id;
    public String fecha_publicacion;
    public String contenido;

    public blogItem(int id, String titulo, String imagen_name, int autor_id, String fecha_publicacion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.imagen_name = imagen_name;
        this.autor_id = autor_id;
        this.fecha_publicacion = fecha_publicacion;
        this.contenido = contenido;
    }
}