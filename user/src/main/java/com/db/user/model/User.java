package com.db.user.model;

import com.db.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * User object.
 */
@Data
@Getter
@Setter
@Entity
@Table(name = "systemuser",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private UserRole role;

    /**
     * PII data.
     */

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

}
