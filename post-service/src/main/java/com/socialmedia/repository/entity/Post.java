package com.socialmedia.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Post extends BaseEntity{

    @Id
    private String id;
    private String userId;
    private String username;
    private String userAvatar;
    private String content;
    private List<String> mediaUrls;
    //@DBRef // String yaptığımız için buna gerek yok
    private List<String> likes; //user beğeniyor,  userId nin tutulduğu bir liste
    private List<String> comments;

}
