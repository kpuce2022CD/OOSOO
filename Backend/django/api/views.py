from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import *
import asyncio
from api.Watcha.Wishes import w_wishes
from api.Watcha.Watchings import w_watchings
from api.Watcha.AddWish import w_addwish
from api.Netflix.Wishes import n_wishes
from api.Netflix.Watchings import n_watchings
from api.Netflix.AddWish import n_addwish
from api.Wavve.Wishes import wav_wishes
from api.Wavve.Watchings import wav_watchings
from api.Wavve.AddWish import wav_addwish
from api.Tving.Watchings import t_watchings
from api.Tving.Wishes import t_wishes
from api.Tving.AddWish import t_addwish
from api.DisneyPlus.Watchings import d_watchings
from api.DisneyPlus.Wishes import d_wishes
from api.DisneyPlus.AddWish import d_addwish
from api.Sign.SignIn import signIn
from api.Sign.SignUp import signUp
from api.Sign.Interworking import Interworking
from api.Search.SearchTest import search_test
from api.Search.SearchTitle import search_title
from api.User.WishList import wishlist
from api.User.WatchingLog import watchinglog
from api.User.AddWishList import add_wishlist
from api.User.AddWatchingLog import add_watchinglog
from api.User.LoadInterworking import load_interworking
from api.User.Rating import Rating
from api.User.UserInfo import userinfo
from api.User.CallReview import CallReview
from api.User.DeleteReview import DeleteReview
from api.User.AllReview import AllReview
from api.User.LikeReview import CallReviewLike
from api.Recommend.UnseenMovies import unseen_movies
from api.Recommend.LoadAlgo import load_algo
from api.Recommend.LoadDataset import load_dataset
from api.Recommend.RecommendMovies import recommend_movie_list
from api.User.MyReview import MyReview
from api.User.MyInterworking import MyInterworking
from api.User.DeleteUser import deleteUser
from api.User.UpdateUser import updateUser
from api.Contents.contents_link import Contents_link
from api.Contents.ott_link import ott_link
from api.Contents.contents_credits import get_credits
from api.Contents.popular_contents import popular_contents
from api.Wavve.wavve_cookie import wav_login_cookie


class ContentsListAPI(APIView):
    def get(self, request):
        queryset = Contents.objects.all()
        print(queryset)
        serializer = ContentsSerializer(queryset, many=True)
        return Response(serializer.data)


class ContentsEpisodesListAPI(APIView):
    def get(self, request):
        queryset = ContentsEpisodes.objects.all()
        print(queryset)
        serializer = ContentsEpisodesSerializer(queryset, many=True)
        return Response(serializer.data)


class ContentsReviewListAPI(APIView):
    def get(self, request):
        queryset = ContentsReview.objects.all()
        print(queryset)
        serializer = ContentsReviewSerializer(queryset, many=True)
        return Response(serializer.data)


class ContentsSeasonsListAPI(APIView):
    def get(self, request):
        queryset = ContentsSeasons.objects.all()
        print(queryset)
        serializer = ContentsSeasonsSerializer(queryset, many=True)
        return Response(serializer.data)


class UserInterworkingListAPI(APIView):
    def get(self, request):
        queryset = UserInterworking.objects.all()
        print(queryset)
        serializer = UserInterworkingSerializer(queryset, many=True)
        return Response(serializer.data)


class UsersListAPI(APIView):
    def get(self, request):
        queryset = Users.objects.all()
        print(queryset)
        serializer = UsersSerializer(queryset, many=True)
        return Response(serializer.data)


class WatchingLogListAPI(APIView):
    def get(self, request):
        queryset = WatchingLog.objects.all()
        print(queryset)
        serializer = WatchingLogSerializer(queryset, many=True)
        return Response(serializer.data)


class WishListAPI(APIView):
    def get(self, request):
        queryset = WishList.objects.all()
        print(queryset)
        serializer = WishListSerializer(queryset, many=True)
        return Response(serializer.data)


class WatchaWishesListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="watcha")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = w_wishes(email, pwd, name)

        return Response(result)


class WatchaWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="watcha")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = w_watchings(email, pwd, name)
        return Response(result)


class WatchaAddWishAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="watcha")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = w_addwish(email, pwd, name, data.get('title'))
        return Response(result)


class NetflixWishesListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="netflix")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = n_wishes(email, pwd, name)
        return Response(result)


class NetflixWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="netflix")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = n_watchings(email, pwd, name)
        return Response(result)


class NetflixAddWishAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="netflix")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = n_addwish(email, pwd, name, data.get('title'))
        return Response(result)


class WavveWishesListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="wavve")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = wav_wishes(email, pwd, name)
        return Response(result)


class WavveWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="wavve")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = wav_watchings(email, pwd, name)
        return Response(result)


class WavveAddWishAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="wavve")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = wav_addwish(email, pwd, name, data.get('title'))
        return Response(result)


class TvingWishesListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="tving")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = t_wishes(email, pwd, name)
        return Response(result)


class TvingWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="tving")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = t_watchings(email, pwd, name)
        return Response(result)


class TvingAddWishAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="tving")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = t_addwish(email, pwd, name, data.get('title'), data.get('type'))
        return Response(result)


class DisneyWishesListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="disney")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = d_wishes(email, pwd, name)
        return Response(result)


class DisneyWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="disney")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = d_watchings(email, pwd, name)
        return Response(result)


class DisneyAddWishAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="disney")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        result = d_addwish(email, pwd, name, data.get('title'))
        return Response(result)


