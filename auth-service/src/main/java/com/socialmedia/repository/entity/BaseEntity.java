package com.socialmedia.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass// üst sınıf olduğu için database de ayrı bir tablo açmaz..
public class BaseEntity {

    private Long createDate;
    private Long updateDate;
}
