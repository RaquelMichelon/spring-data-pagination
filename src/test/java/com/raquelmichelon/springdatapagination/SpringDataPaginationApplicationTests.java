package com.raquelmichelon.springdatapagination;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class SpringDataPaginationApplicationTests {

	// STEP 5 - CREATE CONSTANTS TO REPRESENT THE POSTS BEING CALLED/SEARCHED
	static final Post POST_0 = new Post(1L, "How to use pagination with Spring Data", "@raquel");
	static final Post POST_1 = new Post(2L, "Learning Java with love", "@raquel");

	// test on the service level only excluding the controller level

	// STEP 2 -INJECT THE PostService to be tested - this is an integration test
	@Autowired
	PostService postService;

	// STEP 1 - GIVE THE TEST A METHOD NAME
	@Test
	void testListPostsPaginated() {

		// STEP 3 - TO MOUNT THE PAGEABLE OBJECT, IT WILL BE NECESSARY TO CREATE IT HERE
		// in order to do that, use PageRequest.of
		// 0 means that I want the first page and 5 means the 5 firsts items from that
		// page
		Pageable pageable = PageRequest.of(0, 2);
		Page<Post> pagePost = postService.listPost(pageable);

		// STEP 4 - verify the results - some data charge were done with the files
		// data.sql and schema.sql
		// once that initial charge of data is on the main code, the test class has this
		// code executed
		// to compare the search result, we can compare it with the script by using
		// constants (SEE STEP 5)

		// STEP 6 - VERIFY THE RESULT
		// test if the pagination is working
		assertEquals(2, pagePost.getContent().size());

		// test the returned post
		assertEquals(POST_0, pagePost.getContent().get(0));
		assertEquals(POST_1, pagePost.getContent().get(1));

		// STEP 7 - after the test has passed, run the main project and test it using a
		// rest client

	}

}
