package com.adem.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adem.DTO.BookDTO;
import com.adem.domain.Book;
import com.adem.domain.ImageData;

@Mapper(componentModel = "spring")
public interface BookMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "imageOfBook", ignore = true)
	Book bookDTOToBook(BookDTO bookDTO);
	
	
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "imageOfBook", target = "imageOfBook" , qualifiedByName ="getImageAsString")
//	@Mapping(target = "imageOfBook", ignore = true)
	BookDTO bookToBookDTO(Book  book);
	
	
	@Named("getImageAsString")
	public static  Set<String> getImageIds( Set<ImageData> imageFiles) {
		Set<String> imgs = new HashSet<>();
		imgs = imageFiles.stream().map(imFile->imFile.getId().
																toString()).
																collect(Collectors.toSet());
		 return imgs;
	}
	
	}


