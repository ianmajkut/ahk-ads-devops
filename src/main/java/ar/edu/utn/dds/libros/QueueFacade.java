package ar.edu.utn.dds.libros;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class QueueFacade {

    public static final String CHANNEL_SOLICITAR = "solicitarValidacion";
    public static final String CHANNEL_CONFIRMAR = "confirmarValidacion";

    private ConnectionFactory factory ;
    private Connection connection ;
    private Channel channel ;


    public void init(String uri) throws Exception {
        factory = new ConnectionFactory();
        factory.setUri(uri);

        //Recommended settings
        factory.setConnectionTimeout(30000);

        connection = factory.newConnection();


    }

    public void createChanel(String queue) throws IOException {
        channel = connection.createChannel();

        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
    }




    public void sendMessage(String queue,String msg) throws IOException {
        String exchangeName = "";
        channel.basicPublish(exchangeName, queue, null, msg.getBytes());

    }
    public void addCallback(String queue, DeliverCallback deliverCallback) throws IOException {
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
    }

}
