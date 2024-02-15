package com.example.platform.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String location;
    @Column(nullable = false)
    private Date join_date;
    @OneToOne(mappedBy = "user")
    private Profile profile;
    @OneToMany(mappedBy = "user1")
    private List<Connection> connectionList=new ArrayList<>();///when the user initiated the connection(1st user column in connection schema)

    @OneToMany(mappedBy = "user2")
    private List<Connection> connectionOf=new ArrayList<>();///when the user accepted the connection(2nd user column in connection schema)

    @OneToMany(mappedBy = "user")
    private List<Education> education;

    @OneToMany(mappedBy = "user")
    private List<Experience> experiences;

    @OneToMany(mappedBy = "user")
    private List<Skill> skills;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;
}