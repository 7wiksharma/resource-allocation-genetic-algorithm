package com.resource.Repository;

import com.resource.entity.Microservice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroserviceRepository extends JpaRepository<Microservice, Long> {
}
