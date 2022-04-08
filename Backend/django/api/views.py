from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import *
from api.Watcha.Wishes import w_wishes
from api.Watcha.Watchings import w_watchings
from api.Watcha.AddWish import w_addwish
from api.Netflix.Wishes import n_wishes
from api.Netflix.Watchings import n_watchings
from api.Netflix.AddWish import n_addwish
from api.Wavve.Wishes import wav_wishes
from api.Wavve.Watchings import wav_watchings
from api.Wavve.AddWish import wav_addwish
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


class SignInAPI(APIView):
    def post(self, request):
        data = request.data
        result = signIn(data.get('email'), data.get('pwd'))
        return Response(result)


class SignUpAPI(APIView):
    def post(self, request):
        data = request.data
        result = signUp(data.get('email'), data.get('pwd'), data.get('name'), data.get('phone_num'), data.get('nickname'), data.get('gender'), data.get('birthday'), data.get('job'), data.get('overview'))
        return Response(result)


class InterworkingAPI(APIView):
    def post(self, request):
        data = request.data
        result = Interworking(data.get('u_email'), data.get('platform'), data.get('id'), data.get('passwd'), data.get('profile_name'))
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
        interworking = load.filter(platform="watcha")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        wishlist = w_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking[0])
        return Response(result)


class AddWatchaWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="watcha")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        log = w_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking[0])
        return Response(result)


class AddNetflixWishlistAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="netflix")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        wishlist = n_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking[0])
        return Response(result)


class AddNetflixWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="netflix")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        log = n_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking[0])
        return Response(result)


class AddWavveWishlistAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="wavve")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        wishlist = wav_wishes(email, pwd, name)
        result = add_wishlist(wishlist, interworking[0])
        return Response(result)


class AddWavveWatchingLogAPI(APIView):
    def post(self, request):
        data = request.data

        load = load_interworking(data.get('email'))
        interworking = load.filter(platform="wavve")

        email = interworking[0].id
        pwd = interworking[0].passwd
        name = interworking[0].profile_name

        log = wav_watchings(email, pwd, name)
        result = add_watchinglog(log, interworking[0])
        return Response(result)

class RatingAPI(APIView):
    def post(self, request):
        data = request.data
        result = Rating(data.get('c_id'), data.get('u_email'), data.get('_like'), data.get('rating'), data.get('review'), data.get('_datetime'))
        return Response(result)