CREATE OR REPLACE VIEW captor_value_day AS
SELECT cv.captor, c.name, CONVERT ('max' USING utf8mb4) AS type, MAX(cv.value) AS value, DATE(cv.date) AS date_c 
	FROM captor_value AS cv
	LEFT JOIN captor AS c ON c.id = cv.captor
	GROUP BY date_c, captor
UNION
SELECT cv.captor, c.name, CONVERT ('min' USING utf8mb4) AS type, MIN(cv.value) AS value, DATE(cv.date) AS date_c 
	FROM captor_value AS cv
	LEFT JOIN captor AS c ON c.id = cv.captor
	GROUP BY date_c, captor;<