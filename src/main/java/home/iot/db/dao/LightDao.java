package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import home.iot.db.dbo.Captor;
import home.iot.db.dbo.Light;

public interface LightDao {

	@Select("SELECT name, position FROM captor WHERE type = 'LIGHT_STATE' ORDER BY position, name")
	@Results({ @Result(column = "name", property = "name"),
			@Result(column = "name", property = "captors", many = @Many(select = "readCaptors")) })
	List<Light> list();

	@Select("SELECT * FROM captor WHERE name = #{name}")
	List<Captor> readCaptors(String name);

}
