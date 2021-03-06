package org.example.realworldapi.infrastructure.repository;

import org.example.realworldapi.domain.model.entity.persistent.Article;
import org.example.realworldapi.domain.model.entity.persistent.ArticlesTags;
import org.example.realworldapi.domain.model.entity.persistent.ArticlesTagsKey;
import org.example.realworldapi.domain.model.entity.persistent.Tag;
import org.example.realworldapi.domain.model.repository.ArticlesTagsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class ArticlesTagsRepositoryHibernate
    extends AbstractRepositoryHibernate<ArticlesTags, ArticlesTagsKey>
    implements ArticlesTagsRepository {

  private EntityManager entityManager;

  public ArticlesTagsRepositoryHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  EntityManager getEntityManager() {
    return this.entityManager;
  }

  @Override
  Class<ArticlesTags> getEntityClass() {
    return ArticlesTags.class;
  }

  @Override
  public List<Tag> findTags(Long articleId) {
    CriteriaBuilder builder = getCriteriaBuilder();
    CriteriaQuery<ArticlesTags> criteriaQuery = getCriteriaQuery(builder);
    Root<ArticlesTags> articlesTags = getRoot(criteriaQuery);
    Path<ArticlesTagsKey> articlesTagsKey = articlesTags.get("primaryKey");
    Path<Article> article = articlesTagsKey.get("article");
    criteriaQuery.select(articlesTagsKey.get("tag"));
    criteriaQuery.where(builder.equal(article.get("id"), articleId));
    TypedQuery typedQuery = getHibernateSession().createQuery(criteriaQuery);
    return (List<Tag>) typedQuery.getResultList();
  }

  @Override
  public ArticlesTags create(ArticlesTags articlesTags) {
    return persist(articlesTags);
  }
}
