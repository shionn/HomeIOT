package home.iot.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.DisposableBean;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
class SqlSession implements org.apache.ibatis.session.SqlSession, DisposableBean {

	private org.apache.ibatis.session.SqlSession session;

	public SqlSession(org.apache.ibatis.session.SqlSession session) {
		this.session = session;
	}

	@Override
	public void clearCache() {
		session.clearCache();
	}

	@Override
	public void close() {
		session.close();
	}

	@Override
	public void commit() {
		session.commit();
	}

	@Override
	public void commit(boolean force) {
		session.commit(force);
	}

	@Override
	public <T> T selectOne(String statement) {
		return session.selectOne(statement);
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		return session.selectOne(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement) {
		return session.selectList(statement);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return session.selectList(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return session.selectList(statement, parameter, rowBounds);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return session.selectMap(statement, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return session.selectMap(statement, parameter, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey,
			RowBounds rowBounds) {
		return session.selectMap(statement, parameter, mapKey, rowBounds);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement) {
		return session.selectCursor(statement);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter) {
		return session.selectCursor(statement, parameter);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
		return session.selectCursor(statement, parameter, rowBounds);
	}

	@Override
	public void select(String statement, Object parameter,
			@SuppressWarnings("rawtypes") ResultHandler handler) {
		session.select(statement, parameter, handler);
	}

	@Override
	public void select(String statement, @SuppressWarnings("rawtypes") ResultHandler handler) {
		session.select(statement, handler);
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds,
			@SuppressWarnings("rawtypes") ResultHandler handler) {
		session.select(statement, parameter, rowBounds, handler);
	}

	@Override
	public int insert(String statement) {
		return session.insert(statement);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return session.insert(statement, parameter);
	}

	@Override
	public int update(String statement) {
		return session.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return session.update(statement, parameter);
	}

	@Override
	public int delete(String statement) {
		return session.delete(statement);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return session.delete(statement, parameter);
	}


	@Override
	public void rollback() {
		session.rollback();
	}

	@Override
	public void rollback(boolean force) {
		session.rollback(force);
	}

	@Override
	public List<BatchResult> flushStatements() {
		return session.flushStatements();
	}

	@Override
	public Configuration getConfiguration() {
		return session.getConfiguration();
	}

	@Override
	public <T> T getMapper(Class<T> type) {
		return session.getMapper(type);
	}

	@Override
	public Connection getConnection() {
		return session.getConnection();
	}

	@Override
	public void destroy() throws Exception {
		try {
			session.rollback();
		} finally {
			session.close();
		}
	}

}
