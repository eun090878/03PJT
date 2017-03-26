package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


public class ProductDao {
	//Constructor	
	public ProductDao() {
	}

	public void insertProduct(Product productVO) throws Exception{
		System.out.println("ProductDao :: insertProduct() 시작 ");
		
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";		
		
		String[] parsing= productVO.getManuDate().split("-");
		String manuDate="";
		for(int i=0; i<parsing.length; i++)	{
			manuDate += parsing[i];
		}
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, manuDate);
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		
		stmt.executeUpdate();
		con.close();
		System.out.println("ProductDao :: insertProduct() 끝 ");
	}

	public Product findProduct(int prodNo) throws Exception{		
		
		System.out.println("ProductDao :: findProduct() 시작 ");
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
			
		}
		
		con.close();
		System.out.println("ProductDao :: findProduct() 끝 ");
		return productVO;
		
	}

	public Map<String,Object> getProductList(Search searchVO) throws Exception{
		
		System.out.println("ProductDao :: getProductList() 시작 ");
	
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT "
				+ "p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, "
				+ "p.image_file, p.reg_date, NVL(t.tran_status_code, 0) tran_status_code "
				+ "FROM product p, transaction t "
				+ "WHERE p.prod_no = t.prod_no(+)";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("") ) {
				sql += " AND prod_no like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("") ) {
				sql += " AND prod_name like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("2") && !searchVO.getSearchKeyword().equals("") ) {
				sql += " AND price like '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += " GROUP BY p.prod_name, p.prod_no, p.prod_detail, "
				+ "p.manufacture_day, p.price, p.image_file, p.reg_date, "
				+ "tran_status_code ORDER BY prod_no";

		System.out.println("ProductDAO :: Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, searchVO);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		System.out.println(searchVO);
	
		List<Product> list = new ArrayList<Product>();
		
//		String proTranCode=rs.getString("tran_status_code").trim();
		
		while(rs.next()){
<<<<<<< HEAD
			Product vo = new Product();
			vo.setProdNo(rs.getInt("prod_no"));
			vo.setProdName(rs.getString("prod_name"));
			vo.setProdDetail(rs.getString("prod_detail"));
			vo.setManuDate(rs.getString("manufacture_day"));
			vo.setPrice(rs.getInt("price"));
			vo.setFileName(rs.getString("image_file"));
			vo.setRegDate(rs.getDate("reg_date"));
			vo.setProTranCode(rs.getString("tran_status_code"));;
			list.add(vo);
=======
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tran_status_code").trim());	
			
			list.add(product);
>>>>>>> refs/heads/new/test

		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();

		System.out.println("ProductDao :: getProductList() 끝 ");

		return map;
		
	}

	public void updateProduct(Product productVO) throws Exception{
		
		System.out.println("ProductDao :: updateProduct() 시작 ");
		
		Connection con = DBUtil.getConnection();

		int prodNo = productVO.getProdNo();
		System.out.println("ProductNo 가져와라::::" + prodNo);
		String sql = "UPDATE product SET prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? WHERE prod_no='"+prodNo+"'";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		stmt.executeUpdate();
		
		con.close();
		
		System.out.println("ProductDao :: updateProduct() 끝 ");
		
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}

		// 게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
		
}
