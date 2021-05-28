package org.khandora.mit.services;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.model.FileDB;
import org.khandora.mit.model.Task;
import org.khandora.mit.repository.FileDBRepository;
import org.khandora.mit.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

//import static net.alibi.projectDemo.queue.RabbitConfiguration.MY_QUEUE;

@RequiredArgsConstructor
@Service
public class FileStorageService {

//    private final RabbitTemplate rabbitTemplate;
    private final FileDBRepository fileDBRepository;
    private final TaskRepository taskRepository;


    public void store(MultipartFile file, Long taskId) throws IOException {
        Task task = taskRepository.findById(taskId).orElseThrow(RuntimeException::new);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), task);
        fileDBRepository.save(fileDB);
//        rabbitTemplate.convertAndSend(MY_QUEUE, fileDB.getId());
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Stream<FileDB> getAllFiles(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(RuntimeException::new);
        return fileDBRepository.findByTask(task).stream();
    }
}
