package com.abdullah996.trio.categories.controller

import com.abdullah996.trio.categories.exception.CategoryNotFoundException
import com.abdullah996.trio.categories.model.Category
import com.abdullah996.trio.categories.service.CategoryService
import com.abdullah996.trio.generic.GenericResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
class CategoryController (private val categoryService: CategoryService) {

    @GetMapping("/categories")
    fun retrieveAllCategories():GenericResponse<List<Category>>{
        return GenericResponse<List<Category>>( data = categoryService.getAllCategories(),status = HttpStatus.OK.reasonPhrase)
    }

    @GetMapping("/categories/{id}")
    fun retrieveCategoryById(@PathVariable id:Int):GenericResponse<Category>{
        val category=categoryService.getCategoryById(id)
        if (category.isPresent){
            return GenericResponse<Category>( data = category.get(),status = HttpStatus.OK.reasonPhrase)
        }else{
            throw CategoryNotFoundException("category with id = $id not exist")
        }
    }



    @PostMapping("/categories")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    fun addCategory( @Valid @RequestBody category: Category):GenericResponse<Any>{
        categoryService.addCategory(category)
        return GenericResponse<Any>(status = HttpStatus.CREATED.reasonPhrase)
    }

    @PutMapping("/categories/{id}")
    fun updateCategory(@PathVariable id:Int,@Valid @RequestBody category: Category):GenericResponse<Category>{
        val updatedCategory=category.copy(id = id)
        categoryService.updateCategory(updatedCategory)
        return GenericResponse<Category>( data = updatedCategory,status = HttpStatus.OK.reasonPhrase)

    }

    @DeleteMapping("/categories/{id}")
    fun deleteCategory(@PathVariable id: Int):GenericResponse<String>{
        categoryService.deleteCategory(id)
        return GenericResponse<String>(status = HttpStatus.OK.reasonPhrase, message = "Category Deleted Successfully")
    }
}