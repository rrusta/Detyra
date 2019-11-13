package org.auk.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String authorName;
    String commentBody;
    Date createdAt;
    //@ManyToOne
  //  @JoinColumn(name = "article_id")
//    Article article;

}
