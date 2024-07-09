package com.adem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adem.domain.ImageData;



public interface ImageDataRepository extends JpaRepository<ImageData, Long>{
	Optional<ImageData> findById(Long id);
}
