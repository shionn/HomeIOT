CREATE OR REPLACE VIEW captor_value_day AS
SELECT captor, 'max' AS type, MAX(value) AS value, DATE(date) AS date_c FROM captor_value GROUP BY date_c, captor
UNION
SELECT captor, 'min' AS type, MIN(value) AS value, DATE(date) AS date_c FROM captor_value GROUP BY date_c, captor;