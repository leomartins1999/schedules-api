ALTER TABLE score
    ADD column IF NOT EXISTS nr_used_rooms INT;

-- re-trigger calculation for all schedules
UPDATE schedule
SET status = 'CALCULATING'
WHERE status = 'DONE';
