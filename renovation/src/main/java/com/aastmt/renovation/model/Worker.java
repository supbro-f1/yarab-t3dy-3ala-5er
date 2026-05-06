package com.aastmt.renovation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profession;  

    @Column(nullable = false)
    private Double rating;       

    @Column(nullable = false)
    private boolean available = true;  


    public Worker() {}   

    public Worker(String name, String profession, Double rating) {
        this.name       = name;
        this.profession = profession;
        this.rating     = rating;
        this.available  = true;
    }

   
    public int getId()                    { return id; }
    public void setId(int id)             { this.id = id; }

    public String getName()                { return name; }
    public void setName(String name)       { this.name = name; }

    public String getProfession()                      { return profession; }
    public void setProfession(String profession)       { this.profession = profession; }

    public Double getRating()              { return rating; }
    public void setRating(Double rating)   { this.rating = rating; }

    public boolean isAvailable()                   { return available; }
    public void setAvailable(boolean available)    { this.available = available; }
}
