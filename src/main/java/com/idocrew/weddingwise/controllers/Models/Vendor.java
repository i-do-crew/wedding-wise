package com.idocrew.weddingwise.controllers.Models;
import jakarta.persistence.*;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="title", columnDefinition="varchar(50)")
    private String title;
    @Column(name="categories_id", columnDefinition="bigint(20)")
    private String categoriesId;
    @Column(name="city_state", columnDefinition="varchar(25)")
    private String cityState;
    @Column(name="about", columnDefinition="longtext")
    private String about;
}
