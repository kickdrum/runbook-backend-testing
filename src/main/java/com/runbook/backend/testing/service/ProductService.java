package com.runbook.backend.testing.service;

import com.runbook.backend.testing.dao.ProductRepository;
import com.runbook.backend.testing.dto.ProductDto;
import com.runbook.backend.testing.entity.ProductEntity;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    @Setter
    private ProductRepository productRepository;

    @Autowired
    @Setter
    private ModelMapper modelMapper;

    public ProductDto getProductByDateCreated(String date) {
        return convertToDto(productRepository.findProductEntityByDate(date));
    }

    public ProductDto getProductByName(String name) {
        productRepository.save(new ProductEntity(1,"asdasf","television"));
        return convertToDto(productRepository.findProductEntityByName(name));
    }

    public ProductEntity convertDtoToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }

    public ProductDto convertToDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }
}
