package com.mie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mie.model.Partner;
import com.mie.util.DbUtil;

public class PartnerDao {

	private Connection connection;

	public PartnerDao() {
		connection = DbUtil.getConnection();
	}

	//insert Partner into the partners table
	public boolean addPartner(Partner partner) {
		boolean success = true;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO partners(user1,user2) VALUES (?,?)");
			// Parameters start with 1
			preparedStatement.setInt(1, partner.getUser1());
			preparedStatement.setInt(2, partner.getUser2());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	//get a list of partners who have user1 equal to the userid passed in
	public List<Partner> getPartnersByUser(int userid){
		List<Partner> partners = new ArrayList<Partner>();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from partners where user1=?");
			preparedStatement.setInt(1, userid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Partner partner = new Partner();
				partner.setUser1(rs.getInt("user1"));
				partner.setUser2(rs.getInt("user2"));
				partner.setPartnerid(rs.getInt("partnerid"));
				partners.add(partner);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return partners;
	}
	
	//delete a set of partners where the users match the user1 and user2 passed in
	public void deletePartners(int user1, int user2) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from partners where (user1=? and user2=?) or (user1=? and user2=?)");
			// Parameters start with 1
			preparedStatement.setInt(1, user1);
			preparedStatement.setInt(2, user2);
			preparedStatement.setInt(3, user2);
			preparedStatement.setInt(4, user1);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}