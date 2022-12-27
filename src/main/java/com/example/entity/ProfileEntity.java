package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
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
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name = "photo_id")
    private String photoId;
    @JoinColumn(name = "photo_id", insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity photo;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role = ProfileRole.ROLE_USER;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.NOT_ACTIVE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private Boolean visible = Boolean.TRUE;
}
