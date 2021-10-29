package com.iscte.mei.ads.schedules.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscte.mei.ads.schedules.api.entities.Lecture;

import java.time.format.DateTimeFormatter;

public class WriteLecture {

    private final static DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final static DateTimeFormatter supportedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Unidade Curricular
    @JsonProperty("Unidade de execu��o")
    private String lecture;

    // Curso
    @JsonProperty("Curso")
    private String course;

    // Turma
    @JsonProperty("Turma")
    private String klass;

    // Turno
    @JsonProperty("Turno")
    private String shift;

    // Sala
    @JsonProperty("Sala da aula")
    private String room;

    // Day
    @JsonProperty("Dia")
    private String day;

    // Dia da semana
    @JsonProperty("Dia da Semana")
    private String dayOfTheWeek;

    // Hora de inicio
    @JsonProperty("In�cio")
    private String startTime;

    // Hora de fim
    @JsonProperty("Fim")
    private String endTime;

    // Inscritos no Turno
    @JsonProperty("Inscritos no t urno (no 1� semestre � baseado em estimativas)")
    private int signedUpForClass;

    // Lotacao
    @JsonProperty("Lota��o")
    private int maxNumberOfStudentsForRoom;

    // Caracteristicas da sala pedida para a aula
    @JsonProperty("Caracter�sticas da sala pedida para a aula")
    private String lectureRoomRequestedCharacteristics;

    // Caracteristicas da sala alocada para a aula
    @JsonProperty("Caracter�sticas reais da sala")
    private String lectureRoomActualCharacteristics;

    // Turnos com capacidade superiores as caracteristicas da sala
    @JsonProperty("Turnos com capacidade superior � capacidade das caracter�sticas das salas")
    private boolean isRoomOverqualifiedForClass;

    // Turnos com inscricoes superiores as caracteristicas da sala
    @JsonProperty("Turno com inscri��es superiores � capacidade das salas")
    private boolean shiftHasTooManyStudentsForRoom;

    public String getLecture() {
        return lecture;
    }

    public String getCourse() {
        return course;
    }

    public String getKlass() {
        return klass;
    }

    public String getShift() {
        return shift;
    }

    public String getRoom() {
        return room;
    }

    public String getDay() {
        return day == null || day.isEmpty() || day.isBlank() ? null : supportedFormatter.format(sourceFormatter.parse(day));
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public String getStartTime() {
        return startTime.isBlank() || startTime.isEmpty() ? null : startTime;
    }

    public String getEndTime() {
        return endTime.isBlank() || endTime.isEmpty() ? null : endTime;
    }

    public int getSignedUpForClass() {
        return signedUpForClass;
    }

    public int getMaxNumberOfStudentsForRoom() {
        return maxNumberOfStudentsForRoom;
    }

    public String getLectureRoomRequestedCharacteristics() {
        return lectureRoomRequestedCharacteristics;
    }

    public String getLectureRoomActualCharacteristics() {
        return lectureRoomActualCharacteristics;
    }

    public boolean isRoomOverqualifiedForClass() {
        return isRoomOverqualifiedForClass;
    }

    public boolean isShiftHasTooManyStudentsForRoom() {
        return shiftHasTooManyStudentsForRoom;
    }

    public Lecture toLecture() {
        return new Lecture(
                lecture,
                course,
                klass,
                shift,
                room,
                getDay(),
                getStartTime(),
                getEndTime(),
                signedUpForClass,
                maxNumberOfStudentsForRoom,
                lectureRoomRequestedCharacteristics,
                lectureRoomActualCharacteristics,
                isRoomOverqualifiedForClass,
                shiftHasTooManyStudentsForRoom
        );
    }
}
