package com.base.boilerplate.auth.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "com_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "comRole")
@ToString
public class ComUser implements Persistable<Integer>, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "employee_number", unique = true, nullable = false)
    private String employeeNumber;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "social_security_number", nullable = false, unique = true)
    private String socialSecurityNumber;
    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;
    @Column(name = "login_password", nullable = false)
    private String loginPassword;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "address", nullable = false)
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_role_id")
    private ComRole comRole;
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public boolean isNew() {
        return createDate==null;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return this.loginPassword;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
