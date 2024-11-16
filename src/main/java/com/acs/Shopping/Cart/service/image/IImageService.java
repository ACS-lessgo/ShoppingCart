package com.acs.Shopping.Cart.service.image;

import com.acs.Shopping.Cart.dto.ImageDto;
import com.acs.Shopping.Cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long productId);
}
