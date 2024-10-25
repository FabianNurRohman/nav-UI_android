
import com.dicoding.fundamentalsub1.data.response.DetailResponse
import com.dicoding.fundamentalsub1.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {

    @GET("events?active=1")
    fun getActiveEvents(): Call<EventResponse>

    @GET("events?active=0")
    fun getFinishedEvents(): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailResponse>

}
