package org.scoutsfev.cudu.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

public class CookieInterceptor implements ClientHttpRequestInterceptor {

    private List<HttpCookie> cookies;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (cookies != null && !cookies.isEmpty()) {
            for (HttpCookie cookie : cookies) {
                request.getHeaders().add(HttpHeaders.COOKIE, cookie.toString());
            }
        }
        return execution.execute(request, body);
    }

    public void setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }
}
