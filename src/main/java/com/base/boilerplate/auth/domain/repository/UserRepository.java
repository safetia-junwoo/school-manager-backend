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
    @Query("select u from ComUser u join fetch u.comRole where u.loginId = :loginId")
    Optional<ComUser> findByLoginId(String loginId);
    List<ComUser> findAllByIdNotIn(List<Integer> ids);

//    @Transactional
//    @Modifying
//    @Query("update ComUser cu set cu.comRole = :comRole, cu.securityRole = :securityRole where cu.id in (:userIds)")
//    Integer updateRoleInUserByUserIds(ComRole comRole, String securityRole, List<Integer> userIds);

    @Query("select u from ComUser u where u.employeeNumber = :employeeNumber")
    ComUser findByVaccineEmployeeNumber(String employeeNumber);

    Optional<ComUser> findByEmployeeNumber(String employeeNumber);

    @Query("select u from ComUser u where SUBSTRING(u.socialSecurityNumber, 1,7) in (:socialSecurityNumber) and u.name in (:names) ")
    List<ComUser> findBySubSocialSecurityNumberIn(List<String> socialSecurityNumber, List<String> names);
}
