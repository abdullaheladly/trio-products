package com.abdullah996.trio.categories.repo

import com.abdullah996.trio.categories.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepo : JpaRepository<Category,Int> {
}