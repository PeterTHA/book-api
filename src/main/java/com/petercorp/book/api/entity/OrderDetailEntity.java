package com.petercorp.book.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail")
public class OrderDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_detail_id")
	private int orderDetailId;
//	@Column(name = "order_id")
//	private int orderId;
	@Column(name = "price")
	private Double price;
	@Column(name = "book_id")
	private int bookId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Cascade(CascadeType.ALL)
	private OrderEntity orderEntity;

}
