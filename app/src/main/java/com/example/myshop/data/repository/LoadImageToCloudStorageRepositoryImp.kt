package com.example.myshop.data.repository

import android.net.Uri
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoadImageToCloudStorageRepositoryImp @Inject constructor(): LoadImageToCloudStorageRepository {
    override suspend fun upLoadImageToCloudStorage(
        userId: String,
        imageFileUri: Uri?,
        constantsImages: String
    ): String {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            "${constantsImages}/${userId}"
        )
        sRef.putFile(imageFileUri!!).await()
        val url = sRef.downloadUrl.await()
        return url.toString()
    }
}