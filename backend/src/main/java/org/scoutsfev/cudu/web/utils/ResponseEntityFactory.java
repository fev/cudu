package org.scoutsfev.cudu.web.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;;

public class ResponseEntityFactory {

    public static ResponseEntity<String> forbidden(String mensaje) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        // Accept-charset es gigante, ver http://git.io/vG79n (siguiente release)
        return new ResponseEntity<>(mensaje, headers, HttpStatus.FORBIDDEN);
    }
}
