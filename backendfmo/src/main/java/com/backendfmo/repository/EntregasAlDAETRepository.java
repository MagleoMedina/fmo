package com.backendfmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendfmo.models.EntregasAlDAET;

import java.util.List;


@Repository
public interface EntregasAlDAETRepository extends JpaRepository<EntregasAlDAET, Long> {

    List<EntregasAlDAET> findByFmoSerial(String fmoSerial);

}
