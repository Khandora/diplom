package org.khandora.mit.controller;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.dto.DiaryDto;
import org.khandora.mit.model.Diary;
import org.khandora.mit.model.User;
import org.khandora.mit.repository.DiaryRepository;
import org.khandora.mit.repository.UserRepository;
import org.khandora.mit.services.DiaryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final ModelMapper modelMapper;
    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Transactional
    @GetMapping("/{id}/all")
    public List<Diary> getAllDiary(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        return diaryRepository.findByScholar(user);
    }

    @GetMapping("/{id}")
    public Diary getDiary(@PathVariable(value = "id") Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(
                () -> new RuntimeException("Error diary with id "+ diaryId +" not found"));
    }

    @PostMapping("/{id}")
    public Diary createDiary(@PathVariable(value = "id") Long id, @RequestBody DiaryDto dto) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        Diary diary = modelMapper.map(dto, Diary.class);
        diary.setScholar(user);
        return diaryRepository.save(diary);
    }

    @PostMapping("/{id}/edit")
    public Diary editDiary(@PathVariable(value = "id") Long diaryId, @RequestBody Diary diary) {
        Diary rpDiary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new RuntimeException("Error diary with id "+ diaryId +" not found"));
        rpDiary.setLevel(diary.getLevel());
        rpDiary.setSubject(diary.getSubject());
        rpDiary.setGrade(diary.getGrade());
        rpDiary.setRating(diary.getRating());

        return diaryRepository.save(rpDiary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDiary(@PathVariable (value = "id") Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new RuntimeException("Error diary with id "+ diaryId +" not found" ));
        diaryRepository.delete(diary);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/{level}/{subject}")
    public Double getRating(@PathVariable Long id, @PathVariable String level, @PathVariable String subject) {
        return diaryService.getRating(id, level, subject);
    }

}
