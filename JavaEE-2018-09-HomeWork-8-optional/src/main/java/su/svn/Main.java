package su.svn;

import su.svn.ws.WebsocketClientEndpoint;

import java.net.URI;
import java.net.URISyntaxException;

public class Main
{
    public static boolean run = true;

    public static void main(String[] args)
    {
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(
                    new URI("ws://localhost:8080/homework8/wspublic")
            );

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // wait 5 seconds for messages from websocket
            Thread.sleep(35000);

        }
        catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
        catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
