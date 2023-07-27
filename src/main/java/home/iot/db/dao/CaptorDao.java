package home.iot.db.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import home.iot.db.dbo.Captor;

public interface CaptorDao {

	// void addToHistory(int id);

	@Select("SELECT * FROM captor WHERE id = #{id}")
	Captor read(int id);

	@Update("UPDATE captor SET last_value = #{value}, last_value_date = CURRENT_TIMESTAMP WHERE id = #{id}")
	void updateLastValue(@Param("id") int id, @Param("value") String value);

}
