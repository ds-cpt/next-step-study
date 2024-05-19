package next.dao;

import java.sql.PreparedStatement;

public interface PreparedStatementSetter {
	public void setValues(PreparedStatement pstmt);
}
