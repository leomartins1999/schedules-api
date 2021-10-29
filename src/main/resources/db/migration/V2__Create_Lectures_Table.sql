CREATE TABLE IF NOT EXISTS lecture
(
    id                                     SERIAL PRIMARY KEY,
    schedule_id                            BIGINT NOT NULL,
    lecture                                VARCHAR(256),
    course                                 VARCHAR(256),
    klass                                  VARCHAR(256),
    shift                                  VARCHAR(256),
    room                                   VARCHAR(256),
    day                                    DATE,
    start_time                             TIME,
    end_time                               TIME,
    signed_up_for_class                    INTEGER,
    max_number_of_students_for_room        INTEGER,
    lecture_room_requested_characteristics VARCHAR(256),
    lecture_room_actual_characteristics    VARCHAR(256),
    is_room_overqualified_for_class        BOOLEAN,
    shift_has_too_many_students_for_room   BOOLEAN
);
