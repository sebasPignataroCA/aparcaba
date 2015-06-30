package com.oreilly.springdata.mongodb.order;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

import com.oreilly.springdata.mongodb.core.AbstractDocument;
import com.oreilly.springdata.mongodb.core.Product;

public class LineItem extends AbstractDocument {

	@DBRef
	private Product product;
	private BigDecimal price;
	private int amount;

	public LineItem(Product product) {
		this(product, 1);
	}

	public LineItem(Product product, int amount) {

		Assert.notNull(product, "The given Product must not be null!");
		Assert.isTrue(amount > 0,
				"The amount of Products to be bought must be greater than 0!");

		this.product = product;
		this.amount = amount;
		this.price = product.getPrice();
	}

	public LineItem() {

	}

	public Product getProduct() {
		return product;
	}

	public int getAmount() {
		return amount;
	}

	public BigDecimal getUnitPrice() {
		return price;
	}

	public BigDecimal getTotal() {
		return price.multiply(BigDecimal.valueOf(amount));
	}
}
