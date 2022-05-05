package com.example.myshop.domain.use_case

import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetOrders @Inject constructor(private val productsRepository: ProductsRepository) {

   suspend  operator fun invoke(userId: String) : EventClass? {
      var registerResult: EventClass? = null

      try {
         productsRepository.getProductInOrders(userId).addOnSuccessListener {
               task ->
            val listOrders: ArrayList<ProductsInOrder> = arrayListOf()
            for (document in task) {
               val product = document.toObject<ProductsInOrder>()
               listOrders.add(product)
            }
            registerResult = EventClass.GetOrders(listOrders)
         }.await()
      }catch (e: Exception) {
         registerResult =  EventClass.ErrorIn("${e.message}")
      }

      return registerResult
   }
}

