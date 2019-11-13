package org.auk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(unique=true)
    private String slug;
    private String content;
    private String published;
    Date createdAt ;
    Date updatedAt;
 //   @OneToMany(mappedBy = "article")
 //   Set<Comment> comments;

    @Override
    public String toString() {
        return Article.class + "\n [ID]: " + id + " [Title]: " + title;
    }
}