package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "origin_name")
    private String originName;

    @Column
    private Integer size;

    @Column
    private String extension;

    @Column
    private String path;

    @Column
    private Integer duration;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
