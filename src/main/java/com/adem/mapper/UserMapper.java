package com.adem.mapper;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adem.DTO.UserDTO;
import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.request.RegisterRequest;
import com.adem.request.UserRequest;



@Mapper(componentModel = "spring")
public interface UserMapper  {
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "updateAt",ignore = true)
	@Mapping(target = "roles",ignore = true)
	
	User userDTOToUser(UserDTO userDTO);
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "roles",ignore = true)
	User registerUserToUser(RegisterRequest registerRequest);
  
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "roles",ignore = true)
	@Mapping(target = "createAt",ignore = true)
	@Mapping(target = "builtIn",ignore = true)
	User userRequestToUser(UserRequest userRequest);
	

	
	UserDTO userToUserDto(User user);
	
	
	List<UserDTO> userToUserDTOList(List<User> userList);
	
	@Named("getRoleAsString")
	public static Set<String> mapRoles(Set<Role> roles) {
		
		Set<String> roleStr = new HashSet<>();
		
       roles.forEach( r-> {
			roleStr.add(r.getType().getName()); // Administrator veya Customer gözükecek
			
		}); 
		
		return roleStr;
	}
//	@Named("getImageAsLong")
//	public static Set<Image> mapping(Set<String> imageUrls) {
//	    Set<Image> images = new HashSet<>();
//	    for (String imageUrl : imageUrls) {
//	        Image image = new Image();
//	        image.setId(imageUrl); // Varsayılan olarak sadece id atanıyor
//
//	        // Diğer alanlar da varsa onları da ayarlayabilirsiniz
//	        // Örneğin:
//	        // image.setName("Image Name"); // İsim belirlenmesi gerekiyorsa
//	        // image.setType("jpg"); // Tip belirlenmesi gerekiyorsa
//	        // image.setData(yourImageData); // Resim verisi atanması gerekiyorsa
//
//	        images.add(image);
//	    }
//	    return images;
//	}
//	  
//	 // long turunde image i image turunde image e cevidik
////	 @Named("getImageAsLong")
////   public static Set<Image> map(Set<String> imageUrls) {
////		 Set<Image> images = new HashSet<>();
////	        for (String imageUrl : imageUrls) {
////	            Image image = new Image();
////	            image.setId(imageUrl);   // Eğer Image sınıfında başka alanlar varsa, diğer alanları da ayarlayabilirsiniz
////	            images.add(image);
////	        }
////	        return images;
////	 }
//	 @Named("getImageAsString")
//		public static  Set<String> getImageIds( Set<Image> imageFiles) {
//			Set<String> imgs = new HashSet<>();
//			imgs = imageFiles.stream().map(imFile->imFile.getId().
//																	toString()).
//																	collect(Collectors.toSet());
//			 return imgs;
//		}
////
////	 @Named("getImageAsStringForRequset")
////	 public static  Set<Image> getImage( Set<String> imageFiles) {
////			Set<Image> imgs = new HashSet<>();
////			imgs = imageFiles.stream().map(imFile->imFile.
////			 return imgs;
////		}
//	
}
	
