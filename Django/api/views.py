from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView
from .models import Movie
from .serializers import MovieSerializer

class MovieListAPI(APIView):
    def get(self, request):
        queryset = Movie.objects.all()
        print(queryset)
        serializer = MovieSerializer(queryset, many=True)
        return Response(serializer.data)

