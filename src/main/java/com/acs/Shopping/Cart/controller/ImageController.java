package com.acs.Shopping.Cart.controller;

import com.acs.Shopping.Cart.dto.ImageDto;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Image;
import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.image.IImageService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images/image")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files ,@RequestParam Long productId){
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, imageDtos,ShoppingCartConstants.UPLOAD_SUCCESS));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, null,ShoppingCartConstants.UPDATE_SUCCESS));
            }
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, null));
    }

    @DeleteMapping("{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,null,ShoppingCartConstants.DELETE_SUCCESS));
            }
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, null));
    }
}
