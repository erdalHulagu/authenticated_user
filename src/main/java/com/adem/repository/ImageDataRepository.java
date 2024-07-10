package com.adem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adem.domain.ImageFile;



public interface ImageDataRepository extends JpaRepository<ImageFile, Long>{
	Optional<ImageFile> findById(Long id);
}
