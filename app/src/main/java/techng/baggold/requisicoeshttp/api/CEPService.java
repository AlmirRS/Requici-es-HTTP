package techng.baggold.requisicoeshttp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import techng.baggold.requisicoeshttp.model.CEP;

public interface CEPService {

    @GET("01001000/json/")
    Call<CEP> recuperarCCEP();
}
