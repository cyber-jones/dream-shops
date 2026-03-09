package com.dailyproject.dreamshops.dto;


import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
