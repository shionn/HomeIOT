package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import home.iot.db.dbo.Captor;
import home.iot.db.dbo.CaptorValue;

public interface CaptorHistoryDao {

	@Select("SELECT * FROM captor_value WHERE captor = #{id} ORDER BY date ASC")
	List<CaptorValue> read(int id);

	@Select("SELECT * FROM captor WHERE id = #{id}")
	Captor readCaptor(int id);

	@Select("SELECT * FROM captor_value_today WHERE captor = #{id} AND DATE(date) = CURRENT_DATE ORDER BY date ASC")
	List<CaptorValue> readCurrentDay(int id);

	@Select("SELECT * FROM captor_value_today WHERE captor = #{id} AND DATE(date) = DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY) ORDER BY date ASC")
	List<CaptorValue> readYesterday(int id);

}
