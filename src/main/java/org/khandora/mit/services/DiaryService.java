package org.khandora.mit.services;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.model.Diary;
import org.khandora.mit.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public Double getRating(Long id, String level, String subject) {
        List<Diary> diaries = diaryRepository.findByIdAndLevelAndSubject(id, level, subject);
        double sum = diaries.stream().map(Diary::getGrade).reduce(0, Integer::sum);
        return sum / diaries.size();
    }
}
