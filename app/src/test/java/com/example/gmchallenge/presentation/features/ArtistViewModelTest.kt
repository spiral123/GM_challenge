package com.example.gmchallenge.presentation.features

import app.cash.turbine.test
import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.data.network.ArtistSearchApi
import com.example.gmchallenge.data.network.NetworkArtistFetch
import com.example.utils.TestRetrofit
import com.example.utils.enqueueResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import kotlin.time.ExperimentalTime

@InternalCoroutinesApi
@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class ArtistViewModelTest {

    private val coroutineScope = TestCoroutineScope()
    private lateinit var mockNetworkArtistFetch: NetworkArtistFetch
    private lateinit var mockWebServer: MockWebServer
    private lateinit var artistSearchApi: ArtistSearchApi

    private var actualValues = mutableListOf<ArtistDataState>()

    private lateinit var sut: ArtistViewModel

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        artistSearchApi = TestRetrofit(mockWebServer).artistSearchApiFake
        mockNetworkArtistFetch = NetworkArtistFetch(artistSearchApi)

        sut = ArtistViewModel(coroutineScope, mockNetworkArtistFetch)

        actualValues.clear()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `verify sut is initialized with empty list`() {
        val expected = ArtistDataState.Success(emptyList())

        assertEquals(expected, sut.artistData.value)
    }

    @Test
    fun `verify resetArtistData emits an empty list to artistData`() {
        runBlocking {
            sut.resetArtistData()

            sut.artistData.test {
                assertEquals(0, (expectItem() as ArtistDataState.Success).data.size)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    /**
     * Something wrong with this test: works when class is run but not when full suite
     */
    @Test
    fun `verify loadArtistData first changes the state to Loading and then returns network result`() {
        mockWebServer.enqueueResponse("artist_data_3_tracks.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            sut.loadArtistData("osijfdsdoi")

            sut.artistData.test {
                assertEquals(ArtistDataState.Loading, expectItem())
                assertEquals(3, (expectItem() as ArtistDataState.Success).data.size)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `verify loadArtistData first changes the state to Loading and then returns network error`() {
        mockWebServer.enqueueResponse("empty_response.json", HttpURLConnection.HTTP_BAD_GATEWAY)

        runBlocking {
            sut.loadArtistData("osijfdsdoi")

            sut.artistData.test {
                assertEquals(ArtistDataState.Loading, expectItem())
                assertEquals(
                    "Server Error",
                    (expectItem() as ArtistDataState.Error).exception.message
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}