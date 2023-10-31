CREATE OR REPLACE VIEW captor_value_day AS
SELECT captor, CONVERT ('max' USING utf8mb4) AS type, MAX(value) AS value, DATE(date) AS date_c FROM captor_value GROUP BY date_c, captor
UNION
SELECT captor, CONVERT ('min' USING utf8mb4) AS type, MIN(value) AS value, DATE(date) AS date_c FROM captor_value GROUP BY date_c, captor;