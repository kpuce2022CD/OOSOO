"""Django URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""

from django.urls import path
from api.views import *

urlpatterns = [
    path('contents/', ContentsListAPI.as_view()),
    path('contents_episodes/', ContentsEpisodesListAPI.as_view()),
    path('contents_review/', ContentsReviewListAPI.as_view()),
    path('contents_seasons/', ContentsSeasonsListAPI.as_view()),
    path('user_interworking/', UserInterworkingListAPI.as_view()),
    path('users/', UsersListAPI.as_view()),
    path('watching_log/', WatchingLogListAPI.as_view()),
    path('wishlist/', WishListAPI.as_view()),

    path('w_wishes/', WatchaWishesListAPI.as_view()),
    path('w_watchings/', WatchaWatchingsListAPI.as_view()),
    path('w_addwish/', WatchaAddWishAPI.as_view()),

    path('n_wishes/', NetflixWishesListAPI.as_view()),
    path('n_watchings/', NetflixWatchingsListAPI.as_view()),
    path('n_addwish/', NetflixAddWishAPI.as_view()),

    path('wav_wishes/', WavveWishesListAPI.as_view()),
    path('wav_watchings/', WavveWatchingsListAPI.as_view()),
    path('wav_addwish/', WavveAddWishAPI.as_view()),

    path('sign_in/', SignInAPI.as_view()),
    path('sign_up/', SignUpAPI.as_view()),
    path('interworking/', InterworkingAPI.as_view()),

    path('search_test/', SearchTestAPI.as_view()),
    path('search_title/', SearchTitleAPI.as_view()),
]
