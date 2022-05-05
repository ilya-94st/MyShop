package com.example.myshop.domain.use_case


import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllProducts @Inject constructor(private val productsRepository: ProductsRepository) {

 suspend operator fun invoke(): EventClass? {
  var registerResult: EventClass? = null

  try {
   productsRepository.getAllProducts().addOnSuccessListener {
     task ->
    val listAllProducts: ArrayList<Products> = arrayListOf()
    for (document in task) {
     val product = document.toObject<Products>()
     listAllProducts.add(product)
    }
    registerResult = EventClass.GetAllProducts(listAllProducts)
   }.await()
  }catch (e: Exception) {
   registerResult =  EventClass.ErrorIn("${e.message}")
  }

  return registerResult
 }
}
