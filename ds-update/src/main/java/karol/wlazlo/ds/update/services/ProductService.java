package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.DetailProductItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private DetailProductItemRepository detailProductItemRepository;

    public ProductItemResponse updateProduct(ProductItem product) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            productItemRepository.save(product);
            detailProductItemRepository.save(product.getProductDetails());

            response.setProduct(productItemRepository.getById(product.getId()));
            response.setSuccessMessage("Pomyślnie zaktualizowano dane");

            return response;
        } catch (Exception ex) {
            log.warn("product.service.update.product.err {}", ex.getLocalizedMessage());
            ex.printStackTrace();
            throw new CarRentalException("msg.err.update.product");
        }
    }

    public DeleteProductItemResponse deleteProduct(Long productId) {
        DeleteProductItemResponse response = new DeleteProductItemResponse();

        try {
            productItemRepository.deleteById(productId);

            List<ProductListItem> products = productItemRepository.findAll().stream()
                    .map(item -> ProductListItem.builder()
                            .productId(item.getId())
                            .productName(item.getProductName())
                            .build())
                    .collect(Collectors.toList());

            response.setProducts(products);
            response.setSuccessMessage("Pomyślnie usunięto dane");

            return response;
        } catch (Exception ex) {
            log.warn("product.service.delete.product.err {}", ex.getLocalizedMessage());
            ex.printStackTrace();
            throw new CarRentalException("msg.err.delete.product");
        }
    }
}
