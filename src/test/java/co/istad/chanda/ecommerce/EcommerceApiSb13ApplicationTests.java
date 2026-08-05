package co.istad.chanda.ecommerce;

import co.istad.chanda.ecommerce.features.category.Category;
import co.istad.chanda.ecommerce.features.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EcommerceApiSb13ApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Autowired
	private CategoryRepository categoryRepository;

	// Test save data in category table

	@Test
	void testSaveCategory() {
		Category category = new Category();
		category.setName("PC");
		category.setIcon("pc-icon");
		category.setDeleted(false);
		categoryRepository.save(category);
	}

	@Test
	void testGetAllCategory() {
		categoryRepository.findAll().forEach(category -> {
			List<Category> categories = categoryRepository.findAll();
			categories.forEach(System.out::println);
			IO.println("=============");
			categories.forEach(cat -> {
				IO.println("ID => " + cat.getId());
				IO.println("Name => " + cat.getName());
				IO.println("Description => " + cat.getDescription());
				IO.println("Icon => " + cat.getIcon());
				IO.println("Deleted => " + cat.isDeleted());
			});
		});
	}

}
