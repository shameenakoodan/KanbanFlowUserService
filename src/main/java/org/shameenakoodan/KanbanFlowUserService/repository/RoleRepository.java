package org.shameenakoodan.KanbanFlowUserService.repository;

import org.shameenakoodan.KanbanFlowUserService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{

    Role findByName(String name);
}

