package com.devsuperior.dsmovie.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String title;
    @Setter
    private Double score;
    @Setter
    private Integer count;
    @Setter
    private String image;

    @OneToMany(mappedBy = "id.movie")
    private Set<Score> scores = new HashSet<>();
}
