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

    path('t_wishes/', TvingWishesListAPI.as_view()),
    path('t_watchings/', TvingWatchingsListAPI.as_view()),
    path('t_addwish/', TvingAddWishAPI.as_view()),

    path('sign_in/', SignInAPI.as_view()),
    path('sign_up/', SignUpAPI.as_view()),
    path('interworking/', InterworkingAPI.as_view()),

    path('search_test/', SearchTestAPI.as_view()),
    path('search_title/', SearchTitleAPI.as_view()),

    path('user_wishlist/', UserWishListAPI.as_view()),
    path('user_watchinglog/', UserWatchingLogAPI.as_view()),

    path('add_w_wishlist/', AddWatchaWishlistAPI.as_view()),
    path('add_w_watchinglog/', AddWatchaWatchingLogAPI.as_view()),
    path('add_n_wishlist/', AddNetflixWishlistAPI.as_view()),
    path('add_n_watchinglog/', AddNetflixWatchingLogAPI.as_view()),
    path('add_wav_wishlist/', AddWavveWishlistAPI.as_view()),
    path('add_wav_watchinglog/', AddWavveWatchingLogAPI.as_view()),
    path('add_t_wishlist/', AddTvingWishListAPI.as_view()),
    path('add_t_watchinglog/', AddTvingWatchingLogAPI.as_view()),


    path('rating/', RatingAPI.as_view()),
    path('userinfo/', UserInfoAPI.as_view()),
    path('delete_review/', DeleteReviewAPI.as_view()),
    path('call_review/', CallReviewAPI.as_view()),
    path('all_review/', AllReviewAPI.as_view()),
    path('like_review/', LikeReviewAPI.as_view()),
    path('my_review/', MyReviewAPI.as_view()),
    path('my_interworking/', MyInterworkingAPI.as_view()),

    path('recommend/', RecommendAPI.as_view()),
    path('delete_user/', DeleteUserAPI.as_view()),
    path('update_user/', UpdateUserAPI.as_view()),
]
