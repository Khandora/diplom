package org.khandora.mit.repository;

import org.khandora.mit.model.Task;
import org.khandora.mit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    void deleteByUser(User user);
}
