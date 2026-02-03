package com.store.springboot_ecommerce.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.store.springboot_ecommerce.model.Category;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.repository.CategoryRepo;
import com.store.springboot_ecommerce.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo ;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

   //adding products
    public Product addProduct(Product product , long categoryId){
        Category category = categoryRepo.findById(categoryId) 
                            .orElseThrow(() -> new RuntimeException("category not found with id "+ categoryId));

        product.setCategory(category);  
        return productRepo.save(product);                  
        
    }




    //all product
    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }

    // all product for sepcific category
    public List<Product> getProductbyCategoryId(long categoryId){
        return productRepo.findByCategoryCategoryId(categoryId);
    }

    //delete product

    public void deleteProduct(long id){
        if(!productRepo.existsById(id)){
                throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);

    }


    // update product 
    public Product updateProduct(long id , Product productDetails){
        Product existingProduct  = productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("product not found"));

        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());

        return productRepo.save(existingProduct);
    }


    // searching 
    public List<Product> searchByName(String keyword){
        return productRepo.findByNameContainingIgnoreCase(keyword);
    }

    // filtering
    public List<Product> filterByPrice(double min , double max){
        return productRepo.findByPriceBetween(min, max);
    }



}