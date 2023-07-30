SELECT captor, value, DATE(date) + SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600) AS date, date AS original_date FROM captor_value;

SELECT captor, value, CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600), date AS original_date FROM captor_value;

SELECT captor, value, ADDTIME(CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600)) AS date, date AS original_date FROM captor_value;

CREATE OR REPLACE VIEW captor_value_today AS
SELECT captor, value, ADDTIME(CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600)) AS date, date AS original_date FROM captor_value;
