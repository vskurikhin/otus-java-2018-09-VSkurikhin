package su.svn.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WebsocketClientEndpoint extends Endpoint
{
    public static final String url = "http://localhost:8080/homework8/";
    private static final Logger LOGGER = LogManager.getLogger(WebsocketClientEndpoint.class.getName());
    private String sessionCookie = null;

    class Config extends ClientEndpointConfig.Configurator
    {
        @Override
        public void beforeRequest(Map<String, List<String>> headers)
        {
            headers.put("Pragma", Collections.singletonList("no-cache"));
            headers.put("Origin", Collections.singletonList("http://localhost:8080"));
            headers.put("Accept-Encoding", Collections.singletonList("gzip, deflate, br"));
            headers.put("Accept-Language", Collections.singletonList("en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4"));
            headers.put("User-Agent", Collections.singletonList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"));
            headers.put("Upgrade", Collections.singletonList("websocket"));
            headers.put("Cache-Control", Collections.singletonList("no-cache"));
            headers.put("Connection", Collections.singletonList("Upgrade"));
            headers.put("Sec-WebSocket-Version", Collections.singletonList("13"));
            if (null != sessionCookie) {
                headers.put("Cookie", Collections.singletonList(sessionCookie));
            }
        }

        @Override
        public void afterResponse(HandshakeResponse hr)
        {
            Map<String, List<String>> headers = hr.getHeaders();
            LOGGER.info("headers -> " + headers);
        }
    }

    Session userSession = null;
    private Handler messageHandler;

    public WebsocketClientEndpoint()
    {
        super();
    }

    public WebsocketClientEndpoint(URI endpointURI)
    {
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setConnectTimeout(900);
            String cookie = connection.getHeaderField("Set-Cookie");
            String[] cookies = cookie.split(";");

            for (String c : cookies) {
                if (c.length() > 10 && "JSESSIONID=".equals(c.substring(0, 11))) {
                    sessionCookie = c;
                }
            }
            System.out.println("sessionCookie = " + sessionCookie);

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ClientEndpointConfig.Builder builder = ClientEndpointConfig.Builder
                     .create()
                     .encoders(Collections.singletonList(CustomWSEncoder.class))
                     .decoders(Collections.singletonList(CustomWSDecoder.class))
                     .configurator(new Config());
            container.connectToServer(this, builder.build(), endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @Override
    public void onOpen(Session userSession, EndpointConfig config)
    {
        System.out.println("opening websocket");
        this.userSession = userSession;

        userSession.addMessageHandler((MessageHandler.Whole<String>) this::handleMessage);
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    public void handleMessage(final String message)
    {
        System.out.println("message = " + message);
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    @Override
    public void onError(Session session, Throwable thr)
    {
        System.out.println("Error = " + thr);
        thr.printStackTrace();
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(Handler msgHandler)
    {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message)
    {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface Handler
    {
        public void handleMessage(String message);
    }
}
