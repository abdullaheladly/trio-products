package com.abdullah996.trio.categories.service

import com.abdullah996.trio.categories.model.Category
import com.abdullah996.trio.categories.repo.CategoryRepo
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class CategoryService (private val categoryRepo: CategoryRepo) {

    fun getAllCategories():List<Category> = categoryRepo.findAll()

    fun addCategory(category: Category)=categoryRepo.save(category)

    fun updateCategory(category: Category) =categoryRepo.save(category)

    fun deleteCategory(id:Int) = categoryRepo.deleteById(id)

    fun getCategoryById(id: Int) : Optional<Category> = categoryRepo.findById(id)
}