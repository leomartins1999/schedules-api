ALTER TABLE score
    ADD column IF NOT EXISTS pct_overqualified_rooms_for_lectures FLOAT;

-- re-trigger calculation for all schedules
UPDATE schedule
SET status = 'CALCULATING'
WHERE status = 'DONE';
