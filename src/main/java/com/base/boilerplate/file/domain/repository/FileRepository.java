package com.base.boilerplate.file.domain.repository;

import com.base.boilerplate.file.domain.model.ComFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<ComFile, Integer> {
    @Query("select f from ComFile f where f.fileGroupCode in (select rf.fileGroupCode from ComRelationshipFile rf where rf.taskId = :taskId and rf.taskName = :taskName)")
    List<ComFile> findAllByTaskIdAndTaskName(Integer taskId, String taskName);

    @Transactional
    @Modifying
    @Query("delete from ComFile cf where cf.id in (:fileIds)")
    Integer deleteByIds(List<Integer> fileIds);
    @Query("select f.id from ComRelationshipFile rf join ComFile f on rf.fileGroupCode = f.fileGroupCode where rf.taskId = null and f.createDate < :threeDaysAgo")
    List<Integer> findAllByTaskIdIsNullAndCreateDate(LocalDateTime threeDaysAgo);
//    @Query("SELECT f from ComFile f WHERE f.comCode = :codeId and f.dataId = :dataId")
//    List<ComFile> findAllByCodeIdAndDataId(@Param("codeId") ComCode codeId, @Param("dataId") Integer dataId);
}
