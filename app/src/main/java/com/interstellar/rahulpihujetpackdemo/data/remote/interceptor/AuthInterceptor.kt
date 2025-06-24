package com.interstellar.rahulpihujetpackdemo.data.remote.interceptor


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    // Inject token manager when needed
    // private val tokenManager: TokenManager
) : Interceptor {
    
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Add auth token if available
        val requestBuilder = originalRequest.newBuilder()
        
        // Add common headers
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.addHeader("token", "1234567890")

        // Add auth token for authenticated requests
        // val token = tokenManager.getToken()
        // if (!token.isNullOrEmpty() && !originalRequest.url.pathSegments.contains("login")) {
        //     requestBuilder.addHeader("Authorization", "Bearer $token")
        // }
        
        return chain.proceed(requestBuilder.build())
    }
}