SELECT captor, value, DATE(date) + SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600) AS date, date AS original_date FROM captor_value;

SELECT captor, value, CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600), date AS original_date FROM captor_value;

SELECT captor, value, ADDTIME(CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600)) AS date, date AS original_date FROM captor_value;

CREATE OR REPLACE VIEW captor_value_today AS
SELECT
	captor,
	ROUND(AVG(value), 2) as value,
	max(value) as max,
	min(value) as min,
	ADDTIME(CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600)) AS date,
	ADDTIME(CONVERT(DATE(date), DATETIME), SEC_TO_TIME(ROUND(TIME_TO_SEC(date) / 600)*600)) AS g_date,
	date AS original_date,
	CONVERT (
		CASE DATE(date)
			WHEN CURRENT_DATE THEN "today"
			WHEN DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY) THEN "yesterday"
			ELSE "other"
		END USING utf8mb4
	) AS type
FROM captor_value
GROUP BY captor, g_date, type;


ALTER DATABASE databasename CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE tablename CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;