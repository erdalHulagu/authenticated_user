package com.adem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adem.DTO.ImageDTO;
import com.adem.DTOresponse.Response;
import com.adem.DTOresponse.ResponseMessage;
import com.adem.domain.Image;
import com.adem.domain.ImageData;
import com.adem.exception.ResourceNotFoundException;
import com.adem.exception.message.ErrorMessage;
import com.adem.repository.ImageDataRepository;
import com.adem.repository.ImageRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

	   private  ImageRepository imageRepository;

	   

		@Autowired
		public ImageService(ImageRepository imageRepository,  ImageDataRepository fileDataRepository) {

			this.imageRepository = imageRepository;
			this.imageRepository=imageRepository;
		}

		private final String folder_path="C:\\Users\\user\\Pictures\\Saved Pictures";
		
		public String uploadImage(MultipartFile file) throws IOException {


			Image image = null;
			// name kısımı
			String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
			
			try {
				ImageData imData= new ImageData(file.getBytes());
				image = new Image(fileName, file.getContentType(), imData);
				
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			
			imageRepository.save(image);
			
			return image.getId();

			
		}

 
//--------------------------------------------------------------------------------------------
//   
//    public byte[] getImage(String id) {
//        Image dbImageData = imageRepository.findImageById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,true)));
//        byte[] images = ImageUtils.decompressImage(dbImageData.getData());
//        return images;
//    }
//--------------------------------------------------------------------------------------------
    public void removeById(String id) {
    	Image image =  getImageById(id);
		 imageRepository.delete(image);
	}
  //--------------------------------------------------------------------------------------------
    
	public Image getImageById(String id) {
		Image image= imageRepository.findById(id).orElseThrow(()->
                                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,true)));

		return image;
		
	}


//-----------------------------------------------------------------------------------------------

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=folder_path+file.getOriginalFilename();

        Image image=imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (image != null) {
            return new Response(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true) + filePath;
        }
        return null;
    }
//------------------------------------------------------------------------------------------------
    public byte[] downloadImageFromFileSystem(String id) throws IOException {
        Optional<Image> fileData = imageRepository.findImageById(id);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
  //*****************************************
	
  	public List<ImageDTO> getAllImages() {
  		
  		List<Image > images =  imageRepository.findAll();
  		
  		 List<ImageDTO> imageDTOs =images.stream().map(imFile->{
  			//URI oluşturmamız gerekiyor
  			     String imageUri =  ServletUriComponentsBuilder
  			    		 .fromCurrentContextPath()
  			    		 .path("/files/download/")
  			    		 .path(imFile.getId()).toUriString();// end-point sonuna image id eklendi   --> localhost:8080/files/download/3
  			     
  			     return new ImageDTO(imFile.getName(), imageUri,imFile.getType(),imFile.getLength());
  		}).collect(Collectors.toList());
  		 
  		 return imageDTOs;
  	}


	



}
