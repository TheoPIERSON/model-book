package com.masphoto.masphoto.entities;

import com.masphoto.masphoto.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jwt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    private boolean desactive;
    private boolean expire;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
