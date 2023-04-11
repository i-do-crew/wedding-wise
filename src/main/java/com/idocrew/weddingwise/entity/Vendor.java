package com.idocrew.weddingwise.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="vendors")
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
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;
}
