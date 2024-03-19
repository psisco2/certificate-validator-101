package domain.ports.outgoing;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface HttpClientPort {
    HttpResponse sendRequest(HttpUriRequest request) throws IOException;
}