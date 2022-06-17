package ar.edu.utn.dds.libros;

import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class WorkerLibros {

    public static void main(String[] args) throws Exception {
        QueueFacade queue = new QueueFacade();
        queue.init(System.getenv("QUEUE_URL"));
        queue.createChanel(QueueFacade.CHANNEL_SOLICITAR);
        queue.createChanel(QueueFacade.CHANNEL_CONFIRMAR);

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received222 '" + message + "'");
            if(message.startsWith("validarLibro|")){
                String[] split = message.split("\\|");
                try {
                    // Simulamos demora en el proceso
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.sendMessage( QueueFacade.CHANNEL_CONFIRMAR ,"libroValidado|" + split[1]);

            }

        };
        queue.addCallback(QueueFacade.CHANNEL_SOLICITAR,deliverCallback1);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    queue.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
