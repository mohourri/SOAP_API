package p1;


import javax.xml.ws.Endpoint;

public class  Servlet{

    public static void main(String[] args) {
        String url = "http://localhost:8086/myWebService";
        Endpoint.publish(url, new RhServiceImpl());
        System.out.println("Service web disponible à l'adresse : " + url);
    }
}