package com.example.myshop.domain.use_case


import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository) {

   suspend operator fun invoke(userId: String):EventClass? {
      var registerResult: EventClass? = null

      try {
         productsRepository.getProduct(userId).addOnSuccessListener {
               task ->
            val listProducts: ArrayList<Products> = arrayListOf()
            for (document in task) {
               val product = document.toObject<Products>()
               listProducts.add(product)
            }
            registerResult = EventClass.GetProducts(listProducts)
         }.await()
      }catch (e: Exception) {
         registerResult =  EventClass.ErrorIn("${e.message}")
      }

      return registerResult
   }
}

