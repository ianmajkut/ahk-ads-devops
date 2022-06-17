package ar.edu.utn.dds.libros;

import com.rabbitmq.client.DeliverCallback;

public class WorkerLibros {

    public static void main(String[] args) throws Exception {
        QueueFacade queue = new QueueFacade();
        queue.init(System.getenv("QUEUE_URL"));
        queue.createChanel();
        queue.sendMessage("eapepe");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received222 '" + message + "'");
            if(message.startsWith("validarLibro|")){
                String[] split = message.split("\\|");
                queue.sendMessage( "libroValidado|" + split[1]);
                try {
                    // Simulamos demora en el proceso
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        queue.addCallback(deliverCallback1);

    }

}
