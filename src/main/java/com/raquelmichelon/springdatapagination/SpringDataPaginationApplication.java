package com.raquelmichelon.springdatapagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * In this project I developed a search web service with pagination support
 * using spring boot and spring data jdbc
 */
@SpringBootApplication
public class SpringDataPaginationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataPaginationApplication.class, args);
	}

}

// STEP 1 - CREATE A RECORD TO REPRESENT THE POST ENTITY/MODEL
record Post(Long id, String content, String author) {
}

// STEP 2 - IMPLEMENT THE SEARCH FOR THE POSTS: THE REPOSITORY
// PagingAndSortingRepository is the interface used to inheriting
// the search paginated
interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}

// STEP 3 - CREATE THE SERVICE THAT WILL USE THE REPOSITORY
// it will implement the business logic - the listable posts

@Service
class PostService {

	PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	// the idea is to return a page with the posts instead of a post list
	public Page<Post> listPost(Pageable pageable) {
		// the PagingAndSortingRepository interface is waiting for this object
		return postRepository.findAll(pageable); // this will return my searching by using the pagination patterns
	}

}

// STEP 4 - CONTROLLER TO DO HTTP REQUESTS
@RestController
@RequestMapping("/posts")
class PostController {

	@Autowired
	PostService postService;

	// search method to return a post list
	// using the following sintax on the resquest
	// request: /posts?page=0&size=2
	// the spring boot can mount for me a Pageable object as parameter of the method
	@GetMapping
	public List<Post> listPosts(Pageable pageable) {
		// to get the content of the page getContent()
		return postService.listPost(pageable).getContent();
	}
}

// STEP 5 - DO AUTOMATIZADED TESTS: go to /test folder
