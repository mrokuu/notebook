package com.example.nootebook.repository;

import com.example.nootebook.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Transactional
    Note findByUuid(String uuid);

    @Transactional
    List<Note> findAllByUserId(Long Id);

    @Transactional
    @Modifying
    @Query("delete from Note n where n.user.id = :id")
    void deleteByUserId(Long id);
}
