package com.paloma.product_manager.application;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.adapters.mapper.ProductMapper;
import com.paloma.product_manager.domain.exception.ProductAlreadyExistsException;
import com.paloma.product_manager.domain.model.ProductEntity;
import com.paloma.product_manager.domain.respository.ProductModelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.paloma.product_manager.adapters.mapper.ProductMapper.converterDtoToEntity;
import static com.paloma.product_manager.adapters.mapper.ProductMapper.converterEntityToDto;

@Service
public class ProductService implements ProductServiceUseCase {

    @Autowired
    private ProductModelUseCase useCase;

    @Override
    public ProductDTO getProductById(Long id) {
        ProductEntity entity = useCase.findById(id);
        return converterEntityToDto(entity);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return useCase.findAll().stream()
                .map(ProductMapper::converterEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO newProduct) {
        String treatedProductName = treatmentString(newProduct.getName());
        Optional<ProductEntity> entity = useCase.findByName(treatedProductName);
        if (entity.isPresent()){
            throw new ProductAlreadyExistsException(entity.get().getId(), converterEntityToDto(entity.get()));
        }
        newProduct.setName(treatedProductName);
        return converterEntityToDto(useCase.create(
                converterDtoToEntity(newProduct)));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updateProduct) {
        String treatedProductName = treatmentString(updateProduct.getName());
        updateProduct.setName(treatedProductName);
        return converterEntityToDto(useCase.update(
                id, converterDtoToEntity(updateProduct)));
    }

    @Override
    public void deleteProduct(Long id) {
        useCase.delete(id);
    }

    private String treatmentString(String productName){
        String normalized = Normalizer.normalize(productName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String treatedName = pattern.matcher(normalized).replaceAll("");
        treatedName = treatedName.replaceAll("[^a-zA-Z0-9]", " ");
        treatedName = treatedName.replaceAll("\\s+", " ");
        treatedName = treatedName.trim();
        return treatedName;
    }
}
