package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import home.iot.db.dbo.CaptorValue;

public interface CaptorHistoryDao {

	@Select("SELECT * FROM captor_value WHERE captor = #{id} ORDER BY date ASC")
	List<CaptorValue> read(int id);

}
