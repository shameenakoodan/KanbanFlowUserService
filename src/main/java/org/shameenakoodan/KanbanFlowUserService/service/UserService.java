package org.shameenakoodan.KanbanFlowUserService.service;
import org.shameenakoodan.KanbanFlowUserService.dto.UserDTO;
import org.shameenakoodan.KanbanFlowUserService.model.User;

import java.util.List;

public interface UserService {
    void saveStudent(UserDTO studentDto);
    User findStudentByEmail(String email);
    List<UserDTO> findAllStudents();
}
