package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import home.iot.db.dbo.Captor;
import home.iot.db.dbo.CaptorValue;

public interface CaptorHistoryDao {

	@Select("SELECT * FROM captor WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "max", column = "id", one = @One(select = "readMax")) })
	Captor readCaptor(int id);

	@Select("SELECT value FROM captor_value_day WHERE captor = #{id} AND type = 'max' AND DATE(date_c) = CURRENT_DATE")
	CaptorValue readMax(int id);

	@Select("SELECT * FROM captor_value_today WHERE captor = #{id} AND DATE(date) = CURRENT_DATE ORDER BY date ASC")
	List<CaptorValue> readCurrentDay(int id);

	@Select("SELECT * FROM captor_value_today WHERE captor = #{id} AND DATE(date) = DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY) ORDER BY date ASC")
	List<CaptorValue> readYesterday(int id);

	@Select("SELECT * FROM captor_value_day "
			+ "WHERE captor = #{id} "
			+ "AND date_c BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) AND CURRENT_DATE "
			+ "AND type = #{type} "
			+ "ORDER BY date_c ASC")
	List<CaptorValue> readLastMonth(@Param("id")int id, @Param("type") String type);


}
