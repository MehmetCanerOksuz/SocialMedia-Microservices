package com.socialmedia.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Comment extends BaseEntity{

    @Id
    private String id;
    private String userId; //yorum yapan kişinin id si
    private String postId;
    private String username;//yorum yapan kişinin adı
    private String userAvatar;
    private String comment;
    private List<String> subCommentId;
    private List<String> commentLikes; //user beğeniyor,  userId nin tutulduğu bir liste
}
