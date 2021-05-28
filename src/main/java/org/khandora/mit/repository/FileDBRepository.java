package org.khandora.mit.repository;

import org.khandora.mit.model.FileDB;
import org.khandora.mit.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
    List<FileDB> findByTask(Task task);
}
