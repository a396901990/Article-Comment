package com.dean.articlecomment.article;

import dagger.Component;

@Component(modules = ArticlePresenterModule.class)
public interface ArticleComponent {

    void inject(ArticleActivity articleActivity);
}
