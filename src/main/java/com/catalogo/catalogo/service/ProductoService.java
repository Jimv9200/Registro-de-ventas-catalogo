package com.catalogo.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.catalogo.catalogo.dto.ProductoRequestDTO;
import com.catalogo.catalogo.dto.ProductoResponseDTO;
import com.catalogo.catalogo.exception.CategoriaNotFoundException;
import com.catalogo.catalogo.exception.ProductoNotFoundException;
import com.catalogo.catalogo.model.Producto;
import com.catalogo.catalogo.repository.CategoriaRepository;
import com.catalogo.catalogo.repository.ProductoRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    //CRUD PRODUCTO

    @Transactional
    public ProductoResponseDTO createProduct(ProductoRequestDTO request){
        return mapProduct(productoRepository.save(buildProducto(request)));
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO searchProductoByCode(String code){
        return productoRepository.findByCode(code).stream()
        .map(this::mapProduct)
        .findFirst()
        .orElseThrow(()-> new ProductoNotFoundException(code));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> searchAllProductos(){
        return productoRepository.findAll().stream()
        .map(this::mapProduct)
        .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findProductByCategory(Long id){
        return productoRepository.findByCategoryId(id).stream()
        .map(this::mapProduct)
        .toList();
        
    }

    @Transactional
    public ProductoResponseDTO updateProduct (String code, ProductoRequestDTO request){
        Producto p = productoRepository.findByCode(code).orElseThrow(()-> new ProductoNotFoundException(code));

        p.setCategory(categoriaRepository.findById(request.getCategoryId()).orElseThrow(()-> new CategoriaNotFoundException(request.getCategoryId())));
        p.setDescription(request.getDescription());
        p.setIva(request.getIva());
        p.setName(request.getName());
        p.setPrice(request.getPrice());
        p.setSku(request.getSku());
        return mapProduct(p);
    }

    @Transactional
    public void deleteProduct(String code){
        Producto p= productoRepository.findByCode(code).orElseThrow(()-> new ProductoNotFoundException(code));
        p.setActive(false);
    }

    private Producto buildProducto(ProductoRequestDTO request){
        Producto p= new Producto();
        p.setCode(request.getCode());
        p.setCategory(categoriaRepository.findById(request.getCategoryId()).orElseThrow(()-> new CategoriaNotFoundException(request.getCategoryId())));
        p.setDescription(request.getDescription());
        p.setIva(request.getIva());
        p.setName(request.getName());
        p.setPrice(request.getPrice());
        p.setSku(request.getSku());
        return p;
    }

    private ProductoResponseDTO mapProduct(Producto product){
        ProductoResponseDTO response= new ProductoResponseDTO();

        response.setCode(product.getCode());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setIdCategory(product.getCategory().getId());
        response.setIva(product.getIva());
        response.setSku(product.getSku());
        return response;
    }
}
