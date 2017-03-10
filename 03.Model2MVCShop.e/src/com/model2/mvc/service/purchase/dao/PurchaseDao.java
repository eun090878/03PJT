package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDao {
	//Constructor	
	public PurchaseDao() {
	}
	
	//구매정보 입력
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao :: insertPurchase 시작 ::");
		
		Connection con = DBUtil.getConnection();
		
		String sql ="INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";

// INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, 
		//10000, 'user01', 'pay', 'user01', '010-1111', '서울시', '전화', 'del', sysdate, to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'))

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDlvyAddr());
		stmt.setString(7, purchaseVO.getDlvyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDlvyDate());
		
		stmt.executeUpdate();
		con.close();
		
		System.out.println("PurchaseDao :: insertPurchase 끝 ::");
	}
	
	//구매정보 상세조회
	public Purchase findPurchase(int tranNo) throws Exception {
		
		System.out.println("PurchaseDao :: findPurchase() 시작 ");
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT "
				+ "tran_no, prod_no, buyer_id, payment_option, "
				+ "receiver_name, receiver_phone, dlvy_addr, dlvy_request, "
				+ "dlvy_date, order_date "
				+ "FROM transaction "
				+ "WHERE tran_no='"+tranNo+"'";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
						
		Purchase purchase	 = null;
		while (rs.next()) {
			
			purchase = new Purchase();
			
			Product product = new ProductServiceImpl().getProduct(rs.getInt("prod_no"));			
			User user = new UserServiceImpl().getUser(rs.getString("buyer_id"));
			
			System.out.println("getPurchase() :: product.prod_no : " + product);
			System.out.println("getPurchase() :: user.user_id : " + user);
			
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);	
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDlvyAddr(rs.getString("dlvy_addr"));
			purchase.setDlvyRequest(rs.getString("dlvy_request"));
			purchase.setDlvyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_date"));
		
		}
		System.out.println("findPurchase () : purchase 정보:: " + purchase);
		
		rs.close();
		stmt.close();
		con.close();

		System.out.println("PurchaseDao :: getPurchase() 끝 ");
		
		return purchase;
	}
	
	//구매목록
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId)throws Exception {
		
		System.out.println("PurchaseDao :: getPurchaseList() 시작 ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT t.tran_no, t.buyer_id, t.receiver_name, t.receiver_phone, NVL(t.tran_status_code, 0) tran_status_code"
				+ "	from users u, transaction t"
				+ " where t.buyer_id='"+buyerId+ "'"
				+ " and u.user_id = t.buyer_id(+)";
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDao :: totalCount  :: " + totalCount);
		sql = makeCurrentPageSql(sql, searchVO);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
	
		List<Purchase> list = new ArrayList<Purchase>();
		
		UserService service = new UserServiceImpl();
		User user = service.getUser(buyerId);
		
		while(rs.next()){
			Purchase vo = new Purchase();
			vo.setTranNo(rs.getInt("tran_no"));
			vo.setBuyer(user);
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setTranCode(rs.getString("tran_status_code"));
			//정보수정 추가

			list.add(vo);

		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("PurchaseDao :: getPurchaseList() 끝 ");
		return map;
	}
	
	//판매목록
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	//구매정보 수정
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao :: updatePurchase() 시작 ");
		
		Connection con = DBUtil.getConnection();
		
		int tranNo = purchaseVO.getTranNo();
		System.out.println("traNo 가져와라" + tranNo);
		
		String sql = "UPDATE transaction "
				+ "SET "
				+ "payment_option=?, receiver_name=?, "
				+ "receiver_phone=?, dlvy_addr=?, "
				+ "dlvy_request=?, dlvy_date=? "
				+ "WHERE tran_no='"+tranNo+"'";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDlvyAddr());
		stmt.setString(5, purchaseVO.getDlvyRequest());
		stmt.setString(6, purchaseVO.getDlvyDate());
		
		stmt.executeUpdate();
		
		System.out.println("updatePurchase() :: purchase정보 : " + purchaseVO);

		stmt.close();
		con.close();
		
		System.out.println("PurchaseDao :: updatePurchase() 시작 ");
	}
	
	//구매상태코드 수정
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
	}
	
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
	
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
	

}
