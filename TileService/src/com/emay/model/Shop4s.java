package com.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


@Table("picc.s_4sshop")
public class Shop4s {
	
	/*id*/
	@Id
	@Column("shop_id")
	private Integer shop4sId;
	
	@Column("createdon")
	private Date createdon;
	
	@Column("createdby")
	private Date createdby;
	
	
	@Column("shop_address")
	private String shopAddress;
	
	@Column("shop_name")
	private String shopName;

	public Integer getShop4sId() {
		return shop4sId;
	}

	public void setShop4sId(Integer shop4sId) {
		this.shop4sId = shop4sId;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Date createdby) {
		this.createdby = createdby;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
