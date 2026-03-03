package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import home.iot.db.dbo.CaptorSubscription;

public interface CaptorSubscriptionDao {

	@Select("""
			SELECT s.id, s.captor as captor_id, s.host, s.hostname, s.enable,
			c.name, c.last_value, c.type, c.unit
			FROM captor_subscription AS s
			LEFT JOIN captor AS c On c.id = s.captor
			ORDER BY name
			""")
	@Results({ //
			@Result(property = "captor.name", column = "name"), //
			@Result(property = "captor.lastValue", column = "last_value"), //
			@Result(property = "captor.type", column = "type"), //
			@Result(property = "captor.unit", column = "unit") })
	List<CaptorSubscription> list();

//	@Select("""
//			SELECT *
//			FROM captor_subscription
//			""")
//	@Results({ @Result(property = "captor", column = "captor", one = @One(select = "readCaptor"))
//	})
//	List<CaptorSubscription> list();
//
//	@Select("SELECT * FROM captor WHERE id = #{id}")
//	Captor readCaptor(int id);

	@Insert("""
			INSERT INTO captor_subscription (captor, host, hostname)
			VALUES (#{captorId}, #{host}, #{hostname} )
			ON DUPLICATE KEY UPDATE
			hostname = #{hostname}, enable = true, updated = NOW()
			""")
	int register(CaptorSubscription subscription);

}
