  import org.apache.xmlrpc.server.PropertyHandlerMapping;
  import org.apache.xmlrpc.server.XmlRpcServer;
  import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
  import org.apache.xmlrpc.webserver.WebServer;

  public class Server {
      private static final int port = 8080;

      public static void main(String[] args) throws Exception {
          WebServer webServer = new WebServer(port);
        
          XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        
          PropertyHandlerMapping phm = new PropertyHandlerMapping();

          // provide the handler the class Adviser
          phm.addHandler("Adviser", Adviser.class);
          xmlRpcServer.setHandlerMapping(phm);
        
          XmlRpcServerConfigImpl serverConfig =
              (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
          serverConfig.setEnabledForExtensions(true);
          serverConfig.setContentLengthOptional(false);

          webServer.start();
          System.out.println("Server Running on Port: "+port);
      }
  }