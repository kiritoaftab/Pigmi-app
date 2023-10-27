package com.ot.pigmy.dto;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class AgentIdGenerator implements IdentifierGenerator {

	@SuppressWarnings("removal")
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		String prefix = "AGENT-";
		Connection connection = session.connection();

		String generatedId = "";

		Statement statement;
		try {
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("Select id as Id from agent Order By Id Desc LIMIT 1");

			if (rs.next() == false) {
				String id = prefix + new Integer(101).toString();
				return id;
			} else {
				String id = rs.getString(1);
				String[] i = id.split("-");
				int gid = Integer.parseInt(i[1]) + 1;

				generatedId += prefix + new Integer(gid).toString();
				System.out.println("Generated Id: " + generatedId);
				return generatedId;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}