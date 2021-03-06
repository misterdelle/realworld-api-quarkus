package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.persistent.Article;
import org.example.realworldapi.domain.model.entity.persistent.UsersFollowers;
import org.example.realworldapi.domain.model.entity.persistent.UsersFollowersKey;

import java.util.List;

public interface UsersFollowersRepository {

  boolean isFollowing(Long currentUserId, Long followerUserId);

  UsersFollowers findByKey(UsersFollowersKey primaryKey);

  UsersFollowers insertOrUpdate(UsersFollowers usersFollowers);

  void delete(UsersFollowers usersFollowers);

  List<Article> findMostRecentArticles(Long loggedUserId, int offset, int limit);

  int count(Long userId);
}
