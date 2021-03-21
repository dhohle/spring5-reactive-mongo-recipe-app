package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    private Category CATEGORY;

    private String id;
    private String descriptionTest = "description";
    @Before
    public void setUp() throws Exception{
        this.categoryReactiveRepository.deleteAll().block();
        CATEGORY = new Category();
        CATEGORY.setDescription(descriptionTest);
//        final Mono<Category> result = categoryReactiveRepository.save(CATEGORY);
//        this.id = result.block().getId();
        this.id = categoryReactiveRepository.save(CATEGORY).block().getId();

    }

    @Test
    public void testGetOneCategory(){
        final Mono<Category> categoryMono = this.categoryReactiveRepository.findById(id);
        Assertions.assertThat(categoryMono.block()).isNotNull();

    }

    @Test
    public void testGetAllDocuments(){
        final Flux<Category> categoryFlux = this.categoryReactiveRepository.findAll();
        Assertions.assertThat(categoryFlux.count().block()).isEqualTo(1L);
    }

    @Test
    public void testFindByDescription(){
        final Mono<Category> categoryMono = this.categoryReactiveRepository.findByDescription(descriptionTest);
        Assertions.assertThat(categoryMono.block()).isNotNull();
    }

//    @Test
//    public void testFindByDescriptionNPE(){
//        final Mono<Category> categoryMono = this.categoryReactiveRepository.findByDescription("Fail"+descriptionTest);
//        Assertions.assertThat(categoryMono.block()).isNotNull();
//    }



}
