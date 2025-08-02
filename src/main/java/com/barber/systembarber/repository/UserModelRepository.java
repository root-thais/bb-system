package com.barber.systembarber.repository;

import com.barber.systembarber.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
}
