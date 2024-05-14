package jpaswing.entity;
import jakarta.persistence.*;

import javax.swing.*;

@Entity
public class Book {
    @Id
    //AUTO no funciona bien con SQLite porque intenta obtener el valor de una tabla de secuencias para cada tabla
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Book(){

    }
    public Book(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return id + " - " + name;
    }
}

