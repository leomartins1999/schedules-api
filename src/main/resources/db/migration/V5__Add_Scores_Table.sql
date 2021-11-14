CREATE TABLE IF NOT EXISTS score
(
    id                      SERIAL PRIMARY KEY,
    schedule_id             BIGINT NOT NULL,
    pct_overflowing_lectures FLOAT
);

-- re-trigger calculation for all schedules
UPDATE schedule
SET status = 'CALCULATING'
WHERE status = 'DONE';
