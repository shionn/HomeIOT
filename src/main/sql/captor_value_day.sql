CREATE OR REPLACE VIEW captor_value_day AS
SELECT captor, MAX(value), DATE(date) AS date FROM captor_value GROUP BY date;
