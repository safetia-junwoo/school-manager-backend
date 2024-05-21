package com.base.boilerplate.file.domain.repository;

import com.base.boilerplate.file.domain.model.ComRelationshipFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipFileRepository extends JpaRepository<ComRelationshipFile, Integer> {
    Optional<ComRelationshipFile> findByFileGroupCode(String fileGroupCode);
    @Modifying
    @Transactional
    @Query("delete from ComRelationshipFile rf where rf.id in (:relationshipFileIds)")
    Integer deleteAllById(List<Integer> relationshipFileIds);
    @Query("select distinct rf.id from ComRelationshipFile rf join ComFile f on rf.fileGroupCode = f.fileGroupCode where rf.taskId = null and f.createDate < :threeDaysAgo")
    List<Integer> findAllByTaskIdIsNullAndCreateDate(LocalDateTime threeDaysAgo);

    @Query("SELECT cf.id FROM ComRelationshipFile crf JOIN ComUser cu ON crf.taskId = cu.id AND crf.taskName = 'user-image' JOIN ComFile cf ON crf.fileGroupCode = cf.fileGroupCode AND cu.id = :id")
    Integer findByUserImage(Integer id);
}
