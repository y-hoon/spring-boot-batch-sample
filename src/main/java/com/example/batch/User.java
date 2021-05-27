package com.example.batch;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private LocalDateTime createDate;

    @Builder
    public User(Long id, String name, String phone, LocalDateTime createDate ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.createDate = createDate;
    }

    public void setPhone() {
        this.phone = this.phone + "a";
    }
}
