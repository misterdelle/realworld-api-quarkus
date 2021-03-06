package org.example.realworldapi.infrastructure.repository;

import org.example.realworldapi.domain.model.entity.persistent.Article;
import org.example.realworldapi.domain.model.entity.persistent.Comment;
import org.example.realworldapi.domain.model.entity.persistent.User;
import org.example.realworldapi.domain.model.repository.CommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Optional;

@ApplicationScoped
public class CommentRepositoryHibernate extends AbstractRepositoryHibernate<Comment, Long>
    implements CommentRepository {

  private EntityManager entityManager;

  public CommentRepositoryHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Comment create(Comment comment) {
    return persist(comment);
  }

  @Override
  public Optional<Comment> findComment(String slug, Long commentId, Long authorId) {

    CriteriaBuilder builder = getCriteriaBuilder();
    CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery(builder);
    Root<Comment> comment = getRoot(criteriaQuery);

    Join<Comment, Article> article = comment.join("article");
    Join<Comment, User> author = comment.join("author");

    criteriaQuery.select(comment);
    criteriaQuery.where(
        builder.and(
            builder.equal(builder.upper(article.get("slug")), slug.toUpperCase().trim()),
            builder.equal(comment.get("id"), commentId),
            builder.equal(author.get("id"), authorId)));

    return Optional.ofNullable(getSingleResult(criteriaQuery));
  }

  @Override
  public void delete(Comment comment) {
    entityManager.remove(comment);
  }

  @Override
  EntityManager getEntityManager() {
    return this.entityManager;
  }

  @Override
  Class<Comment> getEntityClass() {
    return Comment.class;
  }
}
