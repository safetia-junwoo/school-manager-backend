package com.base.boilerplate.auth.domain.repository;

import com.base.boilerplate.auth.domain.model.ComUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ComUser,Integer> {

    Optional<ComUser> findById(Integer id);
    @Query("select u from ComUser u join fetch u.comRole where u.email = :email")
    Optional<ComUser> findByLoginId(String email);
    List<ComUser> findAllByIdNotIn(List<Integer> ids);

}
