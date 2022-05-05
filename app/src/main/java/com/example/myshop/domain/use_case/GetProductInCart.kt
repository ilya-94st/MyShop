package com.example.myshop.domain.use_case

import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.repository.ProductsRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetProductInCart @Inject constructor(private val productsRepository: ProductsRepository) {

  suspend operator fun invoke(idBuyer: String): EventClass? {
    var registerResult: EventClass? = null

    try {
      productsRepository.getProductInCart(idBuyer).addOnSuccessListener {
          task ->
        val listProductsInCart: ArrayList<ProductsInCart> = arrayListOf()
        for (document in task) {
          val product = document.toObject<ProductsInCart>()
          listProductsInCart.add(product)
        }
        registerResult = EventClass.GetProductsInCart(listProductsInCart)
      }.await()
    }catch (e: Exception) {
      registerResult =  EventClass.ErrorIn("${e.message}")
    }

    return registerResult
  }
}
