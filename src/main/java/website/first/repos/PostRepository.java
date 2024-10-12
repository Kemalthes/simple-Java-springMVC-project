package website.first.repos;

import org.springframework.data.repository.CrudRepository;
import website.first.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
