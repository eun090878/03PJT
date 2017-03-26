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
	
	//�������� �Է�
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao :: insertPurchase ���� ::");
		
		Connection con = DBUtil.getConnection();
		
		String sql ="INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";

// INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, 
		//10000, 'user01', 'pay', 'user01', '010-1111', '�����', '��ȭ', 'del', sysdate, to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'))

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDlvyAddr());
		stmt.setString(7, purchaseVO.getDlvyRequest());
		stmt.setString(8, "1");
		stmt.setString(9, purchaseVO.getDlvyDate());
		
		stmt.executeUpdate();
		con.close();
		
		System.out.println("PurchaseDao :: insertPurchase �� ::");
	}
	
	//�������� ����ȸ
	public Purchase findPurchase(int tranNo) throws Exception {
		
		System.out.println("PurchaseDao :: findPurchase() ���� ");
		
		Connection con = DBUtil.getConnection();

/*		String sql = "SELECT "
				+ "tran_no, prod_no, buyer_id, payment_option, "
				+ "receiver_name, receiver_phone, dlvy_addr, dlvy_request, "
				+ "dlvy_date, order_date "
				+ "FROM transaction "
				+ "WHERE tran_no='"+tranNo+"'";*/
		String sql="SELECT * FROM transaction WHERE tran_no='"+tranNo+"'";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
						
		Purchase purchase	 = null;
		while (rs.next()) {
			
			purchase = new Purchase();
			
			Product product = new ProductServiceImpl().getProduct(rs.getInt("prod_no"));			
			User user = new UserServiceImpl().getUser(rs.getString("buyer_id"));
			
			System.out.println("getPurchase() :: product.prod_no : " + product.getProdNo());
			System.out.println("getPurchase() :: user.user_id : " + user.getUserId());
			
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
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			
			
		
		}
		System.out.println("findPurchase () : purchase ����:: " + purchase);
		
		rs.close();
		stmt.close();
		con.close();

		System.out.println("PurchaseDao :: getPurchase() �� ");
		
		return purchase;
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception {
		
		Purchase purchase = new Purchase();
		
		return purchase;
	}
	
	//���Ÿ��
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId)throws Exception {
		
		System.out.println("PurchaseDao :: getPurchaseList() ���� ");
		
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
			vo.setTranCode(rs.getString("tran_status_code").trim());
			//�������� �߰�

			list.add(vo);

		}
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("PurchaseDao :: getPurchaseList() �� ");
		return map;
	}
	
	//�ǸŸ��
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	//�������� ����
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao :: updatePurchase() ���� ");
		
		Connection con = DBUtil.getConnection();
		
		int tranNo = purchaseVO.getTranNo();
		System.out.println("traNo �����Ͷ�" + tranNo);
		
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
		
		System.out.println("updatePurchase() :: purchase���� : " + purchaseVO);

		stmt.close();
		con.close();
		
		System.out.println("PurchaseDao :: updatePurchase() �� ");
	}
	
	public Purchase getPurchase2(int prodNo) throws Exception {
		
		System.out.println("PurchaseDao :: getPurchase2() ���� ");
		
		 Connection con = DBUtil.getConnection();
		 
		 String sql = "SELECT * FROM transaction WHERE prod_no=?";
		 
		 PreparedStatement stmt = con.prepareStatement(sql);
		 stmt.setInt(1, prodNo);
		 
		 ResultSet rs = stmt.executeQuery();
		 
		 Purchase purchase = null;
		 
		 while(rs.next()) {
			 purchase = new Purchase();
			 
			 String userId=rs.getString("buyer_id");
			 UserService service = new UserServiceImpl();
			 User user = service.getUser(userId);
			 
			 ProductService pservice = new ProductServiceImpl();
			 Product product = pservice.getProduct(prodNo);
			 
			 purchase.setBuyer(user);
			 purchase.setDlvyAddr(rs.getString("dlvy_addr"));
			 purchase.setDlvyDate(rs.getString("dlvy_date"));
			 purchase.setDlvyRequest(rs.getString("dlvy_request"));
			 purchase.setOrderDate(rs.getDate("order_date"));
			 purchase.setPaymentOption(rs.getString("payment_option").trim());
			 purchase.setPurchaseProd(product);
			 purchase.setReceiverName(rs.getString("receiver_name"));
			 purchase.setReceiverPhone(rs.getString("receiver_phone"));
			 purchase.setTranCode(rs.getString("tran_status_code"));
			 purchase.setTranNo(rs.getInt("tran_no"));
		 }
		
		 System.out.println("purchaseDao : getPurchase2() :: purchase " + purchase);
	
		 rs.close();
		 stmt.close();
		 con.close();
		 System.out.println("purchaseDao :: getPurchase2() ��");
		 return purchase;	
	}
	
	
	
	//���Ż����ڵ� ����
	public void updateTranCode(Purchase purchase) throws Exception {
		
		System.out.println("PurchaseDao :: updateTranCode() ���� ");
		
		Connection con = DBUtil.getConnection();
//		int tranNo = purchaseVO.getTranNo();
//		System.out.println("PurchaseDao : tranNo :: " + tranNo);
		String sql ="UPDATE transaction SET tran_status_code=3 WHERE tran_no=?";
		System.out.println("updateTranCode :: getTranCode() ::" + purchase.getTranCode());
		if(purchase.getTranCode().equals( "1")) {
			sql="UPDATE transaction SET tran_status_code=2 WHERE tran_no=?";					
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getTranNo());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();

		
		System.out.println("PurchaseDao :: updateTranCode() �� ");
		
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
