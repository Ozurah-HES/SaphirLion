package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
