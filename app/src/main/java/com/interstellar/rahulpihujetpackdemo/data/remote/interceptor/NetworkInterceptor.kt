package com.interstellar.rahulpihujetpackdemo.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor()  : Interceptor {
    
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        
        // Log request details
        Log.d("NetworkInterceptor", "🚀 Request: ${request.method} ${request.url}")
        Log.d("NetworkInterceptor", "📝 Headers: ${request.headers}")
        
        val startTime = System.currentTimeMillis()
        val response: Response = chain.proceed(request)
        val endTime = System.currentTimeMillis()
        
        // Log response details
        Log.d("NetworkInterceptor", "✅ Response: ${response.code} ${response.message}")
        Log.d("NetworkInterceptor", "⏱️ Duration: ${endTime - startTime}ms")
        
        return response
    }
}