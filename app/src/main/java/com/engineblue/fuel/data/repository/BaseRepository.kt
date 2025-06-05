package com.engineblue.fuel.data.repository

import com.engineblue.fuel.data.entity.ResponseResult
import retrofit2.Response
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger


abstract class BaseRepository(private val logger: Logger) {
    protected suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): T? {

        val result: ResponseResult<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is ResponseResult.Success ->
                data = result.data
            is ResponseResult.Error -> {
                logger.log(
                    Level.FINE,
                    getRepositoryName() + "$errorMessage & Exception - ${result.exception}"
                )
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): ResponseResult<T> {
        try {
            val response = call.invoke()
//            Log.d("LOG1**", response.headers().toString())
            if (response.isSuccessful) return ResponseResult.Success(response.body()!!)
            logger.log(
                Level.FINE,
                getRepositoryName() + "$errorMessage & Exception - ${response.body()}"
            )
            logger.log(Level.FINE, response.toString())
            return ResponseResult.Error(IOException("Ha ocurrido un error al lanzar la api - $errorMessage"))
        } catch (exception: Exception) {
            val error = exception.message ?: "Ha ocurrido un error al lanzar la api - $errorMessage"
            return ResponseResult.Error(IOException(error))
        }
    }

    abstract fun getRepositoryName(): String

}