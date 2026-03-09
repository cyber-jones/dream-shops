package com.dailyproject.dreamshops.service.image;

import com.dailyproject.dreamshops.dto.ImageDto;
import com.dailyproject.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}

