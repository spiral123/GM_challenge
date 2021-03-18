package com.example.gmchallenge.data.network

import com.example.gmchallenge.data.models.ArtistDataState
import com.example.utils.TestRetrofit
import com.example.utils.enqueueResponse
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection


class NetworkArtistFetchTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artistSearchApi: ArtistSearchApi

    private lateinit var sut: NetworkArtistFetch

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        artistSearchApi = TestRetrofit(mockWebServer).artistSearchApiFake

        sut = NetworkArtistFetch(artistSearchApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `verify good call returns expected result`() {
        mockWebServer.enqueueResponse("artist_data_3_tracks.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val actual = sut.fetchArtistData("whatever")

            assertTrue(actual is ArtistDataState.Success)
            assertEquals(3, (actual as ArtistDataState.Success).data.size)
        }
    }

    @Test
    fun `verify no tracks returns meaningful error`() {
        mockWebServer.enqueueResponse("artist_data_no_tracks.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val actual = sut.fetchArtistData("whatever")

            assertTrue(actual is ArtistDataState.Error)
            assertEquals(
                "No tracks for whatever",
                (actual as ArtistDataState.Error).exception.message
            )
        }
    }

    @Test
    fun `verify bad network response returns meaningful error`() {
        mockWebServer.enqueueResponse("empty_response.json", HttpURLConnection.HTTP_BAD_GATEWAY)

        runBlocking {
            val actual = sut.fetchArtistData("whatever")

            assertTrue(actual is ArtistDataState.Error)
            assertEquals("Server Error", (actual as ArtistDataState.Error).exception.message)
        }
    }

}