package com.adem.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.adem.domain.ImageData;

import java.util.List;
import java.util.Optional;


public interface ImageRepository extends JpaRepository<ImageData,String> {
	

	@EntityGraph(attributePaths = "id") // parametreye id değeri girildiği zaman, aynı seviyedeki datalar gelir, 
	//bağlı olduğu imageData lar gelmemiş olacak
	List<ImageData> findAll();
	
	@EntityGraph(attributePaths = "id") // imageFile ile ilgili datalar gelsin
	Optional<ImageData> findImageById(String Id);

	

}
	


