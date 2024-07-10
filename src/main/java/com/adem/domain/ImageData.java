package com.adem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image")
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID yerine IDENTITY kullanıyoruz
    private String id;

    private String name;

    private String type;

    private long length;

    private String filePath;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageFile imageFile; // İlişkilendirilen alanın adı imageFile olarak değiştirildi

    public ImageData(String name, String type, ImageFile imageFile) {
        this.name = name;
        this.type = type;
        this.imageFile = imageFile;
        this.length = imageFile.getData().length; // ImageFile uzunluğu imageFile'dan çekiliyor
    }
}
