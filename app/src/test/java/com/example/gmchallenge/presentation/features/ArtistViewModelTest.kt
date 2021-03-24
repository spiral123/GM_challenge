package com.example.gmchallenge.presentation.features

import app.cash.turbine.test
import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.data.network.ArtistSearchApi
import com.example.gmchallenge.data.network.NetworkArtistFetch
import com.example.utils.TestRetrofit
import com.example.utils.enqueueResponse
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
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

    private lateinit var sut: ArtistViewModel

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        artistSearchApi = TestRetrofit(mockWebServer).artistSearchApiFake
        mockNetworkArtistFetch = NetworkArtistFetch(artistSearchApi)

        sut = ArtistViewModel(coroutineScope, mockNetworkArtistFetch)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `verify sut is initialized with empty list`() {
        runBlocking {
            sut.artistData.test {
                // initial state
                assertEquals(ArtistDataState.Success(emptyList()), expectItem())

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `verify loadArtistData changes the state to Loading and then returns network result`() {
        mockWebServer.enqueueResponse("artist_data_3_tracks.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            sut.artistData.test {
                // initial state
                assertEquals(ArtistDataState.Success(emptyList()), expectItem())

                sut.loadArtistData("osijfdsdoi")
                assertEquals(ArtistDataState.Loading, expectItem())
                assertEquals(3, (expectItem() as ArtistDataState.Success).data.size)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `verify loadArtistData changes the state to Loading and then returns network error`() {
        mockWebServer.enqueueResponse("empty_response.json", HttpURLConnection.HTTP_BAD_GATEWAY)

        runBlocking {
            sut.artistData.test {
                // initial state
                assertEquals(ArtistDataState.Success(emptyList()), expectItem())

                sut.loadArtistData("osijfdsdoi")
                assertEquals(ArtistDataState.Loading, expectItem())
                assertEquals(
                    "Server Error", (expectItem() as ArtistDataState.Error).exception.message
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `verify resetArtistData emits an empty list to artistData`() {
        mockWebServer.enqueueResponse("artist_data_3_tracks.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            sut.artistData.test {
                // initial state
                assertEquals(ArtistDataState.Success(emptyList()), expectItem())

                sut.loadArtistData("osijfdsdoi")
                assertEquals(ArtistDataState.Loading, expectItem())
                assertEquals(3, (expectItem() as ArtistDataState.Success).data.size)

                sut.resetArtistData()
                assertEquals(0, (expectItem() as ArtistDataState.Success).data.size)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `verify sort by release date`() {
        mockWebServer.enqueueResponse("artist_data_3_tracks.json", HttpURLConnection.HTTP_OK)

        val expectedOrder = arrayOf(192688540L, 192688675L, 251002654L)

        runBlocking {
            sut.artistData.test {
                // initial state
                assertEquals(ArtistDataState.Success(emptyList()), expectItem())

                sut.sortArtistData("osijfdsdoi")
                assertEquals(ArtistDataState.Loading, expectItem())

                val actual = (expectItem() as ArtistDataState.Success).data
                assertEquals(3, actual.size)

                val actualOrder = actual.map { it.trackId }.toTypedArray()
                assertTrue(expectedOrder.contentEquals(actualOrder))

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}