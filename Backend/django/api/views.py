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
        result = w_wishes(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)


class WatchaWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data
        result = w_watchings(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)


class WatchaAddWishAPI(APIView):
    def post(self, request):
        data = request.data
        result = w_addwish(data.get('email'), data.get('pwd'), data.get('name'), data.get('title'))
        return Response(result)


class NetflixWishesListAPI(APIView):
    def post(self, request):
        data = request.data
        result = n_wishes(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)


class NetflixWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data
        result = n_watchings(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)


class NetflixAddWishAPI(APIView):
    def post(self, request):
        data = request.data
        result = n_addwish(data.get('email'), data.get('pwd'), data.get('name'), data.get('title'))
        return Response(result)


class WavveWishesListAPI(APIView):
    def post(self, request):
        data = request.data
        result = wav_wishes(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)


class WavveWatchingsListAPI(APIView):
    def post(self, request):
        data = request.data
        result = wav_watchings(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)

class WavveAddWishAPI(APIView):
    def post(self, request):
        data = request.data
        result = wav_addwish(data.get('email'), data.get('pwd'), data.get('name'), data.get('title'))
        return Response(result)


class SignInAPI(APIView):
    def post(self, request):
        data = request.data
        result = signIn(data.get('email'), data.get('pwd'))
        return Response(result)


class SignUpAPI(APIView):
    def post(self, request):
        data = request.data
        result = signUp(data.get('email'), data.get('pwd'), data.get('name'), data.get('phone_num'), data.get('nickname'), data.get('gender'), data.get('age'), data.get('job'), data.get('overview'))
        return Response(result)


class InterworkingAPI(APIView):
    def post(self, request):
        data = request.data
        result = Interworking(data.get('u_id'), data.get('platform'), data.get('id'), data.get('passwd'), data.get('profile_name'))
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
