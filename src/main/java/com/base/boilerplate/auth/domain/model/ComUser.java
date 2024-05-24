package com.base.boilerplate.auth.domain.model;

import com.base.boilerplate.api.sample.domain.model.Sample;
import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
import com.base.boilerplate.auth.dto.UserRequestDTO;
import com.base.boilerplate.util.AuditableEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ComUser extends AuditableEntity implements Persistable<Integer>, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password",  nullable = false)
    private String password;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "language")
    private String language;
    @Column(name = "login_type")
    private String loginType;
    @Column(name = "login_token")
    private String loginToken;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "gender")
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_role_id")
    private ComRole comRole;



    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public String getAddress() {
        return address;
    }

    //    @Override
//    public boolean isNew() {h
//        return createDate==null;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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

    public static ComUser convertToEntity(UserRequestDTO requestDto) {
        return ComUser.builder()
                .id(requestDto.getId())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .language(requestDto.getLanguage())
                .loginType(requestDto.getLoginType())
                .loginToken(requestDto.getLoginToken())
                .address(requestDto.getAddress())
                .gender(requestDto.getGender())
                .phone(requestDto.getPhone())
                .build();
    }
}
