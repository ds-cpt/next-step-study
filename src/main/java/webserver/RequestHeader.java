package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {

    private static final Logger log = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers = new HashMap<>();
    private String method;
    private String uri;
    private String protocol;


    public RequestHeader(InputStream in) throws IOException {
        parseHeader(in);
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    private void parseHeader(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        if (line != null) {
            parseRequestLine(line);
            log.debug("{}", line);
            while (!line.isEmpty()) {
                line = bufferedReader.readLine();
                parseHeaderLine(line);
                log.debug("{}", line);
            }
        }

    }

    private void parseRequestLine(String line) {
        String[] tokens = line.split(" ");
        if (tokens.length >= 3) {
            method = tokens[0];
            uri = tokens[1];
            protocol = tokens[2];
        }
    }

    private void parseHeaderLine(String line) {
        String[] tokens = line.split(":");
        if (tokens.length == 2) {
            headers.put(tokens[0].trim(), tokens[1].trim());
        }
    }

}
