package cn.itcast.estore.domain;

public class OrderItem {

	private String order_id; // 订单号
	private String product_id; // 商品号
	private int buynum; // 购买数量

	// 要查询订单中商品信息时，可以将商品信息封装到OrderItem中.
	private String name;
	private double price;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public int getBuynum() {
		return buynum;
	}

	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
