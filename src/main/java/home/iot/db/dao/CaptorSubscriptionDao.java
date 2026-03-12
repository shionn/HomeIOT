package home.iot.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
			@Result(property = "captor.id", column = "captor_id"), //
			@Result(property = "captorId", column = "captor_id"), //
			@Result(property = "captor.name", column = "name"), //
			@Result(property = "captor.lastValue", column = "last_value"), //
			@Result(property = "captor.type", column = "type"), //
			@Result(property = "captor.unit", column = "unit") })
	List<CaptorSubscription> listAllCaptorSubscription();

	@Select("""
			SELECT s.id, s.captor as captor_id, s.host, s.hostname, s.enable,
			c.name, c.last_value, c.type, c.unit
			FROM captor_subscription AS s
			LEFT JOIN captor AS c On c.id = s.captor
			WHERE s.captor = #{id}
			ORDER BY name
			""")
	@Results({ //
			@Result(property = "captor.id", column = "captor_id"), //
			@Result(property = "captorId", column = "captor_id"), //
			@Result(property = "captor.name", column = "name"), //
			@Result(property = "captor.lastValue", column = "last_value"), //
			@Result(property = "captor.type", column = "type"), //
			@Result(property = "captor.unit", column = "unit") })
	List<CaptorSubscription> listOneCaptor(int captorId);


	@Select("""
			SELECT s.id, s.captor as captor_id, s.host, s.hostname, s.enable,
			c.name, c.last_value, c.type, c.unit
			FROM captor_subscription AS s
			LEFT JOIN captor AS c On c.id = s.captor
			WHERE s.id = #{id}
			""")
	@Results({ //
			@Result(property = "captor.id", column = "captor_id"), //
			@Result(property = "captorId", column = "captor_id"), //
			@Result(property = "captor.name", column = "name"), //
			@Result(property = "captor.lastValue", column = "last_value"), //
			@Result(property = "captor.type", column = "type"), //
			@Result(property = "captor.unit", column = "unit") })
	CaptorSubscription readOneSubscription(int subscriptionId);


	@Insert("""
			INSERT INTO captor_subscription (captor, host, hostname)
			VALUES (#{captorId}, #{host}, #{hostname} )
			ON DUPLICATE KEY UPDATE
			hostname = #{hostname}, enable = true, updated = NOW()
			""")
	int register(CaptorSubscription subscription);

	@Delete("DELETE FROM captor_subscription WHERE id = #{id}")
	int removeSubscription(int id);


}
