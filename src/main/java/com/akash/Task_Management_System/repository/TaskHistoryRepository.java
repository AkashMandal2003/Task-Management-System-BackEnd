package com.akash.Task_Management_System.repository;

import com.akash.Task_Management_System.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory,Long> {
    List<TaskHistory> findByTaskId(Long taskId);
}
