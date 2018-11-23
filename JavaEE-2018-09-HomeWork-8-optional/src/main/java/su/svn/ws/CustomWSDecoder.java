package su.svn.ws;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class CustomWSDecoder implements Decoder.Text<Object> {
    @Override
    public Object decode(String s) throws DecodeException {
        return s;
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
