package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
