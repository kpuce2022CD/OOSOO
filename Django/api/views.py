from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import *
from api.Watcha.Watcha_Wishes import w_wishes


class ContentsListAPI(APIView):
    def get(self, request):
        queryset = Contents.objects.all()
        print(queryset)
        serializer = ContentsSerializer(queryset, many=True)
        return Response(serializer.data)


class ContentsReviewListAPI(APIView):
    def get(self, request):
        queryset = ContentsReview.objects.all()
        print(queryset)
        serializer = ContentsReviewSerializer(queryset, many=True)
        return Response(serializer.data)


class InterworkingListAPI(APIView):
    def get(self, request):
        queryset = Interworking.objects.all()
        print(queryset)
        serializer = InterworkingSerializer(queryset, many=True)
        return Response(serializer.data)


class JjimListAPI(APIView):
    def get(self, request):
        queryset = Jjim.objects.all()
        print(queryset)
        serializer = JjimSerializer(queryset, many=True)
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


class WatchaWishesListAPI(APIView):
    def post(self, request):
        data = request.data
        result = w_wishes(data.get('email'), data.get('pwd'), data.get('name'))
        return Response(result)