class SignInAPI(APIView):
    def post(self, request):
        data = request.data
        result = signIn(data.get('email'), data.get('pwd'))
        return Response(result)


class SignUpAPI(APIView):
    def post(self, request):
        data = request.data
        result = signUp(data.get('email'), data.get('pwd'), data.get('name'), data.get('phone_num'),
                        data.get('nickname'), data.get('gender'), data.get('birthday'), data.get('job'),
                        data.get('overview'))
        return Response(result)


class InterworkingAPI(APIView):
    def post(self, request):
        data = request.data
        result = Interworking(data.get('u_email'), data.get('platform'), data.get('id'), data.get('passwd'),
                              data.get('profile_name'))
        return Response(result)


# 검색 테스트용 API
class SearchTestAPI(APIView):
    def get(self, request):
        queryset = search_test()
        print(queryset)
        serializer = ContentsSerializer(queryset, many=True)
        return Response(serializer.data)


class SearchTitleAPI(APIView):
    def post(self, request):
        data = request.data
        result = search_title(data.get('keyword'))
        print(result)
        return Response(result)


class UserWishListAPI(APIView):
    def post(self, request):
        data = request.data
        result = wishlist(data.get('email'))
        print(result)
        return Response(result)


class UserWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data
        result = watchinglog(data.get('email'))
        print(result)
        return Response(result)


class AddWatchaWishlistAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="watcha")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        wishlist = asyncio.run(w_wishes(email, pwd, name))
        result = add_wishlist(wishlist, interworking)

        return Response(result)


class AddWatchaWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="watcha")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        log = asyncio.run(w_watchings(email, pwd, name))
        result = add_watchinglog(log, interworking)

        return Response(result)


class AddNetflixWishlistAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="netflix")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        wishlist = n_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking)

        return Response(result)


class AddNetflixWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="netflix")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        log = n_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking)

        return Response(result)


class AddWavveWishlistAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="wavve")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        wishlist = wav_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking)

        return Response(result)


class AddWavveWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.get(platform="wavve")

        email = interworking.id
        pwd = interworking.passwd
        name = interworking.profile_name

        log = wav_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking)

        return Response(result)


class AddTvingWishListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="tving")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        wishlist = t_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking[0])
        return Response(result)


class AddTvingWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="tving")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        log = t_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking[0])
        return Response(result)


class AddDisneyWishListAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="disney")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        wishlist = d_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking[0])
        return Response(result)


class AddDisneyWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="disney")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        log = d_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking[0])
        return Response(result)


class RatingAPI(APIView):
    def post(self, request):
        data = request.data
        result = Rating(data.get('c_id'), data.get('u_email'), data.get('_like'), data.get('rating'),
                        data.get('review'), data.get('_datetime'))
        return Response(result)


class UserInfoAPI(APIView):
    def post(self, request):
        data = request.data
        result = userinfo(data.get('email'))
        return Response(result)


class CallReviewAPI(APIView):
    def post(self, request):
        data = request.data
        result = CallReview(data.get('c_id'), data.get('u_email'))
        return Response(result)


class DeleteReviewAPI(APIView):
    def post(self, request):
        data = request.data
        result = DeleteReview(data.get('c_id'), data.get('u_email'))
        return Response(result)


class AllReviewAPI(APIView):
    def post(self, request):
        data = request.data
        result = AllReview(data.get('c_id'))
        return Response(result)


class LikeReviewAPI(APIView):
    def post(self, request):
        data = request.data
        result = CallReviewLike(data.get('id'), data.get('_like'), data.get('islike'))
        return Response(result)


class RecommendAPI(APIView):
    def post(self, request):
        data = request.data

        algo = load_algo()
        userId = data.get('email')
        ratings, movies = load_dataset()
        unseen = unseen_movies(userId, movies)

        recommend = recommend_movie_list(algo, userId, unseen, movies, 200)

        return Response(recommend)


class MyReviewAPI(APIView):
    def post(self, request):
        data = request.data
        result = MyReview(data.get('email'))
        return Response(result)


class MyInterworkingAPI(APIView):
    def post(self, request):
        data = request.data
        result = MyInterworking(data.get('email'))
        return Response(result)


class DeleteUserAPI(APIView):
    def post(self, request):
        data = request.data
        result = deleteUser(data.get('email'))
        return Response(result)


class UpdateUserAPI(APIView):
    def post(self, request):
        data = request.data
        result = updateUser(data.get('email'), data.get('pwd'), data.get('name'), data.get('phone_num'),
                            data.get('nickname'), data.get('gender'), data.get('birthday'), data.get('job'),
                            data.get('overview'))
        return Response(result)


class ContentsLinkAPI(APIView):
    def post(self, request):
        data = request.data
        result = Contents_link(data.get('c_id'))
        return Response(result)


class OTTLinkAPI(APIView):
    def post(self, request):
        data = request.data
        result = ott_link(data.get('email'), data.get('title'), data.get('c_type'), data.get('platform'))
        return Response(result)


class GetCreditsAPI(APIView):
    def post(self, request):
        data = request.data
        result = get_credits(data.get('c_id'))
        return Response(result)

class PopularContentsListAPI(APIView):
    def get(self, request):
        result = popular_contents()
        return Response(result)

class CookieAPI(APIView): ##플랫폼별 쿠키 테스트용
    def post(self, request):
        data = request.data
        result = wav_login_cookie(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)