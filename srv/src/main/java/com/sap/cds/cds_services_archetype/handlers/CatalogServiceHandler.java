package com.sap.cds.cds_services_archetype.handlers;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.*;
import cds.gen.catalogservice.CatalogService_;
import cds.gen.catalogservice.Books;



import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.ql.*;
import com.sap.cds.services.cds.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sap.cds.services.EventContext;
import com.sap.cds.services.*;
import com.sap.cds.CdsData;

import cds.gen.catalogservice.AddReviewContext;
import cds.gen.catalogservice.AccessDataBaseContext;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

	@After(event = CqnService.EVENT_READ)
	public void discountBooks(Stream<Books> books) {
		books.filter(b -> b.getTitle() != null && b.getStock() != null)
				.filter(b -> b.getStock() > 200)
				.forEach(b -> b.setTitle(b.getTitle() + " (discounted)"));
	}


	@Before(event = CqnService.EVENT_CREATE)
	public void beforeCreate(Stream<Books> books) {
		System.out.println("Before books");
		System.out.println("books");
		System.out.println(books);
	}

	@On(event = CqnService.EVENT_CREATE)
	public void onCreate(CdsCreateEventContext context, Books books) {
		System.out.println("On books");
		System.out.println("books");
		System.out.println(books);

		var insert = context.getCqn(); // INSERT 문 정보
		System.out.println("삽입 요청: " + insert);


//		if (books.getId() != null) {
//			books.setStock(books.getStock() + 9999);
//		}
//		PersistenceService db = PersistenceService();
//		// db에 저장
//		db.run(Insert.into("Books").entry(books));  // book: Books 객체
//		// 결과 세팅 (예시: 응답 반환용)
//		Map<String, Object> result = Map.of("ID", "101", "title", "신간 도서");
//		context.setResult(List.of(result)); // 반드시 Iterable<Map> 또는 Result 사용
	}

	@After(event = CqnService.EVENT_CREATE)
	public void afterCreate(Stream<Books> books) {
		System.out.println("After books");
		System.out.println("books");
		System.out.println(books);
	}


	@On(event = "addReview")
	public void onAddReview(AddReviewContext context) {
		// 액션 처리 로직 작성 (예: 리뷰 저장)
		// 결과 객체 생성 및 반환
		AddReviewContext.ReturnType result = AddReviewContext.ReturnType.create();
		result.setTitle("리뷰가 정상적으로 등록되었습니다.");
		System.out.println("\n\nonAddReview 호출됨요\n\n");
		context.setResult(result);  // ❗ 결과 반드시 세팅
	}


	@On(event = "accessDataBase")
	public void accessDataBase(AccessDataBaseContext context) {
		// 액션 처리 로직 작성 (예: 리뷰 저장)
		// 결과 객체 생성 및 반환
		AccessDataBaseContext.ReturnType result = AccessDataBaseContext.ReturnType.create();
		result.setResult("리뷰가 정상적으로 등록되었습니다.");
		System.out.println("\n\naccessDataBase 호출됨요\n\n");
		context.setResult(result);  // ❗ 결과 반드시 세팅
	}


}



//package service;import cds.gen.mysepackage service;
//
//import cds.gen.myservice.Products;
//import cds.gen.myservice.Products_;
//import com.sap.cds.ql.Delete;
//import com.sap.cds.ql.Insert;
//import com.sap.cds.ql.Select;
//import com.sap.cds.ql.Update;
//import com.sap.cds.ql.cqn.CqnDelete;
//import com.sap.cds.ql.cqn.CqnSelect;
//import com.sap.cds.services.ErrorStatuses;
//import com.sap.cds.services.ServiceException;
//import com.sap.cds.services.cds.*;
//import com.sap.cds.services.handler.EventHandler;
//import com.sap.cds.services.handler.annotations.On;
//import com.sap.cds.services.handler.annotations.ServiceName;
//import com.sap.cds.services.persistence.PersistenceService;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//
//@Component
//@ServiceName("MyService")
//public class ProductService  implements EventHandler {
//
//	private final PersistenceService db;
//
//	public ProductService(PersistenceService db) {
//		this.db = db;
//	}
//
//	// 조회
//	@On(entity = Products_.CDS_NAME)
//	List<Map> readProduct(CdsReadEventContext context) {
//		CqnSelect select = context.getCqn();  // 👉 요청된 CQN 쿼리
//
//		// db.run(select) 실행하면 조건에 맞는 결과만 자동 조회됨 (예: ID='P001')
//		List<Map> result = db.run(select).listOf(Map.class);
//
//		return result;
//	}
//
//	// 생성
//	@On(event = CqnService.EVENT_CREATE, entity = Products_.CDS_NAME)
//	public void onCreateProduct(CdsCreateEventContext context, List<Products> products) {
//		for (Products product : products) {
//			// 필요한 유효성 검사 또는 값 설정
//			if (product.getProductId() == null) {
//				product.setProductId(UUID.randomUUID().toString());
//			}
//
//			// db에 저장
//			db.run(Insert.into(Products_.class).entry(product));
//		}
//
//		context.setResult(products);  // 클라이언트에 결과 반환
//	}
//
//	// 수정
//	@On(event = CqnService.EVENT_UPDATE, entity = Products_.CDS_NAME)
//	public void onUpdateProduct(CdsUpdateEventContext context,Products updateProduct) {
//		db.run(Update.entity(Products_.class)
//				.data(updateProduct)
//				.where(p -> p.product_id().eq(updateProduct.getProductId()))
//		);
//		context.setResult(List.of(updateProduct));
//	}
//
//	// 삭제
//	@On(event = CqnService.EVENT_DELETE, entity = Products_.CDS_NAME)
//	public void onDeleteProduct(CdsDeleteEventContext context, Products deleteProduct) {
//		// 1️⃣ Query Parameter에서 product_id 값 가져오기
//		String productId = context.getParameter("product_id");
//
//		// 2️⃣ product_id 값이 없으면 예외 처리
//		if (productId == null || productId.isEmpty()) {
//			throw new ServiceException(ErrorStatuses.BAD_REQUEST, "Missing required parameter: product_id");
//		}
//
//		// 3️⃣ CQN DELETE 쿼리 생성
//		CqnDelete delete = Delete.from(Products_.class)
//				.where(p -> p.product_id().eq(productId));
//
//		// 4️⃣ 삭제 실행
//		db.run(delete);
//	}
//
//}
