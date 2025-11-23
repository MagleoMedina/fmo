package com.backendfmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendfmo.models.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long>{

}
