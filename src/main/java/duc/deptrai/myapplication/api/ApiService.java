package duc.deptrai.myapplication.api;

import java.util.List;

import duc.deptrai.myapplication.Model.Account1;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("todos")
    Call<List<Account1>> getListAccount(@Query("userId") int userId);
}
