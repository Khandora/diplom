package org.khandora.mit.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.stereotype.Component;

//import static net.alibi.projectDemo.queue.RabbitConfiguration.MY_QUEUE;

@RequiredArgsConstructor
@EnableRabbit
@Component
public class FileMessageListener {

//    private final FileDBRepository fileDBRepository;
//
//    @RabbitListener(queues = MY_QUEUE)
//    public void receiveMessage(String massage) {
//        FileDB fileDB = fileDBRepository.findById(massage).orElseThrow(RuntimeException::new);
//        fileDB.setStatus(Status.ACTIVE);
//        fileDBRepository.save(fileDB);
//    }
}
