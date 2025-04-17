package com.example.vumovies

import com.example.vumovies.data.ApiService
import com.example.vumovies.api.LoginRequest
import com.example.vumovies.api.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class ApiServiceTest {

    private val mockApi = mockk<ApiService>()

    @Test
    fun `login returns success and keypass`() = runBlocking {
        // Arrange
        val expectedKeypass = "movies"
        val request = LoginRequest("ankit", "spassword")

        coEvery { mockApi.login(request) } returns Response.success(LoginResponse(expectedKeypass))

        // Act
        val response = mockApi.login(request)

        // Assert
        assertTrue(response.isSuccessful)
        assertEquals(expectedKeypass, response.body()?.keypass)
    }

    @Test
    fun `login returns failure on invalid credentials`() = runBlocking {
        // Arrange
        val request = LoginRequest("wrong", "wrongpass")

        coEvery { mockApi.login(request) } returns Response.error(
            401,
            ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                """{"error":"Unauthorized"}"""
            )
        )

        // Act
        val response = mockApi.login(request)

        // Assert
        assertFalse(response.isSuccessful)
        assertEquals(401, response.code())
    }
}
