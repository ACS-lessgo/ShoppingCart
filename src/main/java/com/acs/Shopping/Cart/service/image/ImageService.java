package com.acs.Shopping.Cart.service.image;

import com.acs.Shopping.Cart.Repository.ImageRepository;
import com.acs.Shopping.Cart.dto.ImageDto;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.exceptions.ResourceUpdateException;
import com.acs.Shopping.Cart.model.Image;
import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.service.product.ProductService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ShoppingCartConstants.IMAGE_NOT_FOUND));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> {throw new ResourceNotFoundException(ShoppingCartConstants.IMAGE_NOT_FOUND);});
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setFileType(file.getContentType());
                image.setProduct(product);
                image.setCreatedAt(LocalDateTime.now());

                Image savedImage = imageRepository.save(image);

                // create download link after we save image in db with correct id
                savedImage.setDownloadUrl(ShoppingCartConstants.IMAGE_DOWNLOAD_URL + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setCreatedAt(LocalDateTime.now());
                savedImageDto.add(imageDto);

            }catch (IOException | SQLException e){
                throw new ResourceUpdateException(ShoppingCartConstants.IMAGE_SAVE_FAILURE + productId, e);
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long productId) {
        Image image = getImageById(productId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setUpdatedAt(LocalDateTime.now());

            imageRepository.save(image);
        }catch (IOException | SQLException e){
            throw new ResourceUpdateException(ShoppingCartConstants.IMAGE_UPDATE_FAILURE + productId, e);
        }
    }
}
