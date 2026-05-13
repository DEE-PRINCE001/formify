package com.honour.formify.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "answers")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;
}