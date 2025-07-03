package com.resource.Repository;

import com.resource.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository  extends JpaRepository<Resource, Long> {
}
